package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Setter
@Getter
@Table(name = "user_tb")
@NoArgsConstructor
@Entity // 테이블을 만들어줘
public class User {
    @Id // PK 설정 primary key 가 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_increment 설정, 시퀀스 설정
    private Integer id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder // 빌더는 생성자로 관리하는 것을 추천, 유저클래스에 붙일 수는 있는데 private List<> 이런거 붙으면 터질 수 있음
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
}
