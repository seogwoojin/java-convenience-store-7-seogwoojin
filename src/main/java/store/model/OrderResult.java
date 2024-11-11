package store.model;

public class OrderResult {
    private final String productName;
    private final int productPrice;
    private final int promotionApplyQuantity;
    private final int nonPromotionQuantity;
    private final int freeGiftQuantity; //promotionApplyQuantity 에도 포함됨

    public OrderResult(String productName, int productPrice, int promotionApplyQuantity, int nonPromotionQuantity,
                       int freeGiftQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.promotionApplyQuantity = promotionApplyQuantity;
        this.nonPromotionQuantity = nonPromotionQuantity;
        this.freeGiftQuantity = freeGiftQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalQuantity() {
        return promotionApplyQuantity + nonPromotionQuantity;
    }

    public int getTotalPrice() {
        return productPrice * getTotalQuantity();
    }

    public int getFreeGiftQuantity() {
        return freeGiftQuantity;
    }

    public int getFreeGiftPrice() {
        return productPrice * getFreeGiftQuantity();
    }

    public int getNonPromotionPrice() {
        return productPrice * nonPromotionQuantity;
    }
}
