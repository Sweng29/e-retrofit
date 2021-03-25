package com.retrofit.app.model;

import com.retrofit.app.commons.BaseEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username",updatable = false,nullable = false,unique = true)
    private String username;
    @Column(name = "email",updatable = false,nullable = false,unique = true)
    private String email;
    private String password;
    @Column(name = "mobile_number",nullable = false,unique = true)
    private String mobileNumber;
    private Date dateOfBirth;
    private Boolean isActive;

}
