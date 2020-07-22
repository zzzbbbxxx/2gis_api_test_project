import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

public class test_class_page {



        @Test
        public void givenValidInput_whenValidating_thenValid() {

                JSONObject jsonSchema = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/test_schema.json")));
                JSONObject jsonSubject = new JSONObject(new JSONTokener(reqWithParamQ.class.getResourceAsStream("/product_valid.json")));

                Schema schema = SchemaLoader.load(jsonSchema);
                schema.validate(jsonSubject);
        }

}