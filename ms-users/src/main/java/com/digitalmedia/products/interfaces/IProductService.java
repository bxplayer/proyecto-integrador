package com.digitalmedia.products.interfaces;


import com.digitalmedia.products.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
