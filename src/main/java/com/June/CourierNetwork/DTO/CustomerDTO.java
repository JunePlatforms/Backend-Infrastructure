package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String username;
    private String customerNumber;
    private String profilePicture;
    private Boolean acceptedTermsAndConditions;
    private String mailBox;
    private User user;
    private Long userId;
}
