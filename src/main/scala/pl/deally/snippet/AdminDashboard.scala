package pl.deally {
package snippet {

import scala.xml.{NodeSeq,Text}
import net.liftweb.http._
import net.liftweb.http.S._
import net.liftweb.util._
import net.liftweb.common.Full
import net.liftweb.mapper.By
import pl.deally.model.User
import Helpers._

class AdminDashboard {
  
  def dashboard(in: NodeSeq): NodeSeq = {
    if(!User.superUser_?) {
      S.redirectTo("/admin/login")
    } else {
      in
    }
  }
  
  def login(in: NodeSeq): NodeSeq = {
   
    def doLogin() {
      if(S.post_?) {
        println(S.param("username"))
        println(S.param("password"))
        S.param("username").flatMap(username => User.find(By(User.userName, username),
          By(User.superUser, true))) match {
          case Full(user) if user.validated &&  
            user.password.match_?(S.param("password").openOr("*")) => {
            User.logUserIn(user)
            println("Zalogowano!!!")
            S.redirectTo("/admin/index")
          }
          case _ => S.redirectTo("/error")
        }
      }
    }
    
    if(!User.superUser_?) {
      bind("form", in, "submit" -> SHtml.submit(??("log.in"), doLogin, ("class","btn btn-orange")))
    } else {
      S.redirectTo("/admin/index")
    }
  }
  
  def loggedInBox(in: NodeSeq): NodeSeq = {
    if(User.loggedIn_?) {
      User.currentUserId match {
        case Full(id) => {
          bind("user", in, "username" -> Text("Zalogowano jako " + User.getUsernameById(id.toLong)))
        }
        case _ => in
      }
    } else {
      in
    }
  }
  
  def logout = {
    User.logoutCurrentUser  
    S.redirectTo("/admin/login")
  }
  
}

}
}
