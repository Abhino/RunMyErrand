package com.runMyErrand.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.runMyErrand.model.UserInfo;
import com.runMyErrand.services.TaskServices;
import com.runMyErrand.services.UserServices;

@Controller
@SessionAttributes("user")
public class DashboardController {
	
	private static final Logger logger = Logger.getLogger(DashboardController.class);
	
	
	@RequestMapping("/dashboard**")	
	public ModelAndView dashboard(HttpSession session)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	   		
		String username = auth.getName();
		logger.debug("Entered dashboard "+ username);
		UserInfo user = UserServices.selectUser(username); 
		ModelAndView model = new ModelAndView("Dashboard");
		ArrayList list_roomy = (ArrayList) UserServices.selectMyRoomies(user.getRoom(), user.getEmail());//getting other roommates
		logger.debug(list_roomy);
		ArrayList list_task = (ArrayList) TaskServices.retriveAllTasks(user.getRoom());
		logger.debug(list_task);
		ArrayList mytasks = (ArrayList) TaskServices.retrieveMyTasks(user.getEmail());
		logger.debug("LOgin:"+mytasks);
		
		session.setAttribute("user", user);
		session.setAttribute("roomies", list_roomy);
		
		model.addObject("user", user);
		model.addObject("roomies", list_roomy);
		model.addObject("tasks", list_task);
		model.addObject("mytasks", mytasks);
				
		return model;
	}
	
	//public static ModelAndView myRoomies()

}