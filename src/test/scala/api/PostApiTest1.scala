package api

import io.gatling.core.Predef.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class PostApiTest1 extends Simulation{
  //protocol
  val httpProtocol=http
    .baseUrl("https://reqres.in/api")

  //Scenario
  val scn=scenario("Post Api Request Demo")
    .exec(
      http("Create User")
        .post("/users")
        .header("content-type","application/json")
        .asJson
        .body(RawFileBody("data/user.json")).asJson
//        .body(StringBody(
//          """
//            |{
//            |    "name": "Anuj",
//            |    "job": "leader"
//            |}
//            |""".stripMargin)).asJson
        .check(
          status.is(201),
          jsonPath("$.name")is("Akash")
        )
    ).pause(1)
  val scn2=scenario("update user")
  .exec(
    http("update-user")
      .put("/users/2")
      .body(RawFileBody("data/user.json")).asJson
      .check(
        status.is(200),
        jsonPath("$.name")is("Akash")
      )
  ).pause(1)

  val scn3= scenario("Delete user")
    .exec(
      http("delete-user")
        .delete("/users/2")
        .check(status.is(204))
    )
  setUp(
    scn.inject(rampUsers(10).during(5)),
    scn2.inject(rampUsers(5).during(5)),
    scn3.inject(rampUsers(3).during(3))

  ).protocols(httpProtocol)

}
