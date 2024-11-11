package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PromotionServiceTest {

    PromotionService promotionService = new PromotionService();

    @Test
    void findMissingPromotionQuantity() {

        Promotion promotion = new Promotion(new String[]{"반짝할인", "2", "1", "2024-11-01", "2024-11-30"});

        int result = promotionService.findMissingPromotionQuantity(promotion, 5, 8);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void 프로모션_재고를_넘는_경우() {
        Promotion promotion = new Promotion(new String[]{"반짝할인", "2", "1", "2024-11-01", "2024-11-30"});

        int orderQuantity = 8;
        int promotionQuantity = 7;

        int result = promotionService.findOverPromotionQuantity(promotion, orderQuantity, promotionQuantity);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void 프로모션_적용_숫자_계산_주문량이_더작은경우() {
        Promotion promotion = new Promotion(new String[]{"반짝할인", "2", "1", "2024-11-01", "2024-11-30"});

        int promotionQuantity = 9;

        int[] result = promotionService.calculatePromotionApplyQuantity(promotion, 6, promotionQuantity);

        assertThat(result).isEqualTo(new int[]{6, 2});
    }

    @Test
    void 프로모션_적용_숫자_계산_주문량이_남은_프로모션보다_많은경우() {
        Promotion promotion = new Promotion(new String[]{"반짝할인", "2", "1", "2024-11-01", "2024-11-30"});

        int promotionQuantity = 5;

        int[] result = promotionService.calculatePromotionApplyQuantity(promotion, 9, promotionQuantity);

        assertThat(result).isEqualTo(new int[]{3, 1});
    }
}