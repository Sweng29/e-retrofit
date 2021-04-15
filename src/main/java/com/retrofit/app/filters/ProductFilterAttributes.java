package com.retrofit.app.filters;

import com.retrofit.app.payload.request.ERetrofitPageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterAttributes extends ERetrofitPageRequest {

    private Long productId;
    private boolean isActive = Boolean.TRUE;

    public static ProductFilterAttributes defaultFilter() {
        return new ProductFilterAttributes();
    }

}
