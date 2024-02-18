package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsDTOMapper implements RowMapper<ProductDetailsDTO> {
    @Override
    public ProductDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductDetailsDTO.builder()
                .id((long) rs.getInt("id"))
                .weight(rs.getString("weight"))
                .description(rs.getString("description"))
                .supplierName(rs.getString("supplier_name"))
                .customerNumber(rs.getString("customer_number"))
                .customerFirstName(rs.getString("first_name"))
                .customerLastName(rs.getString("last_name"))
                .trackingNumber(rs.getString("tracking_number"))
                .shipmentType(ShipmentType.valueOf(rs.getString("shipment_type")))
                .packageStatus(PackageStatus.valueOf(rs.getString("status")))
                .preAlertFileName(rs.getString("pre_alert"))
                .createdOn(rs.getTimestamp("created_on"))
                .JunId(rs.getString("jun_id"))
                .updatedOn(rs.getTimestamp("updated_on"))
                .build();
    }
}
