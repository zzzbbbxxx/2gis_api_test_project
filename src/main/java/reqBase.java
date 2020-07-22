import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class reqBase {



        //
        public boolean validationSchema(String pathToSchema, HttpResponse<JsonNode> json) {

                org.json.JSONObject jsonSchema = new org.json.JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream(pathToSchema)));
                Schema schema = SchemaLoader.load(jsonSchema);

                org.json.JSONObject jsonObject = new org.json.JSONObject(json.getBody().toString());

                try {
                        schema.validate(jsonObject);
                        return true;
                } catch (ValidationException e) {
                        return false;
                }

        }


        //
        public boolean validationSchema(String pathToSchema, org.json.JSONObject json) {

                org.json.JSONObject jsonSchema = new org.json.JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream(pathToSchema)));
                Schema schema = SchemaLoader.load(jsonSchema);


                try {
                        schema.validate(json);
                        return true;
                } catch (ValidationException e) {
                        return false;
                }

        }

        //
        public org.json.JSONObject sendRequestGetJSON(String url, String params) {

                 HttpResponse<JsonNode> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asJson();

                org.json.JSONObject json = new org.json.JSONObject(jsonResponse.getBody().toString());

                return json;
        }

        //
        public HttpResponse<JsonNode> sendRequestGetResponse (String url, String params) {

                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asJson();

                return jsonResponse;
        }


        //
        public HttpResponse<String> sendRequestGetResponseString (String url, String params) {

                HttpResponse<String> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asString();

                return jsonResponse;
        }


        //
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





}