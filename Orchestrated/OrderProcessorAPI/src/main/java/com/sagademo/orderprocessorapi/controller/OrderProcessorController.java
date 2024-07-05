package com.sagademo.orderprocessorapi.controller;

import com.sagademo.orderprocessorapi.dto.OrderCreationRequestDTO;
import com.sagademo.orderprocessorapi.service.OrderProcessorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.*;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.logging.Logger;

@Path("/orderProcessor")
@ApplicationScoped
@Tag(name = "orderProcessor", description = "Endpoints for processing orders")
public class OrderProcessorController {

    private static final Logger LOG = Logger.getLogger(OrderProcessorController.class.getName());

    private final OrderProcessorService orderProcessorService;

    @Inject
    public OrderProcessorController(OrderProcessorService orderProcessorService) {
        this.orderProcessorService = orderProcessorService;
    }

    @LRA(value = LRA.Type.REQUIRES_NEW)
    @Path("placeOrder")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(@Parameter(hidden = true) @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                               @Valid OrderCreationRequestDTO orderCreationRequestDTO) {
        LOG.info("OrderProcessorAPI placeOrder | Saga STARTED with LRA ID: " + lraId);
        var response = this.orderProcessorService.process(orderCreationRequestDTO, lraId.toString());
        return Response.ok(response).build();
    }

    @Complete
    @Path("complete")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response completePlaceOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("OrderProcessorAPI completePlaceOrder | Saga COMPLETED with LRA ID: " + lraId);
        // Here you can put your logic if applicable.
        return Response.ok(ParticipantStatus.Completed.name()).build();
    }

    @Compensate
    @Path("compensate")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response compensatePlaceOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("OrderProcessorAPI compensatePlaceOrder | Saga COMPENSATED  LRA ID: " + lraId);
        // Here you can put your logic if applicable.
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }

    @AfterLRA
    @PUT
    @Path("/after")
    @Operation(hidden = true)
    public Response afterLRA(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) URI lraId,
                             LRAStatus status) {
        LOG.info("OrderProcessorAPI afterLRA | Saga ENDED LRA ID: " + lraId);
        if (status == LRAStatus.Closed) {
            LOG.info("LRA ID: " + lraId + " has been closed successfully");
        } else {
            LOG.info("LRA ID: " + lraId + " has been status " + status);
            // Here you can put your logic to create and alert or whatever you want
        }
        return Response.ok().build();
    }
}


