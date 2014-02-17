package gwt.client.main.base;

import java.util.ArrayList;
import java.util.List;

import gwt.client.main.Move;
import gwt.client.main.OObjectB;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.AreaMap;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.IDecision;
import gwt.client.output.OutputDirector;

public abstract class BaseMoveMap<T extends MapData> extends OObjectB{
	
	public String getDescr(){
		return getS(VConstants.description);
	}
	public int getRadius() {
		return (Integer) get(VConstants.radius);
	}



	public T getToMove() {
		return (T) get(VConstants.move);
	}



	public void setToMove(T toMove) {
		put(VConstants.move,toMove);
	}



	public BaseMoveMap() {
	}



	public BaseMoveMap(int radius, String descr) {
		super();
		put(VConstants.radius, radius);
		put(VConstants.description,descr);
	}



	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		
		
		
//		LivingBeing doer = (LivingBeing) person.getVariable(VConstants.doer);
//		if (doer != null&&!person.equals(doer)) {
//			setToMove( (T) person.getVariable(this.getClass().getName()));
//			
//		}
		
		if(getToMove() == null){
			setToMove( findNewMD(fullMapData, person));
		}
		if(getToMove() == null){
			return new Returnable(true,1);
		}
//		if(person.equals(doer)){
//			for(LivingBeing p : (List<LivingBeing>) person.getVariable(VConstants.doees)){
//				p.setVariable(this.getClass().getName(),getToMove());
//			}
//		}
		
		addMove(person);
		return null;
	}



	protected void addMove(LivingBeing person) {
		addToList(person, new Move((FullMapData) getToMove(), "moveto "+get(VConstants.description)));
	}


	protected abstract T findNewMD(FullMapData symbolicmap, LivingBeing person);

}
