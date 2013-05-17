package gwt.client.statisticalciv;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class RunRules extends VParams {

	public static boolean pause;

	public RunRules(Object... vp) {
		super(vp);
	}

	public RunRules() {
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute(final Map<String, Object> map) {
		List<VParams> list = (List<VParams>) getList(VConstants.vparams);
		for (VParams v : list) {
			v.init();
		}

		for (FullMapData fmd : EntryPoint.game.getMapArea().getMap()) {

			for (VParams v : list) {
				v.execute(AttachUtil.createMap(fmd, fmd));
			}

		}
		EntryPoint.game.getHtmlOut().displayAreaMap(
				EntryPoint.game.getHtmlOut().currentFMD);
		EntryPoint.game.getHtmlOut().displaySymbolicMap(
				EntryPoint.game.getMapArea().getMap());

	}

	@Override
	public PBase clone() {
		// TODO Auto-generated method stub
		return new RunRules().copyProperties(this);
	}
}
