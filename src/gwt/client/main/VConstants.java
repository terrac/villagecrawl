package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.main.base.ActionHolder;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.google.gwt.user.client.Command;

public class VConstants {
	//public static final String person = "person";
	public static final String obstacle = "obstacle";
	public static final String gate = "gate";
	public static final String update = "update";
	public static final String template = "template";
	public static final String livingbeing = "livingbeing";
	public static final String foodgroup = "foodgroup";
	public static final String random = "random";
	public static final String dominant = "dominant";
	public static final String defaultT = "default";
	public static final String instinct = "instinct";
	public static final String under = "under";
	public static final String tribe = "tribe";
	public static final String friends = "friends";
	public static final String relationship = "relationship";
	public static final String family = "family";
	public static final String doer = "doer";
	public static final String corpse = "corpse";
	public static final String friendship = "friendship";
	public static final String doees = "doees";
	public static final String human = "human";
	public static final String animal = "animal";
	public static final String runaway = "runaway";
	public static final String leader ="leader";
	public static final String culture = "culture";
	public static final String pregnancyTime = "pregnancytime";
	public static final String domesticated = "domesticated";
	public static final String deer = "deer";
	public static final String person = "person";
	public static final String wolf = "wolf";
	public static final String cow = "cow";
	public static final String chicken = "chicken";
	public static final String farming = "farming";
	
	public static final String cuttree = "cuttree";
	public static final String needs = "needs";
	public static final String forest = "forest";
	public static final String items = "items";
	public static final String multiItem = "multiitem";
	
	
	public static final String spring = " spring";
	public static final String summer = "summer";
	public static final String fall = "fall";
	public static final String winter = "winter";
	public static final String defaultplant = "defaultplant";
	public static final String cultivated = "cultivatedRoots";
	public static final String slashandburn = "slashAndBurn";
	public static final String defaultfish = "defaultfish";
	public static final String pithouse = "pithouse";
	public static final String soil = "soil";
	public static final String water = "water";
	public static final String name = "name";
	public static final String integer = "integer";
	public static final String bool = "bool";
	public static final String type = "type";
	public static final String fire = "fire";
	public static final String large = "large";
	public static final String home = "home";
	public static final String metarun = "metarun";
	public static final String food = "food";
	public static final String temporary = "temporary";
	public static final String oObject = "oObject";
	public static final String item = "item";
	public static final String ggroup = "ggroup";
	public static final String initList = "initList";
	public static final String mapInitList = "mapInitList";
	public static final String root = "root";
	public static final String percentagemap = "percentagemap";
	public static final String init = "init";
	public static final String storage = "storage";
	public static final String defaultFormula = "defaultFormula";
	public static final String sittingspot = "sittingspot";
	public static final String preset = "preset";
	public static final String game = "game";
	public static final String team = "team";
	public static final String health = "health";
	public static final String physical = "physical";
	public static final String applydamage = "applydamage";
	public static final String fullmapdata = "fullmapdata";
	public static final String idnum = "idnum";
	public static final String speed = "speed";
	public static final String pause = "pause";
	public static final String turn = "turn";
	public static final String turns = "turns";
	public static final String statedata = "statedata";
	public static final String damagetype = "damagetype";
	public static final String damage = "damage";
	public static final String resistance = "resistance";
	public static final String air = "air";
	public static final String earth = "earth";
	public static final String range = "range";
	public static final String radius = "radius";
	public static final String defaultattack = "defaultattack";
	public static final String damagemodifier = "damagemodifier";
	public static final String visualdamage = "visualdamage";
	public static final String sound = "sound";
	
	public static final String currentlySelected = "currentlySelected";
	public static final String percenttake = "percenttake";
	public static final String level = "level";
	public static final String weapon = "weapon";
	public static final String gloves ="gloves";
	public static final String strength = "strength";
	public static final String value = "value";
	public static final String gold = "gold";
	public static final String allskills = "allskills";
	public static final String experience = "experience";
	public static final String levelup = "levelup";
	public static final String maxhealth = "maxhealth";
	public static final String rechargetime = "rechargetime";
	public static final String armor = "armor";
	
