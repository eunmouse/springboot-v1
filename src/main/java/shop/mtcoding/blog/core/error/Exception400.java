package shop.mtcoding.blog.core.error;

// 400 번대 에러
// 유효성 검사
public class Exception400 extends RuntimeException {  // 상속은 타입이 일치하여야한다. Exception400 도 RuntimeException 이다.
    // 실행 중에 exception 은 다 RuntimeException 임

    public Exception400(String message) {
        super(message);
    }
}
