package com.arjuncodes.studentsystem.controller;

import com.arjuncodes.studentsystem.model.BeautyProduct;
import com.arjuncodes.studentsystem.service.BProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class BProductController {

    @Autowired
    private BProductService beautyProductService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/add")
    public ResponseEntity<String> add(
            @RequestParam("brandName") String brandName,
            @RequestParam("productName") String productName,
            @RequestParam("amount") double amount,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {

        try {
            String imageUrl = saveFile(file);
            BeautyProduct product = new BeautyProduct();
            product.setBrandName(brandName);
            product.setProductName(productName);
            product.setAmount(amount);
            product.setDescription(description);
            product.setImageUrl(imageUrl);
            beautyProductService.saveProduct(product);
            return new ResponseEntity<>("New beauty product added", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BeautyProduct>> list() {
        List<BeautyProduct> products = beautyProductService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BeautyProduct> get(@PathVariable int id) {
        BeautyProduct product = beautyProductService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody BeautyProduct product) {
        BeautyProduct existingProduct = beautyProductService.getProductById(id);
        if (existingProduct != null) {
            product.setId(id);
            beautyProductService.saveProduct(product);
            return new ResponseEntity<>("Beauty product updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        BeautyProduct existingProduct = beautyProductService.getProductById(id);
        if (existingProduct != null) {
            beautyProductService.deleteProduct(id);
            return new ResponseEntity<>("Beauty product deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path path = Paths.get(UPLOAD_DIR + uniqueFilename);

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Files.write(path, file.getBytes());
        return uniqueFilename; // Return the filename or URL for the saved file
    }
}
