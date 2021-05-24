package repositories

import models.Vehicle
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import play.api.test.Injecting
import uk.gov.hmrc.mongo.MongoComponent

import scala.concurrent.ExecutionContext

class DataRepositorySpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  implicit lazy val executionContext: ExecutionContext = app.injector.instanceOf[ExecutionContext]
  lazy val mongoComponent: MongoComponent = app.injector.instanceOf[MongoComponent]

  object dataRepositoryTestController extends DataRepository(mongoComponent)(executionContext)

      "getVehicle() .get" should {
          "successfully return vehicle" when{
            "vehicle name matches vehicle options" in{
              lazy val result = dataRepositoryTestController.getVehicle("BMW")
              await(result) mustBe Seq(Vehicle(wheels = 4, heavy = true, name = "BMW"))
            }
          }
      }
          "unsuccessfully return vehicle" when{
            "vehicle name do not match vehicle options" in{
              lazy val result = dataRepositoryTestController.getVehicle("test")
              await(result) mustBe Seq()
            }
          }
  }

