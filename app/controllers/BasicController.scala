package controllers

import models.Vehicle
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import repositories.DataRepository

import javax.inject._
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class BasicController @Inject()(
                                 val controllerComponents: ControllerComponents,
                                 dataRepository: DataRepository,
                                 implicit val ec:ExecutionContext) extends BaseController {


  def getOneVehicle(vehicleName: String) = Action { implicit request =>

    val vehicle = dataRepository.getVehicle(vehicleName)
    vehicle match {
      case Some(Vehicle(wheels, heavy, name)) => Ok(Json.toJson(vehicle.get))
      case _ => NotFound
    }
  }


  def receivedForm():Action[AnyContent]= Action{ implicit request:Request[AnyContent] =>
   val jsonReceived = request.body.asJson.get
    val vehicleNameFromJsonReceived = jsonReceived.\("Vehicle Name").as[String]

print("vehicleNameFromJsonReceived " + vehicleNameFromJsonReceived)

    val vehicle = dataRepository.getVehicle(vehicleNameFromJsonReceived)
    print("vehicle is " + vehicle)
    vehicle match {
      case Some(Vehicle(wheels, heavy, name)) => Ok(Json.toJson(vehicle.get))
      case _ => NotFound
    }
  }

}



