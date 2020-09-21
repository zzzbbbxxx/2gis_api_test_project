import kong.unirest.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReqWithParamPageSize extends ReqBase {



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test14")
        public Object[][] parameterTestProvider_test14() {
            return new Object[][] {
                    {"?page_size"},
                    {"?page_size="},
                    {"?page_size=1.5"},
                    {"?page_size=one"},
            };
        }

        // Tests for page_size = empty, 1.5, one
        // ...status code = 200
        // ...structure of json = errorscheme
        @Test(dataProvider = "Data-Provider-Function-test14")
        public void test14(String q)  {

                HttpResponse<String> jsonResponse = HelperReq.sendRequestGetResponseString
                        (PATH,q);

                        JSONObject jsonExpected = HelperReq.getJSONfromJSONFile("page_size\\json_example_for_page_size_param_with_error.json");

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                        HelperReq.validateSchema
                                ("page_size\\param_page_size_code_error_schema.json",
                                        jsonObject);

        }


         //This function will provide the patameter data
         @DataProvider(name = "Data-Provider-Function-test15")
         public Object[][] parameterTestProvider_test15() {
            return new Object[][] {
                    {"?page_size=0"},
                    {"?page_size=1"},
                    {"?page_size=-1"}

            };
            }


         @Test(dataProvider = "Data-Provider-Function-test15",
         description = "page_size = 0, 1, -1" )
         public void test15(String q)  {

            HttpResponse<String> jsonResponse = HelperReq.sendRequestGetResponseString
                (PATH,q);

            JSONObject jsonExpected = HelperReq.getJSONfromJSONFile
                    ("page_size\\json_example_for_page_size_param_with_error_2.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

            HelperReq.validateSchemaV2
                    ("page_size\\param_page_size_code_error_schema_2.json",
                  "page_size\\json_example_for_page_size_param_with_error_2.json",
                            jsonObject);

        }


        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test16")
        public Object[][] parameterTestProvider_test16() {
             return new Object[][] {
                {"?page_size=5",5},
                {"?page_size=10",10},
                {"?page_size=15",15}

             };
        }


        @Test(dataProvider = "Data-Provider-Function-test16",
        description = "items in JSON must be equal page_size")
        public void test16(String q, int i)  {

            HttpResponse<String> jsonResponse = HelperReq.sendRequestGetResponseString
                (PATH,q);

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());
            JSONArray tmpObj = jsonObject.getJSONArray("items");
            Assert.assertEquals(tmpObj.length(),i,"Count regions in response by default must be equel: "+i);

        }


}