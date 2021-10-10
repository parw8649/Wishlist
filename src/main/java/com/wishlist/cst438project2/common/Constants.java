package com.wishlist.cst438project2.common;

public class Constants {

    /** FIREBASE DOCUMENTS */
    public static final String DOCUMENT_PRODUCT = "product";
    public static final String DOCUMENT_USER = "user";
    public static final String DOCUMENT_ITEM = "item";

    /** FIREBASE FIELDS **/
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_ITEM_NAME = "name";
    public static final String FIELD_ITEM_LINK = "link";
    public static final String FIELD_ITEM_DESCRIPTION = "description";
    public static final String FIELD_ITEM_IMG_URL = "imgUrl";
    public static final String FIELD_ITEM_USER_ID = "userId";

    /** ERROR MESSAGES */
    public static final String ERROR_BAD_REQUEST = "Bad request";
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_BAD_GATEWAY = "Bad gateway";
    public static final String ERROR_UNABLE_TO_UPDATE_USER = "Unable to update user at the moment";
    public static final String ERROR_USER_ALREADY_EXISTS = "User with {username} already exists!";
    public static final String ERROR_USER_DOES_NOT_EXISTS = "User with {username} does not exists!";
    public static final String ERROR_USER_PASSWORD_MISMATCH = "Password & Confirm password does not match!";
    public static final String ERROR_USERNAME_PASSWORD_MUST_NOT_BE_NULL = "Username or password must not be null or empty";
    public static final String ERROR_INVALID_PASSWORD = "Invalid password!";
    public static final String ERROR_ITEM_NOT_FOUND = "Item not found";
    public static final String ERROR_ITEM_ALREADY_EXISTS = "Item with name: {name} already exists!";
    public static final String ERROR_ITEM_DOES_NOT_EXISTS = "Item with name: {name} does not exists!";

    /** SUCCESS MESSAGES */
    public static final String USER_DELETED = "User deleted successfully";
    public static final String USER_PASSWORD_CHANGED_SUCCESSFULLY = "User password changed successfully";
    public static final String USER_UPDATED = "User updated successfully";
    public static final String USER_LOGIN_SUCCESSFUL = "User logged-in successfully";
    public static final String ITEM_REMOVED = "Item removed successfully: ";

    /** KEYS */
    public static final String KEY_USERNAME = "{username}";
    public static final String KEY_ITEM_NAME = "{name}";
}
