package store.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OrderDtoTest {

    @Test
    void OrderDto_생성_테스트() {
        OrderDto orderDto = OrderDto.valueOf("[감자칩-5]");
        String orderDtoName = orderDto.getName();
        int quantity = orderDto.getQuantity();

        assertThat(orderDtoName).isEqualTo("감자칩");
        assertThat(quantity).isEqualTo(5);
    }

    @Test
    void OrderDto_생성_예외() {
        assertThatThrownBy(() -> OrderDto.valueOf("[감자칩-5")).isInstanceOf(IllegalArgumentException.class);
    }
}