import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.model.RoadModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Before
    public void setUp() {
        // Initialize any required resources before each test
        Parser.data = new Data();
        Parser.intersectionMap.clear();
    }

    @Test
    public void testParsePlan() {
        File file = new File("src/public/xml/petitPlan.xml");
        Parser.parsePlan(file);

        List<IntersectionModel> intersections = Parser.data.getIntersections();
        List<RoadModel> roads = Parser.data.getRoads();

        assertNotNull(intersections);
        assertNotNull(roads);
        assertFalse(intersections.isEmpty());
        assertFalse(roads.isEmpty());

        // Add more assertions to verify the correctness of parsed data
        assertEquals(308, intersections.size()); // Example assertion
        assertEquals(616, roads.size()); // Example assertion
    }

    @Test
    public void testParseDemande() {
        File file = new File("src/public/xml/demandePetit2.xml");
        Parser.parseDemande(file);

        Data data = Parser.data;
        assertNotNull(data.getDeliveryRequest());




        // Verify the deliveries
        assertEquals(2, data.getDeliveryRequest().getDeliveries().size());

    }
}