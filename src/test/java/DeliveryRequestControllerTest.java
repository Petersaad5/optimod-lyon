import com.optimodlyon.optimodlyon.service.DeliveryRequestService;
import org.junit.Test;

public class DeliveryRequestControllerTest {
    String couriers = "[{\"id\":7,\"name\":\"Parker Thomas\"}]";
    String deliveries = "[{\"origin\":{\"id\":18232184,\"latitude\":45.740696,\"longitude\":4.8703103},\"destination\":{\"id\":21702437,\"latitude\":45.742043,\"longitude\":4.877001}}]";
    // tester parseDeliveryRequestFile
//    @Test
//    public void testParseDeliveryRequestFile() {
//        DeliveryRequestService deliveryRequestService = mock(DeliveryRequestService.class);
//        DeliveryRequestController deliveryRequestController = new DeliveryRequestController(deliveryRequestService);
//        MultipartFile file = mock(MultipartFile.class);
//        when(deliveryRequestService.parseAndGetBestRoutePerCourier(file, couriers, deliveries)).thenReturn(new ArrayList<>());
//        List<Tour> tours = deliveryRequestController.parseDeliveryRequestFile(file, couriers, deliveries);
//        assertEquals(0, tours.size());
//    }
}
