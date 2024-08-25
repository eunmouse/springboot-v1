package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor // final 붙은 애들의 생성자를 만들어줌
@Controller // 화면 연결이 먼저 되어야 한다.
public class UserController {

    private final UserRepository userRepository;
    private final HttpSession session;

//    @Autowired // IoC 에 있으니까 오토와이어드만 하면 됨 힛
//    private HttpSession session;

//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    // 첫번째 전략, 변수로 받는 것
//    @PostMapping("/join") // 핵심로직 먼저 짜고, 부가적인거 나중에 짜자
//    public String join(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) {
//        userRepository.save(username, password, email);
//        return "redirect:/login-form";
//    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 락카의 특정서랍 세션 ID 부분을 날리는 것
        return "redirect:/board";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        session.setAttribute("sessionUser", sessionUser); // 락카에 담아
        return "redirect:/board";
    }

    //두번째 전략, 클래스로 받는 것
    @PostMapping("/join") // 핵심로직 먼저 짜고, 부가적인거 나중에 짜자
    public String join(UserRequest.JoinDTO joinDTO) {
        userRepository.save(joinDTO.toEntity());
        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/login-form";
    }
}