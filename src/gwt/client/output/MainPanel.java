package gwt.client.output;

import gwt.client.EntryPoint;
import gwt.client.edit.EditPage;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.StringUtils;
import gwt.client.map.SymbolicMap;
import gwt.client.output.html.GCanvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;





public abstract class MainPanel <Canvas> extends PBase{
	
	public static final int imagesize = 48;

	public HashMapData selectedTile;
	public Canvas symbolicShell;
	public Map<FullMapData, Canvas> fmdMap = new HashMap();
	protected int symbolicXSize = 700;
	int symbolicYSize = 700;
	public boolean startOpen = false;
	private LivingBeing currentlyFollowed;
	public LivingBeing getCurrentlyFollowed() {
		return currentlyFollowed;
	}
	public void setCurrentlyFollowed(LivingBeing currentlyFollowed) {
		this.currentlyFollowed = currentlyFollowed;
	}
	public List<LivingBeing> cfList = new ArrayList<LivingBeing>();
	boolean unsetC = false;


	public EditPage editPage;
	FullMapData lastMap;


	SymbolicMap symbolicMap;
	 
	 
	abstract protected Canvas addShell(AreaMap parent,int sizex,int sizey, SEventClick ml) ;
	
	int count = 4;
	protected void drawEquipment(final Canvas symbolicShell2, int y, int x,
			LivingBeing lb) {
	}
	protected void drawPerson(final Canvas symbolicShell2, int y, int x,
			LivingBeing lb) {
	}
	public void displaySymbolicMap(SymbolicMap map){
		if(!EntryPoint.game.getB(VConstants.symbolicmap)){
			return;
		}
		count++;
		if(count < 4){
			
			return;
		}
		count = 0;
		if(symbolicShell == null){
		
			symbolicXSize = map.getXsize() * imagesize;
			symbolicYSize = map.getYsize() * imagesize;
			symbolicShell= addShell(map,symbolicXSize,symbolicYSize,symbolic);
			symbolicMap = map;
		}
		clear(symbolicShell);
		//System.out.println(map);
		//drawAreaMap(map, symbolicShell);
		for(int x = 0; x < map.getWidth();x++){		
		   for(int y = 0; y < map.getHeight();y++){
			   FullMapData fmd = map.getData(x, y);
			   if(fmd.people.size() == 0){
				   drawImage(symbolicShell, y, x, fmd.getImage());
				   continue;
			   }
			   int count=fmd.getType(VConstants.cache).getInt("imagecount");
			   
			   //only alter one out of a few times and clear the prev
			   //image
			   if(count >= fmd.people.size()){
				   count = 0;
				   drawImage(symbolicShell, y, x, fmd.getImage());
			   } else {
				   LivingBeing lb=fmd.people.get(count);
				  // drawImage(symbolicShell, y, x, lb.getImage());
				   drawPerson(symbolicShell, y, x, lb);
				   count++;
			   }
			   fmd.getType(VConstants.cache).put("imagecount",count);
			   
		   }
		}
		
		
			   
		
		if(startOpen){
			displayAreaMap(map.getData(1,0));
		}
	}
	protected void clear(Canvas symbolicShell2) {
		
	}
	public  void displayAreaMap(LivingBeing lb) {
		FullMapData parent = lb.getParent().getParent();
		
		

		displayAreaMap(parent);
		
			
			
	}
	protected void init() {
		//displayTopPanel();
	}
	//String[] equipOrder = new String[]{VConstants.weapon,VConstants.gloves};
	public abstract void drawAreaMap(AreaMap parent,final Canvas symbolicShell2);
	public void drawAreaMapChoice(IDecision oo, LivingBeing person,List plist) {
		
		AreaMap parent = person.getParent().getParent().getParent();
		
		
		//label = (Label) shell.getChildren()[0];
		//String output=parent.toStringChoice(plist);
		
		//drawAreaMap(parent, symbolicShell, output);
	}
	protected void drawAreaMap(AreaMap parent,
			final Canvas symbolicShell2, String output) {
		String[] outputArray = output.split("-");
		int i = 0;
		
		for(int y = 0; y < parent.getYsize();y++){
			 for(int x = 0; x < parent.getXsize()+1;x++){
				 	
				 if(i >= outputArray.length) return;
				 	
				String text = outputArray[i];
				
				if(StringUtils.isEmpty(text)){
					text ="none";
				}
				
				text = text.replace(' ','-' );
				
				drawImage(symbolicShell2, y, x, text);
				
				
				//id.addErrorHandler(imageerrorhandler);
				//if(id != null){
				
//				
//				}else {
//				symbolicShell2.setWidget(x, y, new Label(""+OneCharacterMap.get1CharRep(text)));	
//				
//				}
				i++;
			 }
		}
	}
	
