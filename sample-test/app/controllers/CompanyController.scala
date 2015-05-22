package controllers

import play.api._
import play.api.mvc._

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import com.github.nscala_time.time.Imports._

/*Overview of companies_events.spec.js

  Request
    endpoint: /api/companies/events
    method: POST
    contentType: URLENCODED
    params: token (String), from (Date), offset
      (int, default=0), limit (int, default=1)

    Example query: SELECT * FROM events INNER JOIN users
    ON user.id=[user's id stored] AND events.user_id=users.id LIMIT limit
    OFFSET offset

  response
    contentType: JSON
    data:
      code (int), events ([id:int, name:string, start_date:datetime,
                            number_of_attendees: int])

  Tests

  Test1 - Try to access without a token
    Call API
    Params: token = null, from: "2015-04-01"
    Response: 401 code
    ==> Check from the url params that token is null, then return 401

  Test2 - Try to access as a student
    Login: email, password ==> success, return the user + token
    Call API
    Params: token = token, from: ~~~
    Response: 401
    ==> Look up the user and see that he/she is a student (not a company)
        then return 401

  Test3 - Try to access as a company
    Login: email, password ==> success, return the user + token
    Without From Parameter
    Call API
    Params: token = token, from: null
    Response: badRequest

    Wrong limit
    Call API
    Params: token = token, from: "2015-04-01", offset: 0, limit: 0
    Response: badRequest

    From 2015-04-01
    Call API
    Params: token = token, from: "2015-04-01"
    Response: 200
      data.events.length = 2
      data.events[0].name = "Givery Event1"
      data.events[0].number_of_attendees = 2
      data.events[1].number_of_attendees = 0

    From 2015-05-01
    Call API
    Params: token = token, from: "2015-05-01"
    Response: 200
      data.events.length = 0

    Specify offset
    Call API
    Params: token = token, from: "2015-04-01", offset: 3
    Response: 200
      data.events.length = 0

    Specify offset and limit
    Call API
    Params: token = token, from: "2015-04-01", offset: 1, limit: 3
    Response: 200
      data.events.length = 1
      data.events[0].name = "Givery Event2"
*/


object CompanyController extends Controller {

  def events(token: String, from: String, offset: Option[Int], limit: Option[Int]) = Action {
    if (token==null) {
      SimpleResult (
        header = ResponseHeader(401)
      )
    }

  }


}
