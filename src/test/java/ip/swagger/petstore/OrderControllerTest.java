package ip.swagger.petstore;

import io.swagger.oas.inflector.models.RequestContext;
import io.swagger.oas.inflector.models.ResponseContext;
import io.swagger.petstore.controller.OrderController;
import io.swagger.petstore.data.OrderData;
import io.swagger.petstore.data.PetData;
import io.swagger.petstore.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderControllerTest {

    private RequestContext request;
    private final OrderController orderController = new OrderController();

    @BeforeEach
    void setUpRequest() {
        request = new RequestContext();
        request.setHeaders(new MultivaluedHashMap<>());
        request.setAcceptableMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    void placeOrderRejectsDeletedPet() {
        final long petId = 1L;
        new PetData().deletePetById(petId);

        final Order order = OrderData.createOrder(9001L, petId, 1, new Date(), "placed", false);
        final ResponseContext response = orderController.placeOrder(request, order);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Pet not found", response.getEntity());
    }

    @Test
    void placeOrderAcceptsExistingPet() {
        final long petId = 4L;
        final Order order = OrderData.createOrder(9002L, petId, 2, new Date(), "placed", false);
        final ResponseContext response = orderController.placeOrder(request, order);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(petId, ((Order) response.getEntity()).getPetId());
    }
}
