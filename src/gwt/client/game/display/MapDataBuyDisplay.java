package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.ui.AddTogetherPeople;
import gwt.client.game.vparams.ui.CloneDeposit;
import gwt.client.game.vparams.ui.Score;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.output.OutputDirector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class MapDataBuyDisplay extends UIVParams{

	public MapDataBuyDisplay() {
		// TODO Auto-generated constructor stub
	}
	HorizontalPanel hp ;
	
	
	
	public List<MapData> getMDs() {
		return  getListCreate(VConstants.list);
	}
	public String getType() {
		return getS(VConstants.type);
	}
	

	public MapDataBuyDisplay(String type,String ... items) {
		put(VConstants.type,type);

		put(VConstants.list,items);
		
		
	}
	ListBox lb ;
	Map<String,MapData> mdMap = new HashMap();
	
	Score score;
	
	
	@Override
	public void execute(final Map<String, Object> map) {
	
		
		
		
		score.execute(map);
		
		

		//add random items to listbox, add a selection property
	}
	
	@Override
	public void setList(List l) {
		lb.clear();
		for(MapData i :(List<MapData>) l){
			
			String itemDisplay = i.getDescription();
			
			lb.addItem(itemDisplay);
			mdMap.put(lb.getItemText(lb.getItemCount()-1), i);
			
		}
		score.execute(EntryPoint.game.getPBase(VConstants.human));
	}
	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return hp;
	}
	
	@Override
	public MapDataBuyDisplay clone() {
		
		return new MapDataBuyDisplay().copyProperties(this);
	}
	@Override
	public void init() {
		hp= new HorizontalPanel();
		lb  = new ListBox(true);
		score  = new Score(getType(),getType());
		
		hp.add(lb);		
		hp.add(score.getWidgetAndInit());
		lb.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				int sel=lb.getSelectedIndex();
				
				MapData md = mdMap.get(lb.getItemText(sel));
				PBase pBase = EntryPoint.game.getPBase(VConstants.human);
				Integer gold =(Integer)pBase.getInt(getType());
				gold -= md.getInt(VConstants.value);
				if(gold < 0){
					Window.alert("not enough gold");
					return;
				}
				
				
				lb.removeItem(sel);
				
				pBase.put(getType(),gold);
				score.execute(pBase);
				
				//subtract gold, add item to current person
				
				LivingBeing lb=EntryPoint.game.getHtmlOut().getCurrentOrLastSelectedPerson();
				if(md instanceof Item){
					//lb.getItems().put(md.clone());
					lb.addItems(md.clone());
				}
				if(md instanceof LivingBeing){
					HashMapData hmd=lb.getParent().getParent().getNearestEmpty(lb);
					hmd.putLivingBeing((LivingBeing) md);
				}
			}
		});
	}
}
