package com.cristianml.service.impl;

import com.cristianml.dataProvider.ProductProvider;
import com.cristianml.models.MakerModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.impl.ProductDAOImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductDAOImpl productDAO;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testFindAll() {
        // Given
        List<ProductModel> productModelList = ProductProvider.productListMock();

        // When
        when(this.productDAO.findAll()).thenReturn(productModelList);
        List<ProductModel> result = this.productService.findAll();

        // Then
        verify(this.productDAO).findAll();
        assertFalse(result.isEmpty());
        assertEquals("Poco F5", result.get(1).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals(BigDecimal.valueOf(500), result.get(1).getPrice());
    }

    @Test
    public void testFindById_MakerExists() {
        // Given
        Optional<ProductModel> productModel = ProductProvider.productMock();
        Long idToFind = productModel.get().getId();

        // When
        when(this.productDAO.findById(anyLong())).thenReturn(productModel);
        Optional<ProductModel> result = this.productService.findById(idToFind);

        // Then
        verify(this.productDAO).findById(anyLong());
        assertEquals(2, result.get().getId());
        assertEquals("Poco F5", result.get().getName());
        assertEquals(BigDecimal.valueOf(500), result.get().getPrice());
    }

    @Test
    public void testFindById_MakerDoesNotExists() {
        // Given
        Long id = 999L;

        // When
        when(this.productDAO.findById(anyLong())).thenReturn(Optional.empty());
        Optional<ProductModel> result = this.productService.findById(id);

        // Then
        verify(this.productDAO).findById(anyLong());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSave() {
        // Given
        ProductModel productModel = ProductProvider.productMock().get();

        // When
        this.productService.save(productModel);

        // Then
        ArgumentCaptor<ProductModel> argumentCaptor = ArgumentCaptor.forClass(ProductModel.class);
        verify(this.productDAO).save(argumentCaptor.capture());

        assertEquals(2L, argumentCaptor.getValue().getId());
        assertEquals("Xiaomi", argumentCaptor.getValue().getMaker().getName());
    }

    @Test
    public void testDeleteById() {
        // Given
        Long idToDelete = 2L;

        // When
        this.productService.deleteById(idToDelete);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.productDAO).deleteById(anyLong());
        verify(this.productDAO).deleteById(argumentCaptor.capture());
        assertEquals(idToDelete, argumentCaptor.getValue().longValue());
    }

    @Test
    public void testFindByPriceInRange() {
        // Given
        BigDecimal minPrice = BigDecimal.valueOf(200);
        BigDecimal maxPrice = BigDecimal.valueOf(2000);

        // When
        when(this.productDAO.findByPriceInRange(minPrice, maxPrice)).thenReturn(ProductProvider.productListMock());
        List<ProductModel> result = this.productService.findByPriceInRange(minPrice, maxPrice);

        // Then
        verify(this.productDAO).findByPriceInRange(minPrice, maxPrice);
        assertEquals("Poco F5", result.get(1).getName());
        assertEquals(3L, result.get(2).getId());
        assertEquals(BigDecimal.valueOf(500), result.get(1).getPrice());

        // Verifica que todos los productos estén dentro del rango de precios
        assertTrue(result.stream().allMatch(product ->
                product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0
        ));
    }

    @Test
    public void testExistsRegisterByMaker_Exists() {
        // Given
        Long id = 2L;

        // When
        when(this.productDAO.existsRegisterByMakerModel(any(MakerModel.class))).thenReturn(true);
        boolean result = this.productService.existsRegisterByMaker(id);

        // Then
        verify(this.productDAO).existsRegisterByMakerModel(any(MakerModel.class));
        assertTrue(result);
    }

    @Test
    public void testExistsRegisterByMaker_DoesNotExists() {
        // Given
        Long id = 999L;

        // When
        when(this.productDAO.existsRegisterByMakerModel(any(MakerModel.class))).thenReturn(false);
        boolean result = this.productService.existsRegisterByMaker(id);

        // Then
        verify(this.productDAO).existsRegisterByMakerModel(any(MakerModel.class));
        assertFalse(result);
    }
}
