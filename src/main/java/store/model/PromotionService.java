package store.model;

public class PromotionService {

    public int findMissingPromotionQuantity(Promotion promotion, int orderQuantity, int promotionQuantity) {
        if (promotionQuantity <= orderQuantity) {
            return 0;
        }

        int remainQuantity = promotionQuantity - orderQuantity;

        int buyNum = promotion.getBuyNum();
        int getNum = promotion.getGetNum();

        int remain = orderQuantity % (buyNum + getNum);

        if (!isSatisfyBuyCount(remain, buyNum)) {
            return 0;
        }
        // get 조건 충족 시 실행
//        int userCanPlusQuantity = getNum - (remain - buyNum);
//
//        if (remainQuantity < userCanPlusQuantity) {
//            return 0;
//        }

        return getNum;
    }

    private Boolean isSatisfyBuyCount(int quantity, int buyCount) {
        return quantity >= buyCount;
    }

    public int findOverPromotionQuantity(Promotion promotion, int orderQuantity, int promotionQuantity) {
        if (orderQuantity <= promotionQuantity) {
            return 0;
        }

        int buyNum = promotion.getBuyNum();
        int getNum = promotion.getGetNum();
        int bundle = buyNum + getNum;

        int promotionBundleSize = promotionQuantity / bundle;

        return orderQuantity - promotionBundleSize * bundle;
    }

    public int[] calculatePromotionApplyQuantity(Promotion promotion, int orderQuantity, int promotionQuantity) {
        if (!Promotion.isPromotionNow(promotion)) {
            return new int[]{0, 0};
        }

        int buyNum = promotion.getBuyNum();
        int getNum = promotion.getGetNum();
        int bundleSize = buyNum + getNum;

        if (promotionQuantity >= orderQuantity) {
            int promotionBundleSize = orderQuantity / bundleSize;
            return new int[]{promotionBundleSize * bundleSize, promotionBundleSize};
        }

        int promotionBundleSize = promotionQuantity / bundleSize;
        return new int[]{promotionBundleSize * bundleSize, promotionBundleSize};
    }
}
