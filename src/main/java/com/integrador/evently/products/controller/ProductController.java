package com.integrador.evently.products.controller;

import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.interfaces.IProductController;
import com.integrador.evently.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController implements IProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return (productDTO != null)
                ? new ResponseEntity<>(productDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProductDTO>> getAvailableProductsByEventDate(@RequestParam LocalDate eventDate) {
        List<ProductDTO> products = productService.getAllAvailableProductsByEventDate(eventDate);
        return (products.size() > 0)
                ? new ResponseEntity<>(products, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable Long id) {
        List<ProductDTO> products = productService.getAllProductsByCategory(id);
        return (products.size() > 0)
                ? new ResponseEntity<>(products, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.saveProduct(productDTO);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody  ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
