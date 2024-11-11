package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void addProduct() {
        Store store = new Store();
        Product product = new Product("물", "500");
        Product product1 = new Product("감자칩", "2000");

        store.addProduct(product);
        store.addProduct(product1);

        assertThat(store.getProductByName("감자칩")).isSameAs(product1);
        assertThat(store.getProductByName("물")).isSameAs(product);
    }
}