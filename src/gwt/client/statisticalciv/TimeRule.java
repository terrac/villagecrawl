package gwt.client.statisticalciv;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.Parameters;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.shared.ClientBuild;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class TimeRule extends VParams {

	public TimeRule() {
	}
	
	public TimeRule(Object... vp) {
		super(vp);
	}
	List<Integer> timeNew = Arrays.asList(new Integer[]{50000,10000});
	List<Integer> timeIncrement = Arrays.asList(new Integer[]{10,1});
	int index= -1;
	int time;
	int bcPoint = 0; //basically an offset to go to the year 2000 make the bcpoint 2000
	
	DiseaseRule dr = new DiseaseRule();
	@Override
	public void execute(Map<String, Object> map) {
		if(time < 8000){
			dr.execute(map);
		}
		if(index > timeIncrement.size()){
			return;
		}
		if(index == -1){
			index++;
			time = timeNew.get(index);
		}
		if(timeNew.size() > index+1&&time < timeNew.get(index+1)){
			index++;
		}
		time -= timeIncrement.get(index);
		
		UVLabel label=(UVLabel) EntryPoint.game.get(VConstants.score);
		label.setText(""+time+" BC");
	}
	@Override
	public PBase clone() {
		return new TimeRule().copyProperties(this);
	}

}
