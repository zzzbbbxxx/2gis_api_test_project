package api_tests;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.*;

import static org.testng.Assert.*;

public  class HelperReq {



    public static JSONObject sendRequestGetJSON (String url, String params) {

        HttpResponse<String> jsonResponse
                = Unirest.get(url+params)
                .header("accept", "application/json")
                .asString();

        assertEquals(200, jsonResponse.getStatus(),
                "expected Status Code [200]" +
                        " but found Status Code [" + (jsonResponse.getStatus())+"]");


        return new org.json.JSONObject(jsonResponse.getBody());

    }

    public static JSONObject getJSONfromJSONFile(String path) {

        return new JSONObject(
                new JSONTokener(
                        HelperReq.class.getClassLoader().getResourceAsStream(path)));

    }


    public static void validateSchema(String pathToSchema, JSONObject json) {

        org.json.JSONObject jsonSchema = new org.json.JSONObject
                (new JSONTokener(String.valueOf(getJSONfromJSONFile(pathToSchema))));



        Schema schema = SchemaLoader.load(jsonSchema);

        try {
            schema.validate(json);
        } catch (Exception exception){
                 throw new AssertionError( exception.getMessage() + "");
        }
    }


    public static void validateSchemaV2(String pathToSchema, String pathToExpectedJson, JSONObject json) {

        org.json.JSONObject jsonSchema = new org.json.JSONObject
                (new JSONTokener(String.valueOf(getJSONfromJSONFile(pathToSchema))));

        JSONObject jsonExpected = HelperReq.getJSONfromJSONFile
                (pathToExpectedJson);

        Schema schema = SchemaLoader.load(jsonSchema);

        try {
            schema.validate(json);
        } catch (Exception exception){
            throw new AssertionError( exception.getMessage() + "\n"
                    + "Actual json: " + "\n" + json + "\n"
                    + "Expected json: " + "\n" + jsonExpected);
         }
    }



    public static JSONArray getJsonArray(JSONObject jsonObject){

        JSONArray tmp = jsonObject.getJSONArray("items");

        return tmp;

    }


    public static int getCountOfRegions (JSONObject jsonResponse) {

        JSONArray tmpObj = jsonResponse.getJSONArray("items");

        return tmpObj.length();

    }

    public static int getTotalValue(JSONObject jsonResponse){

        return jsonResponse.getInt("total");

    }



    public static String getCountryCodeValue(JSONObject jsonObject){

        return jsonObject.getJSONObject("country").get("code").toString();

    }

    public static String getNameOfRegion(JSONObject jsonObject){

        return jsonObject.get("name").toString();

    }

    public static boolean arrayContainsElem(String value, List<String> list ) {

        for (String item : list) {
            if (item.equals(value)) return true;
        }
        return false;
    }


    public static void islistsEquals(List<String> codeSet, List<String> codeList ) {

        List<String> tmp = new ArrayList<String>();

        for (String codeS : codeSet) {
            if (!arrayContainsElem(codeS, codeList)) {
                tmp.add(codeS);
            }
        }

        if (!tmp.isEmpty())
            throw new AssertionError("This regions was not found:" + tmp);

    }

}