 package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.astar.pathfinder.algorithm.PathFinder;
import gwt.client.astar.pathfinder.algorithm.impl.AStarPathFinder;
import gwt.client.astar.pathfinder.heuristic.impl.AStarHeuristic;
import gwt.client.astar.pathfinder.path.Path;
import gwt.client.astar.pathfinder.path.Step;
import gwt.client.game.OCommand;
import gwt.client.game.util.PointBase;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.WhatSound;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.output.OutputDirector;

import java.util.List;

import com.google.gwt.user.client.Command;




public class Move extends OObject implements WhatSound{

	
	public Move() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public HashMapData getTo() {
		return (HashMapData) get(VConstants.to);
	}
	public void setTo(HashMapData to) {
		
		put(VConstants.to,to);
	}
	



	private Point moveto;



	private Path movepath;



	private Integer movepathindex;
	public Move(HashMapData to,String description) {
		super();
		
		if(to == null){
			throw new IllegalArgumentException();
		}
		//this.from = from;
		
		
		
		setTo(to);
		setDescr(description);
	
		
		
	}
	public Move(HashMapData to,String description,String overlay) {
		this(to,description);
		put(VConstants.overlay,overlay);
	}
		
	private void setDescr(String description) {
		put(VConstants.description,description);
		
	}



	public Move(int x, int y){
		put(VConstants.xfull,x);
		put(VConstants.yfull,y);
	}
	
	@Override
	public void init(LivingBeing person) {
		if(containsKey(VConstants.xfull)){
			int x =getInt(VConstants.xfull);
			int y=getInt(VConstants.yfull);
			setTo(person.getParent().getParent().getData(x, y));
		}
		HashMapData to = getTo();
		
		if(to != null&&to.isBlock()){
			setTo(to.getParent().getNearestEmpty(to));
		}
	}
	
	public Move(FullMapData to,String description) {
		super();
		
		if(to == null){
			throw new IllegalArgumentException();
		}
		
		setTo(to.getNearestEmpty(new Point(2,2)));
		
		
		
		setDescr( description);
		
		
	}
	
	




	public OCommand command;
	
	public Returnable execute(FullMapData map, LivingBeing person) {
		Returnable ret = null;
		Integer in= (Integer) person.getStats().get(VConstants.walkspeed);
		if(in == null){
			in = 1;
		}
		for(int a = 0; a < in; a++){
			ret = walkOne(map, person);
			if(ret == null||!ret.isShouldcontinue()){
				return ret;
			}
		}
		return ret;
		
	}



