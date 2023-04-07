package tqs.hw1.envmonitor.boundary;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.envmonitor.cache.EnvCache;

import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(CacheRestController.class)
public class CacheRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void givenEnvCache_whenGetStats_thenStatus200() {
        EnvCache cache = EnvCache.getInstance();
        Integer ttl = cache.getTtl().intValue();
        Integer capacity = cache.getCapacity();
        Integer nRequests = cache.getNRequests();
        Integer nHits = cache.getNHits();
        Integer nMisses = cache.getNMisses();
        Integer nItems = cache.getNItems();

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/cache/stats")
            .then()
                .statusCode(200)
                .body("ttl", is(ttl))
                .body("capacity", is(capacity))
                .body("nrequests", is(nRequests))
                .body("nhits", is(nHits))
                .body("nmisses", is(nMisses))
                .body("nitems", is(nItems));
    }
}
