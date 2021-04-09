package com.retrofit.app.helper;

import com.retrofit.app.payload.request.ERetrofitPageRequest;
import java.security.InvalidParameterException;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

public class PageUtils {

    private PageUtils(){}

    public static Pageable createPage(int page, int size) {
        return PageRequest.of(page - 1, size);
    }

    public static Pageable createPage(int page, int size, Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }

    public static Pageable createPageWithSort(ERetrofitPageRequest eRetrofitPageRequest) {

        Sort sort =
                getSortOrder(
                        eRetrofitPageRequest.getSortProperties(),
                        eRetrofitPageRequest.isDescending());
        if (eRetrofitPageRequest.getPageNumber() <= 0) {
            throw new InvalidParameterException(
                    "Page index must not be less than or equal to zero!");
        }
        return PageRequest.of(
                eRetrofitPageRequest.getPageNumber() - 1,
                eRetrofitPageRequest.getPageSize(),
                sort);
    }

    public static Sort getSortOrder(List<String> properties, boolean isDescending) {

        if (CollectionUtils.isEmpty(properties)) {
            return  Sort.by(isDescending ? Sort.Direction.DESC : Sort.Direction.ASC,"id");
        }
        return  Sort.by(isDescending ? Sort.Direction.DESC : Sort.Direction.ASC, properties.get(0));
    }
}
