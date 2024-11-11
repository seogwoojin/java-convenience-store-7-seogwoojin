package store.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.common.Validator;

public class OrderDto {
    private final String name;
    private final int quantity;


    private OrderDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static OrderDto valueOf(String orderString) {
        String[] prasedString = parseString(orderString.trim());
        String name = prasedString[0];
        int quantity = Validator.validateNumber(prasedString[1]);
        return new OrderDto(name, quantity);
    }

    private static String[] parseString(String string) {
        Pattern pattern = Pattern.compile("\\[(.*?)-(\\d+)]");
        Matcher matcher = pattern.matcher(string);

        if (matcher.matches()) {
            return new String[]{
                    matcher.group(1),
                    matcher.group(2)}; // matcher.group(1) is name, matcher.group(2) is quantity
        }
        throw new IllegalArgumentException("Input string format is invalid: " + string);
    }


    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
