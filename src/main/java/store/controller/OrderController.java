package store.controller;

import static store.common.ExceptionMessage.ERROR_HEAD;
import static store.common.OutputMessage.CHECK_NEXT_ORDER;
import static store.common.OutputMessage.MEMBERSHIP_DISCOUNT_MESSAGE;
import static store.common.OutputMessage.PROMOTION_ADD_MESSAGE;
import static store.common.OutputMessage.PROMOTION_OVER_MESSAGE;

import java.util.List;
import java.util.Optional;
import store.dto.OrderDto;
import store.dto.ReceiptFormatter;
import store.model.Order;
import store.model.OrderResult;
import store.model.Orders;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionService;
import store.model.Receipt;
import store.model.Store;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {


    private final InputView inputView;
    private final PromotionService promotionService;
    private final OutputView outputView;

    public OrderController(InputView inputView, PromotionService promotionService, OutputView outputView) {
        this.inputView = inputView;
        this.promotionService = promotionService;
        this.outputView = outputView;
    }

    public void handleOrderProcessing(Store store) {
        while (true) {
            Orders orders = getOrders(store);
            validateOrdersWithPromotions(store, orders);
            List<OrderResult> orderResults = executeOrders(store, orders);
            Boolean isMembership = isMembership();
            Receipt receipt = new Receipt(orderResults, isMembership);
            outputView.printMessage(ReceiptFormatter.format(receipt));

            if (isFinishOrder()) {
                return;
            }
        }
    }

    private OrderResult executeEachOrder(Store store, Order order) {
        Product product = store.getProductByName(order.getProductName());
        Optional<Promotion> productPromotion = product.getPromotion();
        if (productPromotion.isPresent()) {
            return applyPromotion(order.getQuantity(), product, productPromotion.get());
        }
        return applyNonPromotion(order.getQuantity(), product);
    }

    private OrderResult applyPromotion(int orderQuantity, Product product, Promotion promotion) {

        int[] promotionDetail = promotionService.calculatePromotionApplyQuantity(promotion,
                orderQuantity, product.getPromotionQuantity());
        int promotionApplyQuantity = promotionDetail[0];
        int freeGiftQuantity = promotionDetail[1];
        int nonPromotionQuantity = orderQuantity - promotionApplyQuantity;
        product.updateQuantity(orderQuantity);
        return new OrderResult(product.getName(), product.getPrice(), promotionApplyQuantity,
                nonPromotionQuantity, freeGiftQuantity);
    }

    private void updateProductQuantity(Product product, int quantity) {
        int leftQuantity = product.exhaustPromotion(quantity);
        product.executeOrder(false, leftQuantity);
    }

    private OrderResult applyNonPromotion(int orderQuantity, Product product) {
        updateProductQuantity(product, orderQuantity);
        return new OrderResult(product.getName(), product.getPrice(), 0, orderQuantity, 0);
    }

    private Boolean isFinishOrder() {
        outputView.printMessage(CHECK_NEXT_ORDER);
        Boolean userCheck = getUserCheck();
        outputView.printMessage("");
        return !userCheck;
    }

    private List<OrderResult> executeOrders(Store store, Orders orders) {
        List<Order> orderList = orders.getOrders();
        return orderList.stream()
                .map(order -> executeEachOrder(store, order))
                .toList();
    }

    private void validateOrdersWithPromotions(Store store, Orders orders) {
        List<Order> orderList = orders.getOrders();

        orderList.forEach(order -> checkMissPromotion(store, order));
        orderList.forEach(order -> checkOverPromotion(store, order));
        store.checkOrderPossible(orders); // 업데이트 이후 유효성 한번 더 체크
    }

    private Orders getOrders(Store store) {
        outputView.printMessage(store.toStringStoreInfo());
        return getPossibleOrders(store);
    }


    private Boolean isMembership() {
        outputView.printMessage(MEMBERSHIP_DISCOUNT_MESSAGE);
        return getUserCheck();
    }

    private void checkOverPromotion(Store store, Order order) {
        Product product = store.getProductByName(order.getProductName());
        Optional<Promotion> optionalPromotion = product.getPromotion();
        if (optionalPromotion.isPresent()) {
            Promotion promotion = optionalPromotion.get();
            int overPromotionQuantity = promotionService.findOverPromotionQuantity(promotion, order.getQuantity(),
                    product.getPromotionQuantity());
            updateOverQuantity(order, overPromotionQuantity);
        }
    }

    private void updateOverQuantity(Order order, int overPromotionQuantity) {
        if (overPromotionQuantity == 0) {
            return;
        }

        outputView.printMessage(String.format(PROMOTION_OVER_MESSAGE, order.getProductName(), overPromotionQuantity));
        if (!getUserCheck()) {
            order.updateQuantity(-overPromotionQuantity);
        }
    }

    private void checkMissPromotion(Store store, Order order) {
        Product product = store.getProductByName(order.getProductName());
        Optional<Promotion> optionalPromotion = product.getPromotion();
        if (optionalPromotion.isPresent()) {
            Promotion promotion = optionalPromotion.get();
            if (!Promotion.isPromotionNow(promotion)) {
                return;
            }
            int canAdditionQuantity = promotionService.findMissingPromotionQuantity(promotion, order.getQuantity(),
                    product.getPromotionQuantity());
            updateOrderQuantity(order, canAdditionQuantity);
        }
    }

    private void updateOrderQuantity(Order order, int canAdditionQuantity) {
        if (canAdditionQuantity == 0) {
            return;
        }
        outputView.printMessage(String.format(PROMOTION_ADD_MESSAGE, order.getProductName(), canAdditionQuantity));
        if (getUserCheck()) {
            order.updateQuantity(canAdditionQuantity);
        }
    }

    private Boolean getUserCheck() {
        while (true) {
            try {
                return inputView.getUserCheck();
            } catch (IllegalArgumentException e) {
                outputView.printMessage(ERROR_HEAD + e.getMessage());
            }
        }
    }

    private Orders getPossibleOrders(Store store) {
        while (true) {
            try {
                List<OrderDto> orderDtos = inputView.getUserOrder();
                List<Order> orderList = orderDtos.stream()
                        .map(orderDto -> new Order(orderDto.getName(), orderDto.getQuantity()))
                        .toList();
                Orders orders = new Orders(orderList);
                store.checkOrderPossible(orders);
                return orders;
            } catch (IllegalArgumentException e) {
                outputView.printMessage(ERROR_HEAD + e.getMessage());
            }
        }
    }
}
