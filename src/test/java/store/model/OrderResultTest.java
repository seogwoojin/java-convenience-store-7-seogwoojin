package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OrderResultTest {

    @Test
    void getProductName() {
        OrderResult orderResult = new OrderResult("물", 500, 3, 3, 1);

        //then
        assertThat(orderResult.getTotalQuantity()).isEqualTo(6);
        assertThat(orderResult.getFreeGiftPrice()).isEqualTo(500);
        assertThat(orderResult.getTotalPrice()).isEqualTo(3000);
    }
}