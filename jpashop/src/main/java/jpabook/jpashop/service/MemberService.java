package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service    // 스프링에서 service 어노테이션 제공
@Transactional        // jpa 모든 데이터 변경, 로직 등은 가급적 트랜잭션 안에서 실행되게!  / javax 말고 스프링에서 제공하는 어노테이션 사용
@RequiredArgsConstructor   // final로 설정된 필드만 가지고 생성자 만들어준다 / 밑에 autowired 부분과 같은 역할
public class MemberService {


    private final MemberRepository memberRepository;  // 변경할 일이 없기에 final 권장

    /*@Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;    // 생성자 인젝션이 굳
    }*/

    // 회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);  // 중복 회원 검증 로직
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());  // findByName 같은 경우 레포지토리에서 만들어줘야함
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)   // 읽기 전용 해주면 성능 조금 더 최적화
    public List<Member> findMembers(){

        return memberRepository.findAll();
    }  //

    // 한 건 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){

        return memberRepository.findById(memberId).get();
    }


    @Transactional
    public void update(Long id, String name) {  // 회원 정보 수정  /  name =  변경된 값
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
