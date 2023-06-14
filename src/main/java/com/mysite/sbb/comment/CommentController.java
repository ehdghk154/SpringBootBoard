package com.mysite.sbb.comment;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final AnswerService answerService;
    private final CommentService commentService;
    private final UserService userService;
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}/{page}")
    public String createComment(Model model, @PathVariable("id") Integer id, @PathVariable("page") int page, 
            CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("answer", answer);
            return "question_detail";
        }
        this.commentService.create(answer, commentForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s?page=%s#answer_%s", answer.getQuestion().getId(), page, answer.getId());
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}/{page}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id, @PathVariable("page") int page) {
        Comment comment = this.commentService.getComment(id);
        Answer answer = comment.getAnswer();
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.commentService.vote(comment, siteUser);
        return String.format("redirect:/question/detail/%s?page=%s#answer_%s", answer.getQuestion().getId(), page, answer.getId());
    }
}
