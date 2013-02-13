package gwt.server;

import gwt.client.game.buildgame;
import gwt.server.datamodel.GUser;
import gwt.server.datamodel.ServerGame;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.JsonData;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("displaypersongames")
public class EditJson extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PersonLoginInfo info = LoginService.login(request.getRequestURI(),
				response.getWriter());

		if (!info.loginInfo.isLoggedIn()) {
			response.getWriter().write("You must login to use this service");
			return;
		}
		long id;
		JsonData jd;


		if (request.getParameter("new") != null) {

			

			try {
				id = Long.parseLong(request.getParameter("gkey"));
			} catch (NumberFormatException e) {
				response.getWriter().write("invalid format for game key");
				return;
			}
			jd = new JsonData("new",true,id);
			jd.setData(new Blob("aoeu".getBytes()));
			Key k = SDao.getJsonDataDao().put(jd);
			
			ServerGame sg = SDao.getServerGameDao().getRN(id);
			sg.addOther(k);
			SDao.getServerGameDao().put(sg);
			id = k.getId();
			jd.id = k.getId();
		} else {
			try {
				id = Long.parseLong(request.getParameter("jsonkey"));
			} catch (NumberFormatException e) {
				response.getWriter().write("invalid format for key");
				return;
			}
			jd = SDao.getJsonDataDao().getRN(id);
			id = jd.id;	
		}
		
		String parameter = request.getParameter("jsonname");
		if(parameter != null){
			if(info.person == null||!info.person.id.equals(id)){
				return;
			}
			jd.name = parameter;
			jd.setData(new Blob(request.getParameter("text").getBytes()));
			SDao.getJsonDataDao().put(jd);
		}
		
		String desc="<script type=\"text/javascript\">\r\n" + 
				"va1 = "+new String(jd.getData().getBytes())+ ";\r\n" + 
				"\r\n" +
				"function validateForm()\r\n" + 
				"{" +
				"var x=document.forms['f1']['j1'].value;\r\n" + 
				"try{" +
				"JSON.parse(x) == null;}\r\n" + 
				"catch (err)  {\r\n" + 
				"  alert(\"invalid json\"+err);\r\n" + 
				"  return false;\r\n" + 
				"  }\n" +
				"}\r\n" +
				" document.forms['f1']['j1'].value=JSON.stringify(va1,null,5);" +
				//" document.forms[f1][j1].value='aoeu';" +
				"</script>" ;
		
		String form="<form name=f1 onsubmit=\"return validateForm();\" METHOD=POST action=/editjson> <input type=hidden name=jsonkey value="+jd.id+">" +
				"name: <input type=text name=jsonname value=\""+jd.getName()+"\" /><br />" +
				"<textarea name=j1 rows=60 cols=200  ></textarea><br><input type=submit value=Submit></form>"+
				"";
		
		//on submit make sure to do a stringify and then a document submit 
		
		
		
		
//		
//		"<script type=\"text/javascript\">document.write(\"" +

//				"+JSON.stringify("++")+" +
//						"<br><input type=submit value=Submit></form>" +
//						"\")</script>";
		response.getWriter().write(form);
		response.getWriter().write(desc);
		// display an edit box and a name text field
//		response.getWriter().write(
//				"<form METHOD=POST action=/editjson> <input type=hidden name=jsonkey value="+jd.id+">");
//		response
//				.getWriter()
//				.write(
//						"name: <input type=\"text\" name=jsonname value=\""+jd.getName()+"\" /><br />");
//		response.getWriter().write(
//				"<textarea name=text rows=60 cols=200  >"
//						+ new String(jd.getData().getBytes()) + " </textarea>");
//		response.getWriter().write("<br><input type=submit value=Submit></form>");

	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
