package jv.bazar.amacame.enums;

import lombok.Getter;

@Getter
public enum PromoScopeEnum {
    BILL("BILL"),
    PRODUCT("PRODUCT");
    private String scope;
    PromoScopeEnum(String scope) {
        this.scope = scope;
    }
}
