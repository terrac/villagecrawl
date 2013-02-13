package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.output.HtmlOut;
import gwt.shared.datamodel.VParams;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InventoryDisplay extends UIVParams {

	public InventoryDisplay(String name,PBase itp) {
		refreshData(itp);
		put(VConstants.name,name);
	}

	public void refreshData(PBase itp) {
		Items items= (Items) itp.get(VConstants.items);
		//if an issue just use something other than the parent, 
		items= items.clone();
		
		items.setParent((MapData) itp);

		put(VConstants.items,items);
	}

	public InventoryDisplay() {
		// TODO Auto-generated constructor stub
	}

	public BagMap getBm() {
		return (BagMap) get(VConstants.bagmap);
	}

	int inventorysize;

	@Override
	public void execute(Map<String, Object> map) {
		Items it = ((Items)get(VConstants.items));
		if(it == null){
			return;
		}
		Collection<Item> items = it.values();

		//if (!(inventorysize == items.size() || items.size() == 0)) {
			inventorysize = items.size();
			getBm().getBagMap().setXsize(items.size()+1);
			getBm().getBagMap().init();
			getBm().bagCanvas.setCoordinateSpaceWidth(getBm().getSize()
					* getBm().getBagMap().getXsize());
			int y = 0;
			for (Item item : items) {
				SimpleMD pb = new SimpleMD();
				Object object = item.getImage();
				pb.put(VConstants.image, object);
				pb.put(AttachUtil.OBJECT, item);
				getBm().getBagMap().setData(y, 0, pb);
				// call the first person on the list for the level up display
				
				y++;
			}
			SimpleMD pb = new SimpleMD();
			pb.put(AttachUtil.OBJECT, null);
			getBm().getBagMap().setData(y, 0, pb);
			
 
		//} 
		
		
		getBm().update();
		EntryPoint.game.getHtmlOut().loadMissing(this);
		
	}

	@Override
	public Widget getWidget() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label(getS(VConstants.name)));
		vp.add(getBm().getWidget());
		
		return vp;
	}

	@Override
	public void update() {
		getBm().update();
	}
	@Override
	public UIVParams clone() {

		return new InventoryDisplay().copyProperties(this);
	}

	@Override
	public void init() {
		put(VConstants.bagmap, new BagMap(3, 1, HtmlOut.imagesize * 2));
		getBm().init();
		getBm().bagCanvas.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		getBm().bagCanvas.getElement().getStyle().setBorderWidth(10, Unit.PX);

	}

	
}
