package com.Yaktta.Disco.controller;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.request.BrandSaveRequest;
import com.Yaktta.Disco.models.response.BrandResponse;
import com.Yaktta.Disco.service.Implementation.BrandServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/brands")
@RestController
public class BrandController {
    private final BrandServiceImpl brandService;

    BrandController(BrandServiceImpl brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public List<BrandResponse> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Optional<BrandResponse> brand = brandService.findById(id);
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new-brand")
    public ResponseEntity<Object> save(@RequestBody BrandSaveRequest brand) {
        try {
            BrandResponse brandResponse = brandService.save(brand);
            return new ResponseEntity<>(brandResponse, HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
