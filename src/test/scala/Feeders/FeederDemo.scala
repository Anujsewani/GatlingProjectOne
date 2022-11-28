package Feeders

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FeederDemo extends Simulation{
  // Protocol
  val httpProtocol=http.baseUrl("http://computer-database.gatling.io/")
  //scenario
  val feeder=csv("data/data1.csv").circular

  val scn=scenario("Feeders Demo")
    .repeat(3) {
      feed(feeder)
        .exec { session =>
          println("name: " + session("name").as[String])
          println("job: " + session("job").as[String])
          println("page: " + session("page").as[String])
          session
        }
        .pause(1)
        .exec(http("Goto ${page}")
        .get("/#{page}")
        )
    }
  //setUp
  setUp(scn.inject(atOnceUsers(3))).protocols(httpProtocol)
}
