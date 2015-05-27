package controllers

import play.api._
import play.api.mvc._

import play.api.libs.json._
import play.api.libs.iteratee.Enumerator

import models.User

/*
  Manager for handling logins.
*/
object LoginManager extends Controller {

  /*
  * Login the user. Returns a 500 Error if it fails.
  * @params: email[String], password[String]
  */
  def login(email: String, password: String) = Action { request =>
    val md = java.security.MessageDigest.getInstance("SHA-1")
    val ha = new sun.misc.BASE64Encoder().encode(md.digest(password.getBytes))

    var result = User.find(email, ha)

    result(0) match {
      case null => Result(
        header = ResponseHeader(500),
        body = Enumerator("Authentication failed".getBytes())
      )
      case User(id, name, password, email, group_id) =>
        var token = name+email+group_id
        Ok(JsObject(Seq("code" -> JsNumber(200), "token" -> JsString(token),
            "user" -> Json.toJson(result(0))))).withSession(
              token -> email
      )
    }
  }
}

/*Overview of login.spec.js

  Request
    endpoint: /api/auth/login
    method: POST
    contentType: URLENCODED
    params: email (String), password (String)

  Response
    contentType: JSON
    data:
      code: int, token: string, user: {id: int, name: string, group_id: int}

  Tests

  Wrong password
    Call API
    Params: email="user1@test.com", password="wrong"
    Response: 500

  Wrong username
    Call API
    Params: email="unknown@test.com", password="password"
    Response: 500

  Success
    Call API
    Params: email="user1@test.com", password="password"
    Response: 200
      data.token.length > 0
      data.user.name = "John Smith"
      data.user.group_id = 1
*/
