
package com.sagademo.paymentapi;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;

@HelidonTest
class MainTest {

    @Inject
    private WebTarget target;

}
