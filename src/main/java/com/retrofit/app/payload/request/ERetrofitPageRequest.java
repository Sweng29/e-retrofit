package com.retrofit.app.payload.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ERetrofitPageRequest {

    private Integer pageNumber;
    private Integer pageSize;
    private boolean descending;
    private String keyword;
    private List<String> sortProperties;

    public ERetrofitPageRequest() {
        this.pageNumber = 1;
        this.pageSize = 10;
        this.descending = true;
        this.sortProperties = new ArrayList<>();
        this.sortProperties.add("id");
    }

    public static ERetrofitPageRequest of(
            Integer pageNumber,
            Integer pageSize,
            Boolean descending,
            List<String> sortProperties) {

        ERetrofitPageRequest eRetrofitPageRequest = new ERetrofitPageRequest();
        eRetrofitPageRequest.setPageNumber(pageNumber);
        eRetrofitPageRequest.setPageSize(pageSize);
        if (CollectionUtils.isEmpty(sortProperties)) {
            eRetrofitPageRequest.setSortProperties(Collections.singletonList("id"));
        } else {
            eRetrofitPageRequest.setSortProperties(sortProperties);
        }
        eRetrofitPageRequest.setDescending(descending);
        return eRetrofitPageRequest;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = (pageNumber == null) ? 1 : pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null) ? 10 : pageSize;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending != null && descending;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setSortProperties(List<String> sortProperties) {
        this.sortProperties = sortProperties;
    }
}
