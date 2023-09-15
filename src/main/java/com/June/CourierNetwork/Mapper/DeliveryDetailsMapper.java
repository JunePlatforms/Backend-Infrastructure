package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.POJO.Address;
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
                .parish(deliveryAddressParts[3])
                .build();

        Address pickupAddress = Address.builder()
                .line1(pickupAddressParts[0])
                .line2(pickupAddressParts[1])
                .city(pickupAddressParts[2])
                .parish(pickupAddressParts[3])
                .build();

        return DeliveryDetails.builder()
                .pickUpLocation(pickupAddress)
                .dropOffLocation(deliveryAddress)
                .packageDescription(rs.getString("description"))
                .specialInstructions(rs.getString("special_instructions"))
                .status(DeliveryStatus.valueOf(rs.getString("status")))
                .customerFirstName(rs.getString("customerFirstName"))
                .customerLastName(rs.getString("customerLastName"))
                .customerPhoneNumber(rs.getString("customerPhoneNumber"))
                .courierFirstName(rs.getString("courierFirstName"))
                .courierLastName(rs.getString("courierLastName"))
                .courierPhoneNumber(rs.getString("courierPhoneNumber"))
                .deliveryDateTime(rs.getTimestamp("delivery_date_time").toLocalDateTime())
                .build();
    }
}
