package gwt.client.game;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;

public class Attack extends OObject  {
	

	public Attack() {
		// TODO Auto-generated constructor stub
	}
	public Attack(LivingBeing lb,LivingBeing attacker) {
		super();
		put(VConstants.livingbeing,lb);
		
		PBase defaultAttack = getDefaultAttack(attacker);
		
		if(defaultAttack == null){
			Window.alert(attacker.getType()+" has no default attack");
		}
		Integer range=(Integer) defaultAttack.get(VConstants.range);
		if(range == null){
			range = 1;
		}
		put(VConstants.range,range);
		put(VConstants.ability,defaultAttack);
	}
	
	public Attack(LivingBeing lb,PBase ability) {
		super();
		put(VConstants.livingbeing,lb);
		put(VConstants.ability,ability);
		
		Integer range=(Integer) ability.get(VConstants.range);
		if(range == null){
			range = 1;
		}
		put(VConstants.range,range);
	}

	@Override
	public Returnable execute(FullMapData fullMapData, final LivingBeing person) {
		//move within range
	
		final LivingBeing lb = getLivingBeing();
		if(lb.getParent() == null){
			return null;
		}
		put(VConstants.display,lb);
		Point lbpos = lb.getPosition();
		if(lbpos == null){
			return null;
		}
		Point perpos=person.getPosition();
		if(perpos == null){
			return null;
		}
		if (Point.distance(perpos, lbpos) > getInt(VConstants.range)) {
			Move move = new Move(lb.getParent(), "attack!");
			move.command = new OCommand() {
				
				@Override
				public boolean execute(OObject oo,LivingBeing person) {
					Move move = (Move) oo;
					LivingBeing livingBeing = (LivingBeing) Attack.this.get(VConstants.livingbeing);
					if(livingBeing == null||livingBeing.getParent() == null ){
						return false;
					}
					move.setTo(livingBeing.getParent());
					PBase defaultAttack = getPBase(VConstants.ability);
					
					
					Integer range = ApplyDamage.getRange(defaultAttack);
					Point pos=person.getPosition();
					if(pos == null){
						return false;
					}
					if (Point.distance(pos, move.getTo()) <= range) {
						// pops the current Move and returns null, shouldn't do more
						// complicated things than this
						// but I think this should be fine
						
						return false;
					}
					return true;
				}
			};
			addToList(person, move);
		}
		// get game apply damage function, call it
		addToList(person, new OObject() {

			@Override
			public Returnable execute(FullMapData fullMapData,
					final LivingBeing person) {
				if(lb.getParent() == null){
					return null;
				}
				
				final ApplyDamage ad = (ApplyDamage) person.getMapArea().game
						.get(VConstants.applydamage);
				
				final PBase attack = Attack.this.getPBase(VConstants.ability);
				
				if (Point.distance(lb,person) > ApplyDamage.getRange(attack)) {
					System.out.println(Point.distance(lb,person));
					System.out.println(person.getType());
					System.out.println(lb.getType());
					
					person.getTemplate().clear();
					return null;
				}
				ad.execute(person, lb, attack);
				
				
				//ad.execute(person, lb, attack);
				return null;
			}
		});

		// else call apply damage and exit

		return null;
	}
	public static PBase getDefaultAttack(LivingBeing person) {
		return (PBase) EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.ability).get((String) person.getStats().get(VConstants.defaultattack));
	}
	public LivingBeing getLivingBeing() {
		return (LivingBeing) get(VConstants.livingbeing);
	}

	@Override
	public OObject clone() {
		
		return new Attack().copyProperties(this);
	}
	@Override
	public String toString() {
		
		return getPBase(VConstants.ability).get(VConstants.name)+getLivingBeing().getType();
	}
}
