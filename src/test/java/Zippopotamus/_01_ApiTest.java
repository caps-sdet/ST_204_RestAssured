package Zippopotamus;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _01_ApiTest {

    @Test
    public void Test1() {

        // 1. Endpoint cagirmazdan evvelki hazirliq : Request, gedecek body (varsa), token
        // 2. Endpoint-in cagirildigi hisse : Metod -> GET, POST, PUT, DELETE
        // 3. Endpoint cagirildiqdan sonraki hisse : Response (Test Assert), data

        given().
                // body, token
                        when().
                // Metod, Endpointthen()
                        then()
        // gelen data, assert, test
        ;

    }

    // URI - Uniform Resource Identifier - resursu identifikasiya edir
    // veb sehife, fayl(path yolu), email unvani (test@gmail.com), www.google.com
    // BaseUri -> serverin esas unvani, BasePath "/blog"

    // URL - Uniform Resource Locator - resursun yerini bildirir
    // www.google.com/search
    // Butun URL-ler URI-dir, ancaq ki Butun URI-ler URL deyil.

    @Test
    public void statusCodeTest() {
        given().
                when()
                .get("https://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                //.log().all() // qayidan butun melumatlar
                .statusCode(200) // geriye qayidan response deyeri
        ;
    }

    @Test
    public void contentTypeTest() {
        given().
                when()
                .get("https://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON) // qayidatan data type JSON?
        ;
    }

    @Test
    public void checkResponseTest() {
        given().
                when()
                .get("https://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON) // qayidatan data type JSON?\
                .body("country", equalTo("United States"))
        // county-nin reponse-deki deyerini assert edirik
        ;
    }

    @Test
    public void checkHasItemTest() {
        given().
                when()
                .get("https://api.zippopotam.us/DE/01067")
                .then()
                .log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Dresden"))
        // places icerisindeki place-namelerin her hansi biri Dresden-dir mi?
        ;
    }

    @Test
    public void bodyArrayHasSizeTest() {
        given().
                when()
                .get("https://api.zippopotam.us/DE/01067")
                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(3)) // elementin size-si 1 dirmi?
        // places icerisindeki place-namelerin her hansi biri Dresden-dir mi?
        ;
    }

    @Test
    public void getSizeTest() {
        // Sorgudan gelen deyeri bir deyisene menimsetdik
        int placesSize =
        given().
                when()
                .get("https://api.zippopotam.us/DE/01067")
                .jsonPath()
                .getList("places")
                .size()
        ;

        System.out.println("placesSize = " + placesSize);

    }

    // daha pro yazilish asagidaki kimi olardi
    @Test
    public void getSizeResponseTest() { // Butun response-ni almaq
        Response response =
                given().
                        when()
                        .get("https://api.zippopotam.us/DE/01067")
                ;
        int placesSize1 = response.jsonPath().getList("places").size();
        System.out.println("placesSize1 = " + placesSize1);
        Assert.assertEquals(placesSize1,3,"Uygun gelmedi");

    }
}
