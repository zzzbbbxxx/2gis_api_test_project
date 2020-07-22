import kong.unirest.HttpResponse;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class reqWithParamQ extends reqBase {



        // q = 0 symbols/empty - status code == 200
        // q = 0 symbols/empty - structure of json for response with error == errorscheme
        @Test
        public void test1() {

                HttpResponse<String> jsonResponse = sendRequestGetResponseString
                        ("https://regions-test.2gis.com/1.0/regions","?q");

                if (!checkStatus(200,jsonResponse.getStatus())) {

                } else {
                        JSONObject jsonExpected =
                                new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/json_with_error_for_q_param.json")));

                        org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody().toString());
                        boolean validation = validationSchema
                                ("error_schema_for_param_q.json",
                                        jsonObject);
                        assertTrue(validation,"Response must be equal ErrorSchema,\n"
                        +"Response Expected: "+ jsonExpected+"\n"
                        +"Responce Actual: "+ jsonObject);
                }

        }


        // q = 1,2 symbols
        @Test
        public void test2() {

                List<String> list = Arrays.asList("но", "н");

                for (String x : list) {

                        HttpResponse<String> jsonResponse = sendRequestGetResponseString
                                ("https://regions-test.2gis.com/1.0/regions", "?q="+x);


                        JSONObject jsonExpected =
                                new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/json_with_error_for_q_param.json")));

                        org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse.getBody().toString());

                        boolean validation = validationSchema
                                ("error_schema_for_param_q.json",
                                        jsonObject);

                        assertTrue(validation, "Param 'q' == "+ x + "\n"
                                + "Response must be equal ErrorSchema,\n"
                                + "Response Expected: " + jsonExpected + "\n"
                                + "Responce Actual: " + jsonObject);

                }

        }


        // q = 3 symbols & successful search
        @Test
        public void test3() {

                org.json.JSONObject jsonResponse = sendRequestGetJSON
                        ("https://regions-test.2gis.com/1.0/regions",
                                "?q=нов");


                assertEquals(22, 22);

        }


        // q = 3 symbols & unsuccessful search
        @Test
        public void test4() {

                org.json.JSONObject jsonResponse = sendRequestGetJSON("https://regions-test.2gis.com/1.0/regions","");

                int total = jsonResponse.getInt("total");

                assertEquals(22, total);

        }



}