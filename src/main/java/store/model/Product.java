package store.model;

import java.util.Optional;
import store.common.Validator;

public class Product {
    private final static String NO_QUANTITY = "재고 없음";
    private final static int NAME_LOC = 0;
    private final static int PRICE_LOC = 1;
    private final String name;
    private final int price;
    private Optional<Promotion> promotion = Optional.empty();
    private int promotionQuantity = 0;
    private int normalQuantity = 0;

    public Product(String productName, String productPrice) {
        this.name = productName;
        this.price = Validator.validateNumber(productPrice);
    }

    public String getName() {
        return this.name;
    }

    public void updateQuantity(String quantity, Optional<Promotion> optionalPromotion) {
        int number = Validator.validateNumber(quantity);
        if (optionalPromotion.isPresent()) {
            this.promotion = optionalPromotion;
            this.promotionQuantity += number;
            return;
        }
        this.normalQuantity += number;
    }

    public String toString() {
        if (promotion.isPresent()) {
            Promotion productPromotion = promotion.get();
            return String.format("- %s %,d원 %s %s\n"
                            + "- %s %,d원 %s\n",
                    name, price, convertQuantity(promotionQuantity), productPromotion.getName(),
                    name, price, convertQuantity(normalQuantity));
        }
        return String.format("- %s %,d원 %s\n",
                name, price, convertQuantity(normalQuantity));
    }

    private String convertQuantity(int quantity) {
        if (quantity == 0) {
            return NO_QUANTITY;
        }

        return String.format("%,d개", quantity);
    }

    public Optional<Promotion> getPromotion() {
        return promotion;
    }

    public int getTotalQuantity() {
        return normalQuantity + promotionQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public void executeOrder(Boolean isPromotion, int orderQuantity) {
        if (isPromotion) {
            promotionQuantity -= orderQuantity;
            return;
        }
        normalQuantity -= orderQuantity;
    }

    public void updateQuantity(int orderQuantity) {
        if (orderQuantity >= promotionQuantity) {
            int leftQuantity = orderQuantity - promotionQuantity;
            promotionQuantity = 0;
            normalQuantity -= leftQuantity;
            return;
        }
        promotionQuantity -= orderQuantity;
    }

    public int exhaustPromotion(int nonPromotionQuantity) {
        if (promotionQuantity >= nonPromotionQuantity) {
            promotionQuantity -= nonPromotionQuantity;
            return 0;
        }

        int leftPromotionQuantity = promotionQuantity;
        promotionQuantity = 0;

        return nonPromotionQuantity - leftPromotionQuantity;
    }

    public int getPrice() {
        return price;
    }
}
