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
        @DataProvider(name = "Data-Provider-Function_test13")
        public Object[][] parameterTestProvider_test13() {
            return new Object[][] {
                    {"?page_size"},
                    {"?page_size="},
                    {"?page_size=1.5"},
                    {"?page_size=one"}
                    };
        }

        // for page_size = empty, 0, 1.5, one
        // ...status code = 200
        // ...structure of json = errorscheme
        @Test(dataProvider = "Data-Provider-Function_test13")
        public void test13(String q)  {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        (path,q);

                if (!checkStatus(200,jsonResponse.getStatus())) {

                } else {
                        JSONObject jsonExpected = getJSONfromJSONFile("page_size\\json_example_for_page_size_param_with_error.json");

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                        boolean validation = validationSchema
                                ("page_size\\param_page_size_code_error_schema.json",
                                        jsonObject);
                        assertTrue(validation,"Response must be equal ErrorSchema,\n"
                        +"Response Expected: "+ jsonExpected+"\n"
                        +"Responce Actual: "+ jsonObject);
                }

        }


         //This function will provide the patameter data
         @DataProvider(name = "Data-Provider-Function_test14")
         public Object[][] parameterTestProvider_test14() {
            return new Object[][] {
                    {"?page_size=0"},
                    {"?page_size=1"},
                    {"?page_size=one"},
                    {"?page_size=-1"},
            };
            }

        // for page_size = empty, 0, 1.5, one
        // ...status code = 200
        // ...structure of json = errorscheme
        @Test(dataProvider = "Data-Provider-Function_test14")
        public void test14(String q)  {
            HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path,q);
            if (!checkStatus(200,jsonResponse.getStatus())) {
                } else {
            JSONObject jsonExpected = getJSONfromJSONFile("page_size\\json_example_for_page_size_param_with_error.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

            boolean validation = validationSchema
                    ("page_size\\param_page_size_code_error_schema.json",
                            jsonObject);
            assertTrue(validation,"Response must be equal ErrorSchema,\n"
                    +"Response Expected: "+ jsonExpected+"\n"
                    +"Responce Actual: "+ jsonObject);
            }
        }

}