	public static final String alterholder = "alterholder";
	public static final String altertypes = "altertypes";
	public static final String list = "list";
	public static final String key = "key";
	public static final String description = "description";
	public static final String move = "move";
	public static final String actionlist = "actionlist";
	public static final String metaoobject = "metaoobject";
	public static final String action = "action";
	public static final String stats = "stats";
	public static final String classname = "classname";
	public static final String templatemap = "templatemap";
	public static final String xsize = "xsize";
	public static final String ysize = "ysize";
	public static final String imd = "imd";
	public static final String maparea = "maparea";
	public static final String param = "param";
	public static final String map = "map";
	public static final String htmlout = "htmlout";
	public static final String vparams = "vparams";
	public static final String tab = "tab";
	public static final String flextable = "flextable";
	public static final String personchoicedisplay = "personchoicedisplay";
	public static final String toget = "toget";
	public static final String toequal = "toequal";
	public static final String percent = "percent";
	public static final String bagmap = "bagmap";
	public static final String poison = "poison";
	public static final String message = "message";
	public static final String xsymbolic = "xsymbolic";
	public static final String ysymbolic = "ysymbolic";
	public static final String xfull = "xfull";
	public static final String yfull = "yfull";
	public static final String seed = "seed";
	public static final String expression = "expression";
	public static final String checknegative = "checknegative";
	public static final String pbase = "pbase";
	public static final String time = "time";
	public static final String timewait = "timewait";
	public static final String make = "make";
	public static final String namesorproperties = "namesorproperties";
	public static final String image = "image";
	public static final String activeai = "activeai";
	public static final String attach = "attach";
	public static final String abilityai = "abilityai";
	public static final String not = "not";
	public static final String ability = "ability";
	public static final String target = "target";
	public static final String enemy = "enemy";
	public static final String disabled = "disabled";
	public static final String runturn = "runturn";
	public static final Object self = "self";
	public static final String magic = "magic";
	public static final String damageadded = "damageadded";
	public static final Object white = "white";
	public static final String setup = "setup";
	public static final String abilitysetup = "abilitysetup";
	public static final String imagecache = "imagecache";
	public static final String turnwait = "turnwait";
	
	public static final String turnbased = "turnbased";
	public static final String turnindex = "turnindex";
	public static final String jsondatanames = "jsondatanames";
	public static final String savedpeople = "savedpeople";
	public static final String defaultimage = "defaultimage";
	public static final String repeat = "repeat";
	public static final String to = "to";
	public static final String debug = "debug";
	public static final String agedata = "agedata";
	public static final String physicaldata = "physicaldata";
	public static final String sight = "sight";
	public static final String size = "size";
	public static final String display = "display";
	public static final String walkspeed = "walkspeed";
	public static final String position = "position";
	public static final String namelist = "namelist";
	public static final String scene = "scene";
	public static final String positionlist = "positionlist";
	public static final String stored = "stored";
	public static final String main = "main";
	public static final String entrance = "entrance";
	public static final String saved = "saved";
	public static final String savedmap = "savedmap";
	public static final String overmap = "overmap";
	public static final String minimum = "minimum";
	public static final String maximum = "maximum";
	public static final String intelligence = "intelligence";
	public static final String classes = "classes";
	public static final String actor = "actor";
	public static final String categories = "categories";
	public static final Object friendly = "friendly";
	public static final String resource = "resource";
	public static final String heirarchy = "heirarchy";
	public static final String event = "event";
	public static final String start = "start";
	public static final String exit = "exit";
	public static final String enter = "enter";
	public static final String personality = "personality";
	public static final String rightclick = "rightclick";
	public static final String attributes = "attributes";
	public static final String leftclick = "leftclick";
	public static final String categorymap = "categorymap";
	public static final String previous = "previous";
	public static final String jsoncache = "jsoncache";
	public static final String charisma = "charisma";
	public static final String traits = "traits";
	public static final String unique = "unique";
	public static final String summon = "summon";
	public static final String evade = "evade";
	public static final String undead = "undead";
	public static final String heal = "heal";
	public static final String success = "success";
	public static final String remove = "remove";
	public static final String selectionname = "selectionname";
	public static final String none = "none";
	public static final String hunger = "hunger";
	public static final String thirst = "thirst";
	public static final String lavatory = "lavatory";
	public static final String sleep = "sleep";
	public static final String log = "log";
	public static final String overlay = "overlay";
	public static final String communal = "communal";
	public static final String economy = "economy";
	public static final String owned = "owned";
	public static final String portal = "portal";
	public static final String requirement = "requirement";
	public static final String unbuilt = "unbuilt";
	public static final String plant = "plant";
	public static final String needheirarchy = "needheirarchy";
	public static final String parent = "parent";
	public static final String need = "need";
	public static final String needmap = "needmap";
	public static final String maxsize = "maxsize";
	public static final String play = "play";
	public static final String priority = "priority";
	public static final String property = "property";
	public static final String full = "full";
	public static final String cache = "cache";
	public static final String arablesoil = "arablesoil";
	public static final String decay = "decay";
	public static final String timeofday = "timeofday";
	public static final String destroy = "destroy";
	public static final String cloak = "cloak";
	public static final String body = "body";
	public static final String head = "head";
	public static final String leg ="leg";
	public static final String shield = "shield";
	public static final String randomitem = "randomitem";
	public static final String face = "face";
	public static final String hair = "hair";
	public static final String base = "base";
	public static final String humanoid = "humanoid";
	public static final String neutral = "neutral";
	public static final String basic = "basic";
	public static final String money = "money";
	public static final String favor = "favor";
	public static final String wealth = "wealth";
	public static final String male = "male";
	public static final String female = "female";
	public static final String interval = "interval";
	public static final String equipment = "equipment";
	public static final String rare = "rare";
	public static final String book = "book";
	public static final String decorative = "decorative";
	public static final String market = "market";
	public static final String basictype = "basictype";
	public static final String normal = "normal";
	public static final String selection = "selection";
	public static final String wisdom = "wisdom";
	public static final String quest = "quest";
	public static final String leave = "leave";
	public static final String stay = "stay";
	public static final String die = "die";
	public static final String closeonclick = "closeonclick";
	public static final String activelist = "activelist";
	public static final String end = "end";
	public static final String nextquest = "nextquest";
	public static final String haggle = "haggle";
	public static final String lines = "lines";
	public static final String taggenerator = "taggenerator";
	public static final String country = "country";
	public static final String formula = "formula";
	public static final String sharpness = "sharpness";
	public static final String GDP = "GDP";
	public static final String unoccupied = "unoccupied";
	public static final String citygenerator = "citygenerator";
	public static final String startmessage = "startmessage";
	public static final String question = "question";
	public static final String agree = "agree";
	public static final String disagree = "disagree";
	public static final String response = "response";
	public static final String count = "count";
	public static final String symbolicmap = "symbolicmap";
	public static final String sickness = "sickness";
	public static final String honor = "corruption";
	public static final String efficency = "efficency";
	public static final String itemmap = "itemmap";
	public static final String storyevent = "storyevent";
	public static final Object authority = "authority";
	public static final Object religion = "religion";
	public static final String chance = "chance";
	public static final String dontdrawequipment = "dontdrawequipment";
	public static final String happiness = "happiness";
	public static final String wellness = "wellness";
	public static final String avatar = "avatar";
	public static final String nice = "nice";
	public static final String jerk = "jerk";
	public static final String extrovert = "extrovert";
	public static final String sensing = "sensing";
	public static final String thinking = "thinking";
	public static final String judging = "judging";
	public static final String race = "race";
	public static final String highest = "highest";
	public static final String buy = "buy";
	public static final String sell = "sell";
	public static final String copper = "copper";
	public static final String score = "score";
	public static final String debt = "debt";
	public static final String randomeffect = "randomeffect";
	public static final String calculatedvalue = "calculatedvalue";
	public static final String population = "population";
	public static final String imagelist = "imagelist";
	public static final String next = "next";
	public static final String growth = "growth";
	public static final String cost = "cost";
	public static final String conflict = "conflict";
	public static final String technology = "technology";
	public static final String enabled = "enabled";
	public static final String general = "general";
	public static final String age = "age";
	public static final String demographics = "demographics";
	public static final String hatred = "hatred";
	
	
	public static String string = "string";
	public static String script = "script";
	public static String getRandomEnumString(Enum[] en){
		return en[getRandom().nextInt(en.length)].name();
	}
	public static Random getRandom() {
		Random rand=EntryPoint.game.getRandom();
		
		return rand;
	}
	public static <T>T getRandomFromList(List<T> events) {
		if(events.size() == 0){
			return null;
		}
		return events.get(getRandom().nextInt(events.size()));
	}
	
