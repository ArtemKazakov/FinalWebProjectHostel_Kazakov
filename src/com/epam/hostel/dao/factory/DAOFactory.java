package com.epam.hostel.dao.factory;


import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.PoolDAO;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.UserDAO;

public abstract class DAOFactory {

	
	public static DAOFactory getInstance(Factories factoryName){
		switch (factoryName) {
			case MYSQL: return MySQLDAOFactory.getInstance();
			default: return null;
		}
	}

	public abstract UserDAO getUserDAO();

	public abstract PassportDAO getPassportDAO();

	public abstract PoolDAO getPoolDAO();

	public abstract RoomDAO getRoomDAO();


	public enum Factories {
		MYSQL
	}

}
