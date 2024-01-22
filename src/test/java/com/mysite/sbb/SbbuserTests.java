package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

@SpringBootTest
class SbbuserTests {
    
    @Autowired
    private UserService userService;
    
	@Test
	void testJpa() {
	    SiteUser u = userService.getUser("ehdghk154");
	    if(u.getUsername().equals("ehdghk154")) {
	    	System.out.println("Success");
	    }else {
	    	
	    	System.out.println(u.getUsername());
	    	System.out.println("Fail");
	    }
	}

}
