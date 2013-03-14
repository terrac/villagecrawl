package gwt.client.statisticalciv;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.PTemplate;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.shared.ClientBuild;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class PeopleRule extends VParams {

	public PeopleRule() {
	}
	boolean init;
	public void addTemplate() {
		if(!init){
			init=true;
			return;
		}
		{
			PTemplate pt = StatisticalCiv
					.addTemplate(EntryPoint.game, "person");
			String action = StatisticalCiv.addG("techaction", EntryPoint.game,
					new TechnologyAction());
			StatisticalCiv.addAction(pt, action);
		}
		
		{
			PTemplate pt = StatisticalCiv
					.addTemplate(EntryPoint.game, "person");
			String action = StatisticalCiv.addG("techaction", EntryPoint.game,
					new TechnologyAction());
			StatisticalCiv.addAction(pt, action);
		}
	}

	@Override
	public void execute(Map<String, Object> map) {
		addTemplate();
		FullMapData fmd = getFMD(map);
		for (HashMapData hmd : fmd) {
			LivingBeing lb = RandomPersonCreation.createPerson("human female");
			// set template
			lb.setTemplate("person");
			hmd.put(lb);
			// person has a technology oobject

			// then as the pops grow due to food they split off with
			// the techs and form smaller hybrid communities
			// these avoid conflict due to their size
		}

	}

	@Override
	public PBase clone() {
		return new PeopleRule().copyProperties(this);
	}
}
