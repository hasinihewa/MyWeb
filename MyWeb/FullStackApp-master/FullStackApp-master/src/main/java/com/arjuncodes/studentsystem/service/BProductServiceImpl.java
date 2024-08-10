package com.arjuncodes.studentsystem.service;



        import com.arjuncodes.studentsystem.model.BeautyProduct;
        import com.arjuncodes.studentsystem.repository.BProductRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

@Service
public class BProductServiceImpl implements BProductService{

    @Autowired
    private BProductRepo beautyProductRepository;

    @Override
    public BeautyProduct saveProduct(BeautyProduct product) {
        return beautyProductRepository.save(product);
    }

    @Override
    public List<BeautyProduct> getAllProducts() {
        return beautyProductRepository.findAll();
    }

    @Override
    public BeautyProduct getProductById(int id) {
        Optional<BeautyProduct> product = beautyProductRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public void deleteProduct(int id) {
        beautyProductRepository.deleteById(id);
    }
}

