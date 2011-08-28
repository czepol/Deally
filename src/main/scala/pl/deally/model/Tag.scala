package pl.deally {
package model {

import scala.xml.{NodeSeq}
import net.liftweb.http._
import net.liftweb.mapper._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import net.liftweb.common.Full
import pl.deally.lib._
import S._

object Tag extends Tag with LongKeyedMetaMapper[Tag] 
with CRUDify[Long,Tag] {

  override def dbTableName = "tags"
  override def fieldOrder= List(name)

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

  val superUserLoggedIn = If(User.superUser_? _, CrudifyHelpers.loginAndComeBack _)
  override protected def addlMenuLocParams: List[Loc.AnyLocParam] = superUserLoggedIn :: Nil

}

class Tag extends LongKeyedMapper[Tag] with IdPK with ManyToMany {

  def getSingleton = Tag

  object name extends MappedString(this,128) {
    override def dbIndexed_? = true
		override def dbNotNull_? = true
		override def setFilter = List(x=> x.trim.toLowerCase)
		override def displayName = ?("name")
  }
  
  object slug extends MappedPoliteString(this,128) {
    override def dbIndexed_? = true
		override def dbNotNull_? = true
    override def validations = valUnique("Slug must be unique!") _ :: super.validations
		override def setFilter = trim _ :: toLower _ :: HtmlHelpers.slugify _ :: super.setFilter
    override def displayName = ?("slug")
  }
  
  object deals extends MappedManyToMany(
    DealTag, DealTag.tagid, DealTag.dealid, Deal)
    
  def autocompleteJArray(q: String): JValue = {
    val input = q.toLowerCase.replaceAll("\"", "").replaceAll("'", "")+"%"
    val query = "SELECT name FROM tags WHERE lower(name) LIKE '%s'".format(input)
    val results = Tag.findAllByInsecureSql(query, IHaveValidatedThisSQL("czepol", "2011-08-17"))
    var list = List[String]()
    for(tag <- results) {
      list = list ::: List(tag.name.is)
    }
    val json: JValue = list
    json
  }
  
  def autocompleteJArrayAllTags: JValue = {
    val results = Tag.findAll
    var list = List[String]()
    for(tag <-results) {
      list = list ::: List(tag.name.is)
    }
    val json: JValue = list
    list
  }
  
  def tagsToJArrayString(deal: Deal): String = {
    var tags = deal.tags.toList
    var list: List[String] = Nil
    for(tag <- tags) {
      list = list ::: List(tag.name.is)
    }
    val json: JValue = list
    pretty(JsonAST.render(json))
  }
  
  def tagsToDealJArray(id: Long): JValue = {
    Deal.find(By(Deal.id, id)) match {
      case Full(deal) => {
        var tags = deal.tags.toList
        var list: List[String] = Nil
        for(tag <- tags) {
          list = list ::: List(tag.name.is)
        }
        val json: JValue = list
        json
      }
      case _ => "[]"
    }
  }
  
  def tagsToString(deal: Deal): String = { 
    var str = tagsToJArrayString(deal)
    if(str.startsWith("[") && str.endsWith("]")) {
      str = str.substring(1, str.length-1)
    }
    str = str.replaceAll("\"", "")
    str = str.replaceAll(",", ", ")
    str
  }
}

}
}
