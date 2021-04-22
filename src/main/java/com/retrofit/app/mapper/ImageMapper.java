package com.retrofit.app.mapper;

import com.retrofit.app.dto.ImageDTO;
import com.retrofit.app.model.Image;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface ImageMapper {

    static ImageDTO map(Image image)
    {
        if (image == null)
            return null;

        return ImageDTO
                .builder()
                .imageUrl(image.getImageUrl())
                .imageType(image.getImageType())
                .imageName(image.getImageName())
                .imageId(image.getId())
                .isActive(image.getIsActive())
                .build();
    }

    static List<ImageDTO> map(List<Image> imageList)
    {
        if (imageList == null || imageList.isEmpty())
            return Collections.emptyList();
        return imageList
                .stream()
                .map(ImageMapper::map)
                .collect(Collectors.toList());
    }

}
