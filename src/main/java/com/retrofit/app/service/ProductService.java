package com.retrofit.app.service;

import com.retrofit.app.dto.ProductDTO;
import com.retrofit.app.filters.ProductFilterAttributes;
import com.retrofit.app.model.Product;
import com.retrofit.app.payload.request.CreateProductPayload;
import com.retrofit.app.payload.request.UpdateProductPayload;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductPayload createProductPayload);
    ProductDTO updateProduct(UpdateProductPayload updateProductPayload);
    ProductDTO findProductById(Long productId);
    ProductDTO deleteProductById(Long productId);

    Product findProductGivenId(Long productId);

    List<ProductDTO> findAllProducts(ProductFilterAttributes filterAttributes);
}
