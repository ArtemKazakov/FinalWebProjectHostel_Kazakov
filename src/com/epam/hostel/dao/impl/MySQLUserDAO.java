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
			"SET `login` = ?, `password` = ? " +
			"WHERE `id_client` = ?";

	private static final String SELECT_ADMINISTRATOR_BY_LOGIN_QUERY = "SELECT * FROM `administrators` WHERE `login` = ? ";

	private static final String SELECT_CLIENT_BY_LOGIN_QUERY = "SELECT * FROM `clients` WHERE `login` = ? ";

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
			LOGGER.error("Can not get connection from connection pool");
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot insert user", e);
		} finally {
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					LOGGER.error("Can not free connection from connection pool");
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
			preparedStatement.setInt(3, user.getId());

			preparedStatement.executeUpdate();
		} catch (InterruptedException | ConnectionPoolException e) {
			LOGGER.error("Can not get connection from connection pool");
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot update user", e);
		} finally {
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					LOGGER.error("Can not free connection from connection pool");
				}
			}
		}
	}

	@Override
	public User findByLogin(String login) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement preparedStatementAdministrator = null;
		ResultSet resultSetAdministrator = null;
		PreparedStatement preparedStatementClient = null;
		ResultSet resultSetClient = null;
		User user = null;
		try{
			connection = connectionPool.getConnection();
			preparedStatementAdministrator = connection.prepareStatement(SELECT_ADMINISTRATOR_BY_LOGIN_QUERY);

			preparedStatementAdministrator.setString(1, login);
			resultSetAdministrator = preparedStatementAdministrator.executeQuery();

			if(!resultSetAdministrator.next()){
				preparedStatementClient = connection.prepareStatement(SELECT_CLIENT_BY_LOGIN_QUERY);

				preparedStatementClient.setString(1, login);
				resultSetClient = preparedStatementClient.executeQuery();

				if(resultSetClient.next()){
					user = new User();
					user.setId(resultSetClient.getInt(1));
					user.setLogin(resultSetClient.getString(2));
					user.setPassword(resultSetClient.getString(3));
					user.setAdmin(false);
				}
			}
			else{
				user = new User();
				user.setId(resultSetAdministrator.getInt(1));
				user.setLogin(resultSetAdministrator.getString(2));
				user.setPassword(resultSetAdministrator.getString(3));
				user.setAdmin(true);
			}

		} catch (InterruptedException | ConnectionPoolException e) {
			LOGGER.error("Can not get connection from connection pool");
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot find user by login", e);
		} finally {
			closeResultSet(resultSetAdministrator);
			closeStatement(preparedStatementAdministrator);
			closeResultSet(resultSetClient);
			closeStatement(preparedStatementClient);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					LOGGER.error("Can not free connection from connection pool");
				}
			}
		}

		return user;
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

		} catch (InterruptedException | ConnectionPoolException e) {
			LOGGER.error("Can not get connection from connection pool");
		} catch (SQLException e){
			throw new DAOException("DAO layer: cannot find user by id", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			if (connection != null) {
				try {
					connectionPool.freeConnection(connection);
				} catch (SQLException | ConnectionPoolException e) {
					LOGGER.error("Can not free connection from connection pool");
				}
			}
		}

		return user;
	}


	public void closeStatement(Statement statement){
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e){
			LOGGER.error("Can not close statement");
		}
	}


	public void closeResultSet(ResultSet resultSet){
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e){
			LOGGER.error("Can not close result set");
		}
	}


}
