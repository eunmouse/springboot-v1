package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;

@NoArgsConstructor // 빈 생성자 (하이버네이트가 om(object mapping 할때 필요)
@Setter
@Getter
@Table(name = "board_tb")
@Entity // DB에서 조회하면 자동 매핑이됨
public class Board {
    // id 가 1씩 증가되는게 설정됨
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_increment 설정, 시퀀스 설정
    @Id // PK 설정 primary key 가 설정
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    // fk
    // N:1 (Board:User)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // JPA 는 Integer user_id 가 아니라 오브젝트 타입을 적는다.

    @Builder
    public Board(Integer id, String title, String content, Timestamp createdAt, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}
