package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long customerId;
    private String username;
    private String customerNumber;
    private byte[] profilePicture;
    private Boolean acceptedTermsAndConditions;
    private String mailBox;
    private User user;
}
