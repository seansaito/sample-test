package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import play.api.libs.json.Json
import play.api.libs.json._

case class User(id: Int, name: String, password: String, email: String, group_id: Int)

object User {

  /* Row parser for SQL query */
  val user = {
    get[Int]("id") ~
    get[String]("name") ~
    get[String]("password") ~
    get[String]("email") ~
    get[Int]("group_id") map {
      case id~name~password~email~group_id => User(id, name, password, email, group_id)
      case _ => null
    }
  }

  /* Implementation of Writes formatter for User. */
  implicit object UserFormat extends Format[User] {

    def writes(user: User): JsValue = {
      val userSequence = Seq(
        "id" -> JsNumber(user.id),
        "name" -> JsString(user.name),
        "group_id" -> JsNumber(user.group_id)
      )
      JsObject(userSequence)
    }

    // To satisfy API specifications
    def reads(json: JsValue): JsResult[User] = {
      JsSuccess(User(0, "", "", "", 0))
    }
  }

  def find(email: String, password: String) : List[User] = {
    DB.withConnection {implicit c =>
        SQL("SELECT * FROM users WHERE email = {email} AND password = {password}").on(
        'email -> email,
        'password -> password
      ).as(user *)
    }
  }

  /* Used when retrieving user data from session token */
  def findByEmail(email: String) : List[User] = {
    DB.withConnection{ implicit c =>
      SQL("SELECT * FROM users WHERE email = {email}").on(
        'email -> email
      ).as(user *)
    }
  }
}
