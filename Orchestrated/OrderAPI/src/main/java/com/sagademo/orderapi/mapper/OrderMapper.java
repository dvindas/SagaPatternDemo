package com.sagademo.orderapi.mapper;

import com.sagademo.orderapi.dto.ItemDTO;
import com.sagademo.orderapi.dto.OrderCreationRequestDTO;
import com.sagademo.orderapi.dto.OrderDTO;
import com.sagademo.orderapi.model.Order;
import com.sagademo.orderapi.model.OrderDetail;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;

@ApplicationScoped
public class OrderMapper {

    public Order toEntity(OrderCreationRequestDTO orderCreationRequestDTO, String correlationId) {
        var order = new Order();
        order.setCustomerId(orderCreationRequestDTO.customerId());
        order.setDetails(new ArrayList<>());
        var orderDetail = toEntity(orderCreationRequestDTO.item());
        orderDetail.setOrder(order);
        order.getDetails().add(orderDetail);
        order.setCorrelationId(correlationId);
        return order;
    }

    public OrderDetail toEntity(ItemDTO itemDTO) {
        var detail = new OrderDetail();
        detail.setProductId(itemDTO.productId());
        detail.setPrice(itemDTO.price());
        detail.setQuantity(itemDTO.quantity());
        return detail;
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO(order.getId(), order.getCustomerId(), order.getShippingCostUSD(), order.getTaxAmountUSD(),
                order.getSubTotalUSD(), order.getTotalUSD(), order.getCreationDate(), order.getStatus(), order.getCorrelationId(),
                toDTO(order.getDetails().getFirst()));
    }

    public ItemDTO toDTO(OrderDetail orderDetail) {
        return new ItemDTO(orderDetail.getId(), orderDetail.getProductId(), orderDetail.getPrice(), orderDetail.getQuantity());
    }
}
