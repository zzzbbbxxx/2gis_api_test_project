package api_tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class TestDefaultValue extends ReqBase {

        @Test(description = "country_code by default must be show all regions for all countries")
        public void testDefaultCountryCode() {

                HashSet<String> codeSet = new HashSet<>();
                for (String page :  Arrays.asList("?page_size=5&page=1",
                        "?page_size=5&page=2", "?page_size=5&page=3",
                        "?page_size=5&page=4", "?page_size=5&page=5",
                        "?page_size=5&page=6")) {

                        JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,page);

                        JSONArray tmpObj = HelperReq.getJsonArray(jsonObject);

                        tmpObj.forEach(item -> {
                                codeSet.add(HelperReq.getCountryCodeValue((JSONObject)item));
                        });
                }


                List<String> codeList = Arrays.asList( "ru", "kg", "kz", "cz", "ua");
                List<String> codeListFromSet = new ArrayList<String>(codeSet);

                HelperReq.islistsEquals(codeList, codeListFromSet);

        }


        @Test(description = "page by default must be equal page=1" )
        public void testDefaultPageNumber() {


                org.json.JSONObject jsonObject1 = HelperReq.sendRequestGetJSON(PATH, "?page=1");
                org.json.JSONObject jsonObject2 = HelperReq.sendRequestGetJSON(PATH, "");

                HelperReq.validateSchema(BASE_SCHEMA,jsonObject1);

                assertEquals(jsonObject1.toString(), jsonObject2.toString(),
                                "Page by default must be equel for response for \"page=1\"\n"
                                        + "Response for \"page=1\": " + jsonObject1 + "\n"
                                        + "Default: " + jsonObject2);

        }


        @Test(description= "regions should not be repeated for different pages")
        public void testRegionsNotRepeated() {

                List<String> listOfRegions = new ArrayList<>();
                List<String> listOfRepeatRegions = new ArrayList<>();

                for (String page : Arrays.asList("?page=1", "?page=2", "?page=3")) {

                        JSONArray tmpObj = HelperReq.getJsonArray(
                                HelperReq.sendRequestGetJSON(PATH, page));

                        tmpObj.forEach(item -> {
                                String name = HelperReq.getNameOfRegion((JSONObject)item);

                                if (HelperReq.arrayContainsElem(name,listOfRegions))
                                        listOfRepeatRegions.add(name);

                                else listOfRegions.add(name);
                        });
                }

                assertEquals(listOfRepeatRegions.size(),
                        0,"Repeating regions: " + listOfRepeatRegions.toString());

        }



        @Test(description= "page_size default must be contains 15 regions")
        public void testDefaultPageSize() {

                JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, "");

                int count = HelperReq.getCountOfRegions(jsonObject);

                assertEquals(count,
                        15,
                        "Count of regions in response by default must be equal: 15 \n");

        }





}