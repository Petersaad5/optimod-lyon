import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.utils.Parser;
import com.optimodlyon.optimodlyon.utils.TSP;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TSPTest {
    private Data data;

    @Before
    public void setUp() {
        // Set up the test
        data = new Data();
    }

    @Test
    public void testTSP() {
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

        Map route = TSP.tsp(tours.get(0).getDeliveryRequest(), data.getMap());

//        assertEquals(route.getIntersections().size(), );

        assertEquals(route.getIntersections().get(0), tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
        assertEquals(route.getIntersections().get(route.getIntersections().size()-1), tours.get(0).getDeliveryRequest().getWarehouse().getAddress());

        System.out.println("Route intersections size: " + route.getIntersections().size());
        System.out.println("Route roads size: " + route.getRoads().size());

        for(int i = 0; i < route.getIntersections().size()-2; i++) {
            boolean found = false;
            for(Road road: route.getRoads()) {
                if(road.containsIntersection(route.getIntersections().get(i).getId()) && road.containsIntersection(route.getIntersections().get(i+1).getId())) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                System.out.println("Road not found between " + route.getIntersections().get(i).getId() + " and " + route.getIntersections().get(i + 1).getId());
            }
            assertTrue(found);
        }
    }
}
