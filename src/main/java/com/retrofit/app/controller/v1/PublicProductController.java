package com.retrofit.app.controller.v1;

import com.retrofit.app.controller.BaseController;
import com.retrofit.app.dto.ProductDTO;
import com.retrofit.app.filters.ProductFilterAttributes;
import com.retrofit.app.service.ProductService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/public/products")
@Api(
        tags = {"Public Product controller"},
        value = "Operations pertaining to public products.")
public class PublicProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN,ROLE_USER})
    public List<ProductDTO> findAllProducts(@RequestBody
            ProductFilterAttributes productFilterAttributes)
    {
        return productService.findAllProducts(productFilterAttributes);
    }

}
