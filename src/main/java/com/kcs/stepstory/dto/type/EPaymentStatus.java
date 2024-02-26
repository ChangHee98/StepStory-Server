package com.kcs.stepstory.dto.type;

import lombok.Getter;

@Getter
public enum EPaymentStatus {
    REQUEST("주문 요청"),
    DEPOSIT_COMPLETE("입금 완료"),
    DELIVERY_PREPARING("출고 중"),
    DELIVERY_IN_PROGRESS("배송 중"),
    DELIVERY_COMPLETE("배송 완료"),
    CANCEL("주문 취소");

    private final String value;
    private final String dbValue = this.name();

    EPaymentStatus(String value) {
        this.value = value;
    }

    public static EPaymentStatus fromValue(String value) {
        for (EPaymentStatus paymentStatus : EPaymentStatus.values()) {
            if (paymentStatus.getValue().equals(value)) {
                return paymentStatus;
            }
        }

        return null;
    }
}
