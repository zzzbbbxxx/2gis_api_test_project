import kong.unirest.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class reqWithParamPage extends reqBase {



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

        // for q = 0 symbols/empty, 1 symbols, 2 symbols...
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
         @DataProvider(name = "Data-Provider-Function-test2")
         public Object[][] parameterTestProvider_test2() {
            return new Object[][] {
                    {"нов"}, {"ирс"}
                    };
        }

        // q = 3 symbols & successful search
        // 1. q = нов -> НОВосибирск
        // 2. q = ирс -> новосибИРСк
        @Test(dataProvider = "Data-Provider-Function-test2")
        public void test2(String q) {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        (path, "?q="+q);

                JSONObject jsonExpected = getJSONfromJSONFile(jsonExampleQSuccessSearch);

                JSONObject jsonObject = new JSONObject(jsonResponse.getBody().toString());

                boolean validation = validationSchema
                        ("/q/param_q_scheme_for_success_search_for_3_symbols.json",
                                jsonObject);

                assertTrue(validation, "Response for param q=\"нов\" must be equal schema,\n"
                        + "Response Expected: " + jsonExpected + "\n"
                        + "Responce Actual: " + jsonObject);


        }


        // q = 3 symbols & unsuccessful search
        @Test
        public void test3() {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        (path, "?q="+"нос");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody().toString());

            boolean validation = validationSchema
                        ("q\\param_q_scheme_for_unsuccess_search_for_3_symbols.json",
                                jsonObject);

                assertTrue(validation, "Response for param q=\"нос\" must be equal schema,\n"
                        + "Response Expected: empty array items \n"
                        + "Responce Actual: " + jsonObject);

        }

         // q = UPcase registry
         @Test
         public void test4() {
             HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path, "?q="+"НОВ");

             JSONObject jsonExpected = getJSONfromJSONFile("/q/json_example_for_q_param_success_search_for_3_symbols.json");

             JSONObject jsonObject = new JSONObject(jsonResponse.getBody().toString());

             boolean validation = validationSchema
                ("q/param_q_scheme_for_success_search_for_3_symbols.json",
                        jsonObject);

             assertTrue(validation, "Response for param q=\"нов\" must be equal schema,\n"
                + "Response Expected: " + jsonExpected + "\n"
                + "Responce Actual: " + jsonObject);

        }

        // q = search by full name
        @Test
        public void test5() {

            HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path, "?q="+"Новосибирск");

            JSONObject jsonExpected = getJSONfromJSONFile("/q/json_example_for_q_param_success_search_for_3_symbols.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody().toString());

            boolean validation = validationSchema
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

    // q - other params must be ignored
       @Test(dataProvider = "Data-Provider-Function-test6")
       public void test6(String q) {

            HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path, q);

            JSONObject jsonExpected = getJSONfromJSONFile("/q/json_example_for_q_param_with_country_code_param.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody().toString());

            boolean validation = validationSchema
                ("q/param_q_scheme_for_search_with_country_code_param.json",
                        jsonObject);

            assertTrue(validation, "Response for param q=\"нов\" must be equal schema,\n"
                + "Response Expected: " + jsonExpected + "\n"
                + "Responce Actual: " + jsonObject);

        }





}