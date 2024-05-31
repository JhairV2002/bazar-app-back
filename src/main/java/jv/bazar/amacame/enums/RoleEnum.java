package jv.bazar.amacame.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

}
