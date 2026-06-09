package GoRest;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _01_Users extends Setup {

    int userID=0;

    @Test
    public void usersLists() {
        given().
                when()
                .get("https://gorest.co.in/public/v2/users")
                .then()
                .log().body()
                //.log().all() // qayidan butun melumatlar
                .statusCode(200) // geriye qayidan response deyeri
        ;
    }

    @Test (dependsOnMethods = "usersLists")
    public void createUser() {
        String rndFullName = rndCreate.name().fullName();
        String rndEmail = rndCreate.internet().emailAddress();

        //"name": "test-Melvin Rutherford",
        //    "email": "Cale.Bode@gmail.com",
        //    "gender": "male",
        //    "status": "active"

        Map<String,String> newUser = new HashMap<>();
        newUser.put("name",rndFullName);
        newUser.put("email",rndEmail);
        newUser.put("gender","male");
        newUser.put("status","active");

        userID =
        given()
                .spec(reqSpec)
                .body(newUser)

                .when()
                .post("users") // eger yazdigimiz https ile bashlamirsa baseUri avto evvele kecir
                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id") // id-de olan deyeri userID-ye menimset
        ;

        System.out.println("userID = " + userID);
        System.out.println("CreateUser metodu ishledi \n");

    }

    @Test (dependsOnMethods = "createUser")
    public void getUserByID() {
        given()
                .spec(reqSpec)

                .when()
                .get("users/"+userID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
        ;

        System.out.println("getUserByID metodu ishledi \n");
    }


    @Test (dependsOnMethods = "getUserByID")
    public void updateUser() {
        String updateRndFullName = rndCreate.name().fullName();
        String updtaRndEmail = rndCreate.internet().emailAddress();

        Map<String,String> updateUser = new HashMap<>();
        updateUser.put("name",updateRndFullName);
        updateUser.put("email",updtaRndEmail);
        updateUser.put("gender","male");
        updateUser.put("status","active");


        given()
                .spec(reqSpec)
                .body(updateUser) // melumatlari yeniledik

                .when()
                .put("users/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo(updateRndFullName))
                .body("email",equalTo(updtaRndEmail))
        ;

        System.out.println("updateUser metodu ishledi \n");
    }

    @Test (dependsOnMethods = "updateUser")
    public void deleteUser() {
        given()
                .spec(reqSpec)

                .when()
                .delete("users/"+userID)

                .then()
                .statusCode(204)
        ;

        System.out.println("deleteUser metodu ishledi \n");
    }

    @Test (dependsOnMethods = "deleteUser")
    public void negativeDeleteUser() {
        given()
                .spec(reqSpec)

                .when()
                .delete("users/"+userID)

                .then()
                .statusCode(404)
        ;

        System.out.println("negativeDeleteUser metodu ishledi \n");
    }




}
