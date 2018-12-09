package com.task.test.service;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.task.modelBeans.ScheduleBean;
import com.task.scheduleDao.ScheduleDao;
import com.task.service.ScheduleService;

/**
 * 
 * JUnit test cases
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:WEB-INF/spring-web-servlet.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScheduleTest {

	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ScheduleDao scheduleDao;


	@Test
	public void testCScheduleService() {
		assertEquals(
				"class com.task.service.ScheduleSeviceImpl",
				this.scheduleService.getClass().toString());
	}

	

	
	@Test
	public void testACreateNewSchedule() {
		ScheduleBean schedule=new ScheduleBean();
		schedule.setScheduleName("Test Schedule");
		String[] workingDays= {"Monday","Tuesday","Wednesday"};
		schedule.setEndTime(new Date());
		schedule.setStartTime(new Date());
		schedule.setWeekDaysStatus(workingDays);
		if (schedule != null) {
			assertEquals(true,scheduleService.saveSchedule(schedule));
								
			assertNotNull("End Time isn't null", schedule.getEndTime());
			assertNotNull("Schedule name is not null",
					schedule.getScheduleName());
		}

		assertNotNull("Schedule is not null", schedule);

	}

	@Test
	public void testBScheduleGetSchedule() {

		ScheduleBean retrievedSchedule = scheduleDao.getSchedule();

		if (retrievedSchedule != null) {
			assertThat(retrievedSchedule, instanceOf(ScheduleBean.class));
			assertNotNull("Schedule Name not null",
					retrievedSchedule.getScheduleName());
			assertNotNull("Schedule Start time",
					retrievedSchedule.getStartTime());
			assertNotNull("Schedule End Time",
					retrievedSchedule.getEndTime());
			assertNotNull("Schedule Working days",
					retrievedSchedule.getWeekDaysStatus());
			
		}

		assertNotNull("Schedule Bean is not null", retrievedSchedule);
	}
	
	
	@Test
	public void testGScheduleDeleteSchedule() {
		
		assertEquals(true,scheduleService.deleteSchedule());
	}
	
}