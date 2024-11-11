package store.view;

import static store.common.ExceptionMessage.CHECK_FORMAT_ERROR;
import static store.common.ExceptionMessage.PRODUCT_VALIDATION_ERROR;
import static store.common.ExceptionMessage.PROMOTION_VALIDATION_ERROR;

import camp.nextstep.edu.missionutils.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.dto.OrderDto;

public class InputView {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    private static int PROMOTION_LENGTH = 5;
    private static int PRODUCT_LENGTH = 4;

    public List<String[]> readPromotionFile() {
        List<String[]> promotions = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(PROMOTION_FILE_PATH))) {
            br.readLine(); // 첫번째 줄은 제외합니다.
            while ((line = br.readLine()) != null) {
                String[] promotionDetails = line.split(",");
                validatePromotionDetails(promotionDetails);
                promotions.add(promotionDetails);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(PROMOTION_VALIDATION_ERROR);
        }

        return promotions;
    }

    private void validatePromotionDetails(String[] line) {
        if (line.length != PROMOTION_LENGTH) {
            throw new IllegalArgumentException(PROMOTION_VALIDATION_ERROR);
        }
    }

    public List<String[]> readProductFile() {
        List<String[]> products = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE_PATH))) {
            br.readLine(); // 첫번째 줄은 제외합니다.
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split(",");
                validateProductDetails(productDetails);
                products.add(productDetails);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(PRODUCT_VALIDATION_ERROR);
        }

        return products;
    }

    private void validateProductDetails(String[] line) {
        if (line.length != PRODUCT_LENGTH) {
            throw new IllegalArgumentException(PRODUCT_VALIDATION_ERROR);
        }
    }

    public List<OrderDto> getUserOrder() {
        String input = Console.readLine();
        String[] strings = input.split(",");
        return Arrays.stream(strings)
                .map(OrderDto::valueOf)
                .toList();
    }

    public Boolean getUserCheck() {
        String input = Console.readLine();
        if (input.trim().equals("Y")) {
            return true;
        } else if (input.trim().equals("N")) {
            return false;
        }
        throw new IllegalArgumentException(CHECK_FORMAT_ERROR);
    }
}
