import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReqDefault extends ReqBase {



        // status code = 200
        @Test(description= "Launches the WordPress site")
        public void test1() {

                HttpResponse<JsonNode> jsonResponse = helperReq.sendRequestGetResponse
                        (PATH,"");

                assertEquals(200, jsonResponse.getStatus());

        }


        // structure of json are correct ( contains all keys,params )
        @Test
        public void test2() {

                JSONObject jsonResponse = helperReq.sendRequestGetJSON
                        (PATH,"");

                helperReq.validateSchema(BASE_SCHEMA,jsonResponse);
        }



        //  key total = 22
        // it must be equal real count of regions by req

        @Test
        public void test3_upd() {

                List<String> pages = Arrays.asList("?page=1", "?page=2", "?page=3");
                List<String> listOfRegions = new ArrayList<String>();
                int numPage = 0;
                int count = 0;
                int total = 0;


                for (String q : pages) {

                        numPage++;
                        HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString(PATH, q);

                        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());
                        JSONArray tmpObj = jsonObject.getJSONArray("items");
                        total = jsonObject.getInt("total");

                        JSONObject mJsonObject = new JSONObject();
                        count = count + tmpObj.length();

                        for (int i = 0; i < tmpObj.length(); i++) {

                                mJsonObject = (JSONObject) tmpObj.get(i);
                                String name = mJsonObject.get("name").toString();
                                        if (helperReq.arrayContains(name, listOfRegions))
                                        {

                                        }
                                        else listOfRegions.add(name);
                                }

                              //  listOfRegions.add(":page=" + numPage + " ");
                        //
                }

                int count_ = listOfRegions.size();

                Assert.assertEquals(count_,total, "Фактическое количество городов, отличается от того, что идёт в переменной Total");

        }








        // page_size default must be contains 15 regions
        @Test
        public void test13() {

                HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString(PATH,"");

                JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

                Assert.assertEquals(jsonObject.getJSONArray("items").length(),
                                15,
                                "Count of regions in response by default must be equal: 15 \n");



        }



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test11")
        public Object[][] parameterTestProvider_test11() {
                return new Object[][]{
                        {"?page=1", ""}
                };
        }

        // page by default must be equel page=1
        @Test(dataProvider = "Data-Provider-Function-test11")
        public void test11(String q1, String q2) {

                HttpResponse<String> jsonResponse1 = helperReq.sendRequestGetResponseString
                        (PATH, q1);

                HttpResponse<String> jsonResponse2 = helperReq.sendRequestGetResponseString
                        (PATH, q2);

                JSONObject jsonObject1 = new JSONObject(jsonResponse1.getBody());
                JSONObject jsonObject2 = new JSONObject(jsonResponse2.getBody());

                helperReq.validateSchema(BASE_SCHEMA,jsonObject1);

                assertEquals(jsonObject1.toString(), jsonObject2.toString(),
                                "Page by default must be equel for response for \"page=1\"\n"
                                        + "Response for \"page=1\": " + jsonObject1 + "\n"
                                        + "Default: " + jsonObject2);

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

        // country_code by default must be show all regions for all countries
        // я мог написать тест двумя способами:
        // исходя из документации - проверяя, что все страны из списка
        // ... есть в ответе
        // или исходя из знания, что в списке отсутствует "ua"
        // в первом случае будет ошибка, потому что я ищу только страны из списка и "падаю",
        // ...если появляется то, чего я не жду

        @Test(dataProvider = "Data-Provider-Function_test9")
        public void test9(String q1, String q2) {

                HttpResponse<String> jsonResponse = helperReq.sendRequestGetResponseString
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




                Assert.assertTrue(helperReq.arrayContains__(codeList),
                        "Code for countries by default de must be in {ru,kg, kz,cz} /n "
                +codeList);




        }

}