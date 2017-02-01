package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.DiscountDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link Discount} entity for the MySQL Database.
 */
public class MySQLDiscountDAO extends MySQLDAO implements DiscountDAO {

    private static final String INSERT_DISCOUNT_QUERY = "INSERT INTO `discounts` " +
            "(`id_client`, `discount_value`, `id_administrator`) " +
            "VALUES (?, ?, ?) ";

    private static final String UPDATE_DISCOUNT_QUERY = "UPDATE `discounts` " +
            "SET `id_client` = ?, `discount_value` = ?, `id_administrator` = ?, `discount_used` = ? " +
            "WHERE `id_discount` = ? ";

    private static final String UPDATE_DISCOUNT_VALUE_QUERY = "UPDATE `discounts` " +
            "SET `id_client` = ?, `discount_value` = ? " +
            "WHERE `id_discount` = ? ";

    private static final String SELECT_DISCOUNT_BY_ID_QUERY = "SELECT * FROM `discounts` WHERE `id_discount` = ? ";

    private static final String SELECT_DISCOUNTS_BY_CLIENT_ID_QUERY = "SELECT * FROM `discounts` WHERE `id_client` = ? ";

    private static final String SELECT_UNUSED_DISCOUNTS_BY_CLIENT_ID_QUERY = "SELECT * FROM `discounts` WHERE `id_client` = ? " +
            "AND `discount_used` = false ";

    private static final String SELECT_DISCOUNTS_BY_ADMINISTRATOR_ID_QUERY = "SELECT * FROM `discounts` " +
            "WHERE `id_administrator` = ? ";

    private static final String SELECT_ALL_DISCOUNTS_QUERY = "SELECT * FROM `discounts`";

    private static final String DELETE_DISCOUNT_BY_ID_QUERY = "DELETE FROM `discounts` WHERE `id_discount` = ? ";

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
     * Inserts a new discount into a database.
     *
     * @param discount a discount object for insertion
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void insert(Discount discount) throws DAOException {
        doDataManipulation(dataSource, INSERT_DISCOUNT_QUERY, "DAO layer: cannot insert discount",
                preparedStatement -> {
                    preparedStatement.setInt(1, discount.getClientId());
                    preparedStatement.setInt(2, discount.getValue());
                    preparedStatement.setInt(3, discount.getAdministratorId());
                }
        );
    }

    /**
     * Updates a discount in a database.
     *
     * @param discount a discount object for update
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void update(Discount discount) throws DAOException {
        doDataManipulation(dataSource, UPDATE_DISCOUNT_QUERY, "DAO layer: cannot update discount",
                preparedStatement -> {
                    preparedStatement.setInt(1, discount.getClientId());
                    preparedStatement.setInt(2, discount.getValue());
                    preparedStatement.setInt(3, discount.getAdministratorId());
                    preparedStatement.setBoolean(4, discount.isUsed());
                    preparedStatement.setInt(5, discount.getId());
                }
        );
    }

    /**
     * Updates a discount value in a database.
     *
     * @param discount a discount object that contains value for update
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void updateValue(Discount discount) throws DAOException {
        doDataManipulation(dataSource, UPDATE_DISCOUNT_VALUE_QUERY, "DAO layer: cannot update discount value",
                preparedStatement -> {
                    preparedStatement.setInt(1, discount.getClientId());
                    preparedStatement.setInt(2, discount.getValue());
                    preparedStatement.setInt(3, discount.getId());
                }
        );
    }

    /**
     * Deletes a discount from a database by id.
     *
     * @param id an id of deleting discount
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void delete(int id) throws DAOException {
        doDataManipulation(dataSource, DELETE_DISCOUNT_BY_ID_QUERY, "DAO layer: cannot delete discount",
                preparedStatement -> preparedStatement.setInt(1, id)
        );
    }

    /**
     * Gives a discount from a database by id.
     *
     * @param id an id of a desired discount
     * @return a discount object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public Discount findById(int id) throws DAOException {
        return singleSelect(dataSource, SELECT_DISCOUNT_BY_ID_QUERY, "DAO layer: cannot select discounts by id",
                preparedStatement -> preparedStatement.setInt(1, id),
                resultSet -> resultSet.next() ? this.createDiscount(resultSet) : null
        );
    }

    /**
     * Gives a list of discounts from a database by client id.
     *
     * @param id an client id of desired discounts
     * @return a {@link List} of discounts containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Discount> findByClientId(int id) throws DAOException {
        return select(dataSource, SELECT_DISCOUNTS_BY_CLIENT_ID_QUERY, "DAO layer: cannot select discounts by client id",
                preparedStatement -> preparedStatement.setInt(1, id),
                this :: createDiscount
        );
    }

    /**
     * Gives a list of unused discounts from a database by client id.
     *
     * @param id an client id of desired discounts
     * @return a {@link List} of discounts containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Discount> findUnusedByClientId(int id) throws DAOException {
        return select(dataSource, SELECT_UNUSED_DISCOUNTS_BY_CLIENT_ID_QUERY, "DAO layer: cannot select unused discounts by client id",
                preparedStatement -> preparedStatement.setInt(1, id),
                this :: createDiscount
        );
    }

    /**
     * Gives a list of discounts from a database by administrator id.
     *
     * @param id an administrator id of desired discounts
     * @return a {@link List} of discounts containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Discount> findByAdministratorId(int id) throws DAOException {
        return select(dataSource, SELECT_DISCOUNTS_BY_ADMINISTRATOR_ID_QUERY, "DAO layer: cannot select discounts by administrator id",
                preparedStatement -> preparedStatement.setInt(1, id),
                this :: createDiscount
        );
    }

    /**
     * Gives a list of all discounts from a database.
     *
     * @return a {@link List} of discounts
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Discount> findAll() throws DAOException {
        return select(dataSource, SELECT_ALL_DISCOUNTS_QUERY, "DAO layer: cannot select all discounts",
                this :: createDiscount
        );
    }

    /**
     * Creates a new {@link Discount} object.
     * @param resultSet a {@link ResultSet} object from which information will be extracted
     * @return a discount object
     * @throws SQLException in cases of errors
     */
    private Discount createDiscount(ResultSet resultSet) throws SQLException{
        Discount discount = new Discount();
        discount.setId(resultSet.getInt(1));
        discount.setClientId(resultSet.getInt(2));
        discount.setValue(resultSet.getInt(3));
        discount.setAdministratorId(resultSet.getInt(4));
        discount.setUsed(resultSet.getBoolean(5));
        return discount;
    }

}
