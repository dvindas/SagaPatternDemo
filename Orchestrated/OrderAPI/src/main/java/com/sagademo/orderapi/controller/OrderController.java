package com.sagademo.orderapi.controller;

import com.sagademo.orderapi.helper.JsonHelper;
import com.sagademo.orderapi.dto.OrderCreationRequestDTO;
import com.sagademo.orderapi.mapper.OrderMapper;
import com.sagademo.orderapi.service.OrderService;
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


@Path("/orders")
@ApplicationScoped
@Tag(name = "orders", description = "Endpoints to manage orders")
public class OrderController {

    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Inject
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        var result = orderService.getAll()
                .map(orderMapper::toDTO)
                .toList();
        return Response.ok(result).build();
    }

    @LRA(value = LRA.Type.MANDATORY, end = false)
    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                                @Valid OrderCreationRequestDTO orderCreationRequestDTO) {
        LOG.log(Level.INFO, "OrderAPI createOrder | LRA ID {0} | OrderCreationRequestDTO: {1}",
                new Object[]{lraId, JsonHelper.toJson(orderCreationRequestDTO)});
        var createdOrder = this.orderService.create(orderMapper.toEntity(orderCreationRequestDTO, lraId.toString()));
        return Response.ok(orderMapper.toDTO(createdOrder)).build();
    }

    @Complete
    @Path("/complete")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response completeCreateOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("OrderAPI completeCreateOrder | LRA ID: " + lraId);
        orderService.confirmOrder(lraId.toString());
        return Response.ok(ParticipantStatus.Completed.name()).build();
    }

    @Compensate
    @Path("/compensate")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response compensateCreateOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("OrderAPI compensateCreateOrder | LRA ID: " + lraId);
        orderService.cancelOrder(lraId.toString());
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }

}
