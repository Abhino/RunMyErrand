package com.runMyErrand.controllers.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DashboardControllerTest.class,LoginControllerTest.class,TaskControllerTest.class,UserControllerTest.class,TestMasterTaskServices.class,TestMemberServices.class,TestTaskServices.class,TestUserServices.class})
public class AllTests {

	 
}
