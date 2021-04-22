package com.retrofit.app.model;

import com.retrofit.app.commons.BaseEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    private String productName;
    private String productDescription;
    private BigDecimal unitPrice;
    private BigDecimal tradePrice;
    private Double productRating;
    private Boolean isActive = Boolean.TRUE;
    @ManyToOne(fetch = FetchType.EAGER)
    private Manufacturer manufacturer;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "images",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private List<Image> productImageList;

}
