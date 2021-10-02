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

    @Pattern(regexp = "((http|https)://)(www.)+[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}.[a-z]{2,6}/([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Please enter a valid URL")
    private String link;

    private String description;

    @Pattern(regexp = "((http|https)://)(www.)+[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}.[a-z]{2,6}/([-a-zA-Z0-9@:%._\\+~#?&//=]*)", message = "Please enter a valid URL")
    private String imgUrl;

    public ItemDTO(String name, String link, String description, String imgUrl) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.imgUrl = imgUrl;
    }

//    public String getName() { return name; }
}