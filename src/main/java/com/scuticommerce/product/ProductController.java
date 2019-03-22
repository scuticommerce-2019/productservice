package com.scuticommerce.product;

import com.scuticommerce.model.product.Product;
import com.scuticommerce.product.repository.ProductRepository;
import com.scuticommerce.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/productservice")
public class ProductController {

    Logger logger = LogManager.getRootLogger();

   @Autowired
    ProductRepository repository;

   @Autowired
    ProductService service;

   @GetMapping("/status")
   public ResponseEntity<?> active(){

       logger.info("Site is up");
       return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> products(){

       return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody List<Product> products ){

       for (Product product : products) {
           service.createProduct(product,false);
       }

       return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(){

       repository.deleteAll();

       return new ResponseEntity<>( HttpStatus.OK);
    }
}
