package repositories


import javax.inject.Singleton
import models.Vehicle

@Singleton
class DataRepository  {

  val vehicleOptions = Seq(
    Vehicle(wheels = 4, heavy = true, name = "BMW"),
    Vehicle(wheels = 2, heavy = false, name = "Chopper")
  )

  def getVehicle(vehicleName: String): Option[Vehicle] = vehicleOptions.collectFirst {
    case vehicle if vehicle.name == vehicleName => vehicle
  }

}
