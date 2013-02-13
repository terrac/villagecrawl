package gwt.client.edit;

import gwt.client.EntryPoint;
import gwt.client.inter.TemplateIterate;
import gwt.client.item.Item;
import gwt.client.main.Carry;
import gwt.client.main.Game;
import gwt.client.main.MoveClosest;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.ActionHolder;
import gwt.client.main.base.OObject;
import gwt.client.main.base.Parameters;
import gwt.client.map.StringUtils;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class EditInit {
//	public static final String PERCENTAGEMAPLIST = "percentagemaplist";
//	private static final String OOBJECT = "oobject";
//	private static final String OOBJECTLIST = "oobjectlist";
//	private static final String ITEM = "item";
//	private static final String ITEMLIST = "itemlist";
//	private static final String TEMPLATE = "template";
//	private static final String TEMPLATETREE = "templatetree";
//	Map<String,Object> last= new HashMap<String, Object>();
//	Map<String,Point> fpos = new HashMap<String, Point>();{
//		fpos.put(OOBJECT,new Point(2,1));
//		fpos.put(OOBJECTLIST,new Point(2,0));
//		fpos.put(ITEM,new Point(1,1));
//		fpos.put(ITEMLIST,new Point(1,0));
//		fpos.put(TEMPLATE,new Point(0,1));
//		fpos.put(TEMPLATETREE,new Point(0,0));
//		
//	}
//	FlexTable flextable = new FlexTable();
//	
//	
//	
//	Game game;
//	{
//		game = EntryPoint.game;
//	}
//	public void init(String name,final Object whatIHave){
//		
//		//build a bunch of ifs to determine the type and then pull the appropriate  parameter object from a map
//		//then add a specific oobject type for knowing which type
//		
//		
//		
//		
//		
//		if(last.containsKey(name)&&last.get(name)== null&&whatIHave==null||whatIHave!=null&&whatIHave.equals(last.get(name))){
//			if(TEMPLATETREE.equals(name)){
//				close(TEMPLATE);
//			}
//			if(OOBJECTLIST.equals(name)){
//				close(OOBJECT);
//			}
//			if(ITEMLIST.equals(name)){
//				close(ITEM);
//			}
//			close(name);
//			return;
//		}
//		close(name);
//		Widget toAdd = null;
//		if(TEMPLATETREE.equals(name)){
//			
//			EditPanel ep = null;
//			toAdd = ep = new EditPanel();
//			
//			Tree tree = new Tree();
//			ep.widget = tree;
//			ep.init();
//			TreeItem ti=tree.addItem("root");
//			PTemplate root=game.getTemplateMap().get(VConstants.root);
//			
//			addPTemplates(ti, root);
//			tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
//				
//				@Override
//				public void onSelection(SelectionEvent<TreeItem> event) {
//					TreeItem ti=event.getSelectedItem();
//					
//					init(TEMPLATE,templateMap.get(ti));
//				}
//			});
//			
//		}
//		
//		if(TEMPLATE.equals(name)){
//			
//			VerticalPanel vp = new VerticalPanel();
//			toAdd = vp;
//			//if whatihave is oobject search through all templates for oobjects and appear them.			
//			//if at least one is missing then appear them all, otherwise close them
//			final List<PTemplate> plist = new ArrayList<PTemplate>();
//				
//			if(whatIHave instanceof OObject){
////				game.getTemplateMap().get(VConstants.root).iterate(new TemplateIterate() {
////					
////					@Override
////					public void execute(PTemplate pTemplate) {
////						for(ActionHolder ah:pTemplate.actionList){
////							if(ah.getAction().equals(whatIHave)){								
////								plist.add(pTemplate);
////							}
////							
////							
////						}
////					}
////				});
//			}
//			//if actionlist then just get the parent template
//			if(whatIHave instanceof PTemplate){
//				plist.add((PTemplate) whatIHave);
//			}
//			for(PTemplate pt : plist){
//				
//				EditList el = new EditList();
//				EditPanel ep = new EditPanel<Widget>();
//				vp.add(ep);
//				ep.widget = el;
//				ep.init();
//				el.addListItem("templatename", pt.getTemplateName());
//				
//				el.setBorderWidth(3);
//				
//				for(ActionHolder ah:pt.getActionList()){
//					
//					
//					String canonicalName = ah.getAction();
//				    el.addSuggest("oobj", oracle, canonicalName);
//				    //need to add states eventually
//					//ah.statesNeeded
//					
//					
//				}
//				//display a lis
//				
//			}
//			
//			//if template then open that specific template
//		}
//		
//		if(ITEMLIST.equals(name)){
//			//open or close item list that opens specific items as well
//			
//			toAdd = addDropDown(game.getItemMap(), ITEM);
//			
//		}
//		
//		if(ITEM.equals(name)){
//			VerticalPanel vp = new VerticalPanel();
//			toAdd = vp;
//			List<Item> ilist = new ArrayList<Item>();
//			if(whatIHave instanceof String){
//				
//				//if oobject then open items with that oobject in the build property
//				//then open like above
//				for(Item item : game.getItemMap().values()){
//					if(item.itemBuildAction.equals(whatIHave)){
//						ilist.add(item);
//					}
//				}
//			}
//			if(whatIHave instanceof Item){
//				ilist.add((Item) whatIHave);
//			}
//			for(Item item : ilist){
//				EditPanel ep = new EditPanel<Widget>();
//				vp.add(ep);
//				
//				EditList editlist = new EditList();
//				editlist.addLabel("name", item.getKey());
//				
//				editlist.addSuggest("makeaction", oracle, item.itemBuildAction);
//				ep.widget = editlist;
//				ep.init();
//				
//			}
//			
//			
//			//
//			
//			//if item then open that specific item
//		}
//		
//		if(OOBJECTLIST.equals(name)){
//			toAdd = addDropDown(game.getOOMap(), OOBJECT);
//			
//			
//		}
//		if(OOBJECT.equals(name)){
//			EditPanel ep = new EditPanel<Widget>();
//			toAdd = ep;
//			EditList editlist = new EditList();
//			Parameters pm=((OObject) whatIHave).getParameterTypes();
//			
//			
//			ep.widget = editlist;
//			ep.init();
//			if(pm == null){
//				return;
//			}
//			
//			pm.setupActionParams(editlist);
//			
//		}
//		
//		if(PERCENTAGEMAPLIST.equals(name)){
//			toAdd = addDropDown(game.getMap(VConstants.percentagemap), "percentagemap");
//			
//			
//		}
//		Point p=fpos.get(name);
//		flextable.setWidget(p.x, p.y, toAdd);
//		last.put(name, whatIHave);
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//	protected Widget addDropDown(final Map<String, ?> map, final String type) {
//		Widget toAdd;
//		EditPanel ep = new EditPanel<Widget>();
//		toAdd = ep;
//		ListBox lb = new ListBox();
//		for(String a : map.keySet()){
//			lb.addItem(a);
//		}
//		ep.widget = lb;
//		ep.init();
//		lb.addChangeHandler(new ChangeHandler() {
//			
//			@Override
//			public void onChange(ChangeEvent event) {
//				ListBox lb = (ListBox) event.getSource();
//				
//				init(type,map.get(lb.getItemText(lb.getSelectedIndex())) );
//			}
//		});
//		return toAdd;
//	}
//	
//	protected Widget addType( final String type) {
//		Widget toAdd;
//		EditPanel ep = new EditPanel<Widget>();
//		toAdd = ep;
//		EditList el = new EditList();
//		final TextBox tb=el.addListItem("name", "name1");
//		el.addButton("Add "+type,new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				game.getMap(type);
//				
//			}
//		});
//		
//		
//		ep.add(el);
//		ep.init();
//		
//		return toAdd;
//	}
//
//
//
//	
//
//	
//	
//
//
//
//
//
//
//
//	
//	private void close(String name) {
//		Point p=fpos.get(name);
//		if(!flextable.isCellPresent(p.x, p.y)){
//			return;
//		}
//		
//		last.remove(name);
//		flextable.clearCell(p.x, p.y);
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//	Map<TreeItem,PTemplate> templateMap = new HashMap();
//	protected void addPTemplates(TreeItem tp, PTemplate root) {
//		
//		//create a mapping of this edit list to the appropriate template, save then tally's them all
//		
//		
//
//		//TreeItem tp=ti.addItem(root.getTemplateName());
//		
//		templateMap.put(tp, root);
////		for(PTemplate pt:root.children){
////			TreeItem ch=tp.addItem(pt.getTemplateName());
////			addPTemplates(ch, pt);
////		}
//		
//	}
//
//	 MultiWordSuggestOracle oracle ;
//	public  MultiWordSuggestOracle getOracle(){
//		if(oracle == null){
//			oracle = new MultiWordSuggestOracle();
//			for(String a : game.getOOMap().keySet()){
//				oracle.add(a);
//			}
//		}
//		return oracle;
//	}
//	static String[] iniOobs = new String[]{Carry.class.getName(),MoveClosest.class.getName()};
//	
//
//	public void save(Object gamepiece,EditList editlist) {
//		if(gamepiece instanceof Item){
//			Item item = (Item) gamepiece;
//			//put editlist onto item
//			
//			
//			int time;
//			try {
//				time = Integer.parseInt(editlist.widgetList.get(2).getText());
//			} catch (NumberFormatException e) {
//				return;
//			}
//			String[] tomake=editlist.widgetList.get(3).getText().split(",");
//				
//			//item.putP(VConstants.name, name);
//			
//			
//			
////			item.itemBuildProperties.time = time;
////			item.itemBuildProperties.toMake = tomake;
//		}
//		if(gamepiece instanceof PTemplate){
//			//pull out name, actions are saved similarly though
//			
//			//changing name should be a nonissue aside from updating the dropdown box (which should be similar to item
//		}
//		if(gamepiece instanceof ActionHolder){
//			//changing name creates a new oobject and sets it on actionholder
//			
//			//otherwise just set variables using the oobject set params method
//		}
//	}
}
