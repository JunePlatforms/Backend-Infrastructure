package com.June.CourierNetwork.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    public String line1;

    public String line2;

    public String city;

    public String parish;
}
