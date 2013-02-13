package gwt.client.game.vparams.ui;

import gwt.client.edit.BagMap;
import gwt.client.game.AlterHolder;
import gwt.client.game.AttachUtil;
import gwt.client.game.ItemEquipCanvas;
import gwt.client.game.MultilineLabel;
import gwt.client.game.display.UIVParams;
import gwt.client.item.Item;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.AreaMap;

import java.util.Map;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SelectAddItem extends UIVParams{

	
	@Override
	public void execute(Map<String, Object> map) {
		PBase holder = (PBase) map.get(AttachUtil.attacher);
		
		//pull the current item from the map
		Item toPut= (Item) get(VConstants.currentlySelected);

	    
		//take the currently selected item (if exists)
		Object obj = map.get(AttachUtil.OBJECT);
		
		//bagmap returns a point if no item is on area
		Item toGet = null;
		if(obj instanceof Point){
			((BagMap) holder).getBagMap().setData((Point) obj,toPut);
			if(toPut == null){
				return;
			}
			LivingBeing lb=((AlterHolder)toPut.getMParent()).getParent();
			lb.getAlterHolder().remove(toPut);
			lb.addItems(toPut);
			//also add back to current person
		} else {
			toGet=(Item) obj;
		}
		
		
		put(VConstants.currentlySelected,toGet);
		
		
		if(toGet != null){
			holder.remove(toGet.getKey());
		}
		
		mtl.setHTML(""+toGet);
		bm.getBagMap().setData(0, 0, toGet);
		
		//for the holder, override put to put in the appropriate spot
		holder.put(VConstants.currentlySelected,toPut);
		
		
		//additionally get the person currently associated with the iec and call compute items
		
		
	}
	public void update() {bm.update();}
	
	HorizontalPanel hp;
	BagMap bm;
	MultilineLabel mtl;
	
	@Override
	public Widget getWidget() {

		return hp;
	}
	
	@Override
	public void init() {
		 hp = new HorizontalPanel();
		 bm = new BagMap(1, 1);
		 mtl = new MultilineLabel();

		hp.add(mtl);
		hp.add(bm.getWidgetAndInit());
		hp.add(mtl);
		hp.add(bm.getWidgetAndInit());
	}
}
