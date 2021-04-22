package com.retrofit.app.service;

import com.retrofit.app.dto.ImageDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    List<ImageDTO> saveProductImages(MultipartFile [] productImageList);
    List<ImageDTO> findImagesByProductId(Long productId);
}
