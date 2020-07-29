import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

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





        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test9")
        public Object[][] parameterTestProvider_test9() {
                return new Object[][] {
                        {"?page_size=5&","page=1"},
                        {"?page_size=5&","page=2"},
                        {"?page_size=5&","page=3"},
                        {"?page_size=5&","page=4"},
                        {"?page_size=5&","page=5"},
                        {"?page_size=5&","page=6"},

                };
        }

        // country_code by default must be show all regions for all countries
        // я мог написать тест двумя способами:
        // исходя из документации - проверяя, что все страны из списка
        // ... есть в ответе
        // или исходя из знания, что в списке отсутствует "ua"
        // в первом случае будет ошибка, потому что я ищу только страны из списка и "падаю",
        // ...если появляется то, чего я не жду

        @Test(dataProvider = "Data-Provider-Function_test9")
        public void test9(String q1, String q2) {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        (path,q1+q2);

                if (!checkStatus(200,jsonResponse.getStatus())) {

                } else {

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());
                        JSONArray tmpObj = jsonObject.getJSONArray("items");
                        JSONObject mJsonObject = new JSONObject();

                        for (int i = 0; i < tmpObj.length() ; i++) {

                                mJsonObject = (JSONObject)tmpObj.get(i);
                                String code = mJsonObject.getJSONObject("country").get("code").toString();
                                List<String> list = Arrays.asList("ru", "kg", "kz", "cz");
                                Assert.assertTrue(arrayContains(code, list),
                                        "Code for countries by default de must be in {ru,kg, kz,cz} \n"
                                                +"Expected code response: " + code + "\n"
                                                +"Actual Response: "+ (JSONObject)tmpObj.get(i));
                        }
                }
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