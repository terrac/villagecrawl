package gwt.client.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sourceforge.htmlunit.corejs.javascript.json.JsonParser;

import com.google.gwt.dev.json.JsonValue;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.ability.Heal;
import gwt.client.game.ability.Summon;
import gwt.client.game.display.ItemsDisplay;
import gwt.client.game.display.MapDataBuyDisplay;
import gwt.client.game.display.ItemEquipDisplay;
import gwt.client.game.display.LevelUpDisplay;
import gwt.client.game.display.PersonChoiceDisplay;
import gwt.client.game.display.SpeedDisplay;
import gwt.client.game.display.TemplateDisableDisplay;
import gwt.client.game.oobjects.BackgroundTrade;
import gwt.client.game.oobjects.Build;
import gwt.client.game.oobjects.BuildUnbuilt;
import gwt.client.game.oobjects.CheckStat;
import gwt.client.game.oobjects.Destroy;
import gwt.client.game.oobjects.FillNeed;
import gwt.client.game.oobjects.GetJob;
import gwt.client.game.oobjects.Guard;
import gwt.client.game.oobjects.Haggle;
import gwt.client.game.oobjects.Healing;
import gwt.client.game.oobjects.HuntAnimal;
import gwt.client.game.oobjects.MoveStore;
import gwt.client.game.oobjects.MoveRandomGate;
import gwt.client.game.oobjects.ResetStat;
import gwt.client.game.oobjects.SpecialHaggle;
import gwt.client.game.oobjects.TradeItem;
import gwt.client.game.oobjects.TradeRandom;
import gwt.client.game.util.PointBase;
import gwt.client.game.vparams.AddFood;
import gwt.client.game.vparams.AddNeed;
import gwt.client.game.vparams.AlterFilter;
import gwt.client.game.vparams.BodyStats;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CallTab;
import gwt.client.game.vparams.CategoryMap;
import gwt.client.game.vparams.CheckDamage;
import gwt.client.game.vparams.CheckAttack;
import gwt.client.game.vparams.ChooseFMD;
import gwt.client.game.vparams.ClickSelect;
import gwt.client.game.vparams.CopySelection;
import gwt.client.game.vparams.Count;
import gwt.client.game.vparams.CreateAndDecay;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.DisplayChoices;
import gwt.client.game.vparams.DoAll;
import gwt.client.game.vparams.Exit;
import gwt.client.game.vparams.ExitTile;
import gwt.client.game.vparams.GoTo;
import gwt.client.game.vparams.Mercernaries;
import gwt.client.game.vparams.PersonTypeEffects;
import gwt.client.game.vparams.QuestGenerator;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.SelectAndApply;
import gwt.client.game.vparams.SetTemplate;
import gwt.client.game.vparams.TradeCultureRoute;
import gwt.client.game.vparams.TradeRoute;
import gwt.client.game.vparams.MovePerson;
import gwt.client.game.vparams.PutMap;
import gwt.client.game.vparams.RemoveFood;
import gwt.client.game.vparams.RunTurn;
import gwt.client.game.vparams.SetCurrentFMD;
import gwt.client.game.vparams.SetupAbilities;
import gwt.client.game.vparams.SymbolicMapBuild;
import gwt.client.game.vparams.VMessage;
import gwt.client.game.vparams.adding.AddBuilding;
import gwt.client.game.vparams.adding.AddGDP;
import gwt.client.game.vparams.adding.AddScore;
import gwt.client.game.vparams.adding.AddToMarket;
import gwt.client.game.vparams.adding.Infect;
import gwt.client.game.vparams.adding.PersonCreation;
import gwt.client.game.vparams.adding.SendMoney;
import gwt.client.game.vparams.adding.MarketContains;
import gwt.client.game.vparams.oneoff.Caught;
import gwt.client.game.vparams.oneoff.Jerk;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.Formula;
import gwt.client.game.vparams.random.LeaveStayOrDie;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.random.RandomOverMapCreator;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.game.vparams.random.RandomSceneCreation;
import gwt.client.game.vparams.requirements.CultureStats;
import gwt.client.game.vparams.requirements.DoOnce;
import gwt.client.game.vparams.requirements.DoTurn;
import gwt.client.game.vparams.requirements.MapKeyValue;
import gwt.client.game.vparams.requirements.OutsideTheTown;
import gwt.client.game.vparams.requirements.PersonKeyValue;
import gwt.client.game.vparams.requirements.RecentlyDone;
import gwt.client.game.vparams.requirements.StoryEvent;
import gwt.client.game.vparams.requirements.TwoOptionEvent;
import gwt.client.game.vparams.rules.CreateItem;
import gwt.client.game.vparams.rules.GluttonyRule;
import gwt.client.game.vparams.rules.LaborRule;
import gwt.client.game.vparams.rules.NeedRule;
import gwt.client.game.vparams.rules.ScarcityRule;
import gwt.client.game.vparams.rules.TradeValueRule;
import gwt.client.game.vparams.rules.TypeRule;
import gwt.client.game.vparams.turnbased.Taxation;
import gwt.client.game.vparams.ui.AddTogetherPeople;
import gwt.client.game.vparams.ui.ChatWindow;
import gwt.client.game.vparams.ui.CloneDeposit;
import gwt.client.game.vparams.ui.DisplayDesc;
import gwt.client.game.vparams.ui.DisplayResult;
import gwt.client.game.vparams.ui.DrawFocus;
import gwt.client.game.vparams.ui.Score;
import gwt.client.game.vparams.ui.VEquals;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Animal;
import gwt.client.main.BreakUpItem;
import gwt.client.main.Carry;
import gwt.client.main.ChildHuntAndGather;
import gwt.client.main.ChildsPlay;
import gwt.client.main.Consume;
import gwt.client.main.DropAll;
import gwt.client.main.Eat;
import gwt.client.main.Economy;
import gwt.client.main.Farm;
import gwt.client.main.FollowParent;
import gwt.client.main.FormFamily;
import gwt.client.main.Game;
import gwt.client.main.HuntAndGather;
import gwt.client.main.LocalTemplate;
import gwt.client.main.MakeComplexItem;
import gwt.client.main.MakeDefaultItem;
import gwt.client.main.MakeItem;
import gwt.client.main.MapArea;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.MoveClosestDifferent;
import gwt.client.main.MoveRandomFullMapData;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.Needs;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.PickUp;
import gwt.client.main.Point;
import gwt.client.main.Structure;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.WaitMove;
import gwt.client.main.base.ActionHolder;
import gwt.client.main.base.FollowParentMeta;
import gwt.client.main.base.GGroup;
import gwt.client.main.base.ItemCreateMeta;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.mapobjects.AddPack;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.client.map.SymbolicMap;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.client.personality.Stats;
import gwt.client.rpc.IExecute;
import gwt.client.state.State;
import gwt.client.statisticalciv.ConflictRule;
import gwt.client.statisticalciv.CreateInternal;
import gwt.client.statisticalciv.FoodRule;
import gwt.client.statisticalciv.GrowthRule;
import gwt.client.statisticalciv.PeopleRule;
import gwt.client.statisticalciv.RunRules;
import gwt.client.statisticalciv.TechnologyRule;
import gwt.client.statisticalciv.UVLabel;
import gwt.client.statisticalciv.VillageRule;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.shared.buildjson;

