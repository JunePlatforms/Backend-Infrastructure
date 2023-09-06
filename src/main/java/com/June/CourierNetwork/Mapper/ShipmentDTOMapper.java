package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipmentDTOMapper implements RowMapper<ShipmentDTO> {
    @Override
    public ShipmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ShipmentDTO.builder()
                .id((long) rs.getInt("id"))
                .shipmentManifestFileName(rs.getString("shipment_manifest"))
                .airwayInvoiceFileName(rs.getString("airway_invoice"))
                .departureDate(rs.getDate("departure_date"))
                .arrivalDate(rs.getDate("arrival_date"))
                .type(ShipmentType.valueOf(rs.getString("shipment_type")))
                .status(ShipmentStatus.valueOf(rs.getString("status")))
                .build();
    }
}
