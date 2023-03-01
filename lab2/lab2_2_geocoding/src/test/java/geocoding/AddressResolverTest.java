package geocoding;

import connection.ISimpleHttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    private ISimpleHttpClient httpClient;
    @InjectMocks
    private AddressResolver resolver;
    public static final String MAPQUESTAPI_GEOCODING = "https://www.mapquestapi.com/geocoding/v1/reverse";
    private static final String API_KEY = ConfigUtils.getPropertyFromConfig("mapquest_key");

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaesAddress() throws ParseException, IOException, URISyntaxException {

        double latitude = 40.633116;
        double longitude = -8.658784;

        // Create the URI
        URIBuilder uriBuilder = new URIBuilder(MAPQUESTAPI_GEOCODING);
        uriBuilder.addParameter("key", API_KEY);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.6f,%.6f", latitude, longitude).toString());
        String uri = uriBuilder.build().toString();

        // Load the mock with expectations
        when(httpClient.doHttpGet(uri)).thenReturn("{\n" +
                "    \"info\": {\n" +
                "        \"statuscode\": 0,\n" +
                "        \"copyright\": {\n" +
                "            \"text\": \"© 2022 MapQuest, Inc.\",\n" +
                "            \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "            \"imageAltText\": \"© 2022 MapQuest, Inc.\"\n" +
                "        },\n" +
                "        \"messages\": []\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"maxResults\": 1,\n" +
                "        \"ignoreLatLngInput\": false\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"providedLocation\": {\n" +
                "                \"latLng\": {\n" +
                "                    \"lat\": 40.633116,\n" +
                "                    \"lng\": -8.658784\n" +
                "                }\n" +
                "            },\n" +
                "            \"locations\": [\n" +
                "                {\n" +
                "                    \"street\": \"Avenida João Jacinto de Magalhães\",\n" +
                "                    \"adminArea6\": \"Aveiro\",\n" +
                "                    \"adminArea6Type\": \"Neighborhood\",\n" +
                "                    \"adminArea5\": \"Aveiro\",\n" +
                "                    \"adminArea5Type\": \"City\",\n" +
                "                    \"adminArea4\": \"Aveiro\",\n" +
                "                    \"adminArea4Type\": \"County\",\n" +
                "                    \"adminArea3\": \"\",\n" +
                "                    \"adminArea3Type\": \"State\",\n" +
                "                    \"adminArea1\": \"PT\",\n" +
                "                    \"adminArea1Type\": \"Country\",\n" +
                "                    \"postalCode\": \"3810-149\",\n" +
                "                    \"geocodeQualityCode\": \"B1AAA\",\n" +
                "                    \"geocodeQuality\": \"STREET\",\n" +
                "                    \"dragPoint\": false,\n" +
                "                    \"sideOfStreet\": \"L\",\n" +
                "                    \"linkId\": \"0\",\n" +
                "                    \"unknownInput\": \"\",\n" +
                "                    \"type\": \"s\",\n" +
                "                    \"latLng\": {\n" +
                "                        \"lat\": 40.63312,\n" +
                "                        \"lng\": -8.65873\n" +
                "                    },\n" +
                "                    \"displayLatLng\": {\n" +
                "                        \"lat\": 40.63312,\n" +
                "                        \"lng\": -8.65873\n" +
                "                    },\n" +
                "                    \"mapUrl\": \"\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        Optional<Address> result = resolver.findAddressForLocation(latitude, longitude);
        Address expected = new Address("Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        double latitude = -361;
        double longitude = -361;

        // Create the URI
        URIBuilder uriBuilder = new URIBuilder(MAPQUESTAPI_GEOCODING);
        uriBuilder.addParameter("key", API_KEY);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.6f,%.6f", latitude, longitude).toString());
        String uri = uriBuilder.build().toString();

        // Load the mock with expectations
        when(httpClient.doHttpGet(uri)).thenReturn("{\n" +
                "    \"info\": {\n" +
                "        \"statuscode\": 400,\n" +
                "        \"copyright\": {\n" +
                "            \"text\": \"© 2022 MapQuest, Inc.\",\n" +
                "            \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "            \"imageAltText\": \"© 2022 MapQuest, Inc.\"\n" +
                "        },\n" +
                "        \"messages\": [\n" +
                "            \"Illegal argument from request: Invalid LatLng specified.\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"maxResults\": 1,\n" +
                "        \"ignoreLatLngInput\": false\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"providedLocation\": {},\n" +
                "            \"locations\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        Optional<Address> result = resolver.findAddressForLocation(latitude, longitude);

        assertFalse(result.isPresent());
    }
}