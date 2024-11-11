package store.model;

import static store.common.ExceptionMessage.PROMOTION_NOT_FOUND_ERROR;
import static store.common.ExceptionMessage.PROMOTION_VALIDATION_ERROR;

import java.util.ArrayList;
import java.util.List;

public class PromotionGroup {
    private final List<Promotion> promotions = new ArrayList<>();

    public void addPromotion(Promotion newPromotion){
        if(isDuplicate(newPromotion.getName())){
            throw new IllegalArgumentException(PROMOTION_VALIDATION_ERROR);
        }
        promotions.add(newPromotion);
    }

    private boolean isDuplicate(String promotionName){
        List<String> promotionNames = getPromotionNames();
        if(promotionNames.contains(promotionName)){
            return true;
        }
        return false;
    }

    private List<String> getPromotionNames(){
        return promotions.stream()
                .map(Promotion::getName)
                .toList();
    }

    public Promotion findByName(String productPromotion) {
        List<Promotion> promotion = promotions.stream()
                .filter(it -> it.getName().equals(productPromotion))
                .toList();

        if(promotion.size() < 1){
            throw new IllegalArgumentException(PROMOTION_NOT_FOUND_ERROR);
        }
        return promotion.getFirst();
    }
}
