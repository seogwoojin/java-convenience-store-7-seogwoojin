package store.controller;

import java.util.List;
import java.util.Optional;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionGroup;
import store.model.Store;
import store.view.InputView;

public class StoreController {
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
            Product product;
            String productName = productDetail[0];
            String productPrice = productDetail[1];
            String productQuantity = productDetail[2];
            String productPromotion = productDetail[3];
            Optional<Promotion> optionalPromotion = getProduct(productPromotion, promotionGroup);

            if (store.isDuplicate(productName)) {
                product = store.getProductByName(productName);
                product.updateQuantity(productQuantity, optionalPromotion);
            } else {
                product = new Product(productDetail);
                product.updateQuantity(productQuantity, optionalPromotion);
                store.addProduct(product);
            }
        }
        return store;
    }

    private Optional<Promotion> getProduct(String productPromotion, PromotionGroup promotionGroup) {
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
