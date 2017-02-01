package com.epam.hostel.service.factory;

import com.epam.hostel.service.*;
import com.epam.hostel.util.injection.Injectable;
import com.epam.hostel.util.injection.annotation.InjectBean;

/**
 * Provides a logic of instancing Service objects.
 */
public class ServiceFactory implements Injectable {
	private static final ServiceFactory INSTANCE = new ServiceFactory();

	@InjectBean(beanName = "siteService")
	private SiteService siteService;

	@InjectBean(beanName = "poolService")
	private PoolService poolService;

	@InjectBean(beanName = "roomService")
	private RoomService roomService;

	@InjectBean(beanName = "userService")
	private UserService userService;

	@InjectBean(beanName = "discountService")
	private DiscountService discountService;

	@InjectBean(beanName = "rentalRequestService")
	private RentalRequestService rentalRequestService;

	@InjectBean(beanName = "scheduleRecordService")
	private ScheduleRecordService scheduleRecordService;

	private ServiceFactory(){}

	/**
	 * Gives {@link ServiceFactory} instance
	 * @return ServiceFactory
     */
	public static ServiceFactory getInstance(){
		return INSTANCE;
	}

	/**
	 * Gives {@link SiteService} implementation
	 * @return SiteService implementation
     */
	public SiteService getSiteService(){
		return siteService;
	}

	/**
	 * Gives {@link PoolService} implementation
	 * @return PoolService implementation
	 */
	public PoolService getPoolService() {
		return poolService;
	}

	/**
	 * Gives {@link RoomService} implementation
	 * @return RoomService implementation
	 */
	public RoomService getRoomService() {
		return roomService;
	}

	/**
	 * Gives {@link UserService} implementation
	 * @return UserService implementation
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Gives {@link DiscountService} implementation
	 * @return DiscountService implementation
	 */
	public DiscountService getDiscountService() {
		return discountService;
	}

	/**
	 * Gives {@link RentalRequestService} implementation
	 * @return RentalRequestService implementation
	 */
	public RentalRequestService getRentalRequestService() {
		return rentalRequestService;
	}

	/**
	 * Gives {@link ScheduleRecordService} implementation
	 * @return ScheduleRecordService implementation
	 */
	public ScheduleRecordService getScheduleRecordService() {
		return scheduleRecordService;
	}
}
