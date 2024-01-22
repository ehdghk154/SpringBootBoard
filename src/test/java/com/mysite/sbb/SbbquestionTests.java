package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

@SpringBootTest
class SbbquestionTests {
    
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    
	@Test
	void testJpa() {
		SiteUser user = userService.getUser("test1");
		
	    for(int i = 1; i <= 20; i++) {
	    	String title = String.format("질문테스트[:%d]", i);
	        String content = String.format("질문내용[:%d]", i);
	        this.questionService.create(title, content, user);
	    }
	}

}
