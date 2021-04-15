package com.retrofit.app.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateProductPayload {

    private Long productId;
    private String productName;
    private String productDescription;

}
