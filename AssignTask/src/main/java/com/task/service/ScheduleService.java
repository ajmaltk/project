package com.task.service;

import java.util.Date;

import com.task.modelBeans.ScheduleBean;

/**
 * 
 * 
 * Service class for the calculation of End Time and saving schedule
 *
 */
public interface ScheduleService {

	boolean saveSchedule(ScheduleBean schedule);

	Date calculateEndTime(Date startTime, int duration);

	boolean deleteSchedule();

}
