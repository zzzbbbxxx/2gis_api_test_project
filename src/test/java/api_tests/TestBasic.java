package api_tests;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class TestBasic extends ReqBase {



        @Test(description= "structure of json are correct & contains all keys, params ")
        public void testJSONisCorrect() {

                JSONObject jsonResponse = HelperReq.sendRequestGetJSON(PATH,"");

                HelperReq.validateSchema(BASE_SCHEMA,jsonResponse);

        }

        @Test(description= "value of total variable must be equal real count of regions")
        public void testTotalValue() {

                JSONObject jsonResponse = HelperReq.sendRequestGetJSON(PATH,"");

                int total = HelperReq.getTotalValue(jsonResponse);
                int count = HelperReq.getCountOfRegions(jsonResponse);

                assertEquals(count, total,
                        "Фактическое количество городов: " + count +
                                "...отличается от значения, возвращаемого в переменной total: "+ total);

        }


}