import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;

import static org.testng.Assert.*;

public class ReqBase {


        final public static String PATH = "https://regions-test.2gis.com/1.0/regions";

        final public static String BASE_SCHEMA = "base_schema.json";

        final public static String SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "q/json_example_for_q_param_success_search_for_3_symbols.json";
        final public static String EXAMPLE_FOR_Q_PARAM_WITH_ERROR_JSON = "q/json_example_for_q_param_with_error.json";

        final public static String Q_SCHEME_FOR_SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "q/param_q_scheme_for_success_search_for_3_symbols.json";
        final public static String Q_SCHEME_FOR_UNSUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "q/param_q_scheme_for_unsuccess_search_for_3_symbols.json";
        final public static String ERROR_SCHEMA_FOR_0_1_2_SYMBOLS_JSON = "q/param_q_error_schema_for_0_1_2_symbols.json";


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



        public boolean arrayContains__(HashSet<String> xset ) {

                for (String item : xset) {
                        if (!
                                (item.equals("ru") || item.equals("kg") || item.equals("kz")
                                || item.equals("cz"))
                        )
                        {
                                return false;
                        }
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