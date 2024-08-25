package shop.mtcoding.blog.user;

import lombok.Data;

public class UserRequest {

    // DTO 만드는 목적 : 요청데이터 바디로 받으려고, 관리하기가 편함
    @Data // 안에 getter, setter, toString 있삼
    public static class JoinDTO { // static 붙이면 main 시작하기 전에 메모리에 뜬다.
        private String username;
        private String password;
        private String email;

        // DTO -> UserObject
        // insert 할때만 필요
        public User toEntity() { // 이 메서드의 책임은 DTO 를 entity 로 바꿔주는 역할
            return User.builder().username(username).password(password).email(email).build();
        }
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
