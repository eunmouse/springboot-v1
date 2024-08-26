package shop.mtcoding.blog.core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blog.core.util.Script;

//ConrollerAdvice // 리턴할때 파일로 응답하고, 얘 쓰려면 view 를 찾으니까 @ResponseBody 넣어주면 됨
@RestControllerAdvice // 이걸 걸면 모든 throw 는 얘한테 모임 / 얘는 리턴할때 데이터로 응답함
public class GlobalExceptionHandler {

    // 유효성 검사 실패 (잘못된 클라이언트의 요청)
    @ExceptionHandler(Exception400.class) // 400 은 바디데이터가 잘못들어왔을 떄
    public String ex400(Exception e) { // Exception 의 throw 한 getMessage 가 올테니
        return Script.back(e.getMessage());
    }

    // 인증 실패 (클라이언트가 인증없이 요청했거나, 인증을 하거나 인증을 실패했거나)
    @ExceptionHandler(Exception401.class)
    public String ex401(Exception e) {
        return Script.href("인증되지 않았습니다", "/login-form");
    }

    // 권한 실패 (인증은 되어 있는데, 삭제하려는 게시글이 내가 적은게 아니다)
    @ExceptionHandler(Exception403.class)
    public String ex403(Exception e) {
        return Script.back(e.getMessage());
    }

    // 서버에서 리소스(자원) 찾을 수 없을 때
    @ExceptionHandler(Exception404.class) // 페이지가 아니라 resource 를 찾을 수 없다는 것으로, DB 의 자원이 될수도있고..
    public String ex404(Exception e) {
        return Script.back(e.getMessage());
    }

    // 서버에서 심각한 오류가 발생했을 때 (알고 있을 때)
    @ExceptionHandler(Exception500.class)
    public String ex500(Exception e) {
        return Script.back(e.getMessage());
    }

    // 서버에서 심각한 오류가 발생했을 때 (모를 때)
    @ExceptionHandler(Exception.class)
    public String ex(Exception e) {
        return Script.back(e.getMessage());
    }
}
