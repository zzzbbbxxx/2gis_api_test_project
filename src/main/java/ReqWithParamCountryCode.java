import kong.unirest.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReqWithParamCountryCode extends ReqBase {



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test7")
        public Object[][] parameterTestProvider_test7() {
            return new Object[][] {
                    {"?country_code"},
                    {"?country_code="},
                    {"?country_code=us"}
                    };
        }

        // Tests for country_code = 0/empty symbols
        // and ...not in set [ru, kg, kz, cz]
        @Test(dataProvider = "Data-Provider-Function_test7")
        public void test7(String q)  {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        (path,q);

                if (!checkStatus(200,jsonResponse.getStatus())) {

                } else {
                        JSONObject jsonExpected = getJSONfromJSONFile("countrycode/json_example_for_country_code_param_with_error.json");

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                        boolean validation = validationSchema
                                ("countrycode/param_country_code_error_schema.json",
                                        jsonObject);

                        assertTrue(validation,"Response must be equal ErrorSchema,\n"
                        +"Response Expected: "+ jsonExpected+"\n"
                        +"Responce Actual: "+ jsonObject);
                }

        }



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test8")
        public Object[][] parameterTestProvider_test8() {
            return new Object[][] {
                {"?country_code=","ru"},
                {"?country_code=","kg"},
                {"?country_code=","kz"},
                {"?country_code=","cz"},
                {"?country_code=","ua"}
                };
            }


        // Tests for country_code =  [ru, kg, kz, cz]... and "ua" too
        @Test(dataProvider = "Data-Provider-Function_test8")
        public void test8(String q1, String q2) {

            HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path,q1+q2);

            if (!checkStatus(200,jsonResponse.getStatus())) {
                } else
                    {

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());
                        JSONArray tmpObj = jsonObject.getJSONArray("items");
                        JSONObject mJsonObject = new JSONObject();

                        for (int i = 0; i < tmpObj.length() ; i++) {

                            mJsonObject = (JSONObject)tmpObj.get(i);
                            String code = mJsonObject.getJSONObject("country").get("code").toString();

                            assertEquals(q2,code,"Code country for all regions in response \n" +
                                    "must be equal code that we use in request,\n"
                                    +"Expected code response: " + q2 + "\n"
                                    +"Actual code in response: " + code + "\n"
                                    +"Actual Response: "+ (JSONObject)tmpObj.get(i));
            }

        }

    }

}