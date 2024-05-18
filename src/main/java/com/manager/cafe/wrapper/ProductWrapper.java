package com.manager.cafe.wrapper;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper {
     private Integer id;

    private String name;

    private Integer categoryId;

    private String description;

    private Integer price;

    private String status;

    private String categoryName;
}
