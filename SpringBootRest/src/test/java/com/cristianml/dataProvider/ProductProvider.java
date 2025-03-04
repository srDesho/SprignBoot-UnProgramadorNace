package com.cristianml.dataProvider;

import com.cristianml.models.MakerModel;
import com.cristianml.models.ProductModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductProvider {

    public static List<ProductModel> productListMock() {
        return List.of(ProductModel.builder()
                .id(1L)
                .name("Galaxy S8")
                .price(BigDecimal.valueOf(200))
                .maker(MakerModel.builder().id(1L).name("Samsung").build())
                .build(),

                ProductModel.builder()
                        .id(2L)
                        .name("Poco F5")
                        .price(BigDecimal.valueOf(500))
                        .maker(MakerModel.builder().id(2L).name("Xiaomi").build())
                        .build(),

                ProductModel.builder()
                        .id(3L)
                        .name("Iphone 16")
                        .price(BigDecimal.valueOf(2000))
                        .maker(MakerModel.builder().id(3L).name("Apple").build())
                        .build());
    }

    public static Optional<ProductModel> productMock() {
        return Optional.of(ProductModel.builder()
                .id(2L)
                .name("Poco F5")
                .price(BigDecimal.valueOf(500))
                .maker(MakerModel.builder().id(2L).name("Xiaomi").build())
                .build());
    }

}
