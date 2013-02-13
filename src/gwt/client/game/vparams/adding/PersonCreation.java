package gwt.client.game.vparams.adding;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Map;

public class PersonCreation extends VParams {
	public PersonCreation() {

	}

	public PersonCreation(PBase pb) {
		put(VConstants.pbase,pb);
	}

	public void execute(Map<String,Object> map) {
		LivingBeing lb=RandomPersonCreation.createPerson(getPBase(VConstants.pbase));
		EntryPoint.game.getHtmlOut().currentFMD.getNearestEmpty(new Point(0,0)).put(lb);
	}

	@Override
	public PBase clone() {

		return new PersonCreation().copyProperties(this);
	}

}
