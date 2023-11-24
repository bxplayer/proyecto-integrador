package com.integrador.evently.products.service;

import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.interfaces.IProductService;
import com.integrador.evently.products.model.Product;
import com.integrador.evently.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return (product != null) ? modelMapper.map(product, ProductDTO.class) : null;
    }

    public boolean checkProductAvailabilityInBookings(Long productId, LocalDate eventDate) {
        List<Product> products = productRepository.findProductAvailabilityInBookings(productId, eventDate);
        return products.size() == 0;
    }

    public List<ProductDTO> getAllAvailableProductsByEventDate(LocalDate eventDate) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(product -> checkProductAvailabilityInBookings(product.getId(), eventDate))
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(productDTO.getName());
            Product updatedProduct = productRepository.save(existingProduct);
            return modelMapper.map(updatedProduct, ProductDTO.class);
        }

        return null;
    }

    public List<ProductDTO> getAllProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(product -> Objects.equals(product.getCategory().getId(), categoryId))
                .map((p) -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
