package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    // 상세보기
    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class); // Board.class 로 자동매핑
        query.setParameter(1, id);

        try {
            Board board = (Board) query.getSingleResult(); // 1개의 데이터니까
            return board;
        } catch (Exception e) {
            // exception 을 내가 잡은 것 까지 배움 - 처리 방법은 V2 에서 배우기
            throw new RuntimeException("게시글 id를 찾을 수 없습니다.");
        }
    }

    public List<Board> findAll() { // transaction 은 insert, update 할 떄 commit 이 필요할때만 붙기때문에 얘는 안붙여도됨
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        List<Board> boardList = query.getResultList(); // 5개의 데이터니까

        return boardList;
    }

    // insert 하기
    @Transactional
    public void save(String title, String content) {
        Query query = em.createNativeQuery("insert into board_tb(title, content, created_at) values(?,?,now())");
        query.setParameter(1, title);
        query.setParameter(2, content);

        query.executeUpdate(); // em 이 위의 해당 쿼리를 완성시켜서 전송
    } // insert 나 delete 할 때는 query.executeUpdate();
}
