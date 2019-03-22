package com.scuticommerce.product.service;

import com.scuticommerce.model.product.Product;
import com.scuticommerce.product.repository.ProductRepository;
import com.scuticommerce.product.util.ProductImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    @Autowired
    ProductImageUtil productImageUtil;

    @Autowired
    ProductRepository repository;

    public void createProduct(Product product, boolean uploadImage){

        String imageURL = uploadImage ? productImageUtil.uploadToS3("product.jpg") : "default.jpg";

        repository.save(product);

    }

    public void deleteAll(){

        repository.deleteAll();
    }
}
