package jv.bazar.amacame.enums;

import lombok.Getter;

@Getter
public enum PromoTypeEnum {
    DISCOUNTPRODUCT("DISCOUNTPRODUCT"),
    DISCOUNTBILL ("DISCOUNTBILL"),
    SPECIALPROMOS ( "SPECIALPROMOS" ); //2X1, 3X2, ETC

    private String promoType;

    PromoTypeEnum(String promoType) {
        this.promoType = promoType;
    }
}
