package gwt.client.statisticalciv.generator.nomadic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class TribalConflictGenerator extends VParams {

	public TribalConflictGenerator() {
	}
	
	/**
	 * 

   a revenge killings
      1 if multiple populations are in an area the two will engage in low scale fighting even when not
         actively in conflict
      1 basically just an action that takes one of the people and sends it to kill the other and then   
        the other side returns the favor
   b tribal thoughts (ie defend family right or wrong)
      1 if a dispute comes up (land,etc) then family ties decide who wins
      1 basically if two different populations walk near each other it will 
      have a random chance of starting a conflict.  the tribal stuff determines their response, starting killing, stealing, etc
	 */
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		
		

		
	}


	@Override
	public PBase clone() {
		return new TribalConflictGenerator().copyProperties(this);
	}
}
