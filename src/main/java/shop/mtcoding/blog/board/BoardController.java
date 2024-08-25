package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller // 식별자 요청을 받을 수 있다. 이거 안붙이면 URI 매핑이 안됨
public class BoardController {

    private final BoardRepository boardRepository;
    private final HttpSession session;

//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private HttpSession session;

    @GetMapping("/test/board/1")
    public void testBoard() {
        List<Board> boardList = boardRepository.findAll();
        System.out.println("---------------------------------------------");
        System.out.println(boardList.get(2).getUser().getPassword());
        System.out.println("---------------------------------------------");
        //boardService.gooddd();
    }

//    @GetMapping("/test")
//    public @ResponseBody Board testBoard2() {
//        return Board.builder().title("랄랄").content("룰룰").build();
//    }

    // url : http://localhost:8080/board/1/update
    // body : title=제목1변경&content=내용1변경
    // content-type : x-www-form-urlencoded
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") Integer id, @RequestParam("title") String title, @RequestParam("content") String content) {
        boardRepository.updateById(id, title, content);
        return "redirect:/board/" + id;
    } // "board/detail" 로 가면 안됨, 응답하면 없어지니까 {{model.}} 안담기니까

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        boardRepository.deleteById(id);
        return "redirect:/board";
    }


    // 왜 save 로 했는지? -> post 로 update, insertn delete 다 할거라서
//    @PostMapping("/board/save")
//    public String save(@RequestParam("title") String title, @RequestParam("content") String content) { // 스프링 기본전략 x-www-form-urlencoded 파싱
//        boardRepository.save(title, content); // boardRepository 쓰려면 private 만들고 @Autowired
//        return "redirect:/board";
//    }
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 인증 체크 필요함
        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다."); // 모든걸 throw 로 모아서 앞단에서 처리하도록 해야한다.
        }

        boardRepository.save(saveDTO.toEntity(sessionUser));
        return "redirect:/board";
    }

    // get
    @GetMapping("/board") // 식별자 요청
    public String list(HttpServletRequest request) {
//        System.out.println(request.getRemoteAddr()); // reflection 으로 주입해줌
//        System.out.println(request.getRequestURI()); // null 이면 안뿌려질 것
        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("models", boardList); // session 에 저장하지 않고 request 에 저장
//        session.setAttribute("boardList", boardList);
        return "board/list";
    } // 매개변수에 (HttpServletRequest request) 적혀있네? 디스패처 서블릿(Dispatcher Servlet) 이 발견하고 DIP 로 때려줌

    // 상세 보기
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) { // 이건 문법, id 에 1이든 2든 아래로 return 될 것
        Board board = boardRepository.findById(id);
        request.setAttribute("model", board);
        request.setAttribute("isOwner", false); // 권한 설정을 위함
        return "board/detail"; // 슬래시 없어도 템플릿 경로로 바로 향함
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("model", board);
        return "board/update-form";
    }
}
