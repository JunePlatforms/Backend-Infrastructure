package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {
    Long courierId;
    Integer assessmentScore;
    Integer rating;
    Boolean isAvailable;
    Boolean acceptedTermsAndConditions;
    String policeRecord;
    String driversLicense;

    User user;

}
