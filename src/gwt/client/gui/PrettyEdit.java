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
			
			goa.getObject("jsondata", Window.Location.getParameter("jsonkey"),new AsyncCallback<Map<String,IClientObject>>() {
				
				@Override
				public void onSuccess(Map<String, IClientObject> result) {
					JSONObject jo;
					try {
						jo = (JSONObject) JSONParser.parseStrict(((CString)result.get(Window.Location.getParameter("jsonkey"))).value);
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
					tech = jdPBase.getPBase(VConstants.technology).getPBase(VConstants.map);
					
					VerticalPanel vp = new VerticalPanel();
					rootPanel.add(vp);
					
					
					for(Object o : tech.getObjMap().values()){
						
						if(o instanceof PBase){
							displayTechs(vp, (PBase) o);
						} else {
							for(PBase p : ((List<PBase>)o)){
								displayTechs(vp, p);
							}
						}
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
								postTechs(ent);
							}
							
							String gkey=Window.Location.getParameter("jsonkey");
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
				
				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					Window.alert(caught.getMessage()+caught.getStackTrace());
					
				}
			});
			
					}
	public static void displayTechs(VerticalPanel vp, PBase t) {
		HorizontalPanel hp = new HorizontalPanel();
		vp.add(hp);
		//list name
		hp.add(new Label(t.getS(VConstants.name)));
		//for size put a text box accepting ints
		TextBox tb = new TextBox();
		tb.setText(""+t.get(VConstants.size));
		CheckBox cb = new CheckBox("Enable");
		cb.setValue(t.getB(VConstants.enabled));
		hp.add(tb);
		hp.add(cb);
		Map<String,Object> hashMap = new HashMap<String, Object>();
		techRep.add(hashMap);
		hashMap.put(VConstants.size, tb);
		hashMap.put(VConstants.enabled, cb);
		hashMap.put(AttachUtil.OBJECT, t);
	}
	public static void postTechs(Map<String, Object> m) {
		PBase p = (PBase) m.get(AttachUtil.OBJECT);
		TextBox tb = (TextBox) m.get(VConstants.size);
		p.put(VConstants.size, Double.parseDouble(tb.getText()));
		CheckBox cb = (CheckBox)m.get(VConstants.enabled);
		p.put(VConstants.enabled, cb.getValue());
	}

}
