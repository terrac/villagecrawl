package gwt.client.main;

import java.util.Arrays;
import java.util.List;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;

public class FormFamily extends OObject {


	List<String> flist = Arrays.asList(new String[]{"maleadult","femaleadult"});
	@Override
	public Returnable execute(final FullMapData fullMapData,final LivingBeing person) {
		if(person.getGroup() ==null||person.getGroup().getFamily().size() != 0){
			return null;
		}
		final boolean[] blist = new boolean[flist.size()];
		
		for(int a = 0; a < flist.size(); a++){
			if(flist.get(a).equals(person.getType())){
				blist[a] = true;
			}
		}
		
		fullMapData.getNearby(person, new GetForNearby<HashMapData>() {
				
			@Override
			public HashMapData get(int x1, int y1) {
				LivingBeing lb=(LivingBeing) fullMapData.getData(x1,y1).get(VConstants.livingbeing);
				if(lb == null){
					return null;
				}
				if(!lb.isPerson()||lb.getGroup() == null){
					return null;
				}
				if(lb.getGroup().getFamily().size() != 0){
					return null;
				}
				
				int ind=flist.indexOf(lb.getType());
				
				
				if(ind != -1){
					if(blist[ind]){
						return null;
					}
					blist[ind] = true;
				}
				
				lb.setGroup(person.getGroup());
				person.getGroup().getFamily().add(lb);
				return null;
			}
		}, 40);
		
		return new Returnable();
	}
	
	@Override
	public OObject clone() {
		
		return new FormFamily();
	}

}
