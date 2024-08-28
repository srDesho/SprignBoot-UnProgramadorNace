package com.cristianml.rest.service.impl;

import com.cristianml.rest.entities.Product;
import com.cristianml.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductServiceImpl implements IProductService {

    private final IProductService productService;

    @Autowired
    public ProductServiceImpl(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void save(Product product) {
        this.productService.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productService.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return this.productService.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.productService.deleteById(id);
    }

    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return this.productService.findByPriceInRange(minPrice, maxPrice);
    }
}
