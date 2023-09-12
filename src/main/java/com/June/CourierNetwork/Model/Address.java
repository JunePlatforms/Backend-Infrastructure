package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.Parishes;
import com.June.CourierNetwork.Enum.TokenType;
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

    public Parishes Parish;
}
