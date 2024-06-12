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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductServiceImpl productService;
    public ProductController( ProductServiceImpl productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductResponse> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> edit(@PathVariable Long id, @RequestBody ProductSaveRequest productSaveRequest) {
        try {
            ProductResponse updatedProduct = productService.edit(id, productSaveRequest);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            String message = productService.delete(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
