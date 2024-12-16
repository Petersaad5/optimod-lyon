import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.model.Map;
import com.optimodlyon.optimodlyon.utils.Parser;
import com.optimodlyon.optimodlyon.utils.TSP;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TSPTest {

    @Test
    public void testDijkstra() {
        Data data = new Data();
        // Pasing plan
        String mapFilePath = "src/public/xml/moyenPlan.xml";
        Parser.parsePlan(new File(mapFilePath), data);

        assertEquals(1448, data.getIntersections().size()); // Example assertion
        assertEquals(3097, data.getRoads().size()); // Example assertion

//        // start : <noeud id="26121686" latitude="45.74841" longitude="4.859172"/>
//        // end : <noeud id="191134392" latitude="45.754635" longitude="4.9022007"/>
//        Intersection start = new Intersection(26121686L, 45.74841, 4.859172);
//        Intersection end = new Intersection(191134392L, 45.754635, 4.9022007);

        // start : <noeud id="25610684" latitude="45.741924" longitude="4.89364"/>
        // end : <noeud id="21717915" latitude="45.739056" longitude="4.8859167"/>
        Intersection start = new Intersection(25610684L, 45.741924, 4.89364);
        Intersection end = new Intersection(21717915L, 45.739056, 4.8859167);

//        // An isolated intersection
//        // start : <noeud id="26086307" latitude="45.751766" longitude="4.8568363"/>
//        // end : <noeud id="191134392" latitude="45.754635" longitude="4.9022007"/>
//        Intersection start = new Intersection(26086307L, 45.751766, 4.8568363);
//        Intersection end = new Intersection(191134392L, 45.754635, 4.9022007);


        Map route = TSP.dijkstra(data.getMap(), start, end);

        assertEquals(route.getIntersections().size(), route.getRoads().size() + 1);

        // print the path
        for (int i = 0; i < route.getIntersections().size() - 1; i++) {
            Road intersectionRoad = route.getRoadByIntersections(route.getIntersections().get(i), route.getIntersections().get(i+1));
            assertNotNull(intersectionRoad);
        }

        List<Intersection> pathList = route.getIntersections();
        Set<Intersection> pathSet = new HashSet<>(pathList);

        assertEquals(pathSet.size(), pathList.size());

        assertEquals(end, pathList.get(pathList.size()-1));
        assertEquals(start, pathList.get(0));


    }

    @Test
    public void testGeneratePermutations() {
        Data data = new Data();
        // Pasing plan
        String mapFilePath = "src/public/xml/moyenPlan.xml";
        Parser.parsePlan(new File(mapFilePath), data);

        assertEquals(1448, data.getIntersections().size()); // Example assertion
        assertEquals(3097, data.getRoads().size()); // Example assertion

        /*____________________________________________________________________________*/
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

        Set<Intersection> setIntersections = new HashSet<>();
        setIntersections.add(tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
        for(Delivery delivery1: tours.get(0).getDeliveryRequest().getDeliveries()) {
            setIntersections.add(delivery1.getOrigin());
            setIntersections.add(delivery1.getDestination());
        }

        List<List<Intersection>> permutations = TSP.generatePermutations(tours.get(0).getDeliveryRequest());

        for(List<Intersection> permutation: permutations) {
            assertEquals(14, permutation.size());
            Set<Intersection> setPermutation = new HashSet<>(permutation);

            assertEquals(setIntersections, setPermutation);

            int warehouseCount = Collections.frequency(permutation, tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
            assertEquals(2, warehouseCount);

            // Make sure that the warehouse is at the beginning and at the end of the permutation
            assertEquals(tours.get(0).getDeliveryRequest().getWarehouse().getAddress(), permutation.get(0));
            assertEquals(tours.get(0).getDeliveryRequest().getWarehouse().getAddress(), permutation.get(permutation.size()-1));

            for(Delivery delivery1: tours.get(0).getDeliveryRequest().getDeliveries()) {
                int originIndex = permutation.indexOf(delivery1.getOrigin());
                int destinationIndex = permutation.indexOf(delivery1.getDestination());
                assertTrue("Delivery before pickup !!", destinationIndex > originIndex);
            }
        }
        assertTrue("Permutations aren't good", permutations.size() != 1);

    }

    @Test
    public void testGeneratePertinentPermutation() {
        Data data = new Data();
        // Pasing plan
        String mapFilePath = "src/public/xml/moyenPlan.xml";
        Parser.parsePlan(new File(mapFilePath), data);

        assertEquals(1448, data.getIntersections().size()); // Example assertion
        assertEquals(3097, data.getRoads().size()); // Example assertion

        /*____________________________________________________________________________*/

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

        Set<Intersection> setIntersections = new HashSet<>();
        setIntersections.add(tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
        for(Delivery delivery1: tours.get(0).getDeliveryRequest().getDeliveries()) {
            setIntersections.add(delivery1.getOrigin());
            setIntersections.add(delivery1.getDestination());
        }

        List<List<Intersection>> permutations = TSP.generatePertinentPermutation(tours.get(0).getDeliveryRequest());

        for(List<Intersection> permutation: permutations) {
            assertEquals(14, permutation.size());
            Set<Intersection> setPermutation = new HashSet<>(permutation);

            assertEquals(setIntersections, setPermutation);

            int warehouseCount = Collections.frequency(permutation, tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
            assertEquals(2, warehouseCount);

            // Make sure that the warehouse is at the beginning and at the end of the permutation
            assertEquals(tours.get(0).getDeliveryRequest().getWarehouse().getAddress(), permutation.get(0));
            assertEquals(tours.get(0).getDeliveryRequest().getWarehouse().getAddress(), permutation.get(permutation.size()-1));

            for(Delivery delivery1: tours.get(0).getDeliveryRequest().getDeliveries()) {
                int originIndex = permutation.indexOf(delivery1.getOrigin());
                int destinationIndex = permutation.indexOf(delivery1.getDestination());
                assertTrue("Delivery before pickup !!", destinationIndex > originIndex);
            }
        }
        assertEquals("Permutations not good", 1, permutations.size());

    }

    @Test
    public void testTSP() {
        Data data = new Data();
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

        List<Intersection> intersectionsTour = new ArrayList<Intersection>();
        intersectionsTour.add(tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
        for(Delivery delivery1: tours.get(0).getDeliveryRequest().getDeliveries()) {
            intersectionsTour.add(delivery1.getOrigin());
            intersectionsTour.add(delivery1.getDestination());
        }
        intersectionsTour.add(tours.get(0).getDeliveryRequest().getWarehouse().getAddress());

        List<Intersection> intersectionsRoute = route.getIntersections();

        //I want to make a copy of the list to avoid ConcurrentModificationException
        List<Intersection> intersectionsRouteAll = new ArrayList<>(intersectionsRoute);

        intersectionsRoute.removeIf(intersection -> !intersectionsTour.contains(intersection));

        //assertEquals(intersectionsRouteAll.size() , intersectionsRoute.size());

        // _____________________________________________________________________________________________________________________________________________
        assertEquals(2, Collections.frequency(intersectionsRoute, tours.get(0).getDeliveryRequest().getWarehouse().getAddress()));
        assertEquals(2, Collections.frequency(intersectionsTour, tours.get(0).getDeliveryRequest().getWarehouse().getAddress()));

        // Make sure that the warehouse is at the beginning and at the end of the permutation
        assertEquals(tours.get(0).getDeliveryRequest().getWarehouse().getAddress(), intersectionsRoute.get(0));
        assertEquals(tours.get(0).getDeliveryRequest().getWarehouse().getAddress(), intersectionsRoute.get(intersectionsRoute.size()-1));

        // _____________________________________________________________________________________________________________________________________________

        assertEquals(route.getIntersections().get(0), tours.get(0).getDeliveryRequest().getWarehouse().getAddress());
        assertEquals(route.getIntersections().get(route.getIntersections().size()-1), tours.get(0).getDeliveryRequest().getWarehouse().getAddress());

        assertEquals(intersectionsRouteAll.size(), route.getRoads().size() + 1);

        for(int i = 0; i < intersectionsRouteAll.size()-1; i++) {
            Road intersectionRoad = route.getRoadByIntersections(intersectionsRouteAll.get(i), intersectionsRouteAll.get(i+1));
            assertNotNull(intersectionRoad);
        }
    }

    //    @Test
//    public void testaStar() {
//        data = new Data();
//        // Pasing plan
//        String mapFilePath = "src/public/xml/moyenPlan.xml";
//        Parser.parsePlan(new File(mapFilePath), data);
//
//        assertEquals(1448, data.getIntersections().size()); // Example assertion
//        assertEquals(3097, data.getRoads().size()); // Example assertion
//
//        Intersection start = new Intersection(18232184L, 45.740696, 4.8703103);
//        Intersection end = new Intersection(21702437L, 45.742043, 4.877001);
//
//        Map route = TSP.aStar(data.getMap(), start, end);
//
//        List<Intersection> pathList = route.getIntersections();
//        Set<Intersection> pathSet = new HashSet<>(pathList);
//
//        assertEquals(pathSet.size(), pathList.size());
//
//        assertEquals(end, pathList.get(pathList.size()-1));
//        assertEquals(start, pathList.get(0));
//
//
//    }

}
