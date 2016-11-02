package com.epam.hostel.service.factory;


import com.epam.hostel.service.PoolService;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.impl.PoolServiceImpl;
import com.epam.hostel.service.impl.RoomServiceImpl;
import com.epam.hostel.service.impl.SiteServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	
	private final SiteService siteService = new SiteServiceImpl();
	private final PoolService poolService = new PoolServiceImpl();
	private final RoomService roomService = new RoomServiceImpl();
	
	private ServiceFactory(){}
	
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public SiteService getSiteService(){
		return siteService;
	}

	public PoolService getPoolService() {
		return poolService;
	}

	public RoomService getRoomService() {
		return roomService;
	}
}
