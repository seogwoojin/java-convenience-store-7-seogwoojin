package store.common;

public class ExceptionMessage {
    public static final String ERROR_HEAD ="[ERROR] ";
    public static final String PROMOTION_VALIDATION_ERROR = "promotions.md 형식이 올바르지 않습니다";
    public static final String PRODUCT_VALIDATION_ERROR = "products.md 형식이 올바르지 않습니다";
    public static final String PROMOTION_NOT_FOUND_ERROR = "등록된 프로모션이 없습니다.";
    public static final String ORDER_QUANTITY_ERROR = "주문 수량이 상품 전체 수량보다 많습니다.";
    public static final String CHECK_FORMAT_ERROR = "잘못된 입력입니다. 체크 형식은 Y/N 으로 표현 가능합니다. 다시 입력해 주세요.";
}
