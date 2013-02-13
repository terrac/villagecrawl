package gwt.client.map.runners;

import gwt.client.item.SimpleMDNumber;
import gwt.client.main.Person;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.personality.Stats;

import java.util.ArrayList;
import java.util.List;

public class BaseSetFull {
	public LivingBeing person;
	public void runSetup(FullMapData current,FullMapData fmd, int x, int y){
		
		
		for (int a = 0; a < fmd.getXsize(); a++) {
			for (int b = 0; b < fmd.getYsize(); b++) {
				HashMapData hmd = current.getData(a+x,b+y);
				if(hmd == null){
					continue;
				}
				runHMD(hmd);
				for(MapData md : fmd.getData(a,b).removableValues()){						
					runMD(hmd, md);
				}
			}
		}
		
	}

	protected void runHMD(HashMapData hmd) {
		hmd.remove(VConstants.gate);
	}

	protected void runMD(HashMapData hmd, MapData md) {
//		if(person != null&&"itemstub".equals(md.getKey())){
//			hmd.getItems().put(person.getItemsMap().get(md.getValue()).cloneNumber(((SimpleMDNumber)md).getNumber()));
//			return;
//		}
		if(VConstants.under.equals(md.getKey())){
			hmd.putEvenIfNotEmpty(VConstants.under, md.clone());
		}
//		if(md.getValue().startsWith("maleadult")){
//			
//			Person p =person.getMapArea().addPerson( null,md.getValue(), ((IPhysical)md).getX(), ((IPhysical)md).getY(), Stats.Sex.Male.name());
//			hmd.put(p);
//			return;
//		}
//		if(md.getValue().startsWith("femaleadult")){
//			Person p =person.getMapArea().addPerson( null,md.getValue(), ((IPhysical)md).getX(), ((IPhysical)md).getY(), Stats.Sex.Female.name());
//			hmd.put(p);
//			return;
//		}
		//aoeu put person map
		hmd.put(md.clone());
	}

	public void runAfter(HashMapData hmd) {
		if(hmd.containsKey(VConstants.livingbeing)){
			LivingBeing p = hmd.getPerson();
			hmd.getParent().getNearestEmpty(p).put(p);
		}
	}

}
