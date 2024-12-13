import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.model.RoadModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ParserTest {
    private Data data;

    @Before
    public void setUp() {
        // Initialize any required resources before each test
        data = new Data();
        Parser.intersectionMap.clear();
    }

    @Test
    public void testParsePlan() {
        String filePath = "src/public/xml/petitPlan.xml";
        Parser.parsePlan(new File(filePath), data);

        List<IntersectionModel> intersections = data.getIntersections();
        List<RoadModel> roads = data.getRoads();

        assertNotNull(intersections);
        assertNotNull(roads);
        assertFalse(intersections.isEmpty());
        assertFalse(roads.isEmpty());

        // Add more assertions to verify the correctness of parsed data
        assertEquals(308, intersections.size()); // Example assertion
        assertEquals(616, roads.size()); // Example assertion
    }
}