import gwt.shared.datamodel.VExecute;
import gwt.shared.datamodel.VParams;

public class buildgame {

	static Map<String, String> statename = new HashMap();

	private static Map<String, PBase> map = new HashMap<String, PBase>();

	static {
		String value = "TimeOfDay";
		for (int a = 0; a < 18; a++) {
			statename.put("hour" + a, value);
		}

	}

	public static Game test1(Game game) {
		EntryPoint.game = game;
		// parseFile(game,game1);
		// if(true){
		// return game;
		// }

		// village1(game);

		// defendthetemple(game);

		// for(Entry<String,Object> ent :game.getObjMap().entrySet()){
		//			
		// StringBuffer ret = new StringBuffer();
		// PBase.fexport(ret,ent.getValue());
		// System.out.println(ret);
		//		
		//			
		// }
		System.out.println(game.export());
		game.getHtmlOut().init();
		OutputDirector.timer.executeList.add(new IExecute() {

			@Override
			public void execute() {
				EntryPoint.game.getHtmlOut().refreshFmds();
			}
		});
		return game;
	}

	public static void parseFile(Game game, String json) {

		addO(new PBase());
		addO(new MoveClosest());
		addO(new Item());
		addO(new Person());
		addO(new Stats());
		addO(new ApplyDamage());
		addO(new Items());
		addO(new MoveRandomHashMapData());
		addO(new MoveRandomFullMapData());
		addO(new OobList());
		addO(new PickUp());
		addO(new LevelUp());
		addO(new Animal());
		addO(new ActionHolder());
		addO(new AttackEnemyMeta());
		addO(new PTemplate());
		addO(game);
		addO(game.getHtmlOut());
		addO(new MapArea());
		addO(new MapDataAreaMap());
		addO(new BagMap());
		addO(new FullMapData());
		addO(new HashMapData());
		addO(new SymbolicMap());
		addO(new CloneDeposit());
		addO(new VExpression());
		addO(new AddTogetherPeople());
		addO(new LevelUpDisplay());
		addO(new Score());
		addO(new MapDataBuyDisplay());
		addO(new ItemEquipDisplay());
		addO(new LocalTemplate());
		addO(new VEquals());
		addO(new RandomTypeCreation());
		addO(new SellOne());
		addO(new IfKey());
		addO(new PlaySound());
		addO(new TemplateDisableDisplay());
		addO(new PersonChoiceDisplay());
		addO(new CreateRandom());
		addO(new PutMap());
		addO(new VMessage());
		addO(new FollowParentMeta());
		addO(new FollowParent());
		addO(new SimpleMD());
		addO(new GGroup());
		addO(new Structure());
		addO(new FormFamily());
		addO(new MakeDefaultItem());
		addO(new MakeItem());
		addO(new MakeComplexItem());
		addO(new HuntAnimal());
		addO(new ChildHuntAndGather());
		addO(new Eat());
		addO(new PercentageMap());
		addO(new AddPack());
		addO(new ItemCreateMeta());
		addO(new AddFood());
		addO(new RemoveFood());

		addO(new Heal());
		addO(new MoveClosestNot());
		addO(new SetupAbilities());
		addO(new CheckDamage());
		addO(new Attack());
		addO(new CreateListTurns());
		addO(new Carry());
		addO(new AlterFilter());
		addO(new RunTurn());
		addO(new CheckAttack());
		addO(new ExitTile());
		addO(new Move());
		addO(new Wait());
		addO(new BuildMap());
		addO(new DoAll());
		addO(new GoTo());
		addO(new RandomItemCreation());
		addO(new RandomPersonCreation());
		addO(new CallTab());
		addO(new RandomSceneCreation());
		addO(new VParams());
		addO(new SymbolicMapBuild());
		addO(new RandomOverMapCreator());
		addO(new DisplayAndOk());
		addO(new CategoryMap());
		addO(new Exit());
		addO(new DisplayDesc());
		addO(new ChatWindow());
		addO(new ClickSelect());
		addO(new MoveClosestDifferent());
		addO(new Summon());
		addO(new CopySelection());
		addO(new TradeRoute());
		addO(new CheckStat());
		addO(new MoveStore());
		addO(new ResetStat());
		addO(new Consume());
		addO(new Economy());
		addO(new ChooseFMD());
		addO(new VExecute());
		addO(new BuildUnbuilt());
		addO(new Farm());
		addO(new Guard());
		addO(new GetJob());
		addO(new AddNeed());
		addO(new MovePerson());
		addO(new ChildsPlay());
		addO(new gwt.client.game.oobjects.ResourceGather());
		addO(new BodyStats());
		addO(new TradeItem());
		addO(new PointBase());
		addO(new CreateAndDecay());
		addO(new Destroy());
		addO(new TradeRandom());
		addO(new SetCurrentFMD());
		addO(new QuestGenerator());
		addO(new PersonKeyValue());
		addO(new DisplayResult());
		addO(new DisplayChoices());
		addO(new SendMoney());
		addO(new RecentlyDone());
		addO(new OutsideTheTown());
		addO(new AddScore());
		addO(new LeaveStayOrDie());
		addO(new DoOnce());
		addO(new AddBuilding());
		addO(new DrawFocus());
		addO(new MapKeyValue());
		addO(new AddToMarket());
		addO(new DoTurn());
		addO(new MarketContains());
		addO(new TradeCultureRoute());
		addO(new Haggle());
		addO(new Formula());
		addO(new WaitMove());
		addO(new ComplexCityGenerator());
		addO(new TwoOptionEvent());
		addO(new Build());
		addO(new SelectAndApply());
		addO(new Count());
		addO(new SetTemplate());
		addO(new StoryEvent());
		addO(new MoveRandomGate());
		addO(new Healing());
		addO(new PersonCreation());
		addO(new AddGDP());
		addO(new Caught());
		addO(new FillNeed());
		addO(new SpecialHaggle());
		addO(new Jerk());
		addO(new CultureStats());
		addO(new BackgroundTrade());
		addO(new ItemsDisplay());
		addO(new RandomEffects());
		addO(new Taxation());
		addO(new PersonTypeEffects());
		addO(new Mercernaries());
		addO(new CreateItem());
		addO(new GluttonyRule());
		addO(new LaborRule());
		addO(new NeedRule());
		addO(new ScarcityRule());
		addO(new TradeValueRule());
		addO(new TypeRule());
		addO(new RunRules());
		addO(new ConflictRule());
		addO(new GrowthRule());
		addO(new TechnologyRule());
		addO(new CreateInternal());
		addO(new TechnologyAction());
		addO(new PeopleRule());
		addO(new FoodRule());
		addO(new VillageRule());
		addO(new RemovePerson());
		addO(new UVLabel());
		
		JSONObject jo;
		try {
			jo = (JSONObject) JSONParser.parseStrict(json);
		} catch (Exception e) {
			System.out.println(json);
			Window.alert(e.getMessage());
			return;
		}

		addjson(game, jo);
		// afterParse(game);
	}

