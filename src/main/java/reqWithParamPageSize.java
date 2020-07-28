import kong.unirest.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class reqWithParamPageSize extends reqBase {



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test10")
        public Object[][] parameterTestProvider_test10() {
            return new Object[][] {
                    {"?page"},
                    {"?page="},
                    {"?page=0"},
                    {"?page=1.5"},
                    {"?page=one"}
                    };
        }

        // for page = empty, 0, 1.5, one
        // ...status code = 200
        // ...structure of json = errorscheme
        @Test(dataProvider = "Data-Provider-Function_test10")
        public void test10(String q)  {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        (path,q);

                if (!checkStatus(200,jsonResponse.getStatus())) {

                } else {
                        JSONObject jsonExpected = getJSONfromJSONFile("page\\json_example_for_page_param_with_error.json");

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                        boolean validation = validationSchema
                                ("page\\param_page_code_error_schema.json",
                                        jsonObject);
                        assertTrue(validation,"Response must be equal ErrorSchema,\n"
                        +"Response Expected: "+ jsonExpected+"\n"
                        +"Responce Actual: "+ jsonObject);
                }

        }



         //This function will provide the patameter data
         @DataProvider(name = "Data-Provider-Function-test11")
         public Object[][] parameterTestProvider_test11() {
            return new Object[][] {
                    {"?page=1", ""}
                    };
        }

        // ответ для page=1 == ответу для запроса без page

        @Test(dataProvider = "Data-Provider-Function-test11")
        public void test11(String q1, String q2) {

                HttpResponse<String> jsonResponse1 = sendRequestGetResponseString
                        (path, q1);

                HttpResponse<String> jsonResponse2 = sendRequestGetResponseString
                        (path, q2);

                JSONObject jsonExpected = getJSONfromJSONFile("/json_for_base_schema.json");

                 JSONObject jsonObject1 = new JSONObject(jsonResponse1.getBody());
                 JSONObject jsonObject2 = new JSONObject(jsonResponse2.getBody());

                boolean validation = validationSchema
                        ("/base_schema.json",
                                jsonObject1);

                if (validation)
                    assertEquals(jsonObject1.toString(), jsonObject2.toString(),
                            "Response for \"?page=1\" must be equal response without params,\n"
                        + "Response \"?page=1\": " + jsonObject1 + "\n"
                        + "Responce without param: " + jsonObject2);


        }



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test12")
        public Object[][] parameterTestProvider_test12() {
            return new Object[][] {
                {"?page=1"},
                {"?page=2"},
                {"?page=3"}
            };
        }


        //"Перелистывание" страниц
        @Test(dataProvider = "Data-Provider-Function-test12")
        public void test12(String q) {

            HttpResponse<String> jsonResponse = sendRequestGetResponseString
                    (path,q);

            if (!checkStatus(200,jsonResponse.getStatus())) {

            } else {

                JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                JSONArray tmpObj = jsonObject.getJSONArray("items");

                List<String> listOfRegions = null;

                JSONObject mJsonObject = new JSONObject();

                for (int i = 0; i < tmpObj.length() ; i++) {

                    mJsonObject = (JSONObject)tmpObj.get(i);

                    String name = mJsonObject.get("name").toString();
                    listOfRegions.add(name);

                    Assert.assertTrue(arrayContains(name, listOfRegions));
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