package com.task.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.modelBeans.ScheduleBean;
import com.task.modelBeans.TaskBean;
import com.task.service.ScheduleService;

/**
 * Controller class for the form controls
 */
@Controller
public class ScheduleController {

	List<String> weekDaysList;

	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	@Autowired
	ScheduleService scheduleService;

	/** Provides functionality to the home page. */
	@RequestMapping(value = "/")
	public ModelAndView home() {

		ScheduleBean schedule = new ScheduleBean();

		TaskBean task = new TaskBean();

		weekDaysList = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

		ModelAndView mView = new ModelAndView("index");
		mView.addObject("schedule", schedule);
		mView.addObject("task", task);
		mView.addObject("weekDaysList", weekDaysList);

		return mView;
	}

	/** Provides functionality to the index page and form initialization. */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView showFormAdd(Model model) {

		ScheduleBean schedule = new ScheduleBean();

		TaskBean task = new TaskBean();

		weekDaysList = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

		ModelAndView mView = new ModelAndView("index");
		mView.addObject("schedule", schedule);
		mView.addObject("task", task);
		mView.addObject("weekDaysList", weekDaysList);
		return mView;

	}

	/** Provides functionality for initializing the task form */
	@RequestMapping(value = "/testschedule", method = RequestMethod.GET)
	public ModelAndView showFormTest(Model model) {

		TaskBean task = new TaskBean();

		ModelAndView mView = new ModelAndView("testschedule");

		mView.addObject("task", task);

		return mView;

	}

	/** Provides functionality for testing the task schedule */
	@RequestMapping(value = "/testTask", method = RequestMethod.POST)
	public @ResponseBody String testSchedule(@ModelAttribute("task") @Valid TaskBean task, BindingResult bindingResult,
			Model model) {

		Map<String, Object> jsonTestMap = new HashMap<String, Object>();
		StringBuilder errorMsg = new StringBuilder();
		String jsonMap = null;
		try {
			if (bindingResult.hasErrors()) {

				List<FieldError> errors = bindingResult.getFieldErrors();
				for (FieldError error : errors) {
					errorMsg.append("\u2022  " + error.getDefaultMessage());
					errorMsg.append("</br>");

				}
				jsonTestMap.put("status", "error");
				jsonTestMap.put("message", errorMsg);
				jsonMap = new ObjectMapper().writeValueAsString(jsonTestMap);

				return jsonMap;
			}

			Date endDate = scheduleService.calculateEndTime(task.getTaskStartTime(), task.getTaskDuration());
			if (endDate == null) {

				jsonTestMap.put("status", "NoObject");
				jsonTestMap.put("message", "Please Create a schedule first");

			} else {

				task.setTaskEndTime(endDate);

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonTestMap.put("status", "success");
				jsonTestMap.put("taskName", task.getTaskName().toString());
				jsonTestMap.put("taskEndTime", formatter.format(endDate));
				jsonTestMap.put("taskStartTime", formatter.format(task.getTaskStartTime()));
				jsonTestMap.put("duration", Integer.toString(task.getTaskDuration()));
			}

			jsonMap = new ObjectMapper().writeValueAsString(jsonTestMap);
		} catch (JsonProcessingException e) {

			logger.error("Unable to process the MAP to JSON");
		}
		return jsonMap;
	}

	/** Provides functionality for saving schedule form */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody String saveSchedule(@ModelAttribute("schedule") @Valid ScheduleBean schedule,
			BindingResult bindingResult) {

		Map<String, Object> jsonScheduleMap = new HashMap<String, Object>();

		String jsonSchedule = null;
		StringBuilder errorMsg = new StringBuilder();
		try {
			if (bindingResult.hasErrors()) {
				jsonScheduleMap.put("status", "error");

				List<FieldError> errors = bindingResult.getFieldErrors();
				for (FieldError error : errors) {
					errorMsg.append("\u2022  " + error.getDefaultMessage());
					errorMsg.append("</br>");

				}
				jsonScheduleMap.put("message",errorMsg);
				jsonSchedule = new ObjectMapper().writeValueAsString(jsonScheduleMap);

				return jsonSchedule;
			}
			
			
			if(schedule.getStartTime().getTime()>schedule.getEndTime().getTime()) {
				
				
				jsonScheduleMap.put("status", "error");
				jsonScheduleMap.put("message", "Start time should be less than end time");
				jsonSchedule = new ObjectMapper().writeValueAsString(jsonScheduleMap);

				return jsonSchedule;
				
			}

			boolean status = scheduleService.saveSchedule(schedule);
			if (status) {

				jsonScheduleMap.put("status", "success");
				jsonScheduleMap.put("message", "Success fully created a schedule");
			} else {

				jsonScheduleMap.put("status", "error");
				jsonScheduleMap.put("message", "Unable to save schedule.Please try later");
			}

			jsonSchedule = new ObjectMapper().writeValueAsString(jsonScheduleMap);
		} catch (JsonProcessingException e) {
			logger.error("Unable to process the MAP to JSON");
		}

		return jsonSchedule;
	}
}
