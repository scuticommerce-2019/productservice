package com.scuticommerce.product.service;

import com.scuticommerce.product.model.Product;
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

    public void createProduct(boolean uploadImage){

        String imageURL = uploadImage ? productImageUtil.uploadToS3("product.jpg") : "default.jpg";

        Product product = new Product();
        product.setDescription("test product");
        product.setName("New Product 1");
        product.setSku("2342342");
        product.setUrl(imageURL);
        product.setImage("http://producturl.com");

        repository.save(product);

    }
}
