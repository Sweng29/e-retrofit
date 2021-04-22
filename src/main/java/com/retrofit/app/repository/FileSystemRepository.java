package com.retrofit.app.repository;

import com.retrofit.app.exception.BadRequestException;
import com.retrofit.app.exception.ResourceNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileSystemRepository {

    private String RESOURCES_DIR = FileSystemRepository.class.getResource("/")
            .getPath();

    public String saveImageFile(byte[] content, String imageName) {

        try{
            Path newFile = Paths.get(RESOURCES_DIR +
                    new Date().getTime() +
                    UUID.randomUUID() + "-" + imageName);
            Files.createDirectories(newFile.getParent());
            Files.write(newFile, content);
            return newFile.toAbsolutePath()
                    .toString();
        }catch (IOException e)
        {
            throw new BadRequestException("Could not upload product image "+imageName);
        }
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new ResourceNotFoundException(location);
        }
    }
}
