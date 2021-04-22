package com.retrofit.app.controller.v1;

import com.retrofit.app.controller.BaseController;
import com.retrofit.app.dto.ProductDTO;
import com.retrofit.app.payload.request.CreateProductPayload;
import com.retrofit.app.payload.request.UpdateProductPayload;
import com.retrofit.app.service.ProductService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/products")
@Api(
        tags = {"Product controller"},
        value = "Operations pertaining to products.")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN})
    public ProductDTO createProduct(@ModelAttribute CreateProductPayload createProductPayload)
    {
        return productService.createProduct(createProductPayload);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN})
    public ProductDTO updateProduct(@RequestBody UpdateProductPayload updateProductPayload)
    {
        return productService.updateProduct(updateProductPayload);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN,ROLE_USER})
    public ProductDTO findProductById(
            @Valid @Min(value = 1,message = "Product id can not be less then 1")
            @PathVariable Long productId)
    {
        return productService.findProductById(productId);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN,ROLE_USER})
    public ProductDTO deleteProductById(
            @Valid @Min(value = 1,message = "Product id can not be less then 1")
            @PathVariable Long productId)
    {
        return productService.deleteProductById(productId);
    }
}
