package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.ItemEquipCanvas;
import gwt.client.game.vparams.ui.SelectAddItem;
import gwt.client.item.Item;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ItemEquipDisplay extends UIVParams {
	HorizontalPanel hp;

	public VerticalPanel vp;
	public ItemEquipCanvas ieq;
	BagMap bagmap;

	public ItemEquipDisplay() {

	}

	@Override
	public void execute(Map<String, Object> map) {

		List<LivingBeing> people2 = EntryPoint.game.getHtmlOut().currentFMD.people;
		List<LivingBeing> people = GameUtil.getPlayerTeam(people2);
		for (LivingBeing lb : people) {

			for (Entry<String, Point> ent : ieq.typeLocation.entrySet()) {
				if (lb.getAlterHolder().get(ent.getKey()) == null) {

					if (lb.getItems() != null) {
						for (MapData md : lb.getItems().removableValues()) {
							if (ent.getKey().equals(md.getS(VConstants.type))) {
								lb.getAlterHolder().put(
										(String) md.get(VConstants.type),
										((Item) md).grabOne());

								break;
							}

						}
					}

				}

			}

		}
		current = (LivingBeing) map.get(AttachUtil.OBJECT);
//		PersonChoiceDisplay pcd = (PersonChoiceDisplay) get(VConstants.personchoicedisplay);
//		pcd.execute(map);
//		ieq.execute(map);
//		ieq.update();
	}
	LivingBeing current;
	@Override
	public void update() {
		
		List<Item> ilist = new ArrayList<Item>();
		if (current.getItems() != null) {
			for (MapData md : current.getItems().removableValues()) {

				Item md2 = (Item) md;
				if(md2.getAmount() != 0){
					ilist.add(md2);	
				}
				

			}
		}

		int z = 0;
		for (int x = 0; x < bagmap.getBagMap().getXsize(); x++) {
			for (int y = 0; y < bagmap.getBagMap().getYsize(); y++) {
				bagmap.getBagMap().setData(x, y, null);
				if (z >= ilist.size()) {

					break;
				}

				bagmap.getBagMap().setData(x, y, ilist.get(z));
				z++;
			}

		}
		bagmap.update();
	}

	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return hp;
	}

	@Override
	public void init() {

		hp = new HorizontalPanel();
		PersonChoiceDisplay pcd = new PersonChoiceDisplay();
		put(VConstants.personchoicedisplay, pcd);
		vp = new VerticalPanel();
		ieq = new ItemEquipCanvas();

		bagmap = new BagMap(10, 5);

		hp.add(pcd.getWidgetAndInit());
		hp.add(vp);

		put(VConstants.team, "1");
		ieq.typeLocation.put("glove", new Point(0, 1));
		ieq.typeLocation.put("shield", new Point(0, 3));
		ieq.typeLocation.put("weapon", new Point(5, 0));
		ieq.typeLocation.put("armor", new Point(3, 3));
		ieq.typeLocation.put("leg", new Point(3, 5));
		ieq.typeLocation.put("head", new Point(3, 0));

		SelectAddItem selectAdd = new SelectAddItem();
		ShowStats showStats = new ShowStats();
		AttachUtil.attach(AttachUtil.clickfmd, this, pcd.getBm());
		AttachUtil.attach(AttachUtil.clickfmd, ieq, pcd.getBm());
		

		AttachUtil.attach(AttachUtil.clickfmd, new SetObject(AttachUtil.OBJECT,
				selectAdd), pcd.getBm());
		AttachUtil.attach(AttachUtil.clickfmd, new SetObject(AttachUtil.OBJECT,
				showStats), pcd.getBm());

		AttachUtil.attach(AttachUtil.clickItem, selectAdd, ieq);

		// AttachUtil.attach(AttachUtil.clickfmd, this, bagmap);
		// AttachUtil.attach(AttachUtil.clickfmd, this, pcd.bm);
		AttachUtil.attach(AttachUtil.clickfmd, selectAdd, bagmap);

		AttachUtil.attach(AttachUtil.clickItem, showStats, ieq);
		AttachUtil.attach(AttachUtil.clickfmd, showStats, bagmap);

		hp.add(showStats.getWidgetAndInit());
		hp.add(selectAdd.getWidgetAndInit());
		vp.add(ieq.getWidgetAndInit());
		vp.add(bagmap.getWidgetAndInit());

		List<UIVParams> groupList = new ArrayList<UIVParams>();
		groupList.add(selectAdd);
		groupList.add(showStats);
		groupList.add(this);
		groupList.add(ieq);
		AttachUtil.setGroupList(groupList);
		// OutputDirector.timer.executeList.add(new IExecute() {
		//			
		// @Override
		// public void execute() {
		// OutputDirector.mpane.getList(HtmlOut.refreshList).add(bagmap);
		// }
		// });

	}

	@Override
	public UIVParams clone() {
		return new ItemEquipDisplay().copyProperties(this);
	}
}
