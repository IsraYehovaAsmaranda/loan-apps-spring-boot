package com.enigma.livecode_loan_app.constant;

import lombok.Getter;

@Getter
public enum EInstalmentType {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIXTH_MONTHS(6),
    NINE_MONTHS(9),
    TWELVE_MONTHS(12);

    private final Integer numberOfMonths;

    EInstalmentType(Integer numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }
}
