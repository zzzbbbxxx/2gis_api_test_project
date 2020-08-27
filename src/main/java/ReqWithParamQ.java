import kong.unirest.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ReqWithParamQ extends ReqBase {



        @DataProvider(name = "Data-Provider-Function_test1")
        public Object[][] parameterTestProvider_test1() {
            return new Object[][] {
                    {"?q=но"},
                    {"?q=н"},
                    {"?q="},
                    {"?q"}
                    };
        }

        @Test(description = "search with error response for q = 0,1,2 symbols",
              dataProvider = "Data-Provider-Function_test1")

        public void test1(String q)  {

                HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString(PATH,q);

                JSONObject jsonExpected = helperReq.getJSONfromJSONFile(EXAMPLE_FOR_Q_PARAM_WITH_ERROR_JSON);

                org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody());

                boolean validation = helperReq.validationSchema
                        (ERROR_SCHEMA_FOR_0_1_2_SYMBOLS_JSON, jsonObject);

                assertTrue(validation,"Response must match the ErrorSchema,\n"
                        +"Response Expected: "+ jsonExpected+"\n"
                        +"Responce Actual: "+ jsonObject);

        }



         //This function will provide the patameter data
         @DataProvider(name = "Data-Provider-Function-test2")
         public Object[][] parameterTestProvider_test2() {
            return new Object[][] {
                    {"нов"},
                    {"ирс"}
                    };
        }

        @Test(description = "Successful search by symbols",
              dataProvider = "Data-Provider-Function-test2")

        public void test2(String q) {

                HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString
                        (PATH, "?q="+q);

                JSONObject jsonExpected = helperReq.getJSONfromJSONFile(SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON);

                org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody());

                boolean validation = helperReq.validationSchema
                        ("/q/param_q_scheme_for_success_search_for_3_symbols.json",
                                jsonObject);

                assertTrue(validation, "Response for param q=\"нов\" must be equal schema,\n"
                        + "Response Expected: " + jsonExpected + "\n"
                        + "Responce Actual: " + jsonObject);

        }


        // tests for UNsuccessful search by q = 3
        // ... q = нов -> НОВосибирск
        // ... q = ирс -> новосибИРСк
        @Test(description = "unsuccessful search by 3 symbols")
        public void test3() {

                HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString
                        (PATH, "?q="+"нос");

            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody().toString());

            boolean validation = helperReq.validationSchema
                        ("q\\param_q_scheme_for_unsuccess_search_for_3_symbols.json",
                                jsonObject);

                assertTrue(validation, "Response for param q=\"нос\" must be equal schema,\n"
                        + "Response Expected: empty array items \n"
                        + "Responce Actual: " + jsonObject);

        }


         @Test(description = "successful search in UPcase")
         public void test4() {
             HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString
                (PATH, "?q="+"НОВ");

             JSONObject jsonExpected = helperReq.getJSONfromJSONFile("/q/json_example_for_q_param_success_search_for_3_symbols.json");

             org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody().toString());

             boolean validation = helperReq.validationSchema
                ("q/param_q_scheme_for_success_search_for_3_symbols.json",
                        jsonObject);

             assertTrue(validation, "Response for param q=\"нов\" must be equal schema,\n"
                + "Response Expected: " + jsonExpected + "\n"
                + "Responce Actual: " + jsonObject);

        }

        @Test(description = "searching by full name of regions")
        public void test5() {

            HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString
                (PATH, "?q="+"Новосибирск");

            JSONObject jsonExpected = helperReq.getJSONfromJSONFile("/q/json_example_for_q_param_success_search_for_3_symbols.json");

            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody().toString());

            boolean validation = helperReq.validationSchema
                ("q/param_q_scheme_for_success_search_for_3_symbols.json",
                        jsonObject);

            assertTrue(validation, "Response for param q=\"нов\" must be equal schema,\n"
                + "Response Expected: " + jsonExpected + "\n"
                + "Responce Actual: " + jsonObject);

        }



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test6")
        public Object[][] parameterTestProvider_test6() {

            return new Object[][] {
                    //  если бы второй параметр работал - то в ответе остался бы только один город
                    {"?q=горс&country_code=ru"},
                    // если бы второй параметр работал - то ответ пришёл бы пустым (без городов)
                    {"?q=горс&page=5"},
                    // если бы второй параметр работал - то в ответе пришло бы сообщение об ошибке
                    {"?q=горс&page_size=1"}
                };
        }


       @Test(description = "use q param - other params must be ignored",
               dataProvider = "Data-Provider-Function-test6")

       public void test6(String q) {

            HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString
                (PATH, q);

            JSONObject jsonExpected = helperReq.getJSONfromJSONFile("/q/json_example_for_q_param_with_country_code_param.json");
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody());

            boolean validation = helperReq.validationSchema
                ("q/param_q_scheme_for_search_with_country_code_param.json",
                        jsonObject);

            assertTrue(validation, "Response for: "+q+" ...must be equal Scheme,\n"
                + "Response Expected: " + jsonExpected + "\n"
                + "Responce Actual: " + jsonObject);

        }





}