	protected Returnable walkOne(FullMapData map, LivingBeing person) {
		HashMapData to = getTo();
		if(to == null){
			return null;
		}
		if(command != null){
			if(!command.execute(this)){
				return new Returnable(false,1);
			}
		}
		OutputDirector.log( "move",""+person.getPosition());
		
		//System.out.println(person.getPosition());
		//maybe have an execute init type idea for the first execute of an object
		if(to.isBlock()){
			person.setVariable("movedestination", to);
			to=to.getOpenHashMapDataOneRadius();
			setTo(to);
		}
		
		if(to == null){
			return new Returnable(false, 1);
		}
		
		boolean diffSym = false;
		Point toinside = null;
		
		
		diffSym=!person.getParent().getParent().equals(to.getParent());
			
		
		toinside=moveto;
		
		if(toinside == null){
			//move on symbolic map, pick place to move on full map data
			//if at edge then actually move symbolic map
			
			if(diffSym){
				
				map.getParent().clearVisited();
				PathFinder finder=new AStarPathFinder(map.getParent(), new AStarHeuristic());
				Path path = finder.findPath(person, 
						   			   person.getParent().getParent().getPosition().x, person.getParent().getParent().getPosition().y, to.getParent().getX(), to.getParent().getY());
				if(path == null){
					return null;
				}
				Step step=path.getStep(1);
				
				HashMapData h=map.getAllKeyValue(VConstants.portal, new PointBase(step.getX(),step.getY()));
				if(h != null){
					toinside = h.getPosition();
				}
				if(toinside == null){
					return null;
				}
			} else{
				toinside = to.getPosition();
			}
		
			moveto= toinside;
		}
		//move on fullmapdata from the picked points  
		Point tFrom = (Point) person.getParent().getPosition().clone();
		
		if(!diffSym&&tFrom.equals(toinside)){
			return new Returnable(false, 1);
		}
		
		Path path=movepath;
		Integer pathindex= movepathindex;
		
		if(path== null||path.getLength()+1 == pathindex){
			path = computePath(map, person, toinside, pathindex);
			pathindex = 1;
		}
		boolean movedToNext = false;
		if(path != null){
			//System.out.println(pathindex);
			Step step=path.getStep(pathindex);
			HashMapData data = map.getData(step.getX(),step.getY());
			if(data !=null&& !data.containsKey(VConstants.livingbeing)){
				movepathindex= ++pathindex;
				data.putLivingBeing(person);
			}else {
				if(person.getVariable("move.wait") != null){
					path = computePath(map, person, toinside, pathindex);
					if(path== null){
						return null;
					}
					pathindex = 1;
					movepathindex= pathindex;
					person.unsetVariable("move.wait");
					return new Returnable(true,1);
				}
				person.setVariable("move.wait", true);
			}
			movedToNext =path.getLength() == pathindex; 
		} else {
			//if the person is already at the current position
			if(person.getPosition().equals(toinside)){
				movedToNext = true;
			}
		}
		if(movedToNext){
			moveto = null;
			movepath = null;
			movepathindex = null;
			
		}
		if(per == null){
			per = person;
		}
////		if(per == person)
//			OutputDirector.log("move", "diffSym="+diffSym+" "+toinside+" "+person.getName()+" "+person.getPosition()+" fmdpos:"+person.getParent().getParent().getPosition()+" pathindex:"+pathindex+" "+descr);
		if(diffSym){
			if(movedToNext){
				
				//person.getParent().moveNextAvailableFullMapDataStartHashMap(person);
				return new Returnable(true,1);
			}
			
		} else {
			if(movedToNext){
				return new Returnable(false, 1);
			}
		}
		if(path == null){
			return new Returnable(false,1);
		} else{
			return new Returnable(true,1);
		}
	}



	protected Path computePath(FullMapData map, LivingBeing person,
			Point toinside, Integer pathindex) {
		Path path;
		map.clearVisited();
		PathFinder finder=new AStarPathFinder(map, new AStarHeuristic());
		path = finder.findPath(person, 
				   			   person.getPosition().x, person.getPosition().y, toinside.x, toinside.y);
		//pathindex = 1;
		movepath= path;
		movepathindex =  pathindex;
		return path;
	}
	static LivingBeing per;
	protected void move1(Point from, Point toPoint) {
		Direction dir;
		dir = getDirection(from, toPoint);
		moveDirection(from, dir);
	}


	protected void moveDirection(Point from, Direction dir) {
		from.y += dir.getY();
		from.x += dir.getX();
	}


	public Direction getDirection(Point from, Point toPoint) {
		Direction dir;
		int x = 0, y = 0;
		
		
		if( from.x > toPoint.x){
			x = -1;
		} else if(from.x < toPoint.x){
			x = 1;
		}
		
		if( from.y > toPoint.y){
			y = -1;
		} else if(from.y < toPoint.y){
			y = 1;
		}
		dir = Direction.get(x, y);
		return dir;
	}
	@Override
	public String toString() {
		HashMapData to = getTo();
		if(to == null){
			return super.toString();
		}
		return "Move "+get(VConstants.description) +" "+ to.getPosition();
	}
	public class PointAndListDir {
		Point p;
		List<Direction> d;
		public PointAndListDir(Point p, List<Direction> d) {
			super();
			this.p = p;
			this.d = d;
		}
	}
	
	@Override
	public OObject clone() {

		return new Move().copyProperties(this);
	}
	@Override
	public String getSoundName() {
		
		return "walk";
	}
	
}
