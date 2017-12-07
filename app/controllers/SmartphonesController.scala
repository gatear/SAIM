package controllers

import javax.inject.{Inject, Singleton}

import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.{AbstractController, ControllerComponents}
import services.DatabaseManager

@Singleton
class SmartphonesController @Inject() (cc: ControllerComponents)  extends AbstractController(cc){

def showAll = Action { request : (Request[AnyContent]) =>

  val resultSet = DatabaseManager.selectSmartphones().rows.get

  val jsObjectSeq = resultSet.map( rowData =>  Seq("productname"-> JsString(rowData("productname").toString),
                                                   "productvalue"-> JsString(rowData("productvalue").toString),
                                                   "launchYear"-> JsString(rowData("launchyear").toString)))
                             .map( seq => JsObject(seq))


  val jsArray = JsArray(jsObjectSeq)

  Ok(Json.stringify(jsArray))
}
}
