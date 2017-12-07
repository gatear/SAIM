package services

import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.util.URLParser
import com.github.mauricio.async.db.{Connection, QueryResult, RowData}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object DatabaseManager {

  val configuration = URLParser.parse("jdbc:postgres://localhost:5432/postgres?user=postgres&password=root")

  def selectSmartphones () = {

    val connection = new PostgreSQLConnection(configuration)
    Await.result(connection.connect,5 seconds)

    val future : Future[QueryResult] = connection.sendPreparedStatement(MessageRepo.Select)
    val result = Await.result(future ,5 seconds )

    result
  }
  object MessageRepo {
    val Select = "SELECT productName,productValue,launchYear FROM smartphones ORDER BY launchYear"
    val Insert = "INSERT INTO smartphones(productName, productvalue, launchYear) VALUES (?,?,?)"
  }

}