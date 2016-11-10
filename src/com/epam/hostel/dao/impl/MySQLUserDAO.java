package com.epam.hostel.dao.impl;


import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;

public class MySQLUserDAO implements UserDAO{

	private static final Logger LOGGER = Logger.getRootLogger();

	private static final String INSERT_USER_QUERY = "INSERT INTO `clients` " +
			"(`login`, `password`, `id_passport`) " +
			"VALUES (?, ?, ?)";

	private static final String UPDATE_USER_QUERY = "UPDATE `clients` " +
			"SET `login` = ?, `password` = ?, `id_passport` = ? " +
			"WHERE `id_client` = ?";

	private static final String SELECT_USER_BY_LOGIN_QUERY = "SELECT * FROM `clients` WHERE `login` = ? ";

	private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM `clients` WHERE `id_client` = ? ";

	@Override
	public void insert(User user) throws DAOException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = connectionPool.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_USER_QUERY);

			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setInt(3, user.getPassport().getId());


			preparedStatement.executeUpdate();
		} catch (InterruptedException | ConnectionPoolException e) {
			throw new DAOException("Cannot get a connection from Connection Pool", e);
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot insert user", e);
		} finally {
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					throw new DAOException("Cannot free a connection from Connection Pool", e);
				}
			}
		}
	}

	@Override
	public void update(User user) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = connectionPool.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);

			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setInt(3, user.getPassport().getId());
			preparedStatement.setInt(4, user.getId());

			preparedStatement.executeUpdate();
		} catch (InterruptedException | ConnectionPoolException e) {
			throw new DAOException("Cannot get a connection from Connection Pool", e);
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot update user", e);
		} finally {
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					throw new DAOException("Cannot free a connection from Connection Pool", e);
				}
			}
		}
	}

	@Override
	public User findByLogin(String login) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try{
			connection = connectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_QUERY);

			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				user = new User();
				user.setId(resultSet.getInt(1));
				user.setLogin(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
			}

			return user;
		} catch (InterruptedException | ConnectionPoolException e) {
			throw new DAOException("Cannot get a connection from Connection Pool", e);
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot find user by login", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					throw new DAOException("Cannot free a connection from Connection Pool", e);
				}
			}
		}
	}

	@Override
	public User findById(int id) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try{
			connection = connectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY);

			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				user = new User();
				Passport passport = new Passport();
				passport.setId(resultSet.getInt(4));
				user.setPassport(passport);
				user.setId(resultSet.getInt(1));
				user.setLogin(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
				user.setBanned(resultSet.getBoolean(5));
				user.setVisitsNumber(resultSet.getInt(6));
			}

			return user;
		} catch (InterruptedException | ConnectionPoolException e) {
			throw new DAOException("Cannot get a connection from Connection Pool", e);
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot find user by id", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					throw new DAOException("Cannot free a connection from Connection Pool", e);
				}
			}
		}
	}
}
