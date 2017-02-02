package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link RentalRequest} entity for the MySQL Database.
 */
public class MySQLRequestDAO extends MySQLDAO implements RequestDAO {
    private static final String INSERT_REQUEST_QUERY = "INSERT INTO `requests` " +
            "(`id_client`, `seats_number`, `checkin_date`, `days_stay`, `request_type`, `payment`) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE_REQUEST_QUERY = "UPDATE `requests` " +
            "SET `id_client` = ?, `id_administrator` = ?, `seats_number` = ?, `checkin_date` = ?, " +
            "`days_stay` = ?, `request_type` = ?, `payment` = ?, `request_status` = ? " +
            "WHERE `id_request` = ? ";

    private static final String SELECT_REQUEST_BY_ID_QUERY = "SELECT * FROM `requests` WHERE `id_request` = ? ";

    private static final String SELECT_REQUESTS_BY_CLIENT_ID_QUERY = "SELECT * FROM `requests` WHERE `id_client` = ? ";

    private static final String SELECT_REQUESTS_BY_ADMINISTRATOR_ID_QUERY = "SELECT * FROM `requests` " +
            "WHERE `id_administrator` = ? ";

    private static final String SELECT_ALL_REQUESTS_QUERY = "SELECT * FROM `requests`";

    private static final String SELECT_ALL_REQUESTS_LIMITED_QUERY = "SELECT * FROM `requests` LIMIT ?, ?;";

    private static final String DELETE_REQUEST_BY_ID_QUERY = "DELETE FROM `requests` WHERE `id_request` = ? ";

    private static final String SELECT_COUNT_OF_REQUESTS = "SELECT COUNT(*) FROM `requests`;";

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
     * Inserts a new rental request into a database.
     *
     * @param rentalRequest a rental request object for insertion
     * @return identity key, which this entry got in database
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public Integer insert(RentalRequest rentalRequest) throws DAOException {
        return insert(dataSource, INSERT_REQUEST_QUERY, "DAO layer: cannot insert rentalRequest",
                preparedStatement -> {
                    preparedStatement.setInt(1, rentalRequest.getClient().getId());
                    preparedStatement.setInt(2, rentalRequest.getSeatsNumber());
                    preparedStatement.setDate(3, new Date(rentalRequest.getCheckInDate().getTime()));
                    preparedStatement.setInt(4, rentalRequest.getDaysStayNumber());
                    preparedStatement.setBoolean(5, rentalRequest.isFullPayment());
                    preparedStatement.setInt(6, rentalRequest.getPayment());
                },
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
        );
    }

    /**
     * Updates a rental request in a database.
     *
     * @param rentalRequest a rental request object for update
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void update(RentalRequest rentalRequest) throws DAOException {
        doDataManipulation(dataSource, UPDATE_REQUEST_QUERY, "DAO layer: cannot update rentalRequest\"",
                preparedStatement -> {
                    preparedStatement.setInt(1, rentalRequest.getClient().getId());
                    preparedStatement.setInt(2, rentalRequest.getAdministrator().getId());
                    preparedStatement.setInt(3, rentalRequest.getSeatsNumber());
                    preparedStatement.setDate(4, new Date(rentalRequest.getCheckInDate().getTime()));
                    preparedStatement.setInt(5, rentalRequest.getDaysStayNumber());
                    preparedStatement.setBoolean(6, rentalRequest.isFullPayment());
                    preparedStatement.setInt(7, rentalRequest.getPayment());
                    preparedStatement.setBoolean(8, rentalRequest.getAccepted());
                    preparedStatement.setInt(9, rentalRequest.getId());
                }
        );
    }

    /**
     * Deletes a rental request from a database by id.
     *
     * @param id an id of deleting rental request
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void delete(int id) throws DAOException {
        doDataManipulation(dataSource, DELETE_REQUEST_BY_ID_QUERY, "DAO layer: cannot delete request",
                preparedStatement -> preparedStatement.setInt(1, id)
        );
    }

    /**
     * Gives a rental request from a database by id.
     *
     * @param id an id of a desired rental request
     * @return a rental request object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public RentalRequest findById(int id) throws DAOException {
        return singleSelect(dataSource, SELECT_REQUEST_BY_ID_QUERY, "DAO layer: cannot select rental request by id",
                preparedStatement -> preparedStatement.setInt(1, id),
                resultSet -> resultSet.next() ? this.createRentalRequest(resultSet) : null
        );
    }

    /**
     * Gives a list of rental requests from a database by client id.
     *
     * @param id an client id of desired rental requests
     * @return a {@link List} of rental requests containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<RentalRequest> findByClientId(int id) throws DAOException {
        return select(dataSource, SELECT_REQUESTS_BY_CLIENT_ID_QUERY, "DAO layer:cannot select rentalRequests by client id",
                preparedStatement -> preparedStatement.setInt(1, id),
                this :: createRentalRequest
        );
    }

    /**
     * Gives a list of rental requests from a database by administrator id.
     *
     * @param id an administrator id of desired rental requests
     * @return a {@link List} of rental requests containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<RentalRequest> findByAdministratorId(int id) throws DAOException {
        return select(dataSource, SELECT_REQUESTS_BY_ADMINISTRATOR_ID_QUERY, "DAO layer: cannot select rentalRequests by administrator id",
                preparedStatement -> preparedStatement.setInt(1, id),
                this :: createRentalRequest
        );
    }

    /**
     * Gives a list of all rental requests from a database.
     *
     * @return a {@link List} of rental requests
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<RentalRequest> findAll() throws DAOException {
        return select(dataSource, SELECT_ALL_REQUESTS_QUERY, "DAO layer: cannot select all rentalRequests",
                this :: createRentalRequest
        );
    }

    /**
     * Gives a list of all rental requests from a database.
     *
     * @param start  a number from which entries will be returned
     * @param amount of entries
     * @return a {@link List} of rental requests
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<RentalRequest> findAllLimited(int start, int amount) throws DAOException {
        return select(dataSource, SELECT_ALL_REQUESTS_LIMITED_QUERY, "DAO layer: cannot get all requests limited",
                preparedStatement -> {
                    preparedStatement.setInt(1, start);
                    preparedStatement.setInt(2, amount);
                },
                this::createRentalRequest
        );
    }

    /**
     * Gives number of rental requests in a database.
     *
     * @return count of rental requests
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    @Override
    public int selectRequestCount() throws DAOException {
        return this.singleSelect(
                dataSource,
                SELECT_COUNT_OF_REQUESTS,
                "Can't get count of rental requests",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
        );
    }

    /**
     * Creates a new {@link RentalRequest} object.
     * @param resultSet a {@link ResultSet} object from which information will be extracted
     * @return a rental request object
     * @throws SQLException in cases of errors
     */
    private RentalRequest createRentalRequest(ResultSet resultSet) throws SQLException {
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setId(resultSet.getInt(1));
        User client = new User();
        client.setId(resultSet.getInt(2));
        rentalRequest.setClient(client);
        User administrator = new User();
        administrator.setId(resultSet.getInt(3));
        rentalRequest.setAdministrator(administrator);
        rentalRequest.setSeatsNumber(resultSet.getInt(4));
        rentalRequest.setCheckInDate(resultSet.getDate(5));
        rentalRequest.setDaysStayNumber(resultSet.getInt(6));
        rentalRequest.setFullPayment(resultSet.getBoolean(7));
        rentalRequest.setPayment(resultSet.getInt(8));
        rentalRequest.setAccepted((Boolean)resultSet.getObject(9));
        return rentalRequest;
    }
}
