package gwt.shared;

import gwt.client.EntryPoint;
import gwt.client.game.ApplyDamage;
import gwt.client.game.AttachUtil;
import gwt.client.game.AttackEnemyMeta;
import gwt.client.game.CreateRandom;
import gwt.client.game.MoveClosestNot;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.SellOne;
import gwt.client.game.display.ItemsDisplay;
import gwt.client.game.oobjects.BackgroundTrade;
import gwt.client.game.oobjects.Build;
import gwt.client.game.oobjects.BuildUnbuilt;
import gwt.client.game.oobjects.CheckStat;
import gwt.client.game.oobjects.Destroy;
import gwt.client.game.oobjects.FillNeed;
import gwt.client.game.oobjects.Haggle;
import gwt.client.game.oobjects.Healing;
import gwt.client.game.oobjects.HuntAnimal;
import gwt.client.game.oobjects.MoveStore;
import gwt.client.game.oobjects.MoveRandomGate;
import gwt.client.game.oobjects.ResetStat;
import gwt.client.game.oobjects.ResourceGather;
import gwt.client.game.oobjects.SpecialHaggle;
import gwt.client.game.oobjects.TradeItem;
import gwt.client.game.util.PointBase;
import gwt.client.game.vparams.AddNeed;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.ChooseFMD;
import gwt.client.game.vparams.CopySelection;
import gwt.client.game.vparams.Count;
import gwt.client.game.vparams.CreateAndDecay;
import gwt.client.game.vparams.Mercernaries;
import gwt.client.game.vparams.MovePerson;
import gwt.client.game.vparams.PersonTypeEffects;
import gwt.client.game.vparams.QuestGenerator;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.RunTurn;
import gwt.client.game.vparams.SelectAndApply;
import gwt.client.game.vparams.SetTemplate;
import gwt.client.game.vparams.TradeCultureRoute;
import gwt.client.game.vparams.adding.AddGDP;
import gwt.client.game.vparams.adding.AddToMarket;
import gwt.client.game.vparams.oneoff.Caught;
import gwt.client.game.vparams.oneoff.Jerk;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.rules.CreateItem;
import gwt.client.game.vparams.rules.GluttonyRule;
import gwt.client.game.vparams.rules.LaborRule;
import gwt.client.game.vparams.rules.NeedRule;
import gwt.client.game.vparams.rules.ScarcityRule;
import gwt.client.game.vparams.rules.TradeValueRule;
import gwt.client.game.vparams.rules.TypeRule;
import gwt.client.game.vparams.turnbased.Taxation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.ChildsPlay;
import gwt.client.main.Consume;
import gwt.client.main.Economy;
import gwt.client.main.Game;
import gwt.client.main.MakeItem;
import gwt.client.main.MapArea;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.MoveClosestDifferent;
import gwt.client.main.MoveRandomFullMapData;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.PickUp;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.WaitMove;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.statisticalciv.ConflictRule;
import gwt.client.statisticalciv.CreateInternal;
import gwt.client.statisticalciv.GrowthRule;
import gwt.client.statisticalciv.RunRules;
import gwt.client.statisticalciv.TechnologyRule;
import gwt.shared.datamodel.VExecute;
import gwt.shared.datamodel.VParams;

import java.util.List;

public class StatisticalCiv extends ClientBuild2 {
	/**
	 * create a new package that has statciv rules in it
	 * create a map of a beginning civ (rough outline of africa
	 * add in a process that runs once a turn on each tile
	 * @return
	 */
	public static Game doBasicMap() {
		Game game = new Game();

		// game.getMapInitList().add(new ExitTile(0,0,10,0));
		game.put(VConstants.main, true);
		game.put(VConstants.name, "Basic");
		//game.put(VConstants.symbolicmap, true);
		game.setMapArea(new MapArea());
		game.getMapArea().setMap(new SymbolicMap());
		game.put(VConstants.applydamage, new ApplyDamage());
		
		game.getMapArea().getMap().put(VConstants.xfull, 17);
		game.getMapArea().getMap().put(VConstants.yfull, 15);
		addG(VConstants.runturn, game, new RunTurn());
		game.getMapArea().put(VConstants.turnbased, false);
		AttachUtil.attach(AttachUtil.runpersonbefore, VConstants.runturn, game
				.getMapArea());


		{
		PTemplate pt = addTemplate(game, "person");

		String exp = addG("use", game, new CreateRandom("snake", "rat"));
		addAction(pt, exp);
		}
		PTemplate pt = addTemplate(game, "technology");

		String action = addG("technology", game, new TechnologyAction());
		addAction(pt, exp);		
		
		//add a growth rule
		//test watching the people grow across africa
		//lower growth in desert
		//formation of cities
		
		AttachUtil.attach(AttachUtil.runbefore, new RunRules(new GrowthRule(),new ConflictRule(),new TechnologyRule()), game
				.getMapArea());
		AttachUtil.attach(AttachUtil.mapstart, StatisticalCivMap.getMap1(), game.getMapArea());
		//AttachUtil.attach(AttachUtil.mapstart, StatisticalCivMap.getMap1(), game.getMapArea().getMap().getData(0, 0));
		AttachUtil.attach(AttachUtil.clickfmd, new CreateInternal(), game.getMapArea().getMap());
		
		
		return game;
	}

	public static PBase doStatMap() {
		return new PBase();
	}

}
