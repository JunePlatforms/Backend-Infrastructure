package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.Parishes;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.Address;
import com.June.CourierNetwork.Model.DeliveryDetails;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryDetailsMapper implements RowMapper<DeliveryDetails> {
    @Override
    public DeliveryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        val deliveryAddressData = rs.getString("drop_off_location");
        val pickupAddressData = rs.getString("pick_up_location");

        String[] deliveryAddressParts = deliveryAddressData.split("_");
        String[] pickupAddressParts = pickupAddressData.split("_");

        Address deliveryAddress = Address.builder()
                .line1(deliveryAddressParts[0])
                .line2(deliveryAddressParts[1])
                .city(deliveryAddressParts[2])
                .Parish(Parishes.valueOf(deliveryAddressParts[3]))
                .build();

        Address pickupAddress = Address.builder()
                .line1(pickupAddressParts[0])
                .line2(pickupAddressParts[1])
                .city(pickupAddressParts[2])
                .Parish(Parishes.valueOf(pickupAddressParts[3]))
                .build();

        return DeliveryDetails.builder()
                .pickUpLocation(pickupAddress)
                .dropOffLocation(deliveryAddress)
                .packageDescription(rs.getString("package_description"))
                .specialInstructions(rs.getString("special_instructions"))
                .status(DeliveryStatus.valueOf(rs.getString("status")))
                .customerFirstName(rs.getString("customer_first_name"))
                .customerLastName(rs.getString("customer_last_name"))
                .customerPhoneNumber(rs.getString("customer_phone_number"))
                .courierFirstName(rs.getString("courier_first_name"))
                .courierLastName(rs.getString("courier_last_name"))
                .courierPhoneNumber(rs.getString("courier_phone_number"))
                .build();
    }
}
