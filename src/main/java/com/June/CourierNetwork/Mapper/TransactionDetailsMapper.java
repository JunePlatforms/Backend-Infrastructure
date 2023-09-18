package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Enum.PaymentType;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.Model.TransactionDetails;
import com.June.CourierNetwork.POJO.Address;
import com.June.CourierNetwork.POJO.PartialProduct;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDetailsMapper implements RowMapper<TransactionDetails> {
    @Override
    public TransactionDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        val descriptionData = rs.getString("descriptions");
        val  trackingNumberData = rs.getString("trackingNumbers");

        String[] descriptionParts = descriptionData.split("~");
        String[] trackingNumberParts = trackingNumberData.split("~");


        List<PartialProduct> partialProducts = new ArrayList<>();

        for (int i = 0; i < trackingNumberParts.length; i++) {
            partialProducts.add(PartialProduct.builder()
                    .trackingNumber(trackingNumberParts[i])
                    .description(descriptionParts[i])
                    .build());
        }

        return TransactionDetails.builder()
                .products(partialProducts)
                .customerFirstName(rs.getString("customerFirstName"))
                .customerLastName(rs.getString("customerLastName"))
                .customerNumber(rs.getString("customer_number"))
                .createdOn(rs.getTimestamp("created_on").toLocalDateTime())
                .customerId(rs.getLong("id"))
                .wasDelivered(rs.getBoolean("was_delivered"))
                .totalSpent(BigDecimal.valueOf(rs.getInt("total_spent")))
                .paymentType(PaymentType.valueOf(rs.getString("payment_type")))
                .build();
    }
}
