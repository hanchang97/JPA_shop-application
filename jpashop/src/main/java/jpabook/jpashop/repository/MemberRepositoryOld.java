package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository   // 스프링에서 제공하는 기능 사용
@RequiredArgsConstructor
public class MemberRepositoryOld {

   /* @PersistenceContext
    private EntityManager em; // 스프링이 앤티티매니저 만들어서 주입시켜준다 */

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);   // jpa가 저장   // 트랜잭션 커밋순간 db에 반영
    }

    // 단건 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    // 리스트 조회
    public List<Member> findAll(){
        // 전부 찾는 것
        return em.createQuery("select m from Member m", Member.class)  // ( jpql, 조회 반환 타입)
            .getResultList();
    }

    // 이름으로 검색
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)  // 파라미터 바인딩
                .getResultList();
    }
}
