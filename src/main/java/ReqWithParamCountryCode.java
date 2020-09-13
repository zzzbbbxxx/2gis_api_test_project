import kong.unirest.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.HashSet;

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


        @Test(dataProvider = "Data-Provider-Function_test7",
        description = "country_code = 0/empty symbols and ...not in set [ru, kg, kz, cz]" )
        public void test7(String q)  {
            HttpResponse<String> jsonResponse = HelperReq.sendRequestGetResponseString
                        (PATH,q);

            JSONObject jsonExpected = HelperReq.getJSONfromJSONFile("countrycode/json_example_for_country_code_param_with_error.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

            HelperReq.validateSchema("countrycode/param_country_code_error_schema.json",
                            jsonObject);

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

        @Test(dataProvider = "Data-Provider-Function_test8",
        description = "code for regions in response must be equal code in param")
        public void test8(String q1, String q2) {

            HttpResponse<String> jsonResponse = HelperReq.sendRequestGetResponseString
                (PATH,q1+q2);

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());
            JSONArray tmpObj = jsonObject.getJSONArray("items");
            JSONObject mJsonObject = new JSONObject();

            String code;
            HashSet<String> codeList = new HashSet<String>();

            for (int i = 0; i < tmpObj.length() ; i++) {

                mJsonObject = (JSONObject)tmpObj.get(i);
                code = mJsonObject.getJSONObject("country").get("code").toString();
                codeList.add(code);

            }
            HashSet<String> xset = new HashSet<String>();
            xset.add(q2);
            assertEquals(codeList,xset);

        }

}