	public FullMapData currentFMD;
	abstract protected boolean drawImage(Canvas symbolicShell2, int y, int x, String text); 
	public void displayAreaMap(FullMapData parent) {
		//for testing
//		if(getCurrentlyFollowed() == null&&!unsetC){
//			List<LivingBeing> playerTeam = GameUtil.getPlayerTeam(currentFMD.people);
//			if(playerTeam.size() != 0){
//				LivingBeing lb = playerTeam.get(0);
//				setCurrentlyFollowed(lb);
//				cfList.add(lb);	
//			}			
//		}
		
		if(getCurrentlyFollowed() != null&&getCurrentlyFollowed().getParent() != null){
			
			
			displayMapData(getCurrentlyFollowed().getParent());
			
//			if(currentlyFollowed.equals(lb)){
//				if(!currentlyFollowed.getParent().getParent().equals(lastMap)){
//					fmdMap.get(parent).dispose();
//					fmdMap.remove(parent);
//				}
//			}
//			lastMap = currentlyFollowed.getParent().getParent();
		}

		Canvas shell = fmdMap.get(parent);
		
		
		
//		if(shell == null){
//			boolean addable = false;
//			if(fmdMap.size() >= getFmdSize()){
//				//reuse based on the person calling the display area map
//				//so it gets called with person and the map actually maps to specific people 
//				//rather than fmds
//				
//				//but you still keep the fmdmap
//				//you just add an additional person map
//				//to where a fmd without people or the chosen person gets reused first
//				
//				//additionally we will want to add options to change things
//				//and show some basic images on the display hmd in order to make this a game
//				//return;
//				
//				for(FullMapData fmd:fmdMap.keySet()){
//					if(GameUtil.getPlayerTeam(fmd.people).size() == 0){
//						remove(fmdMap.get(fmd));
//						fmdMap.remove(fmd);
//						addable = true;
//						break;
//					}
//				}
//			}	
//			if(addable||fmdMap.size() < getFmdSize()){
//				shell = addShell(parent,parent.getWidth() * imagesize,parent.getHeight() * imagesize,full);
//				fmdMap.put(parent, shell);
//			}else {
//				return;
//			}
//		}
		if(!fmdMap.containsKey(parent)){
			for(FullMapData fmd:fmdMap.keySet()){
				remove(fmdMap.get(fmd));
				fmdMap.remove(fmd);	
			}
			
			shell = addShell(parent,parent.getWidth() * imagesize,parent.getHeight() * imagesize,full);
			fmdMap.put(parent, shell);
		}
		drawAreaMap(parent, shell);

	}
	protected boolean getEdit() {
		return true;
	}
	protected int getFmdSize() {
		return 2;
	}
	
	abstract public void remove(Canvas canvas);
	SEventClick<Canvas> symbolic = new SEventClick<Canvas>() {

		@Override
		public void execute(int x, int y, Canvas sender) {
			
			setCurrentlyFollowed(null);
			FullMapData md=symbolicMap.getData(x / imagesize, y / imagesize);
			
			EntryPoint.game.getHtmlOut().currentFMD = md;
			playCurrentMapMusic();
			refreshFmds();
			//displayAreaMap(((FullMapData) md));	
			
		}
	};
	
	
	public SEventClick<Canvas> getSymbolic() {
		return symbolic;
	}

	SEventClick<Canvas> full = new SEventClick<Canvas>() {

		@Override
		public void execute(int x, int y, Canvas sender) {			
			
			for(Entry<FullMapData, Canvas> ent : fmdMap.entrySet()){
				if(ent.getValue().equals(sender)){
					
					HashMapData md= ent.getKey().getData(x / imagesize, y / imagesize);
					
					AttachUtil.run(AttachUtil.clickfmd, md, EntryPoint.game.getMapArea().getMap());
					displayMapData(  md);
				}
			}	
			
		}
	};



	protected LivingBeing lastSelected;

	abstract public  void displayMapData(HashMapData mapData) ;
	public void setCurrentlyFollwedUnset(HashMapData mapData) {
		
		LivingBeing lb = (LivingBeing) mapData.get(VConstants.livingbeing);
		setCurrentlyFollowedUnset(lb);
	}
	public void setCurrentlyFollowedUnset(LivingBeing lb) {
		if(unsetC){
			return;
		}
		
		if(lb != null){
			if(lb.getParent() != null){
				//lb.getParent().drawn = null;
				lb.getParent().getParent().getImgCache().clearPositional(lb.getParent().getX(), lb.getParent().getY());
			}
			
			setCurrentlyFollowed( lb);
			cfList.clear();
			cfList.add(lb);
		} else {
			lastSelected  = getCurrentlyFollowed();
			setCurrentlyFollowed(null);
			
			unsetC = true;
		}
		if(!cfList.contains(getCurrentlyFollowed())){
			cfList.clear();
		}
	}
	public void refreshFmds(){
			displaySymbolicMap(EntryPoint.game.getMapArea().getMap());
			if(currentFMD == null){
				return;
			}
			displayAreaMap(currentFMD);
		
	}
	public void displayTopPanel(){
		
	}
	public abstract void say(String name, String say) ;
	public void playCurrentMapMusic() {
		String sound=EntryPoint.game.getHtmlOut().currentFMD.getS(VConstants.sound);
		if(sound != null){
			OutputDirector.soundPlayer.playMusic(sound);
		} else {
			OutputDirector.soundPlayer.stopMusic();
		}
	}
	
}
