package com.retrofit.app.model;

import com.retrofit.app.commons.BaseEntity;
import com.retrofit.app.constants.ProfileStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "mobile_number"
        })
})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username",updatable = false,nullable = false,unique = true)
    private String username;
    @Column(name = "email",updatable = false,nullable = false,unique = true)
    @Email
    private String email;
    private String password;
    @Column(name = "mobile_number",nullable = false,unique = true)
    private String mobileNumber;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus;
    private Boolean isActive;
    @ManyToOne
    private Role role;

}
