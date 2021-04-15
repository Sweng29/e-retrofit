package com.retrofit.app.model;

import com.retrofit.app.commons.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "manufacturers")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer extends BaseEntity {

    private String manufacturerName;
    private Boolean isActive = Boolean.TRUE;
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> productList;

}
