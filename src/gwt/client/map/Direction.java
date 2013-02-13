package gwt.client.map;

import gwt.client.main.VConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Direction {
	

	North(0,-1),Northeast(1,-1),East(1,0),Southeast(1,1),South(0,1),Southwest(-1,1),West(-1,0),Northwest(-1,-1);
	int x,y;
	
	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	};
	public static Direction get(int x, int y){
		for(Direction d : values()){
			if(d.x == x&& d.y == y){
				return d;
			}
		}
		return null;
	}
	public static List<Direction> getClosestOrdered(Direction dir){
		List<Direction> dlist = new ArrayList<Direction>(2);
		List<Direction> vlist=Arrays.asList(values());
		int index = vlist.indexOf(dir);
		for(int a = 1; a < 5; a++){
			int i = index -a;
			if(i < 0){
				i = vlist.size() +i;
			}
			Direction d=vlist.get(i % 8);
			if(d.equals(dir)){
				continue;
			}
			dlist.add(d);
			i = index +a;
			if(i < 0){
				i = vlist.size() +i;
			}
			d=vlist.get(i % 8);
			if(d.equals(dir)){
				continue;
			}
			dlist.add(d);
		}
		
		return dlist;
	}
	
	public static Direction getDirection(IPhysical py, IPhysical py2){
		int x = py.getX() - py2.getX();
		int y = py.getY() - py2.getY();
		int x2 = 0;
		int y2 = 0;
		if(x > 0){
			x2 = 1;
		}
		if(x < 0){
			x2 = -1;
		}
		
		if(y > 0){
			y2 = 1;
		}
		if(y < 0){
			y2= -1;
		}
		return get(x2, y2) ;
		
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public static Direction getRandom() {
		List<Direction> vlist=Arrays.asList(values());
		
		return VConstants.getRandomFromList(vlist);
	}
	

}
