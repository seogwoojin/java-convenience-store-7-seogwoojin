package store.model;

import java.util.List;

public class Receipt {
    private static final int MEMBERSHIP_LIMIT = 8000;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;

    private final List<OrderResult> orderResults;
    private final Boolean isMembership;

    public Receipt(List<OrderResult> orderResults, Boolean isMembership) {
        this.orderResults = orderResults;
        this.isMembership = isMembership;
    }

    public List<OrderResult> getOrderResults() {
        return orderResults;
    }


    public int calculateTotalPrice() {
        return orderResults.stream()
                .mapToInt(OrderResult::getTotalPrice)
                .sum();
    }

    public int calculateTotalDiscount() {
        return orderResults.stream()
                .mapToInt(OrderResult::getFreeGiftPrice)
                .sum();
    }

    public int calculateMembershipDiscount() {
        if (!isMembership) {
            return 0;
        }

        int nonPromotionPrice = calculateNonPromotionPrice();
        int discountPrice = (int) (nonPromotionPrice * MEMBERSHIP_DISCOUNT_RATE);

        return Math.min(discountPrice, MEMBERSHIP_LIMIT);

    }

    private int calculateNonPromotionPrice() {
        return orderResults.stream()
                .mapToInt(OrderResult::getNonPromotionPrice)
                .sum();
    }
}
