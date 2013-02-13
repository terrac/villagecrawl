package gwt.client.main;

import gwt.client.item.Item;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.SymbolicMap;

public class Carry extends OObject{

	private static final String TO_CARRY = "toCarry";
	private static final String TO_MOVE = "toMove";
	public Carry() {
	}
	
	@Override
	public Returnable execute(FullMapData map, LivingBeing person) {
		
		if(!containsKey(TO_MOVE)){
			return new Returnable(false, 1,true);
		}
		HashMapData toMove;
		if(get(TO_MOVE) instanceof String){
			toMove =map.getNearKeyValue(VConstants.gate, (String) get(TO_MOVE), person, 20);
		} else {
			toMove = (HashMapData) get(TO_MOVE);
		}
		person.getTemplate().pending.add(new PickUp((String) get(TO_CARRY)));
		
		person.getTemplate().pending.add(new Move(toMove,"carry"));
		person.getTemplate().pending.add(new PutDown((String) get(TO_CARRY)));
		
		
		return new Returnable(false, 0);
	}

	

	public Carry(String tocarry, HashMapData toMove) {
		super();
		put(TO_CARRY, tocarry);
		put(TO_MOVE, toMove);
		if(toMove == null||tocarry == null){
			throw new IllegalArgumentException(tocarry +" "+ toMove);
		}		
	}


	public Carry(String tocarry, String toMove) {
		super();
		put(TO_CARRY, tocarry);
		
		put(TO_MOVE, toMove);
		if(toMove == null||tocarry == null){
			throw new IllegalArgumentException(tocarry +" "+ toMove);
		}		
	}

	@Override
	public OObject clone() {
		
		return (OObject) new Carry().copyProperties(this);
	}
}
