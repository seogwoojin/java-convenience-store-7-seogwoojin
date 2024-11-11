package store;

import store.controller.OrderController;
import store.controller.StoreController;
import store.model.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();
        PromotionService promotionService = new PromotionService();
        OrderController orderController = new OrderController(inputView, promotionService, outputView);
        StoreController storeController = new StoreController(inputView, orderController);
        storeController.startStore();
    }
}
