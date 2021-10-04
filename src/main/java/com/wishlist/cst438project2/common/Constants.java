package com.wishlist.cst438project2.common;

public class Constants {

    /** FIREBASE DOCUMENTS */
    public static final String DOCUMENT_PRODUCT = "product";
    public static final String DOCUMENT_USER = "user";
    public static final String DOCUMENT_ITEM = "item";

    /** ERROR MESSAGES */
    public static final String ERROR_BAD_REQUEST = "Bad request";
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_BAD_GATEWAY = "Bad gateway";
    public static final String ERROR_UNABLE_TO_UPDATE_USER = "Unable to update user at the moment";
    public static final String ERROR_USER_ALREADY_EXISTS = "User with {username} already exists!";
    public static final String ERROR_USER_DOES_NOT_EXISTS = "User with {username} does not exists!";
    public static final String ERROR_ITEM_NOT_FOUND = "Item not found";
    public static final String ERROR_ITEM_ALREADY_EXISTS = "Item with {name} already exists!";

    /** SUCCESS MESSAGES */
    public static final String USER_DELETED = "User deleted successfully";
    public static final String ITEM_REMOVED = "Item removed successfully: ";

    /** KEYS */
    public static final String KEY_USERNAME = "{username}";
    public static final String KEY_ITEM_NAME = "{name}";
}
