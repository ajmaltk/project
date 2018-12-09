package com.task.scheduleDao;

import com.task.modelBeans.ScheduleBean;

public interface ScheduleDao {

	boolean saveSchedule(ScheduleBean schedule);

	ScheduleBean getSchedule();
	 boolean deleteSchedule();
}
