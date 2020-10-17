import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONObject;

import static org.testng.Assert.assertEquals;

public class ReqBase {

    final public static String PATH = "https://regions-test.2gis.com/1.0/regions";

    final public static String BASE_SCHEMA = "base_schema.json";
    final public static String SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "q/json_example_v1.json";
    final public static String EXAMPLE_FOR_Q_PARAM_WITH_ERROR_JSON = "q/json_error_example.json";
    final public static String Q_SCHEME_FOR_IGNORING_OTHER_PARAMS = "q/success_scheme_v1.json";
    final public static String Q_SCHEME_FOR_SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "q/success_scheme_v2.json";
    final public static String Q_SCHEME_FOR_UNSUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "q/success_scheme_v3.json";
    final public static String ERROR_SCHEMA_FOR_0_1_2_SYMBOLS_JSON = "q/error_schema_v1.json";

}
