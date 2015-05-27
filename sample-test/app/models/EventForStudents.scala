package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import play.api.libs.json.Json
import play.api.libs.json._

case class EventForStudents(id: Int, name: String, start_date: String, user_id: Int, username: String)

object EventForStudents {

  /* Row parser for SQL query */
  val event = {
    get[Int]("id") ~
    get[String]("eventname") ~
    get[String]("start_date") ~
    get[Int]("user_id") ~
    get[String]("name") map {
      case id~eventname~start_date~user_id~username =>
        EventForStudents(id, eventname, start_date, user_id, username)
      case _ => null
    }
  }

  /* Implementation of Writes formatter for EventForStudents. */
  implicit object EventForStudentsFormat extends Format[EventForStudents] {

    def writes(event: EventForStudents): JsValue = {
      val eventHost = JsObject(Seq(
        "id" -> JsNumber(event.user_id),
        "name" -> JsString(event.username)
      ))
      val eventSequence = Seq(
        "id" -> JsNumber(event.id),
        "name" -> JsString(event.name),
        "start_date" -> JsString(event.start_date),
        "company" -> eventHost
      )
      JsObject(eventSequence)
    }

    //To satisfy API specifications
    def reads(json: JsValue): JsResult[EventForStudents] = {
      JsSuccess(EventForStudents(0, "", "", 0, ""))
    }
  }

  def findForStudent(from: String, offset: Option[Int], limit: Option[Int]): List[EventForStudents] = {
    DB.withConnection { implicit c =>
      SQL( """
           SELECT event.id, event.user_id, event.name AS eventname, event.start_date,
           user.name FROM events AS event, users AS user WHERE event.user_id = user.id
           AND start_date >= {from} ORDER BY start_date LIMIT {limit} OFFSET {offset}
           """).on(
        'from -> from,
        'limit -> limit.getOrElse(1000), // A big number
        'offset -> offset.getOrElse(0)
      ).as(event *)
    }
  }
}
