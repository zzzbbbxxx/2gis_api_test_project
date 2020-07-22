import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class test_class_page_size {

@Test
public void shouldReturnStatusOkay() {
        HttpResponse<JsonNode> jsonResponse
        = Unirest.get("http://www.mocky.io/v2/5a9ce37b3100004f00ab5154")
        .header("accept", "application/json").queryString("apiKey", "123")
        .asJson();

        assertNotNull(jsonResponse.getBody());
        assertEquals(200, jsonResponse.getStatus());
        }



        @Test
        public void shouldReturnStatusOkay_() {
                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get("https://regions-test.2gis.com/1.0/regions")
                        .header("accept", "application/json")
                        .asJson();

                assertNotNull(jsonResponse.getBody());
                assertEquals(200, jsonResponse.getStatus());
                System.out.println(jsonResponse.getBody());
        }

}