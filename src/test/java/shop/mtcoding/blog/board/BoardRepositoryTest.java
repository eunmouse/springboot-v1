package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

//@SpringBootTest // C R e h2 -> 모든 레이어를 메모리에 다 올리고 테스트할 때 사용하는 어노테이션
// 다 띄울 필요가 없으니 주석처리, 나는 지금 Repository 이후로 테스트 할거거든
@DataJpaTest // h2, em
@Import(BoardRepository.class) // br
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void updateById_test() {
        // given 매개변수 3개
        int id = 1;
        String title = "제목변경1";
        String content = "내용변경1";

        // when
        boardRepository.updateById(id, title, content);

        // then 실제로 검증해본거 (eye 눈으로 보는거는 sysout)
        Board board = boardRepository.findById(id);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목변경1");
    }

    @Test
    public void deleteById_test() {
        // given
        int id = 1;

        // when
        boardRepository.deleteById(id);

        // eye
        boardRepository.findById(id); // 조회했을 때, NoResultException 이 떠야함
//        try {
//            boardRepository.findById(id);
//        } catch (Exception e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("게시글 id를 찾을 수 없습니다.");
//        }
    }

//    @Test
//    public void findByIdV2_test() {
//        int id = 1;
//
//        Board board = boardRepository.findByIdV2(id);
//        System.out.println(board.getUser().getUsername());
//    }

    @Test
    public void findById_test() {
        // given -> 가짜로 아이디를 만들어 테스트
        int id = 1;

        // when -> 메서드 바로 테스트
        Board board = boardRepository.findById(id);

        // eye -> 눈으로 검증하는거
        System.out.println(board.getId());
        System.out.println(board.getTitle());
        System.out.println(board.getContent());

        // then -> 코드로 검증하는거 // T 는 제네릭이라 뭐가와도됨
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");

    }

    @Test
    public void findAll_test() {
        // given

        // when
        System.out.println("1. 첫번째 조회");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("userId : " + boardList.get(0).getUser().getId());
        System.out.println("=======================");
        // eye
//        System.out.println("사이즈 : " + boardList.size());
        System.out.println("2. 레이지 로딩");
        System.out.println("title : " + boardList.get(0).getUser().getUsername());
        System.out.println("title : " + boardList.get(3).getUser().getUsername());
        System.out.println("title : " + boardList.get(4).getUser().getUsername());
//        for (Board board : boardList) {
//            System.out.println(board.getId());
//            System.out.println(board.getTitle());
//            System.out.println(board.getContent());
//            //System.out.println(board.getUser().getId());
//        }
    }

    @Test // 테스트 메서드에는 매개변수를 사용할 수 없다.
    public void save_test() { // 메서드명_test : 컨벤션(=약속)
        // given (매개변수를 강제로 만들기)
        String title = "제목1"; // null 넣어서 제약조건 잘걸렸는지 확인하고
        String content = "내용1";

        // when
//        boardRepository.save(board);

        // eye (눈으로 확인)
    }
}
