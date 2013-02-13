package gwt.shared;
import gwt.client.item.Item;
import gwt.client.item.ItemBuildProperties;
import gwt.client.item.SimpleMD;
import gwt.client.item.SimpleMDNumber;
import gwt.client.main.AnimalEat;
import gwt.client.main.BreakUpItem;
import gwt.client.main.CarnivoreAnimalEat;
import gwt.client.main.DeerPack;
import gwt.client.main.Eat;
import gwt.client.main.Farm;
import gwt.client.main.FollowParent;
import gwt.client.main.FormFamily;
import gwt.client.main.FormTribe;
import gwt.client.main.Friendship;
import gwt.client.main.Game;
import gwt.client.main.GatheringTrip;
import gwt.client.main.GetResource;
import gwt.client.main.HuntAndGather;
import gwt.client.main.HuntingTrip;
import gwt.client.main.MList;
import gwt.client.main.MakeBaby;
import gwt.client.main.MakeComplexItem;
import gwt.client.main.MakeDefaultItem;
import gwt.client.main.MakeFood;
import gwt.client.main.MakeItem;
import gwt.client.main.Needs;
import gwt.client.main.OneCharacterMap;
import gwt.client.main.OneStringChar;
import gwt.client.main.PTemplate;
import gwt.client.main.Pottery;
import gwt.client.main.Relationship;
import gwt.client.main.SowSeeds;
import gwt.client.main.Story;
import gwt.client.main.Structure;
import gwt.client.main.VConstants;
import gwt.client.main.WildAnimalEat;
import gwt.client.main.WolfPack;
import gwt.client.main.base.ActionHolder;
import gwt.client.main.base.FollowParentMeta;
import gwt.client.main.base.GGroup;
import gwt.client.main.base.ItemCreateMeta;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.StateMangementAction;
import gwt.client.main.base.StatemanagementMeta;
import gwt.client.main.base.under.Fish;
import gwt.client.main.base.under.Water;
import gwt.client.main.mapobjects.AddAnimals;
import gwt.client.main.mapobjects.Coast;
import gwt.client.map.FullMapData;
import gwt.client.state.State;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class buildjson {
	static String[] foods = new String[]{"baked bread", "porridge", "stew","cheese" ,"seasonal vegetables", "meat"};
	static String[] work = new String[]{"vegetable garden","wash","baking", "weaving","house cleaning", "milk","churn"};
	
	
	protected static Map<String,String> mapmap = new HashMap<String, String>();
	


	protected  void setServerMap(final String name, String filepath) {
	
	}


	
	static Map<String,String> statename = new HashMap(); static{
		String value = "TimeOfDay";
//		statename.put("Morning", value);
//		statename.put("Night", value);
//		
//		statename.put("Day", "NightOrDay");
//		
//		//statename.put("food", "CheckFood");
//		statename.put("Noon", value);
//		
//		value = "Month";
//		statename.put("March", value);
//		statename.put("September", value);
		
		
		for(int a = 0; a <18; a++){
			statename.put("hour" + a,value);
		}
			
		
		
	}
	static Game game;
	public static boolean ggroup ;
	public static boolean defaultActions ;
	public static boolean gtribe ;
	public static boolean harvest ;
	public static boolean eat ;
	
	public static void setVars() {
		ggroup = true;
		defaultActions = true;
		gtribe = true;
		harvest =true;
		eat = true;
		
			
	}
	public static Game test1(Game g) {
		game = g;
		
		
	
		return game;
	}
	private static void addFoods(String grains,String type) {
		for(String a : grains.split(",")){
			
			addItem(game, a.trim(),1,true,null,type);
			
		}
	}
	protected static Item addItem(Game game, String name,int time,boolean consumable) {
		return addItem(game, name, time, consumable, null,null);
	}
	protected static Item addItem(Game game, String name,boolean consumable) {
		return addItem(game, name,0, consumable, null,null);
	}
	protected static Item addItem(Game game, String name,int time,boolean consumable,String toMake,String type) {
		Item item = new Item();
		item.setName(name);
		item.setConsumable(consumable);
		item.put(VConstants.type,type);
		game.getItemMap().put(item.getKey(), item);
		String buildName = "defaultBuild"+name;
		if(toMake != null){
			game.getOOMap().put(buildName, new MakeDefaultItem(time,toMake,item));
			item.itemBuildAction = buildName;
		}
		return item;
	}
	private static PTemplate addTemplate(PTemplate root, String name) {
		
		PTemplate pTemplate = new PTemplate(name);
		//root.addChild(pTemplate);
		game.getTemplateMap().put(name, pTemplate);
		return pTemplate;
	}
	

	private static void addCharRep(List<OneStringChar> ol, String string, char c) {
		ol.add(new OneStringChar(c,string));
		
	}

		
		

	
	




	
	static List<OneStringChar> cur;
	public static FullMapData buildFmd(Game game,String st,List<OneStringChar> ol) {
		
		if(ol != null&&(cur == null||!cur.equals(ol))){
			
			for(OneStringChar o : ol){
				OneCharacterMap.hBimapMD.forcePut(o.string,o.character);
	
				OneCharacterMap.hIBimapMD.forcePut( o.character,o.string);
			}
			cur = ol;
		} else{
			OneCharacterMap.hBimapMD.clear();
			OneCharacterMap.hIBimapMD.clear();
		}
		
		
		FullMapData fmd = null;
			
			
			List<String> olist = Arrays.asList(st.split("\n"));
			
			int largestX = 0;
			int largestY = olist.size();
			for(int y = 0; y < olist.size() ; y++){
				char[] line = olist.get(y).trim().toCharArray();
				if(largestX < line.length){
					largestX = line.length;
				}
			}
			fmd = new FullMapData(largestX+1,largestY+1);
			for(int y = 0; y < olist.size() ; y++){
				char[] line = olist.get(y).trim().toCharArray();
				
				
				
				
//				if(fmd.getXsize() < line.length){
//					fmd.setXsize(line.length);
//				}
				for(int x = 0; x < fmd.getXsize()&&x < line.length; x++){
					if(line[x] == ' '){
						continue;
					}
					String key=OneCharacterMap.getString(line[x]);
					String ty = VConstants.gate;
					
					if(Arrays.asList(OneCharacterMap.obstacles).contains(key)){
						ty = VConstants.obstacle;
					}
					if("water".equals(key)){
						fmd.getData(x, y).putEvenIfNotEmpty(VConstants.under,new Water());
						
						fmd.getData(x, y).put(new Fish());
						continue;
					}
//					if("storage".equals(key)&&harvest){
//						fmd.getData(x, y).put(new SimpleMDNumber("itemstub", "wheat",5));
//					}

					//fmd.getData(x, y).put(new Soil());
					Item item=game.getItemMap().get(key);
					if(item != null){
						fmd.getData(x, y).getItems().put(item.clone());
					} else{
						fmd.getData(x, y).put(new SimpleMD(ty, key));
					}
					
				}
			 
				//use xml to build a list of objects which this uses.  It then builds the mapdatas
				//both of which can be used like the item map for actions
				
			}
	
		
		return fmd;
	}

	


	
	static String woods = 
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n"+
"FFFFFFFFFFFFFFFFFF\n";



	static String longhouse = 
	    "@@@@@@@@@@\r\n" + 
		"@    s   @\r\n" + 
		"@        @\r\n" + 
		"@        @.\r\n" + 
		"@   i    @\r\n" + 
		"@       b@\r\n" + 
		"@       o@\r\n" + 
		"@       Z@\r\n" + 
		"@        @.\r\n" + 
		"@        @\r\n" + 
		"@        @  K\r\n" + 
		"@@@dddd@@@\r\n" + 
		"...........\r\n" + 
		"hh...ee....\r\n" + 
		"..........";
	
	static String wheat_field = "wwwwwwwwwwww\r\n" + 
			"\r\n" + 
			"wwwwwwwwwwww\r\n" + 
			"\r\n" + 
			"wwwwwwwwwwww\r\n" + 
			"\r\n" + 
			"wwwwwwwwwwww\r\n" + 
			"\r\n" + 
			"wwwwwwwwwwww\r\n" + 
			"\r\n" + 
			"";
	static String generic="......FrFFF.......R\r\n" + 
	"...F.FFFrFFF.......\r\n" +
	".....F.FrFFFFF.....\r\n" +
	"...FRFFFrFF......F.\r\n" +
	"..F.FR.FrFFR..FF...\r\n" +
	"...F.FFFr.FF..FF...\r\n" +
	"...FFRFFrFFFR...F..\r\n" +
	"....RFFFrFFF.R.F...\r\n" +
	"..FF..FFrF.R.......\r\n" 
 ;
	
	static String pithouse =
		" .@@          \r\n" + 
		" @s @     t  \r\n" + 
		" @@ @\r\n" + 		
	    " @  @    \r\n" + 
	    " @  @   o    G\r\n" + 
	    " @dd@\r\n  " + 
	    ".        K\r\n" + 
	    ".   i   \r\n" + 
	    "";
	static {
		mapmap.put("longhouse", longhouse);
		mapmap.put("pithouse", pithouse);
		mapmap.put("wheat field", wheat_field);
		mapmap.put("woods", woods);
		mapmap.put("generic", generic);
//		for(final String name : filelist){
//			String filepath = "onec/"+name+".txt";
//			
//			
//			
//				//mapmap.put(name,SUtil.readFileAsString(filepath));
//			
//			
//
//			
//		}
	}
}