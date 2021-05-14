package controllers


import models.Vehicle
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}
import repositories.DataRepository

import javax.inject._
import javax.inject.Inject
import scala.concurrent.ExecutionContext

@Singleton
class BasicController @Inject()(val controllerComponents: ControllerComponents, dataRepository: DataRepository,  implicit val ec:ExecutionContext) extends BaseController {


  def getOneVehicle(vehicleName: String) = Action { implicit request =>

    val vehicle = dataRepository.getVehicle(vehicleName)
    vehicle match {
      case Some(Vehicle(wheels, heavy, name)) => Ok(Json.toJson(vehicle.get))
      case _ => NotFound
    }
  }
}



