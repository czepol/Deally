package pl.deally {
package model {

import scala.xml.{NodeSeq, Node}
import java.util.Locale
import net.liftweb.http._
import net.liftweb.mapper._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.common.{Full}
import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import pl.deally.lib._
import S._

object Merchant extends Merchant with LongKeyedMetaMapper[Merchant] 
with CRUDify[Long,Merchant] {

  override def dbTableName = "merchants"

  override def pageWrapper(body: NodeSeq) =
    <lift:surround with="admin" at="content">{body}</lift:surround>
  override def calcPrefix = List("admin",_dbTableNameLC)
  override def showAllMenuLocParams = LocGroup("admin") :: Nil
  override def createMenuLocParams  = LocGroup("admin") :: Nil
  override def viewMenuLocParams    = LocGroup("admin") :: Nil
  override def editMenuLocParams    = LocGroup("admin") :: Nil
  override def deleteMenuLocParams  = LocGroup("admin") :: Nil
  
  override def fieldsForList: List[MappedField[_, Merchant]] = 
    List(id,name,url,city,address)
  
  override def _showAllTemplate = CrudifyHelpers.showAllTemplate
  override def _viewTemplate = CrudifyHelpers.viewTemplate
  override def _createTemplate = CrudifyHelpers.createTemplate
  override def _deleteTemplate = CrudifyHelpers.deleteTemplate
  override def _editTemplate = CrudifyHelpers.editTemplate
  
  val superUserLoggedIn = If(User.superUser_? _, CrudifyHelpers.loginAndComeBack _)
  override protected def addlMenuLocParams: List[Loc.AnyLocParam] = superUserLoggedIn :: Nil

}

class Merchant extends LongKeyedMapper[Merchant] 
with CreatedUpdated with IdPK with OneToMany[Long,Merchant] {

  def getSingleton = Merchant

  object name extends MappedString(this, 128) {
    override def displayName = ?("name")
  }
  object url extends MappedString(this, 128) {
    override def displayName = ?("url")
  }
  object description extends MappedText(this) {
    override def displayName = ?("description")
  }
  object country extends MappedCountry(this) {
    override def displayName = ?("country")
    override def defaultValue = Countries(139)
  }
  object postalcode extends MappedPostalCode(this, country) {
    override def displayName = ?("postalcode")
  }
  object city extends MappedString(this, 64) {
    override def displayName = ?("city")
  }
  object address extends MappedString(this, 64) {
    override def displayName = ?("address")
  }
  object latitude extends MappedDouble(this) {
    override def displayName = ?("latitude")
  }
  object longitude extends MappedDouble(this) {
    override def displayName = ?("longitude")
  }
  object networked extends MappedBoolean(this) {
    override def displayName = ?("networked")
  }
    
  def toJson(id: Long, ver: String): JValue = {
    if(ver == "1.0") {
      Merchant.find(By(Merchant.id, id)) match {
        case Full(merchant) => {
            ("merchant" -> 
              ("name" -> merchant.name.toString) ~
              ("url" -> merchant.url.toString) ~
              ("description" -> merchant.description.toString) ~
              ("country" -> merchant.country.is.toString) ~
              ("postalcode" -> merchant.postalcode.toString) ~
              ("address" -> merchant.address.toString) ~
              ("latitude" -> merchant.latitude.is) ~
              ("longitude" -> merchant.longitude.is) ~
              ("networked" -> merchant.networked.is) 
            )
        }
        case _ => {
          ("merchant" -> 
            ("errors" -> 
              ("error" -> "Not found")
            )
          )
        }
      }
    } else {
      ("merchant" ->
        ("errors" ->
          ("error" -> "Bad API version")
        )
      )
    }
  }

  def toXml(id: Long, ver: String): Node = 
    Xml.toXml(Merchant.toJson(id,ver)).head

}

}
}
