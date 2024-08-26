package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.core.error.Exception404;

import java.util.List;

@Repository // @Repository 를 붙이면 스프링이 new 를 해서 IoC(컬렉션 List 자료형 같은거) 에 저장한다.
public class BoardRepository { // heap 에 올려서 new 되는거

    @Autowired // IoC에 있는 객체를 찾아온다.
    private EntityManager em;

    // 게시글 수정
    @Transactional
    public void updateById(int id, String title, String content) { // id 는 주소로 받고, title, content 는 바디데이터로 받는다.
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        query.executeUpdate(); // 변경하는건 전부 executeUpdate(); @Transactional 걸린건 전부 executeUpdate();
    }

    // 게시글 삭제
    @Transactional // transaction 을 묶어주는 레이어가 없으니, 지금은 여기 건다.
    public void deleteById(int id) { // '게시글삭제'라는 기능명을 적으면 안됨
        Query query = em.createNativeQuery("delete from board_tb where id = ?"); // 조회할때만 매핑 필요해서 삭제에서는 필요없음
        query.setParameter(1, id);
        query.executeUpdate();
    }

    // createNativeQuery 사용하려면 아래와 같이 해야함
//    public Board findByIdV2(int id) {
//        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?", Board.class);
//        query.setParameter(1, id);
//        try {
//            Board board = (Board) query.getSingleResult();
//            return board;
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 익세션을 내가 잡은것 까지 배움 - 처리 방법은 v2에서 배우기
//            throw new RuntimeException("게시글 id를 찾을 수 없습니다");
//        }
//    }

    // 상세보기
    public Board findById(int id) { // Board 가 User 객체를 가지고있어서 자동매핑되니까 DTO 로 안만들어도돼 너무좋아
        // 객체 지향 쿼리 JPQL 하이버네이트 JPA 가 제공해주는 거임 // JPQL 은 ? 를 안씀 :id 넣음
        // JPQL 을 쓰면 이리도 쉽게 되니 Board.class
        // Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        // Query query = em.createNativeQuery("select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?", Board.class);
        // query.setParameter(1, id); // nativequery 하면 Board.class 매핑이 안됨
        // on 절이 필요없어 알아서 매핑
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class); // Board.class 로 자동매핑
        query.setParameter("id", id);

        try {
            Board board = (Board) query.getSingleResult(); // 1개의 데이터니까
            return board;
        } catch (Exception e) {
            e.printStackTrace();
            // exception 을 내가 잡은 것 까지 배움 - 처리 방법은 V2 에서 배우기
            throw new Exception404("게시글 id를 찾을 수 없습니다.");
        }
    }

    public List<Board> findAll() { // transaction 은 insert, update 할 떄 commit 이 필요할때만 붙기때문에 얘는 안붙여도됨
        // JPQL 이 실제로 NativeQuery 는 아님, 실제로 실행될때 하이버네이트가 NativeQuery 로 바꿔준다.
        // 장점: 데이터베이스 마이그레이션 할 때 편함, 자동변환됨
        // 오라클 조인이랑 mysql 조인 문법이 조금 다름
        // JPQL 쓰면 오라클에 맞게 변경되어서 발생되고, mysql에 맞게 변경되어서 발생됨 굿
        // Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = query.getResultList(); // 5개의 데이터니까

        return boardList;
    }

    // insert 하기
    @Transactional
    public void save(Board board) {
        em.persist(board); // 담길 때 영속 객체가 된다.
    }

    // insert 하기
//    @Transactional
//    public void save(String title, String content) {
//        Query query = em.createNativeQuery("insert into board_tb(title, content, created_at) values(?,?,now())");
//        query.setParameter(1, title);
//        query.setParameter(2, content);
//
//        query.executeUpdate(); // em 이 위의 해당 쿼리를 완성시켜서 전송
//    } // insert 나 delete 할 때는 query.executeUpdate();
}
