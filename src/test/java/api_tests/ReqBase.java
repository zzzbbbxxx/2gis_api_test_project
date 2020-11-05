package api_tests;

public class ReqBase {

    final public static String PATH = "https://regions-test.2gis.com/1.0/regions";

    final public static String BASE_SCHEMA = "json/base_schema.json";
    final public static String SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "json/q/json_example_v1.json";
    final public static String EXAMPLE_FOR_Q_PARAM_WITH_ERROR_JSON = "json/q/json_error_example.json";
    final public static String Q_SCHEME_FOR_IGNORING_OTHER_PARAMS = "json/q/success_scheme_v1.json";
    final public static String Q_SCHEME_FOR_SUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "json/q/success_scheme_v2.json";
    final public static String Q_SCHEME_FOR_UNSUCCESS_SEARCH_FOR_3_SYMBOLS_JSON = "json/q/success_scheme_v3.json";
    final public static String ERROR_SCHEMA_FOR_0_1_2_SYMBOLS_JSON = "json/q/error_schema_v1.json";
    final public static String ERROR_SCHEMA_FOR_30_SYMBOLS_JSON = "json/q/error_schema_v2.json";
    final public static String ERROR_SCHEMA_FOR_INTEGER_SYMBOLS_JSON = "json/page_size/error_schema_v1.json";
    final public static String ERROR_SCHEMA_FOR_NON5_10_15_JSON  = "json/page_size/error_schema_v2.json";
    final public static String EXAMPLE_PAGE_ERROR_V2 = "json/page_size/json_error_example_v2.json";
    final public static String ERROR_SCHEMA_COUNTRY_CODE = "json/countrycode/error_schema.json";
}
