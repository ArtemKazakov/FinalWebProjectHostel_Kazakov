package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import org.apache.xerces.impl.dv.util.Base64;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link User} entity for the MySQL Database.
 */
public class MySQLUserDAO extends MySQLDAO implements UserDAO {

	private static final Logger logger = Logger.getLogger(MySQLUserDAO.class);

	private static final String INSERT_USER_QUERY = "INSERT INTO `clients` " +
			"(`login`, `password`, `id_passport`) " +
			"VALUES (?, ?, ?)";

	private static final String UPDATE_USER_QUERY = "UPDATE `clients` " +
			"SET `login` = ?, `password` = ? " +
			"WHERE `id_client` = ?";

	private static final String UPDATE_BANNED_USER_QUERY = "UPDATE `clients` " +
			"SET `banned` = ? " +
			"WHERE `id_client` = ?";

	private static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM `clients` WHERE `id_client` = ? ";

	private static final String SELECT_ADMINISTRATOR_BY_LOGIN_QUERY = "SELECT * FROM `administrators` WHERE `login` = ? ";

	private static final String SELECT_CLIENT_BY_LOGIN_QUERY = "SELECT * FROM `clients` WHERE `login` = ? ";

	private static final String SELECT_CLIENT_BY_ID_QUERY = "SELECT * FROM `clients` WHERE `id_client` = ? ";

	private static final String SELECT_ADMINISTRATOR_BY_ID_QUERY = "SELECT * FROM `administrators` WHERE `id_administrator` = ? ";

	private static final String SELECT_ALL_CLIENTS_QUERY = "SELECT * FROM `clients` LIMIT ?, ?;";

	private static final String SELECT_COUNT_OF_USERS = "SELECT COUNT(*) FROM `clients`;";


	private DataSource dataSource = (DataSource) TransactionManagerImpl.getInstance();

	/**
	 * Set a {@link DataSource} object, that will give a {@link Connection}
	 * for all operation with the database.
	 * @param dataSource for setting
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Inserts a new user into a database.
	 *
	 * @param user a user object for insertion
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public void insert(User user) throws DAOException{
		doDataManipulation(dataSource, INSERT_USER_QUERY, "DAO layer: cannot insert user",
				preparedStatement -> {
					preparedStatement.setString(1, user.getLogin());
					preparedStatement.setString(2, Base64.encode(user.getPassword()));
					preparedStatement.setInt(3, user.getPassport().getId());
				}
		);
	}

	/**
	 * Updates a user in a database.
	 *
	 * @param user a user object for update
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public void update(User user) throws DAOException {
		doDataManipulation(dataSource, UPDATE_USER_QUERY, "DAO layer: cannot update user",
				preparedStatement -> {
					preparedStatement.setString(1, user.getLogin());
					preparedStatement.setString(2, Base64.encode(user.getPassword()));
					preparedStatement.setInt(3, user.getId());
				}
		);
	}

	/**
	 * Updates a user ban status in a database.
	 *
	 * @param user a user object that contains ban status for update
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public void updateBanned(User user) throws DAOException {
		doDataManipulation(dataSource, UPDATE_BANNED_USER_QUERY, "DAO layer: cannot update banned user",
				preparedStatement -> {
					preparedStatement.setBoolean(1, user.isBanned());
					preparedStatement.setInt(2, user.getId());
				}
		);
	}

	/**
	 * Deletes a user from a database by id.
	 *
	 * @param id an id of deleting user
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public void delete(int id) throws DAOException {
		doDataManipulation(dataSource, DELETE_USER_BY_ID_QUERY, "DAO layer: cannot delete user",
				preparedStatement -> preparedStatement.setInt(1, id)
		);
	}

	/**
	 * Gives a user from a database by login.
	 *
	 * @param login a login of a desired user
	 * @return a user object containing the necessary data
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public User findByLogin(String login) throws DAOException {
		PreparedStatement preparedStatementAdministrator = null;
		ResultSet resultSetAdministrator = null;
		PreparedStatement preparedStatementClient = null;
		ResultSet resultSetClient = null;
		User user = null;
		try{
			Connection connection = dataSource.getConnection();
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
					user.setPassword(Base64.decode(resultSetClient.getString(3)));
					user.setBanned(resultSetClient.getBoolean(5));
					user.setAdmin(false);
				}
			}
			else{
				user = new User();
				user.setId(resultSetAdministrator.getInt(1));
				user.setLogin(resultSetAdministrator.getString(2));
				user.setPassword(Base64.decode(resultSetAdministrator.getString(3)));
				user.setAdmin(true);
			}

		}  catch (SQLException e){
			logger.error(e);
			throw new DAOException("DAO layer: cannot find user by login", e);
		} finally {
			close(resultSetAdministrator, preparedStatementAdministrator, resultSetClient, preparedStatementClient);
		}

		return user;
	}

	/**
	 * Gives a user from a database by id and role.
	 *
	 * @param id an id of a desired user
	 * @param isAdmin a status of a desired user
	 * @return a user object containing the necessary data
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public User findByIdAndRole(int id, boolean isAdmin) throws DAOException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try{
			Connection connection = dataSource.getConnection();
			if (isAdmin){
				preparedStatement = connection.prepareStatement(SELECT_ADMINISTRATOR_BY_ID_QUERY);
			} else {
				preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID_QUERY);
			}

			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				user = new User();
				Passport passport = new Passport();
				passport.setId(resultSet.getInt(4));
				user.setPassport(passport);
				user.setId(resultSet.getInt(1));
				user.setLogin(resultSet.getString(2));
				user.setPassword(Base64.decode(resultSet.getString(3)));
				if (!isAdmin) {
					user.setBanned(resultSet.getBoolean(5));
					user.setVisitsNumber(resultSet.getInt(6));
				}
			}

		}  catch (SQLException e){
			logger.error(e);

			throw new DAOException("DAO layer: cannot find user by id", e);
		} finally {
			close(resultSet, preparedStatement);
		}

		return user;
	}

	/**
	 * Gives a list of all users from a database.
	 *
	 * @param start  a number from which entries will be returned
	 * @param amount of entries
	 * @return a {@link List} of users
	 * @throws DAOException in case of some exception with
	 *                      a database or a connection with it
	 */
	@Override
	public List<User> findAll(int start, int amount) throws DAOException {
		return select(dataSource, SELECT_ALL_CLIENTS_QUERY, "DAO layer: cannot select all users",
				preparedStatement -> {
					preparedStatement.setInt(1, start);
					preparedStatement.setInt(2, amount);
				},
				this :: createUser
		);
	}

	/**
	 * Gives number of users in a database.
	 *
	 * @return count of users
	 * @throws DAOException in case of some exception with
	 *                      a data source or a connection with it
	 */
	@Override
	public int selectUserCount() throws DAOException {
		return this.singleSelect(
				dataSource,
				SELECT_COUNT_OF_USERS,
				"Can't get count of users",
				resultSet -> {
					resultSet.next();
					return resultSet.getInt(1);
				}
		);
	}

	/**
	 * Creates a new {@link User} object.
	 * @param resultSet a {@link ResultSet} object from which information will be extracted
	 * @return a user object
	 * @throws SQLException in cases of errors
	 */
	private User createUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		Passport passport = new Passport();
		passport.setId(resultSet.getInt(4));
		user.setPassport(passport);
		user.setId(resultSet.getInt(1));
		user.setLogin(resultSet.getString(2));
		user.setPassword(Base64.decode(resultSet.getString(3)));
		user.setBanned(resultSet.getBoolean(5));
		user.setVisitsNumber(resultSet.getInt(6));
		return user;
	}
}
