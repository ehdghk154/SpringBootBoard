package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // DI로 인한 QuestionRepository 생성 시 JPA가 메서드 명을 분석하여 쿼리를 만들고 실행
    // findBy + Entity 속성명 ex) findBySubject, findById, ...
    Question findBySubject(String subject);
    // And 조건으로 두 개의 속성 조회
    // 그 외에도 Or, Between, LessThan, GreaterThanEqual, Like, In, OrderBy 등이 있음
    // 이 중 응답 결과가 여러건일 경우 메서드의 타입을 Question이 아닌 List<Question>으로 해야 함
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
    
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "    q.subject like %:kw% "
            + "    or q.content like %:kw% "
            + "    or u1.username like %:kw% "
            + "    or a.content like %:kw% "
            + "    or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