	public static PBase getRandomFromList(List<PBase> events,double d ) {
		if(events.size() == 0){
			return null;
		}
		List<PBase> l = new ArrayList<PBase>();
		for(PBase pb : events){
			double max = (Double) pb.get(VConstants.maximum);
			double min = (Double) pb.get(VConstants.minimum);
			if(max > d &&min < d){
				System.out.println(max);
				System.out.println(min);
				System.out.println(d);
				l.add(pb);
			}
		}
		if(l.size() == 0){
			return null;
		}
		return l.get(getRandom().nextInt(l.size()));
	}
	public static Object getRandomFromPBase(PBase pBase2) {
		Object[] arr= pBase2.getObjMap().keySet().toArray();
		
		
		return pBase2.get((String) VConstants.getRandomFromList(Arrays.asList(arr)));
	}
	
	public static Object getRandomFromPBase2(PBase pBase2) {
		Object ret = null;
		Object[] array = pBase2.getObjMap().entrySet().toArray();
		int end = VConstants.getRandom().nextInt(array.length);
		for(int a = end+1; a != end; a++){
			if(a >= array.length){
				a = 0;
			}
			
			Entry<String,Object> ent = (Entry<String, Object>) array[a];
			int rInt = (Integer) ent.getValue();
			ret = ent.getKey();
			if(VConstants.getRandom().nextInt(100) > rInt){
				return ret;
			}
		}
		
		return ret;
	}
	
	public static PBase getRandomFromPBase3(List<PBase> pbl) {
		int end = VConstants.getRandom().nextInt(pbl.size());
		for(int a = end+1; a != end; a++){
			if(a >= pbl.size()){
				a = 0;
			}
			
			PBase pb =pbl.get(a);
			
			if(VConstants.getRandom().nextDouble() < (Double)pb.get(VConstants.random)){
				return pb;
			}
		}
		
		return null;
	}
//	public static PBase getRandomFromList(List<PBase> list,PBExecute pb){
//		int start = VConstants.getRandom().nextInt(list.size());
//		int end = start-1;
//		while(start != end){
//			if(start <= list.size()){
//				start =0;
//			}
//			PBase p = pb.execute(list.get(start));
//			if(p != null){
//				return p;
//			}
//			start++;
//		}
//		return null;
//		
//	}
//	public static abstract class PBExecute{
//		public abstract PBase execute(PBase p);
//	}
}
