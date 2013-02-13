package gwt.client.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditList extends VerticalPanel{

	List<HasText> widgetList = new ArrayList<HasText>();
	public TextBox addListItem(String desc, String name){
		HorizontalPanel p = new HorizontalPanel();
		add(p);
		p.add(new Label(desc));
		TextBox text = new TextBox();
		text.setText(name);
		p.add(text);
		widgetList.add(text);
		return text;
	}

	public void addButton(String string,ClickHandler handler) {
		Button but = new Button();
		add(but);
		but.setText(string);
		but.addClickHandler(handler);
		
	}

	public void addSuggest(String desc, SuggestOracle oracle, String canonicalName) {
		HorizontalPanel p = new HorizontalPanel();
		add(p);
		p.add(new Label(desc));
		SuggestBox sb = new SuggestBox(oracle);
		
		sb.setText(canonicalName);
		p.add(sb);
		widgetList.add(sb);
		//sb.getTextBox().addChangeHandler(handler);
	}

	public void addBooleanItem(String desc, boolean b) {
		HorizontalPanel p = new HorizontalPanel();
		add(p);
		p.add(new Label(desc));
		TextBox text = new TextBox();
		text.setText(""+b);
		p.add(text);
		widgetList.add(text);
	}

	public void addIntegerItem(String desc, int i) {
		HorizontalPanel p = new HorizontalPanel();
		add(p);
		p.add(new Label(desc));
		TextBox text = new TextBox();
		text.setText(""+i);
		p.add(text);
		widgetList.add(text);
	}
	
	public void setListItems(Object ...strings){
		for(int a = 0; a < strings.length; a++){
			widgetList.get(a).setText(""+strings[a]);
		}
	}

	public void addLabel(String name, String lab) {
		HorizontalPanel p = new HorizontalPanel();
		add(p);
		p.add(new Label(name));
		TextBox text = new TextBox();
		text.setText(lab);
		p.add(text);
		widgetList.add(text);
	}
	
}
