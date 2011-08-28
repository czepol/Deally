package pl.deally {
package snippet {

import scala.xml.{NodeSeq, Unparsed}
import net.liftweb.mapper._
import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.common.{Full}
import pl.deally.model.Page

import Helpers._

class Pages {
  
  def render(in: NodeSeq): NodeSeq = {
    val slug = S.param("slug").map(_.toString) openOr ""
    Page.find(By(Page.slug, slug.toString),By(Page.published, true)) match {
      case Full(page) => {
        bind("page", in, 
          "title" -> page.title,
          "content" -> <xml:group>{Unparsed(page.content)}</xml:group>
        )
      }
      case _ => S.redirectTo("/error")
    }
  }
  
}

}
}
