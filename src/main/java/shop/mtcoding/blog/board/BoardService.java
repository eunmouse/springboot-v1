package shop.mtcoding.blog.board;

// C -> S -> R 의존 - 의존 하니, 코드를 수정해야겠군

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // 컨트롤러에서 무조건 sessionUser 받아와야 한다. 인증체크도 해야하고
    public BoardResponse.DetailDTO 상세보기(int id, User sessionUser) { // 기능명이니까 한글로 ^^ 기능명은 정확하게 적어야하니까
        Board board = boardRepository.findById(id); // 조인 (Board - User)
//        boolean isOwner = false;
//
//        if(board.getUser().getId() == sessionUser.getId()) { // 같으면 이 게시글의 주인
//            isOwner = true;
//        }
        // 2개를 리턴 할 수 없음 + Lazy 모드 끌거라서 응답DTO 만든다.
        return new BoardResponse.DetailDTO(board, sessionUser);
    }
}
