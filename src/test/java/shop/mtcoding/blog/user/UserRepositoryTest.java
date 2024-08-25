package shop.mtcoding.blog.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest // h2, em
@Import(UserRepository.class) // br
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameAndPassword_test() {
        // given
        String username = "ssar";
        String password = "1234";
        // when
        User user = userRepository.findByUsernameAndPassword(username, password);
        // eye
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getCreatedAt());
    }

    @Test
    public void save_test() {
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";

//        userRepository.save(username, password, email);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);
        // userRepository.save(User.builder().username(username).password(password).email(email).build());
    }
}
