package com.retrofit.app.service.impl;

import com.retrofit.app.dto.ImageDTO;
import com.retrofit.app.exception.BadRequestException;
import com.retrofit.app.mapper.ImageMapper;
import com.retrofit.app.model.Image;
import com.retrofit.app.repository.FileSystemRepository;
import com.retrofit.app.repository.ImageRepository;
import com.retrofit.app.service.ImageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<ImageDTO> saveProductImages(MultipartFile[] productImageList) {
        List<ImageDTO> imageFileList = new ArrayList<>();
            Arrays.stream(productImageList).forEach(
                    imageFile -> imageFileList.add(uploadImageFile(imageFile))
            );
        return imageFileList;
    }

    private ImageDTO uploadImageFile(MultipartFile imageFile) {
        try {
            String imageUrl = fileSystemRepository
                    .saveImageFile(imageFile.getBytes(),imageFile.getName());
            return ImageDTO
                    .builder()
                    .imageName(imageFile.getName())
                    .imageType(imageFile.getContentType())
                    .imageUrl(imageUrl)
                    .isActive(Boolean.TRUE)
                    .build();
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<ImageDTO> findImagesByProductId(Long productId) {
        List<Image> imageList = imageRepository.findAllByProductId(productId);
        return ImageMapper.map(imageList);
    }
}
