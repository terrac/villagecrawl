package gwt.server;

import gwt.server.datamodel.FileResource;
import gwt.server.datamodel.FResourceManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("createimage")
public class CreateResource extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PersonLoginInfo info =LoginService.login(req.getRequestURI(),res.getWriter());
		
		if(!info.loginInfo.isLoggedIn()){
			res.getWriter().write("You must login to use this service");
			return;
		}
		
		String name  = null;
		res.getWriter().write("<form action=\"/createresource\" method=\"post\" enctype=\"multipart/form-data\">\r\n" + 
//				"    <input name=\"name\" type=\"text\" value=\"\"> <br/>\r\n" + 
				"    <input name=\"imageField\" type=\"file\" size=\"30\"> <br/>\r\n" + 
				"    <input name=\"Submit\" type=\"submit\" value=\"Sumbit\">\r\n" + 
				"</form>\r\n" + 
				"");
		
//		PersistenceManager persistenceManager = PMF.get()
//				.getPersistenceManager();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			ServletFileUpload upload = new ServletFileUpload();
			if(!upload.isMultipartContent(req)){
				return;
			}
			// System.out.println("no files?");
			FileItemIterator iterator = upload.getItemIterator(req);

			InputStream stream = null;
			String contentType = null;

			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();

				if (item.isFormField()) {

				} else {
					System.out.println(item.getName());
					if (item.getFieldName().equals("imageField")) {
						stream = item.openStream();
						contentType = item.getContentType();
						name = item.getName();
						int len;
						byte[] buffer = new byte[8192];
						while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
							out.write(buffer, 0, len);
						}
					}
				}
			}
			
			
			if (name != null && stream != null
					&& contentType != null) {
		

				
				FileResource image = new FileResource("/resource/"+info.person.getDisplayName()+"/"+name, contentType, out
						.toByteArray());
				Key<FileResource> key=SDao.getFResourceDao().put(image);
				FResourceManager frm = info.person.getResourceManager();
				frm.frlist.add(key);
				SDao.getFResourceManagerDao().put(frm);

			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}