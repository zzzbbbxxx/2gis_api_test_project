import kong.unirest.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReqWithParamQ extends ReqBase {



        @DataProvider(name = "dataProvider_for_error_searchTest")
        public Object[][] dataProviderForSearchV1() {
            return new Object[][] {
                    {"?q=но"},
                    {"?q=н"},
                    {"?q="},
                    {"?q"}
                    };
        }

        @Test(description = "search with error response for q = 0,1,2 symbols",
              dataProvider = "dataProvider_for_error_searchTest")
        public void errorSearchTest(String q)  {

            org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,q);

            HelperReq.validateSchema(ERROR_SCHEMA_FOR_0_1_2_SYMBOLS_JSON, jsonObject);

        }


    @Test(description = "search with error response for q = 31 symbols")
    public void errorSearchTestForQ30Sym()  {

        org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH,
                "?q=012345678900123456789001234567890");

        HelperReq.validateSchema(ERROR_SCHEMA_FOR_30_SYMBOLS_JSON, jsonObject);

    }

         //This function will provide the patameter data
         @DataProvider(name = "Data-Provider-Function-test2")
         public Object[][] parameterTestProvider_test2() {
            return new Object[][] {
                    {"нов"},
                    {"ирс"}
                    };
        }

        @Test(description = "Successful search by symbols",
              dataProvider = "Data-Provider-Function-test2")
        public void successfulSearchTest(String q) {

            org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, "?q="+q);

            HelperReq.validateSchema(Q_SCHEME_FOR_SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON, jsonObject);

        }


        @Test(description = "unsuccessful search by 3 symbols")
        public void unsuccessfulSearchTest() {

            org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, "?q="+"нос");

            HelperReq.validateSchema(Q_SCHEME_FOR_UNSUCCESS_SEARCH_FOR_3_SYMBOLS_JSON, jsonObject);

        }


         @Test(description = "successful search in UPcase")
         public void successSearchTestForUpcase() {

             org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, "?q="+"НОВ");

             HelperReq.validateSchema(Q_SCHEME_FOR_SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON, jsonObject);

        }

        @Test(description = "searching by full name of regions")
        public void successSearchByFullNameOfRegions() {

            org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, "?q="+"Новосибирск");

            HelperReq.validateSchema(Q_SCHEME_FOR_SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON, jsonObject);

        }



        //This function will provide the patameter data
        @DataProvider(name = "Data-Provider-Function-test6")
        public Object[][] parameterTestProvider_test6() {

            return new Object[][] {
                    //  если бы второй параметр работал - то в ответе остался бы только один город
                    {"?q=горс&country_code=ru"},
                    // если бы второй параметр работал - то ответ пришёл бы пустым (без городов)
                    {"?q=горс&page=5"},
                    // если бы второй параметр работал - то в ответе пришло бы сообщение об ошибке
                    {"?q=горс&page_size=1"}
                };
        }


       @Test(description = "if use q param - other params must be ignored",
               dataProvider = "Data-Provider-Function-test6")

       public void test6(String q) {

           org.json.JSONObject jsonObject = HelperReq.sendRequestGetJSON(PATH, q);

           HelperReq.validateSchema(Q_SCHEME_FOR_IGNORING_OTHER_PARAMS, jsonObject);

        }





}