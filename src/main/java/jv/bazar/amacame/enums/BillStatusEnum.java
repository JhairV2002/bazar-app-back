package jv.bazar.amacame.enums;

import lombok.Getter;

@Getter
public enum BillStatusEnum {
    PAGADO("PAGADO"),
    PENDIENTE("PENDIENTE"),
    DEVUELTO("DEVUELTO"),
    CANCELADO("CANCELADO");

    private String status;

    BillStatusEnum (String status) {
        this.status = status;
    }

}
