package controllers

// import akka.stream.impl.Stages.DefaultAttributes.recover
import com.mongodb.ReadPreference
import com.sun.tools.javac.jvm.Items
import models.Vehicle
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
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


  def getOneVehicle(vehicleName: String) = Action.async { implicit request =>
    dataRepository.getVehicle(vehicleName).map(vehicle => Ok(Json.toJson(vehicle.head))) recover {
      case _ => NotFound
    }
  }

  def receivedForm():Action[AnyContent]= Action.async{ implicit request:Request[AnyContent] =>
    val jsonReceived = request.body.asJson
    val vehicleNameFromJsonReceived = jsonReceived match {
      case Some(value)=> jsonReceived.get.\("Vehicle Name").as[String]
      case None => "test"
  }
    dataRepository.getVehicle(vehicleNameFromJsonReceived).map(items => Ok(Json.toJson(items.head))) recover {
      case _ => InternalServerError(Json.obj("message"->"Error reading item from Mongo"))
    }
  }

  def create(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Vehicle] match {
      case JsSuccess(vehicle, _) =>
        dataRepository.create(vehicle).map(_ => Created)
      case JsError(_) => Future(BadRequest)
    }
  }

  def getAll(): Action[AnyContent] = Action.async { implicit request =>
    dataRepository.getVehicle("Vehicle Name").map(items => Ok(Json.toJson(items)))
    }
}



