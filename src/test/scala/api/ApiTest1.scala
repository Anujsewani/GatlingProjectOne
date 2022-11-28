package api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ApiTest1 extends Simulation{
  //Protocol
  val httpProtocol=http
    .baseUrl("https://reqres.in/api/users")

  //Scenario
  val scn= scenario("Get Api Request Demo")
    .exec(
      http("Single User")
        .get("/2")
        .check(
          status.is(200),jsonPath("$.data.first_name").is("Janet"))
        //.check(jsonPath("$.data.first_name"))
    ).pause(1)
  //setUp

  setUp(
    scn.inject(rampUsers(10).during(5))
      .protocols(httpProtocol)
  )

}
