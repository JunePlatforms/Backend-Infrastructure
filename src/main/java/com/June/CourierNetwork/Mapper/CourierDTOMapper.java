package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.DTO.CourierDTO;
import com.June.CourierNetwork.Enum.ApplicationStatus;
import com.June.CourierNetwork.Enum.VehicleType;
import com.June.CourierNetwork.Model.Courier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierDTOMapper implements RowMapper<CourierDTO> {
    @Override
    public CourierDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        return CourierDTO.builder()
                .courierId((long) rs.getInt("id"))
                .acceptedTermsAndConditions(rs.getBoolean("accepted_terms_and_conditions"))
                .assessmentScore(rs.getInt("assessment_score"))
                .rating(rs.getInt("rating"))
                .isAvailable(rs.getBoolean("is_available"))
                .policeRecord(rs.getString("police_record"))
                .driversLicense(rs.getString("drivers_license"))
                .reason(rs.getString("reason"))
                .vehicleMake(rs.getString("vehicle_make"))
                .licensePlateNumber(rs.getString("license_plate_number"))
                .vehicleModel(rs.getString("vehicle_model"))
                .vehicleType(VehicleType.valueOf(rs.getString("vehicle_type")))
                .applicationStatus(ApplicationStatus.valueOf(rs.getString("application_status")))
                .userId(rs.getLong("user_id"))
                .build();
    }
}
