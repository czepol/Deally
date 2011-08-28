package pl.deally {
package model {

import scala.xml.NodeSeq
import net.liftweb.mapper._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.http.{S,RedirectResponse,RedirectState,RedirectWithState}
import S._
import pl.deally.lib._

object Page extends Page with LongKeyedMetaMapper[Page] 
with CRUDify[Long, Page] {
  override def dbTableName = "pages"
  
  override def pageWrapper(body: NodeSeq) =
    <lift:surround with="admin" at="content">{body}</lift:surround>
  override def calcPrefix = List("admin",_dbTableNameLC)
  override def showAllMenuLocParams = LocGroup("admin") :: Nil
  override def createMenuLocParams  = LocGroup("admin") :: Nil
  override def viewMenuLocParams    = LocGroup("admin") :: Nil
  override def editMenuLocParams    = LocGroup("admin") :: Nil
  override def deleteMenuLocParams  = LocGroup("admin") :: Nil
  
  override def _showAllTemplate = CrudifyHelpers.showAllTemplate
  override def _viewTemplate = CrudifyHelpers.viewTemplate
  override def _createTemplate = CrudifyHelpers.createTemplate
  override def _deleteTemplate = CrudifyHelpers.deleteTemplate
  override def _editTemplate = CrudifyHelpers.editTemplate
  
  override def fieldsForList: List[MappedField[_, Page]] = 
    List(id,title,createdAt,updatedAt,published)
  
  val superUserLoggedIn = If(User.superUser_? _, CrudifyHelpers.loginAndComeBack _)
  override protected def addlMenuLocParams: List[Loc.AnyLocParam] = superUserLoggedIn :: Nil
  
}

class Page extends LongKeyedMapper[Page] with IdPK with CreatedUpdated {

  def getSingleton = Page
  
  object title extends MappedString(this, 128)
  object slug extends MappedString(this, 64)
  object content extends MappedText(this)
  object published extends MappedBoolean(this)
   
}

}
}
