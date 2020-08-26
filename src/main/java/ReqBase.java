import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.util.List;

import static org.testng.Assert.*;

public class ReqBase {


        public static String path = "https://regions-test.2gis.com/1.0/regions";

        public static String baseSchema = "base_schema.json";

        public static String jsonExampleQSuccessSearch = "q/json_example_for_q_param_success_search_for_3_symbols.json";
        public static String jsonExampleQError = "q/json_example_for_q_param_with_error.json";

        public static String schemaExampleQSuccessSearch = "q/param_q_scheme_for_success_search_for_3_symbols.json";
        public static String schemaExampleQUnsuccessSearch = "q/param_q_scheme_for_unsuccess_search_for_3_symbols.json";
        public static String schemaExampleQError = "q/param_q_error_schema_for_0_1_2_symbols.json";


        public boolean validationSchema(String pathToSchema, org.json.JSONObject json) {

                org.json.JSONObject jsonSchema = new org.json.JSONObject(new JSONTokener(ReqWithParamQ.class.getResourceAsStream(pathToSchema)));
                Schema schema = SchemaLoader.load(jsonSchema);


                try {
                        schema.validate(json);
                        return true;
                } catch (ValidationException e) {
                        return false;
                }

        }


        public org.json.JSONObject sendRequestGetJSON(String url, String params) {

                 HttpResponse<JsonNode> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asJson();

                return new org.json.JSONObject(jsonResponse.getBody().toString());
        }



        public HttpResponse<JsonNode> sendRequestGetResponse (String url, String params) {

                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asJson();

                return jsonResponse;
        }



        public HttpResponse<String> sendRequestGetResponseString (String url, String params) {

                HttpResponse<String> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asString();

                assertEquals(200, jsonResponse.getStatus(),
                        "expected Status Code [200]" +
                                " but found Status Code [" + String.valueOf(jsonResponse.getStatus())+"]");


                return jsonResponse;
        }



        public JSONObject getJSONfromJSONFile (String path) {

                JSONObject json =
                        new JSONObject(new JSONTokener(ReqWithParamQ.class.getResourceAsStream
                                (path)));

                return json;
        }



        public boolean checkStatus (int statusE, int statusR) {

                try {
                        assertEquals(statusE, statusR);

                } catch ( AssertionError  e ) {
                        Assert.fail("expected Status Code [200]" +
                                    " but found Status Code [" + String.valueOf(statusR)+"]");
                        return false;
                }

                return true;

        }


        public boolean arrayContains(String value, List<String> arr ) {
                for (String item : arr) {
                        if (item.equals(value)) {
                                return true;
                        }
                }
                return false;
        }


}