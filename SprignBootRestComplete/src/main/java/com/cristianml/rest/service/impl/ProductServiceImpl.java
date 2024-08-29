package com.cristianml.rest.service.impl;

import com.cristianml.rest.entities.Product;
import com.cristianml.rest.persistence.IProductDAO;
import com.cristianml.rest.persistence.impl.ProductDAOImpl;
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

    private final ProductDAOImpl productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAOImpl productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void save(Product product) {
        this.productDAO.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productDAO.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return this.productDAO.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.productDAO.deleteById(id);
    }

    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return this.productDAO.findByPriceInRange(minPrice, maxPrice);
    }
}
