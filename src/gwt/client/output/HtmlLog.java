package gwt.client.output;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;




public class HtmlLog implements ILog{
	HTML log ;
	public  void log(String type,String message){
		if(log == null){
			RootPanel rootPanel = RootPanel.get("log");
			log = new HTML();
			rootPanel.add(log);
			
		}
		//log.setHTML(log.getHTML()+"<br/>"+type +" "+message);
		
		
	}
	public  void run(){}
	
}
