package com.mysite.sbb.question;

// 설명 : https://wikidocs.net/161165
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 엔티티에서는 데이터베이스와 바로 연결되어 있으므로 데이터를 자유롭게 변경할 수 있는 Setter 메서드를 허용하는 것이 안전하지 않다고
        // 판단하기 때문에 Setter 메서드를 사용하지 않기를 권함.
@Entity
public class Question {
    @Id
    // strategy = GenerationType.IDENTITY : 기본키 생성을 데이터베이스에 위임(자동생성)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 엔티티의 속성은 @Column 애너테이션을 사용하지 않더라도 테이블 컬럼으로 인식. 테이블 컬럼으로 인식하고 싶지 않은 경우에만
    // @Transient 애너테이션을 사용
    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    // createDate 속성의 실제 테이블의 컬럼명은 create_date가 됨
    // 즉, createDate처럼 대소문자 형태의 카멜케이스(Camel Case) 이름은 create_date 처럼 모두 소문자로 변경되고
    // 언더바(_)로 단어가 구분되어 실제 테이블 컬럼명이 됨.
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // CascadeType.REMOVE=질문이 삭제되면 답변들도 삭제됨.
    private List<Answer> answerList;
    
    @ManyToOne
    private SiteUser author;
    
    private LocalDateTime modifyDate;
    
    @ManyToMany
    Set<SiteUser> voter;
}
