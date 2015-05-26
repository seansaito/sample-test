package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Reservation(user_id: Int, event_id: Int)

object Reservation {

  /* Row parser for SQL query */
  val reservation = {
    get[Int]("user_id") ~
    get[Int]("event_id") map {
      case user_id~event_id => Reservation(user_id, event_id)
      case _ => null
    }
  }

  def reserve(student_id: Int, event_id: Int): Boolean = {
    DB.withConnection { implicit c =>
      SQL("""
          SELECT * FROM attends WHERE user_id = {student_id}
          AND event_id = {event_id}""").on(
            'student_id -> student_id,
            'event_id -> event_id
          ).as(reservation *).length match {
            case 0 =>  SQL("""
            INSERT INTO attends (user_id, event_id) VALUES
            ({student_id}, {event_id})""").on(
              'studnet_id -> student_id,
              'event_id -> event_id
            ).executeUpdate()
            case _ => false
          }
    }
    true
  }

  def unreserve(student_id: Int, event_id: Int): Boolean = {
    DB.withConnection { implicit c =>
      SQL("""
          SELECT * FROM attends WHERE user_id = {student_id}
          AND event_id = {event_id}""").on(
            'student_id -> student_id,
            'event_id -> event_id
          ).as(reservation *).length match {
            case 0 => false
            case _ => SQL("""
            DELETE FROM attends WHERE user_id = {student_id}
            AND event_id = {event_id}""").on(
              'student_id -> student_id,
              'event_id -> event_id
            ).executeUpdate()
          }
        }
    true
  }
}
