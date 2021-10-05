package com.wishlist.cst438project2.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {
    @NotEmpty(message = "Item must have a name.")
    private String name;

    @Pattern(regexp = "((http|https)://)(www.)+[a-zA-Z0-9@:%._+~#?&//=]{2,256}.[a-z]{2,6}/([-a-zA-Z0-9@:%._+~#?&//=]*)", message = "Please enter a valid URL")
    private String link;

    private String description;

    // TODO: later: get imgUrl to pass correctly, currently processed as null on intake
//    @Pattern(regexp = "((http|https)://)((www|cdn).)+[a-zA-Z0-9@:%._+~#?&//=]{2,256}.[a-z]{2,6}/([-a-zA-Z0-9@:%._+~#?&//=]*)", message = "Please enter a valid URL")
    private String imgUrl;

    private int userId;

    /**
     * Constructor for an Item Data Transfer Object
     * @param name is the given name of an item, cannot be null
     * @param link is the given URL for where the item is available
     * @param description
     * @param imgUrl is the given URL for where a picture of the item is available
     */
    public ItemDTO(String name, String link, String description, String imgUrl, int userId) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.imgUrl = imgUrl;
        this.userId = userId;
    }
}
