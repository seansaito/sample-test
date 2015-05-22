package controllers

import play.api._
import play.api.mvc._

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import com.github.nscala_time.time.Imports._

/*Overview of login.spec.js

  Request
    endpoint: /api/auth/login
    method: POST
    contentType: URLENCODED
    params: email (String), password (String)

    Example query:
    SELECT * FROM users WHERE email=[email] AND password = sha1([password])

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

object LoginManager extends Controller {

  def login(email: String, password: String) = Action {

  }

}
