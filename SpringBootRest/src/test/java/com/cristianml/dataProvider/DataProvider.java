package com.cristianml.dataProvider;

import com.cristianml.models.MakerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataProvider {

    public static List<MakerModel> makerListMock() {
        return List.of(MakerModel.builder().id(1L).name("Samsung").products(null).build(),
                MakerModel.builder().id(2L).name("Apple").products(null).build(),
                MakerModel.builder().id(3L).name("Xiaomi").products(null).build());
    }

    public static Optional<MakerModel> makerOptionalMock() {
        return Optional.of(MakerModel.builder()
                .id(1L)
                .name("Samsung")
                .products(null)
                .build());
    }
}
