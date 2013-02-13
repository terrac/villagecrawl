package gwt.client.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

import gwt.client.EntryPoint;
import gwt.client.game.display.UIVParams;
import gwt.client.game.vparams.ui.SelectAddItem;
import gwt.client.item.Item;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.AreaMap;
import gwt.client.map.MapData;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.client.output.html.Click;
import gwt.client.output.html.GCanvas;

public class ItemEquipCanvas extends UIVParams{

	SelectAddItem sai;
	ImageElement ie;
	{
//		ImageLoader.loadImages(new String[]{"/images/manikin.jpg"}, new ImageLoader.CallBack() {
//
//			@Override
//			public void onImagesLoaded(ImageElement[] imageElements) {
//				// TODO Auto-generated method stub
//
//				ie = imageElements[0];
//				
//				
//			}
//		});
	}
	

	public ItemEquipCanvas() {	
		
	}
	
	
	
	LivingBeing current;
	
	@Override
	public void execute(Map<String, Object> map) {
		current = (LivingBeing) map.get(AttachUtil.OBJECT);
		
	}



	public void update() {
		//canvas.clear();
		canvas.clear();
		canvas.getContext2d().drawImage(ie,0,0);
		
		for(Entry<String,Point> ent : typeLocation.entrySet()){
			
			MapData mapData = current.getAlterHolder().getMapData(ent.getKey());
			String text = null;
//			if(mapData != null){
//				text = mapData.getKey();
//			}
			
			//get specific selected person
			if(null == mapData){
				text = "/images/question.png";
			} else {
				text = mapData.getImage();
			}
			
			boolean loaded=EntryPoint.game.getHtmlOut().drawImage(canvas, ent.getValue().y, ent.getValue().x, text);
			if(!loaded){
				EntryPoint.game.getHtmlOut().loadMissing(this);
			}
		}
	}
	public GCanvas canvas;
	//use htmlout draw image at specific points that are tied to specific types
	
	//the items will be in a selectable hmd
	
	public Map<String,Point> typeLocation = new HashMap<String, Point>();
	
	

	@Override
	public Widget getWidget() {

		return canvas;
	}
	
	@Override
	public Object remove(String key) {
		//should not be a real remove
		for(Object o : current.getAlterHolder().getObjMap().values()){
			if(!(o instanceof MapData)){
				continue;
			}
			MapData md = (MapData) o;
			if(key.equals(md.getKey()) ){
				
				return current.getAlterHolder().get((String) md.get(VConstants.type));
			}
			
		}
		
		return null;
		//return current.getAlterHolder().remove(key);
	}
	@Override
	public void init() {
		canvas = new GCanvas(500, HtmlOut.imagesize * 11);
canvas.addDomHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				double distance = 1000;
				String cstring = null;
				for(Entry<String, Point> ent : typeLocation.entrySet()){
					double tdist=Point.distance(ent.getValue().getX() *HtmlOut.imagesize,ent.getValue().getY()*HtmlOut.imagesize, event.getX(), event.getY());
					if(tdist < distance){
						distance = tdist;
						cstring = ent.getKey();
					}
					
				}
				
				AttachUtil.runWithoutUpdates(AttachUtil.clickItem, current.getAlterHolder().get(cstring), ItemEquipCanvas.this);
				Item item = (Item) get(VConstants.currentlySelected);
				if(item == null){
					return;
				}
				if(distance > 60){
					return;
				}
				if(!cstring.equals(item.get(VConstants.type))){
					Window.alert("Your currently selected type is "+item.get(VConstants.type)+" You clicked on "+cstring);
					AttachUtil.update(AttachUtil.clickItem, ItemEquipCanvas.this);
					return;
				}
				if(item.getAmount() > 1){
					item =item.grabOne();
				} else {
					item.getMParent().remove(item.getKey());
				}
				current.getAlterHolder().put((String) item.get(VConstants.type),item);
				AttachUtil.update(AttachUtil.clickItem, ItemEquipCanvas.this);
				
				
			}
		}, MouseDownEvent.getType());

	}
}
