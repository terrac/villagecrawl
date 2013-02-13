package gwt.client.edit;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.VExpression;
import gwt.client.game.display.UIVParams;
import gwt.client.item.SimpleMD;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.client.output.HtmlOut;
import gwt.client.output.SEventClick;
import gwt.client.output.html.Click;
import gwt.client.output.html.GCanvas;
import gwt.shared.datamodel.VParams;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.ui.Widget;

public class BagMap extends UIVParams {
	public static final String depositpoint = "depositpoint";

	// public TextBox bagName = new TextBox();

	public BagMap() {

	}

	public BagMap(int x, int y) {
		put(VConstants.xsize, x);
		put(VConstants.ysize, y);
		setBagMap(new MapDataAreaMap(x, y));

	}

	public BagMap(int x, int y, int imagesize) {
		put(VConstants.xsize, x);
		put(VConstants.ysize, y);
		setBagMap(new MapDataAreaMap(x, y));
		put(VConstants.size, imagesize);
	}

	public GCanvas bagCanvas;

	public void init() {
		Integer size = getSize();
		bagCanvas = new GCanvas(getInt(VConstants.xsize) * size,
				getInt(VConstants.ysize) * size);
		// bagCanvas.setBackgroundColor(new Color(40, 40, 40));
		Click c = new Click(bag);
		bagCanvas.addDomHandler(c, MouseDownEvent.getType());

	}

	public Integer getSize() {
		Integer size = (Integer) get(VConstants.size);
		if (size == null) {
			size = HtmlOut.imagesize;
		}
		return size;
	}

	SEventClick<GCanvas> bag = new SEventClick<GCanvas>() {

		@Override
		public void execute(int x, int y, GCanvas sender) {

			Object md = getBagMap().getData(x / getSize(), y / getSize());
			
			selected = new Point(x / getSize(), y / getSize());
			if (md instanceof PBase&&(((PBase) md).containsKey(AttachUtil.runbefore))) {
				AttachUtil.run(AttachUtil.runbefore, md, (PBase) md);
			}

			setSelection(md);
			if (md == null) {
				md = new Point(x / getSize(), y / getSize());
			}
			if (md instanceof SimpleMD) {
				if (((SimpleMD) md).containsKey(VConstants.livingbeing)) {
					md = ((PBase) md).get(VConstants.livingbeing);
				}
			}

			AttachUtil.run(AttachUtil.clickfmd, md, BagMap.this);

			update();
		}
	};

	public MapDataAreaMap getBagMap() {
		return (MapDataAreaMap) get(VConstants.bagmap);
	}

	public void setBagMap(MapDataAreaMap bagMap) {
		put(VConstants.bagmap, bagMap);
	}

	public void update() {

		bagCanvas.clear();
		Integer ini = (Integer) get(VConstants.size);
		HtmlOut htmlOut = EntryPoint.game.getHtmlOut();
		if (ini == null) {
			htmlOut.drawAreaMap(getBagMap(), bagCanvas);
		} else {
			htmlOut.drawAreaMap(getBagMap(), bagCanvas, ini);
		}
		if (selected != null) {

			htmlOut.drawImage(bagCanvas, selected.y, selected.x,
					"/images/selected.png", getSize());
		}
		for (int x = 0; x < getBagMap().getXsize(); x++) {
			for (int y = 0; y < getBagMap().getYsize(); y++) {
				MapData md = getBagMap().getData(x, y);
				if (md != null) {
					String overlay = md.getS(VConstants.overlay);
					if (overlay != null) {

						htmlOut.drawImage(bagCanvas, y, x, overlay, getSize());
					}
				}
			}

		}

		if (selected != null) {
			setSelection(getBagMap().getData(selected));
		}

		// if(containsKey(VConstants.overmap)){
		// Map<Point,String> omap = (Map<Point, String>)
		// get(VConstants.overmap);
		// for(Entry<Point,String> e : omap.entrySet()){
		// EntryPoint.game.getHtmlOut().drawImage(bagCanvas, e.getKey()
		// .x,e.getKey().y, e.getValue(),getSize());
		// }
		// }

	}

	public Point selected;

	@Override
	public Widget getWidget() {

		return bagCanvas;
	}

	@Override
	public Object remove(String key) {
		for (int y = 0; y < getBagMap().getYsize(); y++) {
			for (int x = 0; x < getBagMap().getXsize() + 1; x++) {
				MapData md = getBagMap().getData(x, y);
				if (md != null && md.getKey().equals(key)) {
					getBagMap().setData(x, y, null);
					return md;
				}
			}
		}

		return super.remove(key);
	}

	@Override
	public BagMap clone() {

		BagMap copyProperties = new BagMap().copyProperties(this);

		return copyProperties;
	}

	public void setSelection(Object md) {
		if (md != null && BagMap.this.containsKey(VConstants.selectionname)) {
			PBase selectionpb = new PBase(VConstants.selection, md,
					AttachUtil.attacher, this);
			VExpression.setValue(getS(VConstants.selectionname), md,
					EntryPoint.game);
		}
	}

	// public void addOverlay(Point selected2, String string) {
	// Map<Point,String> omap = (Map<Point, String>) get(VConstants.overmap);
	// if(omap == null){
	// omap = new HashMap<Point, String>();
	// put(VConstants.overmap,omap);
	// }
	// omap.put(selected2, string);
	// }
}
