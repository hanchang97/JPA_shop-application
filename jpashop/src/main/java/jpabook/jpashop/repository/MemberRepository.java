package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {  // 제네릭에 타입, 아이디 넣어준다

    // select m from Member m whrere m.name =    이 자동 실행 된다  / findBy' ' 형식!!
    List<Member> findByName(String name);
}
