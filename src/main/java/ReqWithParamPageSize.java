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

            JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,q);

            HelperReq.validateSchema("page_size\\error_schema_v1.json", jsonObject);

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

            JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,q);

            HelperReq.validateSchemaV2
                    ("page_size\\error_schema_v2.json",
                  "page_size\\json_error_example_v2.json",
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

            int size = HelperReq.getCountOfRegions(PATH,q);

            Assert.assertEquals(size, i,
                    "Count regions in response by default must be equel: " + i);

        }


}