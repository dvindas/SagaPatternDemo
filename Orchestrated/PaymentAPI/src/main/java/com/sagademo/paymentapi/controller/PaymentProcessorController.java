package com.sagademo.paymentapi.controller;

import com.sagademo.paymentapi.helper.JsonHelper;
import com.sagademo.paymentapi.controller.dto.PaymentRequestDTO;
import com.sagademo.paymentapi.controller.dto.PaymentResponseDTO;
import com.sagademo.paymentapi.service.PaymentProcessorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

@Path("/paymentProcessor")
@ApplicationScoped
@Tag(name = "paymentProcessor", description = "Endpoints for payment processing")
public class PaymentProcessorController {

    private static final Logger LOG = Logger.getLogger(PaymentProcessorController.class.getName());

    private final PaymentProcessorService paymentProcessorService;

    @Inject
    public PaymentProcessorController(PaymentProcessorService paymentProcessorService) {
        this.paymentProcessorService = paymentProcessorService;
    }

    @LRA(value = LRA.Type.MANDATORY, end = false)
    @Path("/process")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processPayment(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                                   PaymentRequestDTO paymentRequestDTO) {
        LOG.log(Level.INFO, "PaymentAPI processPayment | LRA ID {0} | paymentRequestDTO: {1}",
                new Object[]{lraId, JsonHelper.toJson(paymentRequestDTO)});
        var paymentId = paymentProcessorService.process(paymentRequestDTO.customerId(), paymentRequestDTO.totalUSD());
        var paymentResponse = new PaymentResponseDTO(paymentId, "SUCCESS");
        return Response.ok(paymentResponse).build();
    }


    @Complete
    @Path("/complete")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public Response completeProcessPayment(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("PaymentAPI completeProcessPayment | LRA ID: " + lraId);
        // You can put your logic here if necessary
        return Response.ok(ParticipantStatus.Completed.name()).build();
    }

    @Compensate
    @Path("/compensate")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Operation(hidden = true)
    public Response compensateProcessPayment(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        LOG.info("PaymentAPI compensateProcessPayment LRA ID: " + lraId);
        paymentProcessorService.refund(lraId.toString());
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }

}
