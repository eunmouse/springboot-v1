package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;
//    @Autowired
//    private EntityManager em;

    public User findByUsernameAndPassword(String username, String password) {
        // 조회커리 만들거임
        // JPQL 은 ? 가 없어서 : 쓴다. user 만 select 한거니, 오브젝트매핑됨(오브젝트릴레이션매핑아님)
        Query query = em.createQuery("select u from User u where u.username=:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Transactional
    public void save(User user) { // user 는 heap 에 올라와있는 user(3개 갖고있는)를 가리키는 주소임
        System.out.println("담기기전 : " + user.getId()); // 외부에서 받아서 new 된 id 가 없는 비영속 객체
        em.persist(user); // 담길때 영속객체가 된다.
        System.out.println("담긴후 : " + user.getId());
    } // user 는 변한게 아니라, 담궈서 초콜릿아이스크림 된 셈

//    @Transactional
//    public void save(String username, String password, String email) { // repository 에는 메서드명 기능명 넣지 않기, join ㄴㄴ
//
//        User user = new User(); // 쿼리 없이 유저객체 하나 만들었삼
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//
//        em.persist(user);
//    }
}
