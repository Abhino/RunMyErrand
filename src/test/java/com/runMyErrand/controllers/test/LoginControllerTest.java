
 
package com.runMyErrand.controllers.test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.runMyErrand.controllers.LoginController;
import com.runMyErrand.services.UserServices;
@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	@InjectMocks
	static
	LoginController logincon;
	@Mock
	UserServices userserv;
	static LoginController stLoginController=null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stLoginController=new LoginController();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		stLoginController=null;
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogout()
	{
		System.out.println("inside test 3");
		assert stLoginController.logout()!=null:"the logout return valueis not null";
		
	}
	public void testloginfailed()
	{
		ModelAndView model = new ModelAndView("signin");
		model.addObject("error", "Invalid Username Or Password");
		assert model!=null :"checking for login null";
		
	}
}