	public static void afterParse(Game game) {
		game.getHtmlOut().init();
		
		// System.out.println(game.export());
		
		//setupMap(game);
		// OutputDirector.timer.executeList.add(new IExecute() {
		//
		// @Override
		// public void execute() {
		//				
		// }
		// });
	}

	public static void setupMap(Game game) {
		AttachUtil.shouldRun = false;
		if (game.getMapArea().getMap().containsKey(VConstants.imd)) {

			for (FullMapData fmd : game.getMapArea().getMap()) {
				if (fmd == null) {
					break;
				}
				// fmd.init();
				fmd.setParent(game.getMapArea().getMap());
				fmd.initIfNeeded();
				
				for (HashMapData hmd : fmd) {
					LivingBeing lb = hmd.getLivingBeing();

					
					if (lb != null) {
						setItemsParents(lb);
						if(!fmd.people.contains(lb)){
							fmd.addPerson(lb);
						}
						
					}
					hmd.setParent(fmd);
					for (MapData md : hmd.removableValues()) {
						if(!VConstants.livingbeing.equals(md.getKey())){
							md.setParent(hmd);
						}
						
					}

				}
				System.out.println();
			}
		}
		AttachUtil.shouldRun = true;
	}

	public static void setItemsParents(LivingBeing lb) {
		if(lb.getItems() != null){
			lb.getItems().setParent(lb);
			for (MapData it : lb.getItems().removableValues()) {
				it.setParent(lb.getItems());
			}	
		}
		

	}

