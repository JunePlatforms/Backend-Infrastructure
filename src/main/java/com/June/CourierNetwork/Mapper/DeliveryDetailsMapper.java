package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.POJO.Address;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.POJO.PartialProduct;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeliveryDetailsMapper implements RowMapper<DeliveryDetails> {
    @Override
    public DeliveryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        val deliveryAddressData = rs.getString("drop_off_location");
        val pickupAddressData = rs.getString("pick_up_location");
        val descriptionData = rs.getString("descriptions");
        val  trackingNumberData = rs.getString("trackingNumbers");
        val  productIdData = rs.getString("productIds");

        String[] deliveryAddressParts = deliveryAddressData.split("_");
        String[] pickupAddressParts = pickupAddressData.split("_");
        String[] descriptionParts = descriptionData.split("~");
        String[] trackingNumberParts = trackingNumberData.split("~");
        String[] productIdParts = productIdData.split("~");

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

        List<PartialProduct> partialProducts = new ArrayList<>();

        for (int i = 0; i < trackingNumberParts.length; i++) {
            partialProducts.add(PartialProduct.builder()
                    .trackingNumber(trackingNumberParts[i])
                    .description(descriptionParts[i])
                    .productId(productIdParts[i])
                    .build());
        }

        return DeliveryDetails.builder()
                .pickUpLocation(pickupAddress)
                .dropOffLocation(deliveryAddress)
                .packageDescription(partialProducts)
                .specialInstructions(rs.getString("special_instructions"))
                .status(DeliveryStatus.valueOf(rs.getString("status")))
                .customerFirstName(rs.getString("customerFirstName"))
                .customerLastName(rs.getString("customerLastName"))
                .customerPhoneNumber(rs.getString("customerPhoneNumber"))
                .courierFirstName(rs.getString("courierFirstName"))
                .courierLastName(rs.getString("courierLastName"))
                .courierPhoneNumber(rs.getString("courierPhoneNumber"))
                .deliveryDateTime(rs.getTimestamp("delivery_date_time").toLocalDateTime())
                .courierId(rs.getLong("courierId"))
                .customerId(rs.getLong("customerId"))
                .build();
    }
}
