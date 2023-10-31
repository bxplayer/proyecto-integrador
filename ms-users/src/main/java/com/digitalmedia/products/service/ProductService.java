package com.digitalmedia.products.service;

import com.digitalmedia.products.dto.ProductDTO;
import com.digitalmedia.products.interfaces.IProductService;
import com.digitalmedia.products.model.Product;
import com.digitalmedia.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
