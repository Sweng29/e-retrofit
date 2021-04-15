package com.retrofit.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private ManufacturerDTO manufacturerDTO;
    private BigDecimal unitPrice;
    private BigDecimal tradePrice;
    private Double productRating;
    private Boolean isActive;

}
