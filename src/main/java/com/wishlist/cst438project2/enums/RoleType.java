package com.wishlist.cst438project2.enums;
/**
 * enum for role types in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

public enum RoleType {

    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    RoleType(String roleType) { this.value = roleType; }

    public String getValue() { return value; }
}
