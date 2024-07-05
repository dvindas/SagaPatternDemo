package com.sagademo.warehouseapi.controller;

import com.sagademo.warehouseapi.dto.ReserveRequestDTO;
import com.sagademo.warehouseapi.mapper.InventoryMapper;
import com.sagademo.warehouseapi.helper.JsonHelper;
import com.sagademo.warehouseapi.service.InventoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/inventoryReservations")
@ApplicationScoped
@Tag(name = "inventoryReservations", description = "Endpoints to manage inventory")
public class InventoryReservationController {

    private static final Logger LOG = Logger.getLogger(InventoryReservationController.class.getName());

    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    @Inject
    public InventoryReservationController(InventoryService inventoryService, InventoryMapper inventoryMapper) {
        this.inventoryService = inventoryService;
        this.inventoryMapper = inventoryMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservations() {
        var result = inventoryService.getAll()
                .map(inventoryMapper::toDTO)
                .toList();
        return Response.ok(result).build();
    }

    @LRA(value = LRA.Type.MANDATORY, end = false)
    @Path("/reserve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reserve(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                            @Valid ReserveRequestDTO reserveRequestDTO) {
        LOG.log(Level.INFO, "WarehouseAPI reserve | LRA ID {0} | ReserveRequestDTO: {1}",
                new Object[]{lraId, JsonHelper.toJson(reserveRequestDTO)});
        var reservation = inventoryService.reserveProduct(reserveRequestDTO.productId(), inventoryMapper.toEntity(reserveRequestDTO, lraId.toString()));
        return Response.ok(inventoryMapper.toDTO(reservation)).build();
    }

    @Complete
    @Path("/complete")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response completeReserve(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("WarehouseAPI completeReserve | LRA ID: " + lraId);
        inventoryService.confirmReservation(lraId.toString());
        return Response.noContent().build();
    }

    @Compensate
    @Path("/compensate")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response compensateReserve(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("WarehouseAPI compensateReserve | LRA ID: " + lraId);
        inventoryService.reverseReservation(lraId.toString());
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }

}
