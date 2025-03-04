package com.cristianml.service.impl;

import com.cristianml.models.MakerModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.IMakerDAO;
import com.cristianml.persistence.IProductDAO;
import com.cristianml.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAO;

    @Override
    public List<ProductModel> findAll() {
        return this.productDAO.findAll();
    }

    @Override
    public Optional<ProductModel> findById(Long id) {
        return this.productDAO.findById(id);
    }

    @Override
    public void save(ProductModel product) {
        this.productDAO.save(product);
    }

    @Override
    public void deleteById(Long id) {
        this.productDAO.deleteById(id);
    }

    @Override
    public List<ProductModel> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return this.productDAO.findByPriceInRange(minPrice, maxPrice);
    }

    // Método para verificar si existen productos relacionado con un maker
    @Override
    public boolean existsRegisterByMaker(Long idMaker) {
        MakerModel makerModel = new MakerModel();
        makerModel.setId(idMaker);
        if (productDAO.existsRegisterByMakerModel(makerModel)) {
            return true;
        }
        return false;
    }
}