package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier{
    Long courierId;
    Integer assessmentScore;
    Integer rating;
    Boolean isAvailable;
    Boolean acceptedTermsAndConditions;

    User user;

}
