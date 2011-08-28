package pl.deally {
package lib {

import net.liftweb.http._
import net.liftweb.http.S._
import net.liftweb.util._
import pl.deally.model.User
import Helpers._

object CrudifyHelpers {

  def nextWord = S.??("Next")  
  def previousWord = S.??("Previous") 

  def showAllId = "show_all"  
  def showAllClass = "show_all data display" 

  def showAllTemplate =
    <div class="x12">
      <lift:crud.all>  
        <table id={showAllId} class={showAllClass}>  
          <thead>  
            <tr>  
              <crud:header_item><th><crud:name/></th></crud:header_item>  
              <th> </th>  
              <th> </th>  
              <th> </th>  
            </tr>  
          </thead>  
          <tbody>  
            <crud:row>  
              <tr class="gradeA">  
                <crud:row_item><td><crud:value/></td></crud:row_item>  
                <td><a crud:view_href="">{S.??("View")}</a></td>  
                <td><a crud:edit_href="">{S.??("Edit")}</a></td>  
                <td><a crud:delete_href="">{S.??("Delete")}</a></td>  
              </tr>  
            </crud:row>  
          </tbody>  
          <tfoot>  
            <tr>  
              <td colspan="3"><crud:prev>{previousWord}</crud:prev></td>  
              <td colspan="3"><crud:next>{nextWord}</crud:next></td>  
            </tr>  
          </tfoot>  
        </table>  
      </lift:crud.all>
    </div>

  def viewId = "view_page"  
  def viewClass = "view_class"  
     
  def viewTemplate =
    <div class="x12">
      <lift:crud.view>  
        <table id={viewId} class={viewClass}>  
          <crud:row>  
            <tr>  
              <td><crud:name/></td>  
              <td><crud:value/></td>  
            </tr>  
          </crud:row>  
        </table>  
      </lift:crud.view>
    </div> 

  def editId = "edit_page"  
  def editClass = "edit_class"  
  
  def editTemplate =  
    <div class="x12">
      <lift:crud.edit form="post">  
        <table id={editId} class={editClass}>  
          <crud:field>  
            <tr>  
              <td>  
                <crud:name/>  
              </td>  
              <td>  
                <crud:form/>  
              </td>  
            </tr>  
          </crud:field>  
      
          <tr>  
            <td> </td>  
            <td class="buttonrow"><crud:submit>{S.?("Edit")}</crud:submit></td>  
          </tr>  
        </table>  
      </lift:crud.edit>  
    </div>
  
  def createId = "create_page"  
  def createClass = "create_class"
  
  def createTemplate =
    <div class="x12">
      <lift:crud.create form="post">  
        <table id={createId} class={createClass}>  
          <crud:field>  
            <tr>  
              <td>  
                <crud:name/>  
              </td>  
              <td>  
                <crud:form/>  
              </td>  
            </tr>  
          </crud:field>  
      
          <tr>  
            <td> </td>  
            <td><crud:submit>{S.?("Create") }</crud:submit></td>  
          </tr>  
        </table>  
      </lift:crud.create> 
    </div> 

  def deleteId = "delete_page"  
  def deleteClass = "delete_class"      
  
  def deleteTemplate =
    <div class="x12">
      <lift:crud.delete form="post">  
        <table id={deleteId} class={deleteClass}>  
          <crud:field>  
            <tr>  
              <td>  
                <crud:name/>  
              </td>  
              <td>  
                <crud:value/>  
              </td>  
            </tr>  
          </crud:field>  
      
          <tr>  
            <td> </td>  
            <td><crud:submit>{S.?("Delete") }</crud:submit></td>  
          </tr>  
        </table>  
      </lift:crud.delete>
    </div>
    
  def loginAndComeBack = {
    if(User.loggedIn_?) {
      S.error("Brak uprawnień")
      RedirectResponse("/")
    } else {
      RedirectWithState("/admin/login", RedirectState(() => User.loginReferer(S.uri))) 
    }
  }
}

}
}
