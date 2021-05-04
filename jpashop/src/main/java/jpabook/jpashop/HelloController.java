package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello22")  // hello url 오면 이 컨트롤러 호출
    public String hello2(Model model){  // 컨트롤러에서 모델에 값을 얹어서 뷰로 넘긴다
        model.addAttribute("data", "hello!!!");
        // 키값 = data / 실제 값 = hello!!!
        return "hello22";  // 화면 이름
    }
}
