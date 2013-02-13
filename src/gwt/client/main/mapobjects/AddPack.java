package gwt.client.main.mapobjects;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.MapArea;
import gwt.client.main.Person;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MapObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.SymbolicMap;
import gwt.client.personality.Stats;
import gwt.shared.datamodel.VParams;

public class AddPack extends VParams {
	public AddPack() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = (FullMapData) map.get(AttachUtil.OBJECT);
		FullMapData fmd2 = EntryPoint.game.getFMDMap().get("generic");
		fmd.setFullMapData(fmd2,0,0,null);
		
		if(!(getInt(VConstants.xfull) == fmd.getX()&& getInt(VConstants.yfull) == fmd.getY())){
			return ;
		}
		
		
		if(VConstants.human.equals(getS(VConstants.type))){
			addHumanPack(fmd);
		}else {
			addPack( fmd, getS(VConstants.type));
		}

					

				
			
			
		
		
		return;
	}

	public AddPack(int xloc, int yloc, String type) {
		super();
		put(VConstants.xfull, xloc);
		put(VConstants.yfull,yloc);
		put(VConstants.type,type);
		
	}

	public static void addPack( FullMapData fmd, String type) {
		List<LivingBeing> family = null;
		for(int i = 0; i < 5 ; i++){
			LivingBeing lb = fmd.getParent().getParent().addAnimal(type);
			fmd.add(Direction.West, lb);
			
			if(i == 0){
				//set leader variable
				lb.setVariable(VConstants.leader, lb);
				family = lb.getGroup().getFamily();
			}
			family.add(lb);
			lb.getGroup().setFamily(family);
			//get one family list, set for all families
			//add onto that list
			
		}
	}
	public static void addHumanPack( FullMapData fmd) {
				SymbolicMap parent = fmd.getParent();
				MapArea parent2 = parent.getParent();
				Person p=parent2.addPerson( "maleadult", 0, 0);
				fmd.add(Direction.East, p);
				p=fmd.getParent().getParent().addPerson( "femaleadult", 0, 0);
				fmd.add(Direction.East, p);
				p=fmd.getParent().getParent().addPerson( "femalechild", 0, 0);
				fmd.add(Direction.East, p);
				p=fmd.getParent().getParent().addPerson( "malechild", 0, 0);
				fmd.add(Direction.East, p);
				
	}

	@Override
	public PBase clone() {
		
		return new AddPack().copyProperties(this);
	}
}
