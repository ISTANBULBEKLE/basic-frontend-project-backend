package controllers

import models.Vehicle
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito.when
import org.scalatest.Matchers._
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.Status
import play.api.mvc.{ControllerComponents, Result}
import play.api.test.{FakeRequest, Injecting}
import repositories.DataRepository
import play.api.test.Helpers._
import scala.concurrent.{Await, ExecutionContext, Future}
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class BasicControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  lazy val controllerComponents: ControllerComponents = app.injector.instanceOf[ControllerComponents]
  implicit lazy val executionContext: ExecutionContext = app.injector.instanceOf[ExecutionContext]
  val mockDataRepository: DataRepository = mock[DataRepository]

  object basicTestController extends BasicController(
    controllerComponents,
    mockDataRepository,
      executionContext
  )


  val dataModel:Vehicle=Vehicle (
    2,
    true,
    "BMW"
  )



"BasicController .getVehicle" should {

  "return OK" when{

    "expected vehicle name submitted" in{
      when(mockDataRepository.getVehicle(any[String]))
        .thenReturn(Some(dataModel))

      val result = basicTestController.getOneVehicle("BMW")(FakeRequest())
      status(result) shouldBe (Status.OK)
    }
  }

  "return not notFound" when{

    "unknown vehicle" in{
//      when(mockDataRepository.getVehicle(any[String]))
//        .thenReturn(dataModel)
//      val result = basicTestController.getOneVehicle()
//      status(result) shouldBe Status.NOT_FOUND
    }
  }

}


}
