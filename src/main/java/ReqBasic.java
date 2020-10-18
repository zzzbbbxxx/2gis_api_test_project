import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ReqBasic extends ReqBase {


        @Test(description= "structure of json are correct & contains all keys, params ")
        public void testJSONisCorrect() {

                JSONObject jsonResponse = HelperReq.sendRequestGetJSON(PATH,"");

                HelperReq.validateSchema(BASE_SCHEMA,jsonResponse);

        }

        @Test(description= "value of total variable must be equal real count of regions")
        public void testTotalValue() {

                int total = HelperReq.getValue("total");
                int count = HelperReq.getCountOfRegions(PATH, "");

                Assert.assertEquals(count, total,
                        "Фактическое количество городов: " + count +
                                "...отличается от значения, возвращаемого в переменной total: "+ total);

        }


}