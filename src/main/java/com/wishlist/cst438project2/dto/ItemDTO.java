package com.wishlist.cst438project2.dto;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ItemDTO {
    @NotEmpty(message = "Item must have a name.")
    private String name;

    private String link;
    private String description;
    private String imgUrl;
}