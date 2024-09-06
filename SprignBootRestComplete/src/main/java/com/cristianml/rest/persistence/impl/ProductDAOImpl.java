package com.cristianml.rest.persistence.impl;

import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.entities.Product;
import com.cristianml.rest.persistence.IProductDAO;
import com.cristianml.rest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAOImpl implements IProductDAO {

    // Implementamos nuestro repositorio
    private final ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return Optional.empty();
        }
        return optionalProduct;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) this.productRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return this.productRepository.findProductsByPriceInRange(minPrice, maxPrice);
    }

    public boolean existsByMaker(Long idMaker) {
        Maker maker = new Maker();
        maker.setId(idMaker);
        if (productRepository.existsByMaker(maker)) {
            return true;
        } else {
            return false;
        }
    }
}
