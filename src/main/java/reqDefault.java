import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class reqDefault extends reqBase {



        // status code = 200
        @Test
        public void test1() {

                HttpResponse<JsonNode> jsonResponse = sendRequestGetResponse
                        (path,"");


                assertEquals(200, jsonResponse.getStatus());

        }


        // structure of json are correct ( contains all keys,params )
        @Test
        public void test2() {

                org.json.JSONObject jsonResponse = sendRequestGetJSON
                        (path,"");


                boolean validation = validationSchema(baseSchema,jsonResponse);

                assertTrue(validation);
        }


        // value for key total = 22
        @Test
        public void test3() {

                org.json.JSONObject jsonResponse = sendRequestGetJSON
                        (path,"");

                int total = jsonResponse.getInt("total");

                assertEquals(22, total);

        }



}