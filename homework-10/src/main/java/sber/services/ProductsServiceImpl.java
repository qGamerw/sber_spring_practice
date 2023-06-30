package sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.entity.Product;
import sber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public long save(Product product) {
        Product savedProduct = productRepository.save(product);

        return savedProduct.getId();
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll(String name) {
        return productRepository.findAll();
    }

    @Override
    public boolean update(Product product) {
        productRepository.save(product);

        return true;
    }

    @Override
    public boolean deleteById(long id) {
        productRepository.deleteById(id);

        return true;
    }
}
