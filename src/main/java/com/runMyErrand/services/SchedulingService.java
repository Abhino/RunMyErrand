package com.runMyErrand.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import com.runMyErrand.logic.DateManager;
import com.runMyErrand.model.UserInfo;

/**
 * The class manages the functionalities which must be done periodically
 * Each method in the class is an independent thread which takes a cron expression;
 * cron expressions decides how frequently the method runs.
 * Moreover the class manages the Timeboxing operations
 *  
 *  @author Tejas
 */

public class SchedulingService {
	/**
	 * The String maintains the Current System Date
	 */
	private static String CurrentSystemDate;
	/**
	 * The String maintains the start date of timebox
	 */
	private static String TimeboxstartDate;
	/**
	 * The String maintains the end date of timebox
	 */
	private static String TimeboxendDate;
	
	@Value("${timeboxdays}")
	private static String timeboxdays;
	
	//@Value("${weekly.goal}")
    //private static String weeklyGoal;

	
	/** Get Timebox start date
	 * @return
	 */
	public static String getTimeboxstartDate() {
		return TimeboxstartDate;
	}

	/** Get Timebox end date
	 * @return
	 */
	public static String getTimeboxendDate() {
		return TimeboxendDate;
	}
	
	/** Get System date
	 * @return
	 */
	public static String getCurrentSystemDate(){
		return CurrentSystemDate;
	}
	
	

	public static void setCurrentSystemDate(String currentSystemDate) {
		CurrentSystemDate = currentSystemDate;
		logger.debug("CurrentDateSet:"+CurrentSystemDate);
	}

	public static void setTimeboxstartDate(String timeboxstartDate) {
		TimeboxstartDate = timeboxstartDate;
		logger.debug("TimeboxStartSet:"+TimeboxstartDate);
	}

	public static void setTimeboxendDate(String timeboxendDate) {
		TimeboxendDate = timeboxendDate;
		logger.debug("TimeboxEndSet:"+TimeboxendDate);
	}



	private static final Logger logger = Logger.getLogger(SchedulingService.class);
	//@Autowired
	//@Scheduled(cron = "0/5 * * * * ?")
	public static void changeCurrentDate(){
			
			SchedulingService.setCurrentSystemDate(DateManager.addDate(SchedulingService.CurrentSystemDate, 1));
			TaskServices.getTaskDao().setCurrentDate(SchedulingService.CurrentSystemDate);
			logger.debug("managingrecurrences");
			//Date todaysdate = new Date();
			//String date = DateManager.convertDateString(SchedulingService);
			TaskServices.checkRecurrence(SchedulingService.CurrentSystemDate);
			timebox();
	}
	
	
	
	//@Scheduled(cron = "*/500 * * * * ?")
	public static void timebox(){
		logger.debug("Timebox");
		String todaysdate = SchedulingService.CurrentSystemDate;
		if(DateManager.addDate(SchedulingService.TimeboxendDate, 1).equals(todaysdate)){
			SchedulingService.TimeboxstartDate = DateManager.addDate(todaysdate, 1);
			TaskServices.getTaskDao().setTimeboxStartDate(todaysdate);
			logger.info("TimeboxStartDate update:"+SchedulingService.TimeboxstartDate);
			SchedulingService.TimeboxendDate = DateManager.addDate(todaysdate, 7);
			logger.debug(timeboxdays);
			TaskServices.getTaskDao().setTimeboxEndDate(SchedulingService.TimeboxendDate);
			logger.info("Timebox end date update:"+SchedulingService.getTimeboxendDate());
			List<String> rooms = TaskServices.getRooms();
		//	logger.debug("Weekly Goal: "+weeklyGoal);
			//int goals = Integer.parseInt(weeklyGoal);
			for(int i=0; i<rooms.size(); i++){
				List<UserInfo> users = UserServices.selectMembers(rooms.get(i));
				//float points = TaskServices.getTimeboxPoints(rooms.get(i));
			//	MemberServices.updatePoints(points, rooms.get(i));
				for(int j=0; j<users.size(); j++){
					UserInfo u = users.get(j);
					float score = 0;
					//float score = TaskServices.changeUserScore(u.getEmail());
				//	float nowpending =  MemberServices.updatePendingScore(u.getRoom(), score);
					UserServices.getUserDao().setScore(u.getEmail(), score);
				//	UserServices.updateUserScore(u.getEmail(), score, u.getPendingscore() + nowpending);
//					UserServices.updateWeeklyGoal(u.getEmail(), u.getWeeklygoal()+15);
					UserServices.updateWeeklyGoal(u.getEmail(), u.getWeeklygoal()+15);					
				}
			}
		}
		
	}
}
