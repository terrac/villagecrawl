package gwt.client.game.oobjects;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.adding.Infect;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FillNeed extends OObject {
	public FillNeed() {	
	}

	public FillNeed(MapData md) {
		put(VConstants.item, md);
	}

	int count = 0;
	;
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		for(Entry<String,Object> ent :person.getType(VConstants.temporary).getType(VConstants.stats).getObjMap().entrySet()){
			Infect.infect(person, ent.getKey());
			return new Returnable(true);
		}
		
		List<String> list = person.getType(VConstants.temporary).getListCreate("needlist");
		
		if(list.size() ==0){
			PBase needs = EntryPoint.game.getPBase(VConstants.culture).getPBase(VConstants.stats);
			for(Entry<String,Object> ent : needs.getObjMap().entrySet()){
				int stat=person.getStats().getInt(ent.getKey());
				
				person.getStats().put(ent.getKey(), stat);
				list.add(ent.getKey());
			}
		}
		
		
		
		//move to lowest need available
		if(count >= list.size()){
			count = 0;
		}
		String need = list.get(count);
		count++;
		int stat=person.getStats().getInt(need);
		
		
		int avgneed=EntryPoint.game.getPBase(VConstants.culture).getPBase(VConstants.stats).getInt(need);
		if(avgneed - 20 > stat){
			stat--;
			person.getStats().put(need, stat);			
		}
		//technically could compute
		if(stat > avgneed){
			return null;
		}
		
		//go into culture and pull randomly from one of the related executables to fill the need
		//in the first case it will be happiness and on the associated stat thing I will put
		//a few of the generic complaints
		Map<String, Object> m=AttachUtil.createMap(person, this);
		
		m.put(VConstants.log, true);
		Object o = EntryPoint.game.getPBase(VConstants.culture).getPBase(VConstants.need).getPBase(VConstants.stats).get(need);
		if(o instanceof VParams){
			((VParams) o).execute(m);
		}
		if(o instanceof OObject){
			addToList(person, (OObject) o);
		}
		//turn into action
		
		//get item off of person

		
		//do display
		
		
		
		return null;
	}
	
	@Override
	public OObject clone() {
		
		return new FillNeed().copyProperties(this);
	}
}
