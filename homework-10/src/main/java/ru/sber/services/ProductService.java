package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.Product;
import ru.sber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с продуктом
 */
@Slf4j
@Service
public class ProductService implements ProductInterfaceService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public long addProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public List<Product> getListProductName(String name){

        if (name == null){
            return productRepository.findAll()
                    .stream()
                    .toList();
        } else {
            return productRepository.findAll()
                    .stream()
                    .filter(item -> name.equals(item.getName()))
                    .toList();
        }
    }

    @Override
    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public boolean update(Product product) {

        var isProduct = productRepository.findAll()
                .stream()
                .filter(item -> item.getId() == product.getId())
                .findAny();

        if (isProduct.isPresent()){
            productRepository.save(product);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {

        var isProduct = productRepository.findAll()
                .stream()
                .filter(item -> item.getId() == id)
                .findAny();

        if (isProduct.isPresent()){
            productRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
