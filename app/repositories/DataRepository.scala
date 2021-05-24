package repositories


import com.mongodb.ReadPreference

import javax.inject.{Inject, Singleton}
import models.Vehicle
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.model.{IndexModel, IndexOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class DataRepository @Inject()(
                                mongoComponent: MongoComponent
                              )(implicit ec: ExecutionContext
                              ) extends PlayMongoRepository[Vehicle](
  collectionName = "vehicles",
  mongoComponent = mongoComponent,
  domainFormat   = Vehicle.format,
  indexes        = Seq(
    IndexModel(ascending("name"), IndexOptions().unique(true))
  ))
{

  // import org.mongodb.scala.model.Indexes.ascending
  // queries and updates can now be implemented with the available `collection: org.mongodb.scala.MongoCollection`
  def getVehicle(vehicleNameFromUrl: String): Future[Seq[Vehicle]] = collection.find( equal("name", vehicleNameFromUrl) ).toFuture()
  def findAll(): Future[Seq[Vehicle]] = collection.withReadPreference(ReadPreference.secondaryPreferred).find().toFuture().map(_.toList)
  def create(vehicle: Vehicle) = collection.insertOne(vehicle).toFuture()
}

