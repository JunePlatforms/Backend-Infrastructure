package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingLabel {
    private String firstName;
    private String lastName;
    private String customerNumber;
    private String weight;
    private String description;
    private String juneAddress;
}
