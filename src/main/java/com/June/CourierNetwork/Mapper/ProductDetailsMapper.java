package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Enum.TokenType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.Token;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsMapper implements RowMapper<ProductDetails> {
    @Override
    public ProductDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductDetails.builder()
                .id((long) rs.getInt("id"))
                .weight(rs.getString("weight"))
                .description(rs.getString("description"))
                .supplierName(rs.getString("supplier_name"))
                .customerNumber(rs.getString("customer_number"))
                .trackingNumber(rs.getString("tracking_number"))
                .shipmentType(ShipmentType.valueOf(rs.getString("shipment_type")))
                .packageStatus(PackageStatus.valueOf(rs.getString("status")))
                .build();
    }
}
