package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

@SpringBootTest
class SbbApplicationTests {
    
    @Autowired
    private AnswerService answerService;
    private QuestionService questionService;
    private UserService userService;
    
	@Test
	void testJpa() {
	    Question q = this.questionService.getQuestion(308);
	    SiteUser u = userService.getUser("ehdghk154");
	    for(int i = 1; i <= 20; i++) {
	        String content = String.format("답변페이징테스트[:%d]", i);
	        this.answerService.create(q, content, u);
	    }
	}

}
