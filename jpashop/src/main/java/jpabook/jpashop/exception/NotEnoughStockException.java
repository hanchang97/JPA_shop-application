package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException{  // 수량 빼는 경우 예외

    public NotEnoughStockException() {
    }
    public NotEnoughStockException(String message) {
        super(message);
    }
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
