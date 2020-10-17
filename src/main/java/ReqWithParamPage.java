import kong.unirest.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReqWithParamPage extends ReqBase {


        @Test(description = "page must be > 0")
        public void test10_0() {

            JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,"?page=-1");

            HelperReq.validateSchema("page\\error_schema_v2.json",
                            jsonObject);

        }


        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function_test10")
        public Object[][] parameterTestProvider_test10() {
            return new Object[][]{
                    {"?page"},
                    {"?page="},
                    {"?page=0"},
                    {"?page=1.5"},
                    {"?page=one"}
                };
        }


        @Test(dataProvider = "Data-Provider-Function_test10",
        description = "page = empty, 0, 1.5, one")
        public void test10(String q) {

            JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, q);

            HelperReq.validateSchema("page\\error_schema_v1.json", jsonObject);

        }


}