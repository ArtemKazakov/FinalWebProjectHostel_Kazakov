package com.epam.hostel.dao.impl;


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

	private static final String SELECT_USER_QUERY = "SELECT * FROM `clients` WHERE `login` = ? ";

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
			preparedStatement.setInt(3, user.getPassportId());

			preparedStatement.executeUpdate();
		} catch (InterruptedException | ConnectionPoolException e) {
			throw new DAOException("Cannot get a connection from Connection Pool", e);
		} catch (SQLException e){
			throw new DAOException("SQL query error", e);
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
			preparedStatement = connection.prepareStatement(SELECT_USER_QUERY);

			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				user = new User();
				user.setLogin(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
			}

			return user;
		} catch (InterruptedException | ConnectionPoolException e) {
			throw new DAOException("Cannot get a connection from Connection Pool", e);
		} catch (SQLException e){
			throw new DAOException("SQL query error", e);
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
