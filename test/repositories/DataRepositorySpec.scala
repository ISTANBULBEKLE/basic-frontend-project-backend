package repositories

import models.Vehicle
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.Status
import play.api.test.Helpers.status
import play.api.test.{FakeRequest, Injecting}

class DataRepositorySpec extends PlaySpec with GuiceOneAppPerTest with Injecting{

  object dataRepositoryTestController extends DataRepository{

  }
  "getVehicle() .get" should {
      "successfully return vehicle" when{
        "vehicle name matches vehicle options" in{
          val result = dataRepositoryTestController.getVehicle("BMW")
          result mustBe Some(Vehicle(wheels = 4, heavy = true, name = "BMW"))
        }
      }
  }
  "unsuccessfully return vehicle" when{
    "vehicle name do not match vehicle options" in{
      val result = dataRepositoryTestController.getVehicle("test")
      result mustBe None
    }
  }
}

//"BasicController .getVehicle" should {
//
//  "return OK" when{
//
//  "expected vehicle name submitted" in{
//  when(mockDataRepository.getVehicle(any[String]))
//  .thenReturn(Some(dataModel))
//
//  val result = basicTestController.getOneVehicle("BMW")(FakeRequest())
//  status(result) shouldBe (Status.OK)
//}
//}
//
//  "return not notFound" when{
//
//  "unknown vehicle name is submitted" in{
//
//  when(mockDataRepository.getVehicle(any[String]))
//  .thenReturn(None)
//
//  val result = basicTestController.getOneVehicle("nnn")(FakeRequest())
//  status(result) shouldBe Status.NOT_FOUND
//
//}
//}
//}