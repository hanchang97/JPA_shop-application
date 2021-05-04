package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 조회 - 안 좋은 ver  -> api응답 스펙에 맞추어 별도의 DTO를 반환하도록 하자!
    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findMembers();  // 단순히 모든 멤버 리스트 넘기는 것
    }

    // 회원 조회 - good ver
    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDTO> collect = findMembers.stream()
                .map(m->new MemberDTO(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{  // json 배열 타입으로 바로 나가지 않게 이렇게 해줘야 한다
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO{  // 현재 단순히 이름 정보만 넘기는 api라고 가정
        private String name;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 회원 등록
    @PostMapping("/api/v2/members")  // v2 가 변경사항/ 오류 잡는 것에 더 좋다
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){  // 요청 형식

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);  // 응답 형식
    }

    // 회원 정보 수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.getName());

        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    @Data
    static class CreateMemberRequest{  // not empty 같은 조건 엔티티 말고 여기서 api별로 각각 해주자!
        @NotEmpty  // 값이 반드시 있어야 한다!
        private String name;

    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest{  // 바디에 입력해서 보내줘야 한다
        private String name;
    }

    @Data
    @AllArgsConstructor  // 모든 필드에 대한 생성자
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }
}
