package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {

    void createShipment(ShipmentDTO shipmentDTO);

    void updateShipment(Long shipmentId, ShipmentDTO shipmentDTO);

    void deleteShipment(Long shipmentId);

    List<ShipmentDTO> getAllShipments();

    Optional<ShipmentDTO> findShipmentById(Long shipmentId);

    void setShipmentType(Long shipmentId, ShipmentType shipmentType);

    void updateShipmentStatus(Long shipmentId, ShipmentStatus status);

    String getAirwayInvoiceFileName(Long shipmentId);

    void updateShipmentManifestFileName(String fileName, Long shipmentId);

    void updateAirwayInvoiceFileName(String fileName, Long shipmentId);
}
