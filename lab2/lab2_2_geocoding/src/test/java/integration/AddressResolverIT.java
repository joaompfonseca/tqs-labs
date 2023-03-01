package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import geocoding.ConfigUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressResolverIT {

    private ISimpleHttpClient httpClient;
    private AddressResolver resolver;
    public static final String MAPQUESTAPI_GEOCODING = "https://www.mapquestapi.com/geocoding/v1/reverse";
    private static final String API_KEY = ConfigUtils.getPropertyFromConfig("mapquest_key");

    @BeforeEach
    public void init(){
        httpClient = new TqsBasicHttpClient();
        resolver = new AddressResolver(httpClient);
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        double latitude = 40.633116;
        double longitude = -8.658784;

        Optional<Address> result = resolver.findAddressForLocation(latitude, longitude);
        Address expected = new Address("Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        double latitude = -361;
        double longitude = -361;

        Optional<Address> result = resolver.findAddressForLocation(latitude, longitude);

        assertFalse(result.isPresent());
    }
}