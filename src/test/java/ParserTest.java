import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ParserTest {
    private Data data;

    @Before
    public void setUp() {
        // Initialize any required resources before each test
        data = new Data();
    }

    @Test
    public void testParsePlan() {
        String filePath = "src/public/xml/petitPlan.xml";
        Parser.parsePlan(new File(filePath), data);

        List<Intersection> intersections = data.getIntersections();
        List<Road> roads = data.getRoads();

        assertNotNull(intersections);
        assertNotNull(roads);
        assertFalse(intersections.isEmpty());
        assertFalse(roads.isEmpty());

        // Add more assertions to verify the correctness of parsed data
        assertEquals(308, intersections.size()); // Example assertion
        assertEquals(616, roads.size()); // Example assertion
    }

    // test parseDemande
    @Test
    public void testParseDemande() {
        // Pasing plan
        String mapFilePath = "src/public/xml/moyenPlan.xml";
        Parser.parsePlan(new File(mapFilePath), data);

        assertEquals(1448, data.getIntersections().size()); // Example assertion
        assertEquals(3097, data.getRoads().size()); // Example assertion

        /*____________________________________________________________________________*/
        // Parsing demande
        String filePath = "src/public/xml/demandeMoyen5.xml";

        // [{"id":7,"name":"Parker Thomas"}] create list of couriers
        Courier courier = new Courier(7L, "Parker Thomas");
        List<Courier> couriers = List.of(courier);

        // [{"origin":{"id":18232184,"latitude":45.740696,"longitude":4.8703103},"destination":{"id":21702437,"latitude":45.742043,"longitude":4.877001}}] create deliveries
        Intersection origin = new Intersection(18232184L, 45.740696, 4.8703103);
        Intersection destination = new Intersection(21702437L, 45.742043, 4.877001);
        Delivery delivery = new Delivery();
        delivery.setOrigin(origin);
        delivery.setDestination(destination);
        List<Delivery> deliveries = List.of(delivery);

        List<Tour> tours = Parser.parseDemande(new File(filePath), couriers, deliveries);
        assertEquals(1, tours.size()); // Example assertion
        assertNotNull(tours.get(0).getDeliveryRequest());
        assertNotNull(tours.get(0).getDeliveryRequest().getCourier());
        assertEquals(6, tours.get(0).getDeliveryRequest().getDeliveries().size()); // Example assertion
        assertEquals(1, couriers.size());
        assertNull(tours.get(0).getRoute());

        for (Delivery value : deliveries) {
            // assert that the origin and destination are in the intersections list
            assertTrue("Intersection not found in the map", data.getIntersections().contains(value.getOrigin()));
            assertTrue("Intersection not found in the map", data.getIntersections().contains(value.getDestination()));

            assertTrue("Delivery not in tour", tours.get(0).getDeliveryRequest().getDeliveries().contains(value));
        }

        for(int i = 0; i < tours.get(0).getDeliveryRequest().getDeliveries().size(); i++) {
            // assert that the origin and destination are in the intersections list
            assertTrue("Intersection not found in the map", data.getIntersections().contains(tours.get(0).getDeliveryRequest().getDeliveries().get(i).getOrigin()));
            assertTrue("Intersection not found in the map", data.getIntersections().contains(tours.get(0).getDeliveryRequest().getDeliveries().get(i).getDestination()));
        }

        assertNotNull(deliveries);
        assertNotNull(couriers);
        assertFalse(deliveries.isEmpty());
        assertFalse(couriers.isEmpty());

    }
}
