import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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



        // page_size default contains must be contains 15 regions
        @Test
        public void test13() {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString(path,"");

                if (!checkStatus(200, jsonResponse.getStatus())) {

                } else {

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                        Assert.assertEquals(jsonObject.getJSONArray("items").length(),
                                15,
                                "Count of regions in response by default must be eque: 15 \n");


                }
        }
        


        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test11")
        public Object[][] parameterTestProvider_test11() {
                return new Object[][]{
                        {"?page=1", ""}
                };
        }

        // page by default must be equel page=1
        @Test(dataProvider = "Data-Provider-Function-test11")
        public void test11(String q1, String q2) {

                HttpResponse<String> jsonResponse1 = sendRequestGetResponseString
                        (path, q1);

                HttpResponse<String> jsonResponse2 = sendRequestGetResponseString
                        (path, q2);

                JSONObject jsonObject1 = new JSONObject(jsonResponse1.getBody());
                JSONObject jsonObject2 = new JSONObject(jsonResponse2.getBody());

                boolean validation = validationSchema
                        ("/base_schema.json",
                                jsonObject1);

                if (validation)
                        assertEquals(jsonObject1.toString(), jsonObject2.toString(),
                                "Page by default must be equel for response for \"page=1\"\n"
                                        + "Response for \"page=1\": " + jsonObject1 + "\n"
                                        + "Default: " + jsonObject2);


        }




}