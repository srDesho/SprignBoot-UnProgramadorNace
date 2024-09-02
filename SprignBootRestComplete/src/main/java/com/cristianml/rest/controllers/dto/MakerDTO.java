package com.cristianml.rest.controllers.dto;

import com.cristianml.rest.entities.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MakerDTO {

    private Long id;
    private String name;
    private List<Product> productList = new ArrayList<>();

}
