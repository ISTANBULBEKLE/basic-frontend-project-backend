package controllers


import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}
import repositories.DataRepository

import javax.inject._
import javax.inject.Inject
import scala.concurrent.ExecutionContext

@Singleton
class BasicController @Inject()(val controllerComponents: ControllerComponents, dataRepository: DataRepository,  implicit val ec:ExecutionContext) extends BaseController {


  def getOneVehicle(vehicleName: String) = Action { implicit request =>
    dataRepository.getVehicle(vehicleName) map { vehicle =>
      // If the post was found, return a 200 with the post data as JSON
      Ok(Json.toJson(vehicle))
    } getOrElse NotFound // otherwise, return Not Found
  }
}



