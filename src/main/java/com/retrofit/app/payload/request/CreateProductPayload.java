package com.retrofit.app.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class CreateProductPayload {

    private String productName;
    private String productDescription;
    private MultipartFile[] productImageList;
    private Long categoryId;
    private Long manufacturerId;

}
