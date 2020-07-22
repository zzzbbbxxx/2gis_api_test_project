import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class reqDefault extends reqBase {



        // code 200
        @Test
        public void test1() {

                int status = sendRequestGetStatus("https://regions-test.2gis.com/1.0/regions","");

                assertEquals(200, status);

        }


        // structure of json
        @Test
        public void test2() {

                org.json.JSONObject jsonResponse = sendRequestGetResponse("https://regions-test.2gis.com/1.0/regions","");

                boolean validation = validationSchema("/base_schema.json",jsonResponse);

                assertTrue(validation);
        }


        // total = 22
        @Test
        public void test3() {

                org.json.JSONObject jsonResponse = sendRequestGetResponse("https://regions-test.2gis.com/1.0/regions","");

                int total = jsonResponse.getInt("total");

                assertEquals(22, total);

        }



}