package gwt.client.game;

import java.util.ArrayList;
import java.util.List;

import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class CreateListTurns extends OObject {

	

	public CreateListTurns() {
		// TODO Auto-generated constructor stub
	}
	
	int turn = 0;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		List<Integer> intList = getListCreate(VConstants.turn);
		if(intList.size() ==0){
			return null;
		}
		
		if(turn == intList.get(0) ){
			
			intList.remove(0);
			
			LivingBeing lb = RandomPersonCreation.createPerson( (String) getList(VConstants.livingbeing).remove(0));
			lb.getTemplate().setRationalChild("main", getS(VConstants.template));
			lb.put(VConstants.team,person.get(VConstants.team));
			fullMapData.getNearestEmpty(new Point(getInt(VConstants.xfull),getInt(VConstants.yfull))).putLivingBeing(lb);
		}
		turn++;
		return new Returnable(true,3);
	}
	public void add(int turn, String string){
		getListCreate(VConstants.livingbeing).add(string);
		getListCreate(VConstants.turn).add(turn);
	}
	
	public CreateListTurns(int x, int y, String string) {
		super();
		put(VConstants.xfull,x);
		put(VConstants.yfull,y);

		put(VConstants.template,string);

	}
	@Override
	public OObject clone() {

		return new CreateListTurns().copyProperties(this);
	}
	

}
