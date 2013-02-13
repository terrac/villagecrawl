package gwt.client.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditPanel<T extends Widget> extends VerticalPanel {
	//holds a list of children
	public List<String>  related = new ArrayList();{
		related.add("aoeu");
	}
	
	//and a main widget T
	public T widget;

	
	
	public EditPanel() {

	

	}
	public void init(){
		//on init add a horizontal panel of children and the widget
		
		HorizontalPanel hp = new HorizontalPanel();
		for(String rel : related){
			//evetually just be color coded
			hp.add(new Button(rel));
		}
		add(hp);
		add(widget);
	}
	
	
}
