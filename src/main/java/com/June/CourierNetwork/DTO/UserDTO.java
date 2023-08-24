package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
public class UserDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String emailAddress;

    @NotNull
    private String password;

    private String phoneNumber;

    @NotNull
    private Boolean isVerified;

    @NotNull
    private Boolean isActive;

    public User toUser(){
        return User.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .emailAddress(this.emailAddress)
                .password(this.password)
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
