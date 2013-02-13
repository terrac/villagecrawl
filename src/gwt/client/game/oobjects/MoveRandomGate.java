package gwt.client.game.oobjects;

import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.adding.AddGDP;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Move;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class MoveRandomGate extends OObject {
	public MoveRandomGate() {
	}
	public MoveRandomGate(String value) {
		put(VConstants.value,value);
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		int count = 0;
		while (count < 1) {
			int xrand = VConstants.getRandom().nextInt(fullMapData.getXsize());
			int yrand = VConstants.getRandom().nextInt(fullMapData.getYsize());
			HashMapData hmd = fullMapData.getData(xrand, yrand);
			MapData md=hmd.getMapData(VConstants.gate);
			if(md != null){
				if(getS(VConstants.value).equals(md.getValue())){
					addToList(person, new Move(hmd,  "move"));
					AttachUtil.run(AttachUtil.runbefore, person, this);
					if(containsKey(VConstants.oObject)){
						addToList(person, (OObject) get(VConstants.oObject));
					}
					
				}
			}
			count++;

		}
		return null;
	}

	@Override
	public OObject clone() {

		return new MoveRandomGate().copyProperties(this);
	}
}
