package com.retrofit.app.service.impl;

import com.retrofit.app.dto.ProductDTO;
import com.retrofit.app.exception.BadRequestException;
import com.retrofit.app.exception.error.ProductErrorType;
import com.retrofit.app.filters.ProductFilterAttributes;
import com.retrofit.app.helper.PageUtils;
import com.retrofit.app.mapper.ProductMapper;
import com.retrofit.app.model.Product;
import com.retrofit.app.payload.request.CreateProductPayload;
import com.retrofit.app.payload.request.UpdateProductPayload;
import com.retrofit.app.repository.ProductRepository;
import com.retrofit.app.service.ProductService;
import com.retrofit.app.specifications.ProductSpecification;
import com.retrofit.app.validator.ProductValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(CreateProductPayload createProductPayload) {
        ProductValidator.validateCreateProductPayload(createProductPayload);
        Product product = ProductMapper.mapToProduct(createProductPayload);
        return ProductMapper.map(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(UpdateProductPayload updateProductPayload) {
        ProductValidator.validateUpdateProductPayload(updateProductPayload);
        Product product = ProductMapper.mapToProduct(updateProductPayload);
        return ProductMapper.map(productRepository.save(product));
    }

    @Override
    public ProductDTO findProductById(Long productId) {
        Product product = findProductGivenId(productId);
        return ProductMapper.map(product);
    }

    @Override
    public ProductDTO deleteProductById(Long productId) {
        Product product = findProductGivenId(productId);
        product.setIsActive(Boolean.FALSE);
        return ProductMapper.map(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> findAllProducts(ProductFilterAttributes filterAttributes) {
        Pageable pageable = PageUtils.createPageWithSort(filterAttributes);
        Page<Product> page = productRepository.findAll(
                ProductSpecification.filter(filterAttributes),
                pageable);
        return page.map(ProductMapper::map).getContent();
    }

    @Override
    public Product findProductGivenId(Long productId)
    {
        return productRepository.findById(productId)
                .orElseThrow(
                        () -> new BadRequestException(
                                ProductErrorType
                                        .PRODUCT_NOT_AVAILABLE_WITH_ID
                                        .getErrorMessage())
                );
    }
}
