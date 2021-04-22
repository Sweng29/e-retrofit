package com.retrofit.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDTO {

    private Long imageId;
    private String imageName;
    private String imageType;
    private String imageUrl;
    private Boolean isActive;
    private FileSystemResource fileSystemResource;

}
