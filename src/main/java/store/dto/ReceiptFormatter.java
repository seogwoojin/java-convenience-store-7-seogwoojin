package store.dto;

import store.model.OrderResult;
import store.model.Receipt;

public class ReceiptFormatter {

    private static final String HEADER = "==============W 편의점================\n";
    private static final String PRODUCT_HEADER = String.format("%-8s\t\t%3s\t%10s\n", "상품명", "수량", "금액");
    private static final String GIFT_HEADER = "=============증\t\t정===============\n";
    private static final String FOOTER = "====================================\n";

    public static String format(Receipt receipt) {
        StringBuilder sb = new StringBuilder();

        sb.append(HEADER);
        sb.append(PRODUCT_HEADER);

        // Format each order's details
        sb.append(formatOrderDetails(receipt));

        // Add gift section
        sb.append(GIFT_HEADER);
        sb.append(formatGiftDetails(receipt));

        // Add total, discounts, and final amount
        sb.append(FOOTER);
        sb.append(formatSummary(receipt));

        return sb.toString();
    }

    private static String formatOrderDetails(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        for (OrderResult order : receipt.getOrderResults()) {
            sb.append(String.format("%-8s\t\t%3d\t%,16d\n",
                    order.getProductName(),
                    order.getTotalQuantity(),
                    order.getTotalPrice()));
        }
        return sb.toString();
    }

    private static String formatGiftDetails(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        for (OrderResult promo : receipt.getOrderResults()) {
            if (promo.getFreeGiftQuantity() > 0) {
                sb.append(String.format("%-8s\t\t%,20d\n",
                        promo.getProductName(),
                        promo.getFreeGiftQuantity()));
            }
        }
        return sb.toString();
    }

    private static String formatSummary(Receipt receipt) {
        int totalPrice = receipt.calculateTotalPrice();
        int giftDiscount = receipt.calculateTotalDiscount();
        int membershipDiscount = receipt.calculateMembershipDiscount();
        int finalAmount = totalPrice - giftDiscount - membershipDiscount;
        int totalOrderQuantity = receipt.getOrderResults().stream()
                .mapToInt(OrderResult::getTotalQuantity)
                .sum();

        return String.format("%-8s\t\t%3d\t%,16d\n", "총구매액", totalOrderQuantity, totalPrice) +
                String.format("%-8s\t\t%,20d\n", "행사할인", -giftDiscount) +
                String.format("%-8s\t\t%,20d\n", "멤버십할인", -membershipDiscount) +
                String.format("%-8s\t\t%,20d\n", "내실돈", finalAmount);
    }

}
