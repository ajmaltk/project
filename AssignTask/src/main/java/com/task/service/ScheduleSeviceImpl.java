package com.task.service;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.controller.ScheduleController;
import com.task.modelBeans.ScheduleBean;
import com.task.scheduleDao.ScheduleDao;

import ch.qos.logback.classic.Logger;

/**
 * 
 * 
 * Service class implementation for retrieving, saving and calclation of end
 * time
 *
 */
@Service("scheduleService")
public class ScheduleSeviceImpl implements ScheduleService {

	@Autowired
	ScheduleDao scheduleDao;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(ScheduleController.class);

	//Method for savin the schedule
	public boolean saveSchedule(ScheduleBean schedule) {

		boolean statusDao = scheduleDao.saveSchedule(schedule);

		return statusDao;
	}

	//calculate the end time
	public Date calculateEndTime(Date startDateTime, int duration) {

		ScheduleBean scheduleIn;
		Date endDate = startDateTime;

		scheduleIn = scheduleDao.getSchedule();
		if (scheduleIn == null) {

			logger.error("Unable to fetch the data from file");
			return null;
		}
		Calendar calStartDate = Calendar.getInstance();
		calStartDate.setTime(startDateTime);

		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(startDateTime);

		Calendar calEndDateTemp = Calendar.getInstance();
		calEndDateTemp.setTime(startDateTime);

		Calendar startTime = Calendar.getInstance();
		startTime.setTime(scheduleIn.getStartTime());
		startTime.set(Calendar.DATE, calStartDate.get(Calendar.DATE));
		startTime.set(Calendar.MONTH, calStartDate.get(Calendar.MONTH));
		startTime.set(Calendar.YEAR, calStartDate.get(Calendar.YEAR));

		Calendar endTime = Calendar.getInstance();
		endTime.setTime(scheduleIn.getEndTime());
		endTime.set(Calendar.DATE, calStartDate.get(Calendar.DATE));
		endTime.set(Calendar.MONTH, calStartDate.get(Calendar.MONTH));
		endTime.set(Calendar.YEAR, calStartDate.get(Calendar.YEAR));

		String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday" };

		long minutesBetween = ChronoUnit.MINUTES.between(startTime.toInstant(), endTime.toInstant());
		long startTimeMilliSeconds = startTime.getTimeInMillis();
		long endTimeMilliSeconds = endTime.getTimeInMillis();

		long TotalMinutes = minutesBetween * duration;
		long dayMilliSeconds = 86400000;

		String[] workingDays = scheduleIn.getWeekDaysStatus();

		List<String> ltWorkingdays = Arrays.asList(workingDays);

		long startDateMillSec = 0;

		for (int i = 0; i < 7; i++) {

			startDateMillSec = calStartDate.getTimeInMillis();

			if ((!(ltWorkingdays.contains(strDays[calStartDate.get(Calendar.DAY_OF_WEEK) - 1])))
					|| (startDateMillSec > endTimeMilliSeconds)) {

				calStartDate.add(Calendar.DATE, 1);

				calStartDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));

				calStartDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
				endTimeMilliSeconds += dayMilliSeconds;
			} else if (startDateMillSec < startTimeMilliSeconds) {

				calStartDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));

				calStartDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));

			} else {

				break;

			}
		}

		calEndDate = calStartDate;
		calEndDateTemp = (Calendar) calEndDate.clone();
		calEndDateTemp.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
		calEndDateTemp.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));

		long minutesBetweenTemp = minutesBetween;

		while (minutesBetweenTemp <= TotalMinutes) {

			if (minutesBetweenTemp > TotalMinutes) {

				break;
			}
			if (ltWorkingdays.contains(strDays[calEndDate.get(Calendar.DAY_OF_WEEK) - 1])) {

				calEndDate.add(Calendar.MINUTE, (int) minutesBetween);

				if (!(ltWorkingdays.contains(strDays[calEndDate.get(Calendar.DAY_OF_WEEK) - 1]))) {
					calEndDate.add(Calendar.MINUTE, (int) minutesBetween * -1);
					calEndDate.add(Calendar.DATE, 1);
					calEndDateTemp.add(Calendar.DATE, 1);
				} else {

					if ((calEndDate.getTimeInMillis() > calEndDateTemp.getTimeInMillis())) {

						long minutesBetweenTemp1 = ChronoUnit.MINUTES.between(calEndDateTemp.toInstant(),
								calEndDate.toInstant());
						calEndDateTemp.add(Calendar.DATE, 1);
						calEndDate.add(Calendar.DATE, 1);
						calEndDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));

						calEndDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
						calEndDate.add(Calendar.MINUTE, (int) minutesBetweenTemp1);
						if (calEndDateTemp.get(Calendar.MONTH) == calEndDate.get(Calendar.MONTH)) {
							calEndDate.set(Calendar.DATE, calEndDateTemp.get(Calendar.DATE));
						} else {

							calEndDate.set(Calendar.MONTH, calEndDateTemp.get(Calendar.MONTH));
							calEndDate.set(Calendar.DATE, calEndDateTemp.get(Calendar.DATE));

						}
					}
				}
				minutesBetweenTemp += minutesBetween;
			} else {

				calEndDate.add(Calendar.DATE, 1);
				calEndDateTemp.add(Calendar.DATE, 1);

			}

		}

		boolean finalDateStatus = false;
		do {

			if (!(ltWorkingdays.contains(strDays[calEndDate.get(Calendar.DAY_OF_WEEK) - 1]))) {

				calEndDate.add(Calendar.DATE, 1);
				finalDateStatus = true;
			} else {

				finalDateStatus = false;
			}

		} while (finalDateStatus);
		endDate = calEndDate.getTime();

		return endDate;

	}

	//delete the schedule
	public boolean deleteSchedule() {

		return scheduleDao.deleteSchedule();
	}

}
