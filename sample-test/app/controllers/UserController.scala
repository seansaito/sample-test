package controllers

import play.api._
import play.api.mvc._

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import com.github.nscala_time.time.Imports._

/*Overview of users_events.spec.js

  Request
    endpoint: /api/users/events
    method: GET
    contentType: URLENCODED
    params: from (Date), offset (int, default=0), limit (int, default=1)

  Response
    contentType: JSON
    data:
      code: int, events: [{id: int, name, string, start_date: date,
                          company: {id: int, name: string}}]

  Tests

  Without from parameter
    Call API
    Params: none
    Response: badRequest

  Wrong limit
    Call API
    Params: from="2015-04-01", offset=0, limit=0
    Response: badRequest

  From 2015-04-01
    Call API
    Params: from="2015-04-01"
    Response: 200
      data.events.length = 6
      data.events[0].name = "Givery Event1"

  From 2015-04-18
    Call API
    Params: from="2015-04-18"
    Response: 200
      data.events.length = 5
      data.events[0].name = "Google Event1"

  Specify offset
    Call API
    Params: from="2015-04-01", offset=2
    Response: 200
      data.events.length = 4

  Specify limit
    Call API
    Params: from="2015-04-01", limit=1
    Response: 200
      data.events.length = 1
      data.events[0].name = "Givery Event1"

*/

/*Overview of users_reserve.spec.js

  Request
    endpoint: /api/users/reserve
    method: POST
    contentType: URLENCODED
    params: token (string), event_id (int), reserve (boolean)

  Response
    contentType: JSON
    data:
      code: int, message: string

  Tests

  Test1 - Without token
    Call API
    Params: token = null, event_id = 1, reserve = true
    Response: 401

  Test2 - With company user
    Login
    Params: email = "givery@test.com", password = "password"
    Respnose: 200, data
    Call API
    Params: token = token, event_id = 1, reserve = true
    Response: 401

  Test3 - With student user
    Login
    Params: email = "user1@test.com", password = "password"
    Response: 200, data

    Can reserve event
    Call API
    Params: token = token, event_id = 1, reserve = true
    Response: 200

    Can't reserve an already-reserved event
    Call API
    Params: token = token, event_id = 1, reserve = true
    Response: 501

    Can reserved a reserved event
    Call API
    Params: token = token, event_id = 1, reserve = false
    Response: 200

    Can't unreserve a non-reserved event
    Call API
    Params: token = token, event_id = 1, reserve = false
    Response: 502
*/

object UserController extends Controller {

  def events(from: String, offset: Option[Int], limit: Option[Int]) = Action {

  }

  def reserve(token: String, id: Int, reserve: Boolean) = Action {

  }

}
