package com.sagademo.warehouseapi.service;

import com.sagademo.warehouseapi.exception.NotAvailableInventoryException;
import com.sagademo.warehouseapi.model.InventoryReservation;
import com.sagademo.warehouseapi.model.ProductInventory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@ApplicationScoped
public class InventoryService {

    @PersistenceContext(unitName = "WarehousePU")
    private EntityManager em;

    public Stream<InventoryReservation> getAll() {
        return em.createNamedQuery("InventoryReservation.findAll", InventoryReservation.class)
                .getResultStream();
    }

    private Optional<InventoryReservation> getByCorrelationId(String correlationId) {
        try {
            var order = em.createNamedQuery("InventoryReservation.findByCorrelationId", InventoryReservation.class)
                    .setParameter("correlationId", correlationId)
                    .getSingleResult();
            return Optional.of(order);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    private Optional<ProductInventory> getProductInventory(String productId) {
        try {
            var productInventory = em.createNamedQuery("ProductInventory.findByProductId", ProductInventory.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
            return Optional.of(productInventory);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Transactional
    public InventoryReservation reserveProduct(String productId, InventoryReservation reservation) {
        final var existingReservation = getByCorrelationId(reservation.getCorrelationId());
        if (existingReservation.isPresent()) {
            return existingReservation.get();
        }

        final var optProductInventory = getProductInventory(productId);
        if (optProductInventory.isEmpty()) {
            throw new NotAvailableInventoryException("There is no inventory for the product.");
        }

        final var inventory = optProductInventory.get();

        validateQuantityAvailable(inventory, reservation.getQuantityReserved());
        updateProductInventory(inventory, i -> i.setQuantity(i.getQuantity() - reservation.getQuantityReserved()));
        return createReservation(inventory, reservation);
    }

    private void validateQuantityAvailable(ProductInventory productInventory, int quantityToReserve) {
        if (quantityToReserve > productInventory.getQuantity()) {
            throw new NotAvailableInventoryException(String.format("The requested quantity %d exceeds the available inventory of %d.", quantityToReserve, productInventory.getQuantity()));
        }
    }

    private void updateProductInventory(ProductInventory productInventory, Consumer<ProductInventory> updateAction) {
        updateAction.accept(productInventory);
        em.merge(productInventory);
    }

    private InventoryReservation createReservation(ProductInventory inventory, InventoryReservation reservation) {
        reservation.setProductInventory(inventory);
        em.persist(reservation);
        return reservation;
    }

    @Transactional
    public Optional<InventoryReservation> confirmReservation(String correlationId){
        var optReservation = getByCorrelationId(correlationId);
        if(optReservation.isPresent()){
            var reservation = updateReservation(optReservation.get(), r -> r.setStatus("CONFIRMED"));
            return Optional.of(reservation);
        }
        return Optional.empty();

    }

    @Transactional
    public Optional<InventoryReservation> reverseReservation(String correlationId) {
        var optReservation = getByCorrelationId(correlationId);
        if(optReservation.isPresent()){
            final var updatedReservation = updateReservation(optReservation.get(), r -> r.setStatus("COMPENSATED"));
            updateProductInventory(optReservation.get().getProductInventory(), p -> p.setQuantity(p.getQuantity() + optReservation.get().getQuantityReserved()));
            return Optional.of(updatedReservation);
        }
        return Optional.empty();
    }

    private InventoryReservation updateReservation(InventoryReservation reservation, Consumer<InventoryReservation> updateAction) {
        updateAction.accept(reservation);
        return em.merge(reservation);
    }
}
