package store.model;

import static store.common.ExceptionMessage.ORDER_QUANTITY_ERROR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store {
    private final static String WELCOME_MESSAGE = """
            안녕하세요. W편의점입니다.
            현재 보유하고 있는 상품입니다.
            """;
    private final static String BUY_EXAMPLE_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean isDuplicate(String productName) {
        List<String> productNames = getProductNames();
        if (productNames.contains(productName)) {
            return true;
        }
        return false;
    }

    private List<String> getProductNames() {
        return products.stream()
                .map(Product::getName)
                .toList();
    }

    public Product getProductByName(String productName) {
        List<Product> productList = products.stream()
                .filter(product -> product.getName().equals(productName))
                .toList();
        if (productList.size() != 1) {
            throw new IllegalArgumentException();
        }
        return productList.get(0);
    }

    public String toStringStoreInfo() {
        return WELCOME_MESSAGE
                + "\n"
                + toStringProducts()
                + "\n"
                + BUY_EXAMPLE_MESSAGE;
    }

    private String toStringProducts() {
        return products.stream()
                .map(Product::toString) // 각 Product 객체의 toString() 호출
                .collect(Collectors.joining()); // 각 문자열을 줄바꿈으로 연결
    }

    public void checkOrderPossible(Orders orders) {
        List<Order> orderList = orders.getOrders();
        orderList.forEach(this::validateOrder);
    }

    private void validateOrder(Order order) {
        Product product = getProductByName(order.getProductName());

        if (product.getTotalQuantity() < order.getQuantity()) {
            throw new IllegalArgumentException(ORDER_QUANTITY_ERROR);
        }
    }

}
