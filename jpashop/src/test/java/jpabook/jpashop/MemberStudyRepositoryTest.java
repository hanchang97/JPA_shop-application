package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberStudyRepositoryTest {

    // MemberRepository 잘 되는지 test
    @Autowired
    MemberRepository_study memberRepositoryStudy;

    @Test
    @Transactional   // 스프링 걸로 사용하기
    @Rollback(false)  // 롤백 false 로 하면 테스트모드에서 자동으로 롤백 안함 / 디비 저장된다!
    public void testMember() throws Exception{
        //given
        Member_study memberStudy = new Member_study();
        memberStudy.setUserName("memberA");

        // when   저장
        Long saveId = memberRepositoryStudy.save(memberStudy);
        Member_study findMemberStudy =  memberRepositoryStudy.find(saveId);

        // then   / 잘 저장되었는지 검증
        Assertions.assertThat(findMemberStudy.getId()).isEqualTo(memberStudy.getId());
        Assertions.assertThat(findMemberStudy.getUserName()).isEqualTo(memberStudy.getUserName());
        Assertions.assertThat(findMemberStudy).isEqualTo(memberStudy);  // 영속성 컨텍스트에서 식별 값 같으면 같은 엔티티로 인식

    }
}