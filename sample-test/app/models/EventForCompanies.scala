package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import play.api.libs.json.Json
import play.api.libs.json._

case class EventForCompanies(id: Int, name: String, start_date: String, number_of_attendees: Int)

object EventForCompanies {

  /* Row parser for SQL query */
  val event = {
    get[Int]("id") ~
    get[String]("name") ~
    get[String]("start_date") ~
    get[Int]("number_of_attendees") map {
      case id~name~start_date~number_of_attendees =>
        EventForCompanies(id, name, start_date, number_of_attendees)
      case _ => null
    }
  }

  /* Implementation of Writes formatter for EventForCompanies. */
  implicit object EventForCompaniesFormat extends Format[EventForCompanies] {

    def writes(event: EventForCompanies): JsValue = {
      val eventSequence = Seq(
        "id" -> JsNumber(event.id),
        "name" -> JsString(event.name),
        "start_date" -> JsString(event.start_date),
        "number_of_attendees" -> JsNumber(event.number_of_attendees)
      )
      JsObject(eventSequence)
    }

    // To satisfy API specifications
    def reads(json: JsValue): EventForCompanies = {
      EventForCompanies(0, "", "", 0)
    }
  }

  def findForCompany(id: Int, from: String, offset: Option[Int], limit: Option[Int]): List[EventForCompanies] = {
    DB.withConnection { implicit c =>
      SQL("""
        SELECT event.id, event.name, event.start_date, COUNT(attend.event_id)
        AS number_of_attendees FROM events AS event LEFT JOIN attends AS attend
        ON event.id = attend.event_id WHERE event.user_id = {id} AND event.start_date
        >= {from} GROUP BY event.id LIMIT {limit} OFFSET {offset}
        """).on(
            'id -> id,
            'from -> from,
            'limit -> limit.getOrElse("1000"), // A big number
            'offset -> offset.getOrElse("0")
          ).as(event *)
    }
  }
}
