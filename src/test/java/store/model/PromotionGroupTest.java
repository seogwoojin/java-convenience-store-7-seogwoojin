package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PromotionGroupTest {

    @Test
    void 프로모션_그룹_생성후_찾기() {
        PromotionGroup promotionGroup = new PromotionGroup();
        Promotion promotion = new Promotion(new String[]{"반짝할인", "1", "1", "2024-11-01", "2024-11-30"});

        promotionGroup.addPromotion(promotion);

        assertThat(promotionGroup.findByName("반짝할인")).isSameAs(promotion);
    }

}