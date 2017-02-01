package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Provides a DAO-logic for the {@link Passport} entity for the MySQL Database.
 */
public class MySQLPassportDAO extends MySQLDAO implements PassportDAO {

    private static final String INSERT_USER_PASSPORT_QUERY = "INSERT INTO `passports` " +
            "(`identification_number`, `series`, `surname`, `name`, `patronymic`, `birthday`) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE_USER_PASSPORT_QUERY = "UPDATE `passports` " +
            "SET `identification_number` = ?, `series` = ?, `surname` = ?, `name` = ?, `patronymic` = ?, `birthday` = ? " +
            "WHERE `id_passport` = ? ";

    private static final String DELETE_PASSPORT_BY_ID_QUERY = "DELETE FROM `passports` WHERE `id_passport` = ? ";

    private static final String SELECT_PASSPORT_BY_ID_QUERY = "SELECT * FROM `passports` WHERE `id_passport` = ? ";

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
     * Inserts a new passport into a database.
     *
     * @param passport a passport object for insertion
     * @return identity key, which this entry got in database
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public Integer insert(Passport passport) throws DAOException {
        return insert(dataSource, INSERT_USER_PASSPORT_QUERY, "DAO layer: cannot insert passport",
                preparedStatement -> {
                    preparedStatement.setInt(1, passport.getIdentificationNumber());
                    preparedStatement.setString(2, passport.getSeries());
                    preparedStatement.setString(3, passport.getSurname());
                    preparedStatement.setString(4, passport.getName());
                    preparedStatement.setString(5, passport.getPatronymic());
                    preparedStatement.setDate(6, new Date(passport.getBirthday().getTime()));
                },
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
        );
    }

    /**
     * Updates a passport in a database.
     *
     * @param passport a passport object for update
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void update(Passport passport) throws DAOException {
        doDataManipulation(dataSource, UPDATE_USER_PASSPORT_QUERY, "DAO layer: cannot update passport",
                preparedStatement -> {
                    preparedStatement.setInt(1, passport.getIdentificationNumber());
                    preparedStatement.setString(2, passport.getSeries());
                    preparedStatement.setString(3, passport.getSurname());
                    preparedStatement.setString(4, passport.getName());
                    preparedStatement.setString(5, passport.getPatronymic());
                    preparedStatement.setDate(6, new Date(passport.getBirthday().getTime()));
                    preparedStatement.setInt(7, passport.getId());
                }
        );
    }

    /**
     * Deletes a passport from a database by id.
     *
     * @param id an id of deleting passport
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void delete(int id) throws DAOException {
        doDataManipulation(dataSource, DELETE_PASSPORT_BY_ID_QUERY, "DAO layer: cannot delete passport",
                preparedStatement -> preparedStatement.setInt(1, id)
        );
    }

    /**
     * Gives a passport from a database by id.
     *
     * @param id an id of a desired passport
     * @return a passport object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public Passport findById(int id) throws DAOException {
        return singleSelect(dataSource, SELECT_PASSPORT_BY_ID_QUERY, "DAO layer: cannot find passport by id",
                preparedStatement -> preparedStatement.setInt(1, id),
                resultSet -> resultSet.next() ? this.createPassport(resultSet) : null
        );
    }

    /**
     * Creates a new {@link Passport} object.
     * @param resultSet a {@link ResultSet} object from which information will be extracted
     * @return a passport object
     * @throws SQLException in cases of errors
     */
    private Passport createPassport(ResultSet resultSet) throws SQLException {
        Passport passport = new Passport();
        passport.setId(resultSet.getInt(1));
        passport.setIdentificationNumber(resultSet.getInt(2));
        passport.setSeries(resultSet.getString(3));
        passport.setSurname(resultSet.getString(4));
        passport.setName(resultSet.getString(5));
        passport.setPatronymic(resultSet.getString(6));
        passport.setBirthday(resultSet.getDate(7));
        return passport;
    }
}
