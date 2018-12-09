package com.task.modelBeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bean Class for the schedule object
 */
import org.springframework.format.annotation.DateTimeFormat;

public class ScheduleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(min = 2, max = 30, message = "Name should be between 2 to 30")
	private String scheduleName;

	@DateTimeFormat(pattern = "HH:mm")
	private Date startTime;

	@DateTimeFormat(pattern = "HH:mm")
	private Date endTime;

	@NotNull
	@Size(min = 1, max = 7, message = "Select at least one day")
	private String[] weekDaysStatus;

	public String[] getWeekDaysStatus() {
		return weekDaysStatus;
	}

	public void setWeekDaysStatus(String[] weekDaysStatus) {
		this.weekDaysStatus = weekDaysStatus;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ScheduleBean [scheduleName=" + scheduleName + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", weekDaysStatus=" + Arrays.toString(weekDaysStatus) + "]";
	}

}
