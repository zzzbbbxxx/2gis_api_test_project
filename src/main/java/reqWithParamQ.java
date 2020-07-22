import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class reqWithParamQ {


        public boolean validationSchema(JSONObject jsonSchema, JSONObject json) {

                Schema schema = SchemaLoader.load(jsonSchema);

                try {
                        schema.validate(json);
                        return true;
                } catch (ValidationException e) {
                        return false;
                }

        }

        @Test
        public void shouldReturnStatusOkay_() {



                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get("https://regions-test.2gis.com/1.0/regions")
                        .header("accept", "application/json")
                        .asJson();

                HttpResponse<JsonNode> jsonResponse1 = Unirest.get("https://regions-test.2gis.com/1.0/regions")
                        .queryString("q", "нов")
                        .asJson();

                // Results in "http://httpbin.org?fruit=apple&droid=R2D2"

                assertNotNull(jsonResponse.getBody());
                assertEquals(200, jsonResponse.getStatus());
                System.out.println(jsonResponse.getBody());
                System.out.println(jsonResponse1.getBody());

                String responseJSONString = jsonResponse.getBody().toString();

        }


        @Test
        public void givenInvalidInput_whenValidating_thenInvalid() {

                JSONObject jsonSchema = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/test_schema.json")));
                JSONObject jsonSubject = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/product_invalid.json")));

                Schema schema = SchemaLoader.load(jsonSchema);
                schema.validate(jsonSubject);
        }

        @Test
        public void givenValidInput_whenValidating_thenValid() {

                JSONObject jsonSchema = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/test_schema.json")));
                JSONObject jsonSubject = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/product_valid.json")));

                Schema schema = SchemaLoader.load(jsonSchema);
                schema.validate(jsonSubject);
        }

        @Test
        public void givenValidInput_whenValidating_thenValid11() {


                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get("https://regions-test.2gis.com/1.0/regions?q=нов")
                        .header("accept", "application/json")
                        .asJson();


                JSONObject jsonSchema = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/base_schema.json")));
                JSONObject jsonSubject = new JSONObject(jsonResponse.getBody().toString());

                System.out.println(jsonResponse);
                System.out.println(jsonResponse.getBody().toString());

                Schema schema = SchemaLoader.load(jsonSchema);

                schema.validate(jsonSubject);
        }

}