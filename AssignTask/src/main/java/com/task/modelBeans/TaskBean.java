package com.task.modelBeans;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Bean class for retrieving task end time
 *
 */
public class TaskBean {

	@Size(min = 2, max = 30, message = "Task Name should be between 2 to 30 characters")
	private String taskName;

	@Min(value = 1, message = "Duration should be greater than 0")
	private int taskDuration;

	
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	private Date taskStartTime;

	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	private Date taskEndTime;

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskDuration() {
		return taskDuration;
	}

	public void setTaskDuration(int taskDuration) {
		this.taskDuration = taskDuration;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	@Override
	public String toString() {
		return "TaskBean [taskName=" + taskName + ", taskDuration=" + taskDuration + ", taskStartTime=" + taskStartTime
				+ "]";
	}

}
