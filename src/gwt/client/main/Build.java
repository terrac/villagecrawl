package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.person.Building;

public class Build extends OObject {
	public Build() {
		// TODO Auto-generated constructor stub
	}
	String type;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		FullMapData fmd = person.getParent().getParent();
		
		build(person, fmd,type,1,0,false);
		
		

		
		return new Returnable();
	}

	public static void build(LivingBeing person, FullMapData fmd,String type,int x, int y,boolean setFmd) {
	
		FullMapData fmd2 = person.getMapArea().game.getFMDMap().get(type);
		fmd.setFullMapData(fmd2,x,y,person);
		
		
		fmd.put("builtup", null);

		Building bu = new Building(type,fmd,fmd2.getXsize(),fmd2.getYsize());
		person.getGroup().put(bu);
		fmd.put(bu);
	}

	public static void setBuild(LivingBeing person, FullMapData fmd,String type,int x, int y) {

		
		
		fmd.put("builtup", null);

		Building bu = new Building(type,fmd);
		person.getGroup().put(bu);
		fmd.put(bu);
	}
	public Build(String type) {
		super();
		this.type = type;
	}



}
