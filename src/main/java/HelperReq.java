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


    public static JSONArray getJsonArray(HttpResponse<String> response, String key){

        JSONObject jsonObject = new JSONObject(response.getBody());

        JSONArray tmp = jsonObject.getJSONArray(key);

        return tmp;
    }


    public static JSONArray getJsonArray(JSONObject jsonObject, String key){

        JSONArray tmp = jsonObject.getJSONArray(key);

        return tmp;

    }


    public static HttpResponse<String> sendRequestGetResponseString (String url, String params) {

        HttpResponse<String> jsonResponse
                        = Unirest.get(url+params)
                        .header("accept", "application/json")
                        .asString();

        assertEquals(200, jsonResponse.getStatus(),
                        "expected Status Code [200]" +
                                " but found Status Code [" + (jsonResponse.getStatus())+"]");

        return jsonResponse;

    }


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


    public static int getCountOfRegions (String url, String params) {

        JSONObject jsonObject = HelperReq.sendRequestGetJSON(url, params);

        JSONArray tmpObj = jsonObject.getJSONArray("items");

        return tmpObj.length();


    }



     public static JSONObject getJSONfromJSONFile (String path) {

            return new JSONObject(
                                new JSONTokener(ReqWithParamQ.class.getResourceAsStream(path)));
         }



        public static boolean arrayContainsElemFrom(HashSet<String> codeSet, List<String> codeList ) {


            for (String codeS : codeSet) {
                if (! arrayContainsElem(codeS,codeList)) return false;
                }

            return true;
        }

        public static boolean arrayContainsElem(String value, List<String> list ) {

             for (String item : list) {
                 if (item.equals(value)) return true;
                }
                return false;
        }

        public static int getValue(String key){

            HttpResponse<String> jsonResponse = HelperReq.sendRequestGetResponseString(ReqBase.PATH,"");

            return new JSONObject(jsonResponse.getBody()).getInt(key);

        }

    public static String getCountryCodeValue(JSONObject jsonObject){

        return ((JSONObject)jsonObject).getJSONObject("country").get("code").toString();

    }

    public static String getNameOfRegion(JSONObject jsonObject){

        return ((JSONObject)jsonObject).get("name").toString();

    }



}