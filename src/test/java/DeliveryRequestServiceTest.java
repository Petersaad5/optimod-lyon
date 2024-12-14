import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.service.DataService;
import com.optimodlyon.optimodlyon.service.DeliveryRequestService;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class DeliveryRequestServiceTest {

    private DeliveryRequestService deliveryRequestService;
    private DataService dataService;

    @Before
    public void setUp() {
        dataService = new DataService();
        deliveryRequestService = new DeliveryRequestService(dataService);
    }

    @Test
    public void testParseAndGetBestRoutePerCourier() throws IOException {
        // Pasing plan
        String mapFilePath = "src/public/xml/moyenPlan.xml";
        Parser.parsePlan(new File(mapFilePath), dataService.getData());

        assertEquals(1448, dataService.getData().getIntersections().size()); // Example assertion
        assertEquals(3097, dataService.getData().getRoads().size()); // Example assertion

        /*________________________________________________________________________*/

        File file = new File("src/public/xml/moyenPlan.xml");


        // Add test code here
        Courier courier = new Courier(7L, "Parker Thomas");
        List<Courier> couriers = List.of(courier);

        // [{"origin":{"id":18232184,"latitude":45.740696,"longitude":4.8703103},"destination":{"id":21702437,"latitude":45.742043,"longitude":4.877001}}] create deliveries
        Intersection origin = new Intersection(18232184L, 45.740696, 4.8703103);
        Intersection destination = new Intersection(21702437L, 45.742043, 4.877001);
        Delivery delivery = new Delivery();
        delivery.setOrigin(origin);
        delivery.setDestination(destination);
        List<Delivery> deliveries = List.of(delivery);

        assertNotNull(file);
        List<Tour> tours = deliveryRequestService.parseAndGetBestRoutePerCourier(file, couriers, deliveries);
        assertNotNull(tours.get(0).getRoute());

    }
}
