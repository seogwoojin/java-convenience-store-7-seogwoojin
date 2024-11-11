package store.controller;

import java.util.List;
import java.util.Optional;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionGroup;
import store.model.Store;
import store.view.InputView;

public class StoreController {
    private static final int NAME_LOC = 0;
    private static final int PRICE_LOC = 1;
    private static final int QUANTITY_LOC = 2;
    private static final int PROMOTION_LOC = 3;

    private static final String NULL_STRING = "null";
    private final InputView inputView;
    private final OrderController orderController;

    public StoreController(InputView inputView, OrderController orderController) {
        this.inputView = inputView;
        this.orderController = orderController;
    }

    public void startStore() {
        //전처리 과정
        List<String[]> promotions = inputView.readPromotionFile();
        PromotionGroup promotionGroup = makePromotionGroup(promotions);
        List<String[]> products = inputView.readProductFile();
        Store store = makeStore(products, promotionGroup);

        // 여기까지 initiate
        orderController.handleOrderProcessing(store);
    }


    private Store makeStore(List<String[]> products, PromotionGroup promotionGroup) {
        Store store = new Store();
        for (String[] productDetail : products) {
            Optional<Promotion> optionalPromotion = getPromotion(productDetail[PROMOTION_LOC], promotionGroup);
            updateOrCreateProduct(productDetail, optionalPromotion, store);
        }
        return store;
    }

    private void updateOrCreateProduct(String[] productDetail, Optional<Promotion> optionalPromotion, Store store) {
        String productName = productDetail[NAME_LOC];
        if (store.isDuplicate(productName)) {
            Product product = store.getProductByName(productName);
            product.updateQuantity(productDetail[QUANTITY_LOC], optionalPromotion);
            return;
        }
        Product newProduct = new Product(productName, productDetail[PRICE_LOC]);
        newProduct.updateQuantity(productDetail[QUANTITY_LOC], optionalPromotion);
        store.addProduct(newProduct);
    }

    private Optional<Promotion> getPromotion(String productPromotion, PromotionGroup promotionGroup) {
        if (productPromotion.equals(NULL_STRING)) {
            return Optional.empty();
        }
        return Optional.of(promotionGroup.findByName(productPromotion));
    }

    private PromotionGroup makePromotionGroup(List<String[]> promotions) {
        PromotionGroup promotionGroup = new PromotionGroup();
        for (String[] promotion : promotions) {
            Promotion newPromotion = new Promotion(promotion);
            promotionGroup.addPromotion(newPromotion);
        }
        return promotionGroup;
    }
}
