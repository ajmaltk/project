
package com.task.scheduleDao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.task.controller.ScheduleController;
import com.task.modelBeans.ScheduleBean;

/**
 * 
 * 
 * DAO Class for saving and retriving the schedule saved in file
 * 
 * 
 */
@Repository
public class ScheduleDaoImpl implements ScheduleDao {

	private static final String filepath = "schedule";
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	//Saving a shedule to file
	public boolean saveSchedule(ScheduleBean schedule) {

		try {

			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

			objectOut.writeObject(schedule);
			objectOut.close();

			return true;
		} catch (Exception ex) {
			logger.error("Unable to create file");
			ex.printStackTrace();
			return false;

		}

	}

	//Retrieving a schedule to file
	public ScheduleBean getSchedule() {

		try {

			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			ScheduleBean obj = (ScheduleBean) objectIn.readObject();

			objectIn.close();
			return obj;
		} catch (Exception ex) {
			logger.error("Unable to read file");
			return null;
		}

	}
	//Delete schedule
	public boolean deleteSchedule() {

		try {

			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

			objectOut.writeChars("");;
			objectOut.close();

			return true;
		} catch (Exception ex) {
			logger.error("Unable to read file");
			return false;
		}

	}

}
