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

 public  class HelperReq {


        public static boolean validationSchema(String pathToSchema, org.json.JSONObject json) {

                org.json.JSONObject jsonSchema = new org.json.JSONObject(new JSONTokener(ReqWithParamQ.class.getResourceAsStream(pathToSchema)));
                Schema schema = SchemaLoader.load(jsonSchema);


                try {
                        schema.validate(json);
                        return true;
                } catch (ValidationException e) {
                        return false;
                }

        }


        public static org.json.JSONObject sendRequestGetJSON(String url, String params) {

                 HttpResponse<JsonNode> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asJson();

                return new org.json.JSONObject(jsonResponse.getBody().toString());
        }



        public static HttpResponse<JsonNode> sendRequestGetResponse (String url, String params) {

                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asJson();

                return jsonResponse;
        }



        public static HttpResponse<String> sendRequestGetResponseString (String url, String params) {

                HttpResponse<String> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asString();

                assertEquals(200, jsonResponse.getStatus(),
                        "expected Status Code [200]" +
                                " but found Status Code [" + String.valueOf(jsonResponse.getStatus())+"]");


                return jsonResponse;
        }



        public static JSONObject getJSONfromJSONFile (String path) {

                JSONObject json =
                        new JSONObject(new JSONTokener(ReqWithParamQ.class.getResourceAsStream
                                (path)));

                return json;
        }



        public static boolean arrayContains__(HashSet<String> xset ) {

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

        public static boolean arrayContains(String value, List<String> arr ) {
                for (String item : arr) {
                        if (item.equals(value)) {
                                return true;
                        }
                }
                return false;
        }


}