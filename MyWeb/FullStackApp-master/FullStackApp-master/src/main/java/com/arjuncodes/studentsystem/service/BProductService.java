package com.arjuncodes.studentsystem.service;



        import com.arjuncodes.studentsystem.model.BeautyProduct;

        import java.util.List;

public interface BProductService {
    BeautyProduct saveProduct(BeautyProduct product);
    List<BeautyProduct> getAllProducts();
    BeautyProduct getProductById(int id);
    void deleteProduct(int id);
}

