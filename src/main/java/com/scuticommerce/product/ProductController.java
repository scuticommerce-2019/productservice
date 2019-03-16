package com.scuticommerce.product;

import com.scuticommerce.product.repository.ProductRepository;
import com.scuticommerce.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    Logger logger = LogManager.getRootLogger();

   @Autowired
    ProductRepository repository;

   @Autowired
    ProductService service;

   @GetMapping("/up")
   public ResponseEntity<?> active(){

       logger.info("Site is up");
       return new ResponseEntity<>("service is up", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> products(){

       return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(){

       service.createProduct(false);

       return new ResponseEntity<>(true,HttpStatus.OK);
    }
}
