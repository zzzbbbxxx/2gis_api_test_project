package api_tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.HashSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestWithParamCountryCode extends ReqBase {



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test7")
        public Object[][] parameterTestProvider_test7() {
            return new Object[][] {
                    {"?country_code"},
                    {"?country_code="},
                    {"?country_code=us"}
                    };
        }


        @Test(dataProvider = "Data-Provider-Function_test7",
        description = "country_code = 0/empty symbols and ...not in set [ru, kg, kz, cz]" )
        public void test7(String q)  {

            JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,q);

            HelperReq.validateSchema(ERROR_SCHEMA_COUNTRY_CODE, jsonObject);

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

        @Test(groups = {"smoke"}, dataProvider = "Data-Provider-Function_test8",
        description = "code for regions in response must be equal code in param")
        public void test8(String q1, String q2) {

            JSONObject jsonResponse = HelperReq.sendRequestGetJSON(PATH,q1+q2);

            JSONArray tmpObj = HelperReq.getJsonArray(jsonResponse);

            HashSet<String> codeList = new HashSet<String>();

            tmpObj.forEach(item -> {
                codeList.add(HelperReq.getCountryCodeValue((JSONObject)item));
            });

            HashSet<String> region = new HashSet<>();
            region.add(q2);
            assertEquals(codeList,region);

        }

}