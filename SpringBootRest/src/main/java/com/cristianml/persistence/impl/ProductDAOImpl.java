package com.cristianml.persistence.impl;

import com.cristianml.models.MakerModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.IProductDAO;
import com.cristianml.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component // Define como un bean para que spring configure internamente
public class ProductDAOImpl implements IProductDAO {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductModel> findAll() {
        return (List<ProductModel>) this.productRepository.findAll();
    }

    @Override
    public Optional<ProductModel> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void save(ProductModel product) {
        this.productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<ProductModel> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return this.productRepository.findProductModelByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public boolean existsRegisterByMakerModel(MakerModel makerModel) {

        if (productRepository.existsByMaker(makerModel)) {
            return true;
        }
        return false;
    }


}
