package com.Yaktta.Disco.controller;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.request.ProductSaveRequest;
import com.Yaktta.Disco.models.response.ProductResponse;
import com.Yaktta.Disco.service.Implementation.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductServiceImpl productService;
    public ProductController( ProductServiceImpl productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductResponse> findAll(){
        return productService.findAll();
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        try{
            Optional<ProductResponse> product = productService.findById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch(NotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/new-product")
    public ResponseEntity<Object> save(@RequestBody ProductSaveRequest product) {
        try{
            ProductResponse productResponse= productService.save(product);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        }catch(BadRequestException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
