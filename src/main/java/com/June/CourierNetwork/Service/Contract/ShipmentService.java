package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.Shipment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShipmentService {
    void createShipment(ShipmentDTO shipmentDTO, MultipartFile airwayInvoice, MultipartFile shipmentManifest) throws IOException;

    void updateShipment(Long shipmentId, ShipmentDTO shipmentDTO);

    void deleteShipment(Long shipmentId);

    List<Shipment> getAllShipments() throws IOException;

    Shipment findShipmentById(Long shipmentId) throws IOException;

    void setShipmentType(Long shipmentId, ShipmentType shipmentType);

    void updateShipmentStatus(Long shipmentId, ShipmentStatus status);
    String getAirwayInvoiceFileName(Long shipmentId);

}
