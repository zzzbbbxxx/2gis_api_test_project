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

public class reqWithParamPage extends reqBase {


    //This function will provide the patameter data
    @DataProvider(name = "Data-Provider-Function_test10_0")
    public Object[][] parameterTestProvider_test10_0() {
        return new Object[][]{
                {"?page=-1"}
        };
    }

    // for page = -1
    // ...status code = 200
    // ...structure of json = errorscheme
    @Test(dataProvider = "Data-Provider-Function_test10_0")
    public void test10_0(String q) {

        HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path, q);

        if (!checkStatus(200, jsonResponse.getStatus())) {

        } else {
            JSONObject jsonExpected = getJSONfromJSONFile("page\\json_example_for_page_param_with_error_2.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

            boolean validation = validationSchema
                    ("page\\param_page_code_error_schema_2.json",
                            jsonObject);
            assertTrue(validation, "Response must be equal ErrorSchema,\n"
                    + "Response Expected: " + jsonExpected + "\n"
                    + "Responce Actual: " + jsonObject);
        }

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

    // for page = empty, 0, 1.5, one
    // ...status code = 200
    // ...structure of json = errorscheme
    @Test(dataProvider = "Data-Provider-Function_test10")
    public void test10(String q) {

        HttpResponse<String> jsonResponse = sendRequestGetResponseString
                (path, q);

        if (!checkStatus(200, jsonResponse.getStatus())) {

        } else {
            JSONObject jsonExpected = getJSONfromJSONFile("page\\json_example_for_page_param_with_error.json");

            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

            boolean validation = validationSchema
                    ("page\\param_page_code_error_schema.json",
                            jsonObject);
            assertTrue(validation, "Response must be equal ErrorSchema,\n"
                    + "Response Expected: " + jsonExpected + "\n"
                    + "Responce Actual: " + jsonObject);
        }

    }




    //"Перелистывание" страниц / у
    @Test
    public void test12() {

        List<String> pages = Arrays.asList("?page=1", "?page=2", "?page=3");
        List<String> listOfRegions = new ArrayList<>();
        int _page = 0;

        for (String q : pages) {

            _page++;
            HttpResponse<String> jsonResponse = sendRequestGetResponseString
                    (path, q);

            if (!checkStatus(200, jsonResponse.getStatus())) {

            } else {

                JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                JSONArray tmpObj = jsonObject.getJSONArray("items");

                List<String> _listOfRegions = new ArrayList<>();


                JSONObject mJsonObject = new JSONObject();

                for (int i = 0; i < tmpObj.length(); i++) {

                    mJsonObject = (JSONObject) tmpObj.get(i);

                    String name = mJsonObject.get("name").toString();
                    _listOfRegions.add(name);

                    Assert.assertTrue(arrayContains(name, listOfRegions),
                            "List of regions before error: " + listOfRegions + "\n"
                                    + "Region: " + name + " is already was in response\n"
                                    + "Current page: " + q + "\n"
                                    + "List of regions on the errorPage: " + _listOfRegions + "\n");

                    if (i == 5) listOfRegions.add("\n");


                    listOfRegions.add(name);
                }
                listOfRegions.add(":page=" + _page + " ");

            }
        }
    }

    public boolean arrayContains(String value, List<String> arr) {
        for (String item : arr) {
            if (item.equals(value)) {
                return false;
            }
        }
        return true;
    }




}