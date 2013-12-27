package gwt.client.gui;

import gwt.client.game.AttachUtil;
import gwt.client.game.buildgame;
import gwt.client.main.Game;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.rpc.GetObject;
import gwt.client.rpc.GetObjectAsync;
import gwt.client.rpc.SendObject;
import gwt.client.rpc.SendObjectAsync;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.CString;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The idea is to pop out some text boxes and allow people to create different
 * simulations
 * @author terrau
 *
 */
public class PrettyEdit {
	static PBase tech;
	static String name;
	static PBase jdPBase = new PBase();
	static List<Map<String,Object>> techRep = new ArrayList();
	static GetObjectAsync goa = GWT.create(GetObject.class);
	static RootPanel rootPanel;
	public static void doPage() {
			rootPanel = RootPanel.get("prettyEdit");
			if(rootPanel == null){
				return;
			}
			//Window.Location.getParameter("jsonkey")
			final String jsonkey=rootPanel.getElement().getAttribute("jsonkey");
			try {
				Integer.parseInt(jsonkey);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			
			goa.getObject("jsondata", jsonkey, new AsyncCallback<Map<String,IClientObject>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					Window.alert(caught.getMessage()+caught.getStackTrace());
	
				}

				@Override
				public void onSuccess(Map<String, IClientObject> result) {
					JSONObject jo;
					try {
						jo = (JSONObject) JSONParser.parseStrict(((CString)result.get("jsondata")).value);
					} catch (Exception e) {
						Window.alert(e.getMessage());
						e.printStackTrace();
						return;
					}
					jdPBase=(PBase) buildgame.addjson(jdPBase, jo,false);
					if(!jdPBase.containsKey(VConstants.technology)){
						return;
					}
					name = jdPBase.getS(VConstants.name);
					tech = jdPBase.getPBase(VConstants.technology).getPBase(VConstants.main);
					
					VerticalPanel vp = new VerticalPanel();
					rootPanel.add(vp);
					
					
					for(String n : tech.getObjMap().keySet()){
						
						double v = tech.getDouble(n);
						displayTechProperty(vp, n, v);
						//add an enable/disable checkbox
						
						//other stuff
					}
					Button b = new Button("submit");
					vp.add(b);
					b.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							SendObjectAsync soa= GWT.create(SendObject.class);
							JsonData jd = new JsonData(name);
							int count = 0;
							for(Map<String, Object> ent :techRep){
								postTechs(ent,tech);
							}
							
							String gkey=jsonkey;
							jdPBase.put(VConstants.classname, Game.class.getName());
							jd.setData(jdPBase.export());
							soa.sendObjectDefault(jd, gkey,"actions",  new AsyncCallback<Void>() {
								
								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									Window.alert("success");
									Window.Location.reload();
								}
								
								@Override
								public void onFailure(Throwable caught) {
									caught.printStackTrace();
									// TODO Auto-generated method stub
									Window.alert("failed");
									
								}
							});
						}
					});
					
				}
			});
			
					}
	public static void displayTechProperty(VerticalPanel vp, String name, double amount) {
		HorizontalPanel hp = new HorizontalPanel();
		vp.add(hp);
		//list name
		hp.add(new Label(name));
		//for size put a text box accepting ints
		TextBox tb = new TextBox();
		tb.setText(""+amount);
		hp.add(tb);
		Map<String,Object> hashMap = new HashMap<String, Object>();
		techRep.add(hashMap);
		hashMap.put(VConstants.name,name);
		hashMap.put(AttachUtil.OBJECT, tb);
	}
	public static void postTechs(Map<String, Object> m,PBase techProps) {
		String name= (String) m.get(VConstants.name);
		TextBox tb = (TextBox) m.get(AttachUtil.OBJECT);
		techProps.put(name, Double.parseDouble(tb.getText()));
		
	}

}