	private static void addO(PBase moveClosest) {
		map.put(moveClosest.getClass().getName(), moveClosest);
	}

	public static Object addjson(Object o, JSONValue jv) {
		return addjson(o, jv, true);
	}
	public static Object addjson(Object o, JSONValue jv,boolean checkExisting) {
		Object obj = null;
		if (jv == null || jv.isNull() != null) {
			return null;
		}
		if (jv.isObject() != null) {
			JSONObject jo = jv.isObject();
			Set<String> s = jo.keySet();
			PBase pb2 = null;

			String stringValue = jo.get(VConstants.classname).isString()
					.stringValue();

			PBase pBase = map.get(stringValue);
			if(!checkExisting){
				pBase = new PBase();
			}
			else if (pBase == null) {
				Window.alert(stringValue + " missing");
				return null;
			}
			PBase old = null;
			if (o != null && stringValue.equals(o.getClass().getName())) {
				obj = pb2 = (PBase) o;
				old = pb2;
			} else {
				obj = pb2 = pBase.clone();
			}

			for (String str : s) {
				JSONValue j = jo.get(str);
				if (!VConstants.classname.equals(str)) {
					boolean flag = old != null && old.get(str) != null;
					Object addjson;
					if (flag) {
						addjson = addjson(old.get(str), j);
						// only additive, which means only leaves
						if (!(addjson instanceof PBase)
								&& !(addjson instanceof List)) {
							pb2.put(str, addjson);
						}
					} else {
						addjson = addjson(null, j,checkExisting);
						pb2.put(str, addjson);
					}

				}

			}

		}
		if (jv.isString() != null) {
			obj = jv.isString().stringValue();
			if ("null".equals(obj)) {
				obj = null;
			}

		}
		if (jv.isNumber() != null) {
			double db = jv.isNumber().doubleValue();
			
			if (!(db > 0 && db < 1)) {
				obj = (int) db;
			}else {
				obj = (double)db;
			}

		}
		if (jv.isBoolean() != null) {
			obj = jv.isBoolean().booleanValue();
		}
		if (jv.isArray() != null) {
			List l = new ArrayList();
			obj = l;
			List old = null;
			// I think this should work, I just don't want it
			// right now because I want additive lists
			// you could probably add it back in with a special identifier for
			// adding certain lists maybe
			// if((o instanceof List)&&jv.isArray().size() == ((List)
			// o).size()){
			// old = (List) o;
			// obj = old;
			// }
			if (o instanceof List) {
				l = (List) o;

			}
			for (int a = 0; a < jv.isArray().size(); a++) {
				JSONValue j = jv.isArray().get(a);
				Object addjson;
				if (old != null) {

					addjson = addjson(old.get(a), j);
					// only additive, which means only leaves
					if (!(addjson instanceof PBase)
							&& !(addjson instanceof List)) {
						old.set(a, addjson);
					}
				} else {
					addjson = addjson(null, j,checkExisting);
					l.add(addjson);
				}

			}

		}

		return obj;
	}

}