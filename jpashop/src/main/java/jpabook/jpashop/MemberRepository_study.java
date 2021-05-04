package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository_study {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member_study memberStudy){
        em.persist(memberStudy);
        return memberStudy.getId();
    }

    public Member_study find(Long id){
        return em.find(Member_study.class, id);
    }
}
