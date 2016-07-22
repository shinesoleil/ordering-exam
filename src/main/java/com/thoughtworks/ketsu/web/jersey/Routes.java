package com.thoughtworks.ketsu.web.jersey;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.payment.Payment;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Routes {

    public Routes(UriInfo uriInfo) {
    }

    public Routes() {

    }

    public URI userUrl(User user) {
        return URI.create("users/" + user.getId());
    }

    public URI productUrl(Product product) {
        return URI.create("products/" + product.getId());
    }

    public URI orderUrl(Order order) {
        return URI.create("users/" + order.getUserId() + "/orders/" + order.getId());
    }

    public URI paymentUrl(Payment payment, int userId) {
        return URI.create("users/" + userId + "/orders/" + payment.getOrderId() + "/payment");
    }
}
