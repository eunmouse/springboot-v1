package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.core.error.Exception400;

@RequiredArgsConstructor
@Service
public class UserService { // userRepository 에 의존
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) { // 동일한게 있으면 안되니, 조회도 해야함
        User oldUser = userRepository.findByUsername(joinDTO.getUsername());

        if (oldUser != null) {
            throw new Exception400("이미 존재하는 유저네임입니다.");
        }
        userRepository.save(joinDTO.toEntity());
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        return user;
    }
}
