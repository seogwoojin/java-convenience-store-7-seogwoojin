package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import store.common.Validator;

public class Promotion {
    private final static int NAME_LOC = 0;
    private final static int BUY_LOC = 1;
    private final static int GET_LOC = 2;
    private final static int START_DATE_LOC = 3;
    private final static int FINAL_DATE_LOC = 4;

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String[] promotionDetails) {
        this.name = promotionDetails[NAME_LOC];
        this.buy = Validator.validateNumber(promotionDetails[BUY_LOC]);
        this.get = Validator.validateNumber(promotionDetails[GET_LOC]);
        this.startDate = Validator.validateDate(promotionDetails[START_DATE_LOC]);
        this.endDate = Validator.validateDate(promotionDetails[FINAL_DATE_LOC]);
    }

    public static Boolean isPromotionNow(Promotion promotion) {
        LocalDateTime now = DateTimes.now();
        LocalDate nowDate = now.toLocalDate();

        return !nowDate.isBefore(promotion.startDate) && !nowDate.isAfter(promotion.endDate);
    }


    public String getName() {
        return this.name;
    }

    public int getBuyNum() {
        return buy;
    }

    public int getGetNum() {
        return get;
    }
}
