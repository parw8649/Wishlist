package com.wishlist.cst438project2.common;

/**
 * @author Chaitanya Parwatkar, Barbara Kondo
 * @version %I% %G%
 */

public class Constants {

    /** FIREBASE DOCUMENTS */
    public static final String DOCUMENT_PRODUCT = "product";
    public static final String DOCUMENT_USER = "user";
    public static final String DOCUMENT_ITEM = "item";
    public static final String DOCUMENT_USER_WISHLIST = "wishlist";
    public static final String DOCUMENT_ACCESS_TOKEN = "access_token";

    /** FIREBASE FIELDS **/
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_ITEM_NAME = "name";
    public static final String FIELD_ITEM_ID = "itemId";
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
    public static final String ERROR_UNAUTHORIZED = "Unauthorized";
    public static final String ERROR_INVALID_TOKEN = "Invalid user token";
    public static final String ERROR_UNABLE_TO_ADD_ITEM_TO_WISHLIST = "Unable to add item to wishlist at the moment";
    public static final String ERROR_UNABLE_TO_FETCH_WISHLIST = "Unable to fetch wishlist at the moment";

    /** SUCCESS MESSAGES */
    public static final String USER_DELETED = "User deleted successfully";
    public static final String USER_PASSWORD_CHANGED_SUCCESSFULLY = "User password changed successfully";
    public static final String USER_UPDATED = "User updated successfully";
    public static final String USER_LOGIN_SUCCESSFUL = "User logged-in successfully";
    public static final String ITEM_REMOVED = "Item removed successfully: ";
    public static final String ITEM_UPDATED = "Item updated successfully: ";
    public static final String USER_LOGOUT_SUCCESSFUL = "User logged-out successfully";
    public static final String USER_ACCESS_TOKEN_DELETED = "User access token deleted!";

    /** KEYS */
    public static final String KEY_USERNAME = "{username}";
    public static final String KEY_ITEM_NAME = "{name}";

    /** SECRETS */
    public static final String KEY_SECRET_TOKEN = "wi$#|i$t";
}
