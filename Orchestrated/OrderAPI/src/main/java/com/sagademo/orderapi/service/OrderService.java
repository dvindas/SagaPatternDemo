package com.sagademo.orderapi.service;

import com.sagademo.orderapi.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@ApplicationScoped
public class OrderService {

    @PersistenceContext(unitName = "OrderAPIPU")
    private EntityManager em;

    private final PricingService pricingService;

    @Inject
    public OrderService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public Stream<Order> getAll() {
        return em.createNamedQuery("Order.findAll", Order.class)
                .setHint("jakarta.persistence.fetchgraph", em.getEntityGraph("Order.details"))
                .getResultStream();
    }

    @Transactional
    public Order create(Order order) {
        var optionalOrder = getByCorrelationId(order.getCorrelationId());

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }

        pricingService.calculate(order);
        order.setStatus("PENDING_TO_CONFIRM");
        em.persist(order);
        em.flush();
        return order;
    }

    private Optional<Order> getByCorrelationId(String correlationId) {
        try {
            var order = em.createNamedQuery("Order.findByCorrelationId", Order.class)
                    .setParameter("correlationId", correlationId)
                    .getSingleResult();
            return Optional.of(order);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Order> confirmOrder(String correlationId) {
        return updateStatus(correlationId, "CONFIRMED");
    }

    @Transactional
    public Optional<Order> cancelOrder(String correlationId) {
       return updateStatus(correlationId, "CANCELLED");
    }

    private Optional<Order> updateStatus(String correlationId, String status) {
        var optOrder = getByCorrelationId(correlationId);
        if(optOrder.isPresent()){
            var updatedOrder = update(optOrder.get(), o -> o.setStatus(status));
            return Optional.of(updatedOrder);
        }
        return Optional.empty();
    }

    private Order update(Order order, Consumer<Order> updateAction) {
        updateAction.accept(order);
        order = em.merge(order);
        em.flush();
        return order;
    }

}
