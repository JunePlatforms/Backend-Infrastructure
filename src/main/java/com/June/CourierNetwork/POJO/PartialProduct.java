package com.June.CourierNetwork.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartialProduct {


    private String trackingNumber;

    private String description;

    private String productId;
}
