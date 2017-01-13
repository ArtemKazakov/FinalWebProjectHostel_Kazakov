package com.epam.hostel.service.factory;


import com.epam.hostel.service.*;
import com.epam.hostel.service.impl.*;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	
	private final SiteService siteService = new SiteServiceImpl();
	private final PoolService poolService = new PoolServiceImpl();
	private final RoomService roomService = new RoomServiceImpl();
	private final UserService userService = new UserServiceImpl();
	private final DiscountService discountService = new DiscountServiceImpl();
	private final RentalRequestService rentalRequestService = new RentalRequestServiceImpl();
	private final ScheduleRecordService scheduleRecordService = new ScheduleRecordServiceImpl();

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

	public UserService getUserService() {
		return userService;
	}

	public DiscountService getDiscountService() {
		return discountService;
	}

	public RentalRequestService getRentalRequestService() {
		return rentalRequestService;
	}

	public ScheduleRecordService getScheduleRecordService() {
		return scheduleRecordService;
	}
}
