package com.cristianml.persistence.impl;

import com.cristianml.dataProvider.MakerProvider;
import com.cristianml.dataProvider.ProductProvider;
import com.cristianml.models.MakerModel;
import com.cristianml.models.ProductModel;
import com.cristianml.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductDAOImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductDAOImpl productDAO;

    @Test
    public void testFindAll() {
        List<ProductModel> productModelList = ProductProvider.productListMock();
        when(this.productRepository.findAll()).thenReturn(productModelList);
        List<ProductModel> result = this.productDAO.findAll();

        verify(this.productRepository).findAll();
        assertFalse(result.isEmpty());
        assertEquals(2L, result.get(1).getId());

        // Verificamos cada uno de los elementos con foreach
        for (ProductModel expected : productModelList) {
            // Capturamos filtrando y comparando los id con el elemento actual
            ProductModel actual = result.stream()
                    .filter(p -> p.getId().equals(expected.getId()))
                    // Que cuando obtenga o encuentre el primero simplemente termine el stream
                    .findFirst()
                    .orElse(null);

            // Verificamos si es null es porque no encontró nada y lanza error
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getPrice(), actual.getPrice());
        }
    }

    // findById
    @Test
    public void testFindById_ProductExists() {
        Optional<ProductModel> productModel = ProductProvider.productMock();
        Long idToFind = productModel.get().getId();

        when(this.productRepository.findById(anyLong())).thenReturn(productModel);
        Optional<ProductModel> result = this.productDAO.findById(idToFind);

        verify(this.productRepository).findById(anyLong());
        assertEquals(2L, result.get().getId());
        assertEquals("Poco F5", result.get().getName());
        assertEquals(BigDecimal.valueOf(500), result.get().getPrice());
    }

    @Test
    public void testFindById_ProductDoesNotExists() {
        Long idToFind = 999L;

        when(this.productRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<ProductModel> result = this.productDAO.findById(idToFind);

        verify(this.productRepository).findById(anyLong());
        assertTrue(result.isEmpty());
    }

    // save
    @Test
    public void testSave() {
        ProductModel productModel = ProductProvider.productMock().get();

        this.productDAO.save(productModel);

        ArgumentCaptor<ProductModel> argumentCaptor = ArgumentCaptor.forClass(ProductModel.class);
        // Capturamos el argumentCaptor
        verify(productRepository).save(argumentCaptor.capture());

        assertEquals(2L, argumentCaptor.getValue().getId());
        assertEquals("Xiaomi", argumentCaptor.getValue().getMaker().getName());

    }

    // testSave sin el argument captor porque no es necesario,
    // ya que sabemos lo que contiene el objeto productModel.
    @Test
    public void testSave_WithoutArgumentCaptor() {
        // Preparación
        ProductModel productModel = ProductProvider.productMock().get();

        // Ejecución
        this.productDAO.save(productModel);

        // Verificación
        verify(productRepository).save(productModel);
        assertEquals(2L, productModel.getId());
        assertEquals("Xiaomi", productModel.getMaker().getName());
    }


    // deleteBy
    @Test
    public void testDeleteById() {
        Long idToDelete = ProductProvider.productMock().get().getId();

        this.productDAO.deleteById(idToDelete);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.productRepository).deleteById(argumentCaptor.capture());
        assertEquals(2L, argumentCaptor.getValue());
    }

    // findByPriceInRange
    @Test
    public void testFindByPriceInRange() {
        BigDecimal minPrice = BigDecimal.valueOf(200);
        BigDecimal maxPrice = BigDecimal.valueOf(2000);
        List<ProductModel> productModelList = ProductProvider.productListMock();

        when(this.productRepository.findProductModelByPriceBetween(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(productModelList);
        List<ProductModel> result = this.productDAO.findByPriceInRange(minPrice, maxPrice);

        verify(this.productRepository).findProductModelByPriceBetween(any(BigDecimal.class), any(BigDecimal.class));
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(productModel ->
                productModel.getPrice().compareTo(minPrice) >= 0 && productModel.getPrice().compareTo(maxPrice) <= 0));
    }

    @Test
    public void testExistsRegisterByMakerModel_MakerExists() {
        MakerModel makerModel = MakerProvider.makerOptionalMock().get();

        when(this.productRepository.existsByMaker(any(MakerModel.class))).thenReturn(true);
        boolean result = this.productDAO.existsRegisterByMakerModel(makerModel);

        verify(this.productRepository).existsByMaker(any(MakerModel.class));
        assertTrue(result);
    }

    @Test
    public void testExistsRegisterByMakerModel_MakerDoesNotExists() {
        MakerModel makerModel = new MakerModel();

        when(this.productRepository.existsByMaker(any(MakerModel.class))).thenReturn(false);
        boolean result = this.productDAO.existsRegisterByMakerModel(makerModel);

        verify(this.productRepository).existsByMaker(any(MakerModel.class));
        assertFalse(result);
    }

}
