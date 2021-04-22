package com.retrofit.app.repository;

import com.retrofit.app.model.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    @Query("FROM Image i WHERE i.product.id = ?1")
    List<Image> findAllByProductId(Long productId);

}
