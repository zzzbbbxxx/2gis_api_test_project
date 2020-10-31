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


        @Test(dataProvider = "Data-Provider-Function-test14")
        public void test14(String q)  {

            JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,q);

            HelperReq.validateSchema(ERROR_SCHEMA_FOR_INTEGER_SYMBOLS_JSON, jsonObject);

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
                    (ERROR_SCHEMA_FOR_NON5_10_15_JSON,
                            EXAMPLE_PAGE_ERROR_V2,
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

            JSONObject jsonResponse = HelperReq.sendRequestGetJSON(PATH,"");

            int size = HelperReq.getCountOfRegions(jsonResponse);

            Assert.assertEquals(size, i,
                    "Count regions in response by default must be equel: " + i);

        }


}