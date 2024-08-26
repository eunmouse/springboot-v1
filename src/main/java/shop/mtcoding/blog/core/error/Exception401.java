package shop.mtcoding.blog.core.error;

// 인증 관련 (인증이 안되어있을 때, 얘를 터트릴거야)
public class Exception401 extends RuntimeException {

    public Exception401(String message) {
        super(message);
    }
}
