package com.wishlist.cst438project2.common.extras;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * product details
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */
@Data
@NoArgsConstructor
public class Product {

    @DocumentId
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
