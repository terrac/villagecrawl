package gwt.server;

import gwt.server.datamodel.FileResource;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public class ServeResource extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		FileResource resource;
		
		resource = SDao.getFResourceDao().getRN(request.getRequestURI());
		if(resource == null){
			return;
		}
		response.setContentType(resource.getContentType());
		
		
		response.getOutputStream().write(resource.getData().getBytes());
	}
}
