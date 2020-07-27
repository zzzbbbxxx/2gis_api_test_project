import kong.unirest.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class reqWithParamCountryCode extends reqBase {



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test7")
        public Object[][] parameterTestProvider_test7() {
            return new Object[][] {
                    {"country_code"}, {"country_code="}, {"country_code=us"}
                    };
        }

        // country_code = 0 symbols/empty, symbols not in {ru, kg, kz, cz}
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
                {"?country_code=","cz"}
        };
    }

    // country_code =  {ru, kg, kz, cz}
    @Test(dataProvider = "Data-Provider-Function_test8")
    public void test8(String q1, String q2) throws NoSuchFieldException, IllegalAccessException {

        HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path,q1+q2);

        if (!checkStatus(200,jsonResponse.getStatus())) {

        } else {
            JSONObject jsonExpected = getJSONfromJSONFile("countrycode/json_example_for_country_code_param_with_error.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());


            JSONArray tmpObj = jsonObject.getJSONArray("items");


            JSONObject mJsonObject = new JSONObject();

            for (int i = 0; i < tmpObj.length() ; i++) {

                mJsonObject = (JSONObject)tmpObj.get(i);
              //  System.out.println(mJsonObject);
                mJsonObject = mJsonObject.getJSONObject("country");
              //  System.out.println(mJsonObject);
                String code = mJsonObject.get("code").toString();

                assertEquals(q2,code,"Code Country in response must be equal Code in request,\n"
                        +"Expected code response: " + q2 + "\n"
                        +"Actual Response: "+ (JSONObject)tmpObj.get(i));
            }


        }

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

    // country_code =  default
    @Test(dataProvider = "Data-Provider-Function_test9")
    public void test9(String q1, String q2) throws NoSuchFieldException, IllegalAccessException {

        HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path,q1+q2);

        if (!checkStatus(200,jsonResponse.getStatus())) {

        } else {

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());


            JSONArray tmpObj = jsonObject.getJSONArray("items");


            JSONObject mJsonObject = new JSONObject();

            for (int i = 0; i < tmpObj.length() ; i++) {

                mJsonObject = (JSONObject)tmpObj.get(i);
                //  System.out.println(mJsonObject);
                mJsonObject = mJsonObject.getJSONObject("country");
                //  System.out.println(mJsonObject);
                String code = mJsonObject.get("code").toString();

                List<String> list = Arrays.asList("ru", "kg", "kz", "cz");
                Assert.assertTrue(arrayContains(code, list),
                        "Code Country in response must be equal Code in {ru,kg, kz,cz}\n"
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