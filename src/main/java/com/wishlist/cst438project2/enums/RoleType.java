package com.wishlist.cst438project2.enums;

public enum RoleType {

    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    RoleType(String roleType) { this.value = roleType; }

    public String getValue() { return value; }
}
