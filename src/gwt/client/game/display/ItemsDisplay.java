package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;

import java.util.Map;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ItemsDisplay extends UIVParams {
	VerticalPanel dhmd;
	Label lab;

	Items items;

	public ItemsDisplay() {

	}

	public ItemsDisplay(String name) {
		put(VConstants.name, name);
	}

	@Override
	public void execute(Map<String, Object> map) {

		if(items == null){
			//
			items = getPerson(getS(VConstants.name)).getItemsCreate();
			
		}
		if(items == null){
			return;
		}
		dhmd.clear();
		HorizontalPanel hp = new HorizontalPanel();
		HorizontalPanel hp2 = new HorizontalPanel();
		dhmd.add(hp);
		dhmd.add(hp2);
		boolean one = true;
		int count = 0;
		for(Item it : items.values()){
			if(count == 7){
				break;
			}
			HorizontalPanel ch = hp;
			if(!one){
				ch = hp2;
			}
			ch.add(new Label(it.getName()));
			ch.add(new Label("" + it.getAmount()));			
			ch.add(new Image(it.getImage()));
			one = !one;
			count++;
		}
	}

	public static LivingBeing getPerson(String name) {
		for(FullMapData fmd : EntryPoint.game.getMapArea().getMap()){
			for(LivingBeing lb : fmd.people){
				if(name.equals(lb.getName())){
					return lb;
				}
			}
		}
		return null;
	}

	@Override
	public Widget getWidget() {
		return dhmd;
	}

	@Override
	public void init() {
		dhmd = new VerticalPanel();
		AttachUtil.attach(AttachUtil.runbefore, this,
				EntryPoint.game.getMapArea());

	}

	@Override
	public UIVParams clone() {
		return new ItemsDisplay().copyProperties(this);
	}

}
