package GoRest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.datafaker.Faker;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class Setup {

    // Bize BaseUri , Auth , Random kimi xususiyyetler lazimdir

    Faker rndCreate = new Faker(); // random olaraq melumat yaratmaq ucun
    RequestSpecification reqSpec;

    @BeforeClass
    public void preRequest(){
        baseURI = "https://gorest.co.in/public/v2";

        reqSpec = new RequestSpecBuilder() // Sorgu xususiyyetleri yaradicisi
                .addHeader("Authorization",
                        "Bearer f6b1e426090ab8a8b157f1ff18ee66c52917475bc0c291cf7a06b891d46d8141")
                .setContentType(ContentType.JSON)
                .build();
    }
}
