package controllers

import models.Vehicle
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito.when
import org.scalatest.Matchers._
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play.materializer
import play.api.http.Status
import play.api.libs.json.Json
import play.api.mvc.ControllerComponents
import play.api.test.{FakeRequest, Injecting}
import repositories.DataRepository
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, Future}
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

  object basicTestController extends BasicController(controllerComponents,
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
        .thenReturn(Future(Seq(dataModel)))
      val result = basicTestController.getOneVehicle("BMW")(FakeRequest())
      status(result) shouldBe (Status.OK)
    }
  }

  "return not notFound" when{
    "unknown vehicle name is submitted" in{
      when(mockDataRepository.getVehicle(any[String]))
        .thenReturn(Future(Seq()))
      val result = basicTestController.getOneVehicle("nnn")(FakeRequest())
      status(result) shouldBe Status.NOT_FOUND
    }
  }
}

  "BasicController .receivedForm" should {
    "return OK" when {
      "expected vehicle name submitted" in {
        when(mockDataRepository.getVehicle(any[String]))
          .thenReturn(Future(Seq(dataModel)))
        val result = basicTestController.receivedForm()(FakeRequest())
        contentAsJson(result) shouldBe Json.toJson(dataModel)
      }
    }

    "return notFound" when{
      "unknown vehicle name is submitted" in{
        when(mockDataRepository.getVehicle(any[String]))
          .thenReturn(Future(Seq()))
        val result = basicTestController.receivedForm()(FakeRequest())
        status(result) shouldBe Status.INTERNAL_SERVER_ERROR
      }
    }
  }
}
