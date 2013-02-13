package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.shared.datamodel.VParams;

public class GoTo extends VParams {

	public GoTo() {
		// TODO Auto-generated constructor stub
	}

	public GoTo(int i, int j) {
		put(VConstants.xsymbolic, i);
		put(VConstants.ysymbolic, j);
	}
	public GoTo(Point pos){
		this(pos.x,pos.y);
	}

	public GoTo(int i, int j, VParams vp) {
		this(i, j);

		AttachUtil.attach(VConstants.list, vp, this);
	}

	@Override
	public void execute(Map<String, Object> map) {

		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		HashMapData hmd = lb.getParent();
		if (!GameUtil.getPlayerTeam().equals(lb.getTeam())) {
			return;
		}

		
		//the goal with this is that only actively moving to the exit will go to the next level
//		if(lb.getTemplate().getCurrent() != null&&lb.getTemplate().getCurrent().getTParent() != null){
//			return;
//		}
		if (!lb.getParent().getParent().getB(VConstants.overmap)) {
			final List<LivingBeing> tlist = GameUtil.getPlayerTeam(hmd
					.getParent().people);
			
			//only if there are enemies
			if(GameUtil.getPlayerTeamAndSummon(hmd
					.getParent().people).size() != hmd.getParent().people.size()){
				tlist.remove(lb);
				hmd.getParent().getNearby(hmd,
						new GetForNearby<HashMapData>(hmd.getParent()) {

							@Override
							public HashMapData get(HashMapData hashmapdata) {
								tlist.remove(hashmapdata.getLivingBeing());
								return null;

							}
						}, 5);

				if (tlist.size() > 0) {
					OutputDirector.soundPlayer.playOnce("youmustgather");
					Window
							.alert("You must gather your party before venturing forth");
					return;
				}

			}
			
		}
		if(getB(VConstants.remove)){
			map.put(AttachUtil.stop, this);
			hmd.remove(AttachUtil.personadded);
			hmd.remove(VConstants.gate);
		}
		if (saveAndReload(hmd.getParent())) {
			return;
		}

		FullMapData fmd = hmd.getParent().getParent().getData(
				getInt(VConstants.xsymbolic), getInt(VConstants.ysymbolic));
		List<LivingBeing> plist = GameUtil
				.getPlayerTeam(hmd.getParent().people);

		fmd.startOnMap(plist.get(0));

		AttachUtil.run(VConstants.list, fmd, this);
		setInitialPos(fmd, plist);
	}

	public static void setInitialPos(FullMapData fmd, List<LivingBeing> plist) {
		if (plist.size() == 0) {
			return;
		}

		PBase human = EntryPoint.game.getPBase(VConstants.human);

		String check = human.getS(VConstants.entrance);
		HashMapData enter = null;
		for (HashMapData h : fmd) {
			for (MapData md : h.removableValues()) {
				if (check != null && check.equals(md.getValue())) {
					enter = h;

					break;
				}
				if (VConstants.enter.equals(md.getValue())) {
					enter = h;

					break;
				}
				if ("gate".equals(md.getValue())) {

					enter = h;
					break;
				}
			}
		}
		List<LivingBeing> omaprefuse = (List<LivingBeing>) EntryPoint.game
				.getTemporary().get(VConstants.savedpeople);
		if (omaprefuse != null) {
			plist.addAll(omaprefuse);
		}

		boolean first = true;
		for (LivingBeing plb : plist) {
			plb.getStats().put(VConstants.health, plb.getStats().getInt(VConstants.maxhealth));
			
			if (!first && fmd.getB(VConstants.overmap)) {
				fmd.addPerson(plb);
				continue;
			}

			if (enter != null) {

				HashMapData hmd = fmd.getNearestEmptyNotEvent(enter);

				hmd.putLivingBeing(plb);
			} else {
				fmd.getNearestEmpty(new Point(5,5)).putLivingBeing(plb);
			}
			first = false;
		}
	}

	public boolean saveAndReload(FullMapData hmd) {
		return false;
	}

	@Override
	public PBase clone() {

		return new GoTo().copyProperties(this);
	}
}
