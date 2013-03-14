package gwt.shared;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.ApplyDamage;
import gwt.client.game.AttachUtil;
import gwt.client.game.Attack;
import gwt.client.game.AttackEnemyMeta;
import gwt.client.game.CreateListTurns;
import gwt.client.game.CreateRandom;
import gwt.client.game.GameUtil;

import gwt.client.game.IfKey;
import gwt.client.game.LevelUp;
import gwt.client.game.MoveClosestNot;
import gwt.client.game.PlaySound;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.SellOne;
import gwt.client.game.UIList;
import gwt.client.game.VExpression;
import gwt.client.game.buildgame;
import gwt.client.game.ability.Heal;
import gwt.client.game.ability.Summon;
import gwt.client.game.display.MapDataBuyDisplay;
import gwt.client.game.display.ItemEquipDisplay;
import gwt.client.game.display.LevelUpDisplay;
import gwt.client.game.display.TemplateDisableDisplay;
import gwt.client.game.vparams.AddFood;
import gwt.client.game.vparams.AlterFilter;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CallTab;
import gwt.client.game.vparams.CheckDamage;
import gwt.client.game.vparams.CheckAttack;
import gwt.client.game.vparams.DoAll;
import gwt.client.game.vparams.ExitTile;
import gwt.client.game.vparams.GoTo;
import gwt.client.game.vparams.PutMap;
import gwt.client.game.vparams.RemoveFood;
import gwt.client.game.vparams.RunTurn;
import gwt.client.game.vparams.SetupAbilities;
import gwt.client.game.vparams.VMessage;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.game.vparams.random.RandomSceneCreation;
import gwt.client.game.vparams.ui.AddTogetherPeople;
import gwt.client.game.vparams.ui.CloneDeposit;
import gwt.client.game.vparams.ui.Score;
import gwt.client.game.vparams.ui.VEquals;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Animal;
import gwt.client.main.BreakUpItem;
import gwt.client.main.Carry;
import gwt.client.main.Eat;
import gwt.client.main.FollowParent;
import gwt.client.main.FormFamily;
import gwt.client.main.Game;
import gwt.client.main.HuntAndGather;
import gwt.client.main.MakeComplexItem;
import gwt.client.main.MakeDefaultItem;
import gwt.client.main.MapArea;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.MoveClosestDifferent;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.PickUp;
import gwt.client.main.Structure;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
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
import gwt.client.map.FullMapData;
import gwt.client.map.SymbolicMap;
import gwt.client.output.HtmlOut;
import gwt.client.personality.Stats;
import gwt.shared.datamodel.VParams;

public class ClientBuild {

	public static Game doAnimalBabies() {
		Game g = new Game();

		buildjson.setVars();
		buildjson.test1(g);
		g.setMapArea(7, 7);
		g.getMapInitList().add(new AddPack(1, 0, VConstants.human));

		g.getLivingBeingGroupMap().get(VConstants.deer).putStatic(
				VConstants.pregnancyTime, 0);
		// g.getMapInitList().clear();
		// g.getMapInitList().add(new AddAnimals());

		return g;
	}

	public static Game doEbbAndFlow() {
		Game g = new Game();

		buildjson.setVars();
		buildjson.test1(g);
		g.setMapArea(3, 3);
		g.getMapInitList().clear();
		g.getMapInitList().add(new AddPack(0, 0, VConstants.deer));
		g.getMapInitList().add(new AddPack(1, 0, VConstants.human));

		return g;
	}

	public static Game doManyPeople() {
		Game g = new Game();

		buildjson.setVars();
		buildjson.test1(g);
		g.setMapArea(5, 5);
		// g.getMapInitList().clear();
		g.getMapInitList().add(new AddPack(1, 0, VConstants.human));
		g.getMapInitList().add(new AddPack(1, 0, VConstants.human));
		g.getMapInitList().add(new AddPack(1, 0, VConstants.human));

		return g;
	}

	public static Game doJomon() {
		Game g = new Game();
		g.put(VConstants.name, "Jomon");
		g.getMapInitList().add(new AddPack(1, 0, VConstants.human));
		ClientBuild.village1(g);
		ClientBuild.village2(g);

		return g;
	}

	public static PBase doBigSnake() {

		PBase pb = new PBase();
		pb.put(VConstants.classname, Game.class.getName());

		Stats snakestats = new Stats();

		Animal snake = new Animal("snake", snakestats);
		snakestats.put(VConstants.strength, 50);
		snake.put(VConstants.level, 1);
		snake.getTemplate().setRationalChild("type", "animal");
		addG("snake", pb, snake);
		snake.put(VConstants.team, "snake");

		return pb;
	}

//	public static PBase doScenesFull() {
//
//		PBase pb = new PBase();
//		pb.put(VConstants.classname, Game.class.getName());
//		String vgd = "village gets destroyed";
//		String snakesandrats = "snakes and rats";
//		String dunen = "in a dungeon held captive by enemy";
//		List scene1 = list(snakesandrats, vgd, dunen);
//		List scene2 = list("scene circus with illusions", "beast tamer",
//				"old abandoned temple", "corrupted forest", "orcs", "swamp");
//		List scene3 = list("hell, undead kingdom, demons attack city");
//		List scene4 = list("enemy's lair", "enemy then real enemy",
//				"enemy besieging main city", "enemy being besieged");
//
//		// build a list of maps and such for each map,
//		// design a couple of lines of dialogue too
//
//		PBase charmap = new PBase();
//
//		charmap.put("H", "Hero");
//		charmap.put("b", "fire");
//		charmap.put("h", "house");
//		charmap.put("e", "enemy");
//		charmap.put("E", "enemy general");
//
//		BuildMap villagedestroyed = new BuildMap(charmap,
//				"                  \\n" + " H    hb   X          \\n"
//						+ "    b ee  h          \\n"
//						+ "      ee     b      \\n" + "    h E  h          \\n"
//						+ "      b            \\n" + "  h        h        \\n"
//						+ "            ");
//
//		charmap = new PBase();
//		charmap.put("w", "wall");
//		charmap.put("H", "Hero");
//		charmap.put("E", "enemy general");
//		BuildMap dungeonenemy = new BuildMap(charmap, "wwwwwwwwwwwww\\n"
//				+ "w\\n" + "w  H  E\\n" + "w\\n" + "w\\n" + "w\\n"
//				+ "wwwwwwwwwwww");
//
//		PBase rscMap = new PBase();
//
//		RandomSceneCreation rsc = new RandomSceneCreation(list(0, 1, 1, 2, 3),
//				list(scene1, scene2, scene3, scene4));
//		rsc.add(vgd, villagedestroyed);
//		rsc.add(dunen, dungeonenemy);
//		rsc.add(snakesandrats, new RandomTypeCreation(list(0, 0),
//				VConstants.person, 2, "rat", "snake"));
//		pb.put(VConstants.scene, rsc);
//
//		AttachUtil.attach(AttachUtil.gamestart, rsc, pb);
//
//		return pb;
//	}

	public static PBase doSummoner() {
		PBase pb = new PBase();
		pb.put(VConstants.name, "summoner");
		pb.put(VConstants.classname, Game.class.getName());

		PTemplate pt = addTemplate(pb, "summon");

		String exp = addG("use", pb, new CreateRandom("snake", "rat"));
		addAction(pt, exp);

		{
			Person person = new Person("summoner");
			// person.getStats().put(VConstants.intelligence, 7);
			person.put(VConstants.level, 5);
			person.getTemplate().setRationalChild("type", "summon");
			// oobject that

			addG("summoner", pb, person);
			person.put(VConstants.team, "summoner");
		}

		String addp = addG("addsummon", pb, new PutMap(15, 15,
				VConstants.livingbeing, "summoner", VConstants.person));
		AttachUtil.attach(AttachUtil.gamestart, addp, pb);
		AttachUtil.attach(AttachUtil.gamestart, new VMessage(
				" I am a summoner, blah blah blah , story and stuff"), pb);
		return pb;
	}

//	public static PBase doThievesAttackingMarquis() {
//		// eventually combine into 1 game with the complicated item
//		// randomization
//		// and additional people/abilities
//
//		PBase pb = new PBase();
//
//		pb.put(VConstants.classname, Game.class.getName());
//
//		{
//			Person person = new Person("marquis");
//			// person.getStats().put(VConstants.intelligence, 7);
//			person.put(VConstants.level, 5);
//			person.getTemplate().setRationalChild("type", "marquis");
//			// oobject that
//
//			addG("marquis", pb, person);
//			person.put(VConstants.team, "human");
//		}
//		AttachUtil.attach(AttachUtil.gamestart, new RandomTypeCreation(list(0,
//				0), VConstants.person, 2, "rogue"), pb);
//
//		String addp = addG("addperson", pb, new PutMap(15, 15,
//				VConstants.livingbeing, "marquis", VConstants.person));
//		AttachUtil.attach(AttachUtil.gamestart, addp, pb);
//		AttachUtil.attach(AttachUtil.gamestart, new VMessage(
//				" You, nobility, help me with this rabble"), pb);
//		AttachUtil.attach(AttachUtil.gamestart, new VMessage(
//				" Lose condition the marquis dies"), pb);
//		return pb;
//	}
//
//	public static PBase doMarquisBetrayal() {
//
//		PBase pb = new PBase();
//
//		pb.put(VConstants.classname, Game.class.getName());
//
//		{
//			Person person = new Person("marquis");
//			// person.getStats().put(VConstants.intelligence, 7);
//			person.put(VConstants.level, 5);
//			person.getTemplate().setRationalChild("type", "marquis");
//			// oobject that
//
//			addG("marquis", pb, person);
//			person.put(VConstants.team, "human");
//		}
//
//		AttachUtil.attach(AttachUtil.gamestart, new RandomTypeCreation(list(0,
//				0), VConstants.person, 2, "knights"), pb);
//
//		String addp = addG("addperson", pb, new PutMap(15, 15,
//				VConstants.livingbeing, "marquis", VConstants.person));
//		AttachUtil.attach(AttachUtil.gamestart, addp, pb);
//		AttachUtil
//				.attach(
//						AttachUtil.gamestart,
//						new VMessage(
//								" I have come against you because you help the less fortunate"),
//						pb);
//		AttachUtil.attach(AttachUtil.gamestart, new VMessage(
//				" Win condition, kill the marquis"), pb);
//		return pb;
//	}
//
//	public static PBase doExitDisplayAndSecondMap() {
//
//		PBase pb = new PBase();
//		pb.put(VConstants.name, "Second level");
//		pb.put(VConstants.classname, Game.class.getName());
//
//		PBase ma = new PBase();
//		pb.put(VConstants.maparea, ma);
//		ma.put(VConstants.classname, MapArea.class.getName());
//
//		PutMap o = new PutMap(AttachUtil.personStartOnMap, list(
//				new RandomTypeCreation(VConstants.item, 5, "gold"),
//				new RandomTypeCreation(VConstants.item, 5, items),
//				new RandomTypeCreation(VConstants.person, 2, "orc", "goblin")));
//		o.setSymbolic(1, 0);
//		String addp = addG("addsecond", pb, o);
//		AttachUtil.attach(AttachUtil.gamestart, addp, pb);
//		// new PutOnArea(1,0,AttachUtil.personStartOnMap,new
//		// RandomTypeCreation(VConstants.item,5,"gold"));
//
//		// AttachUtil.attach(AttachUtil.personStartOnMap, , secondFMD);
//		// AttachUtil.attach(AttachUtil.personStartOnMap, new
//		// RandomTypeCreation(VConstants.item,5,"gold"), secondFMD);
//
//		// AttachUtil.attach(AttachUtil.personStartOnMap, , secondFMD);
//
//		{
//			Person orc = new Person("orc");
//			orc.getStats().put(VConstants.strength, 7);
//			orc.getStats().put(VConstants.maxhealth, 100);
//			orc.put(VConstants.level, 2);
//			orc.getTemplate().setRationalChild("type", "animal");
//			addG("orc", pb, orc);
//			orc.put(VConstants.team, "orc");
//			orc.getStats().put(VConstants.defaultattack, "sword");
//		}
//		{
//			Person orc = new Person("goblin");
//			orc.getStats().put(VConstants.strength, 4);
//			orc.getStats().put(VConstants.maxhealth, 100);
//			orc.put(VConstants.level, 2);
//			orc.getTemplate().setRationalChild("type", "animal");
//			addG("goblin", pb, orc);
//			orc.put(VConstants.team, "orc");
//			orc.getStats().put(VConstants.defaultattack, "sword");
//		}
//		return pb;
//	}
//
//	public static PBase doFirstMapEnemies() {
//
//		PBase pb = new PBase();
//		pb.put(VConstants.name, "First level");
//
//		pb.put(VConstants.classname, Game.class.getName());
//
//		AttachUtil.attach(AttachUtil.gamestart, new RandomTypeCreation(list(0,
//				0), VConstants.item, 5, items), pb);
//		AttachUtil.attach(AttachUtil.gamestart, new RandomTypeCreation(list(0,
//				0), VConstants.item, 5, "gold"), pb);
//		AttachUtil.attach(AttachUtil.gamestart, new RandomTypeCreation(list(0,
//				0), VConstants.person, 2, "rat", "snake"), pb);
//		return pb;
//	}

	public static String[] items = new String[] { "sword", "shield", "gloves",
			"leather_armor", "cap" };

	public static Game doDC1() {
		Game game = defaultGameStuff();

		addItem(game, "corpse", false);

		PTemplate root = addTemplate(game, "root");

		PTemplate animal = addTemplate(game, "animal");

		addAction(animal, addG("explore", game, new MoveRandomHashMapData(
				"explore")));
		animal.setMetaoobj(new AttackEnemyMeta());

		// set
		{
			Stats ratstats = new Stats();
			ratstats.put(VConstants.strength, 1);
			Animal rat = new Animal("rat", ratstats);
			createAttack("bite", VConstants.physical, 1, 1, 4, game);
			ratstats.put(VConstants.maxhealth, 100);
			rat.put(VConstants.level, 1);
			rat.getTemplate().setRationalChild("main", "animal");
			addG("rat", game, rat);
			rat.put(VConstants.team, "rat");

		}
		{
			Stats snakestats = new Stats();

			Animal snake = new Animal("snake", snakestats);
			snakestats.put(VConstants.strength, 5);
			snakestats.put(VConstants.maxhealth, 100);

			PBase pb = createAttack("poisonbite", VConstants.physical, 1, 1, 5,
					game);
			pb.put(VConstants.poison, 3);

			snake.put(VConstants.level, 1);
			snake.getTemplate().setRationalChild("main", "animal");
			addG("snake", game, snake);
			snake.put(VConstants.team, "snake");

		}

		String movetonearestitem = addG("movetonearestitem", game,
				new MoveClosest(VConstants.items, null, 20));
		String movetonearestenemy = addG("movenearestenemy", game,
				new MoveClosestDifferent(VConstants.livingbeing + "."
						+ VConstants.team, VConstants.team, true, 20));
		String wait = addG("wait1", game, new Wait(1));
		PTemplate attack = addTemplate(game, "attack");
		addAction(attack, movetonearestenemy);
		attack.setMetaoobj(new AttackEnemyMeta());

		PTemplate defend = addTemplate(game, "defend");
		addAction(defend, wait);
		defend.setMetaoobj(new AttackEnemyMeta());
		String pickitemsup = addG("pickitemsup", game, new PickUp());
		PTemplate gatheritems = addTemplate(game, "gatheritems");
		String gathitems = addG("gatheritems", game, new OobList(
				movetonearestitem, pickitemsup));
		addAction(gatheritems, gathitems);
		gatheritems.setMetaoobj(new AttackEnemyMeta());
		String human = addG("human", game, new PBase());

		{
			PTemplate callability = addTemplate(game, "summon");
			String summon = addG("summono", game, new Summon());
			addAction(callability, summon);
		}
		{
			PTemplate callability = addTemplate(game, "heal");
			String abil = addG("heal", game, new Heal());
			addAction(callability, abil);
		}

		// final BagMap bagmap = new BagMap(5, 2);
		// String bagmapname = addG("bagmapname", game, bagmap);
		// EntryPoint.game.getHtmlOut().flextable.setWidget(0, 1,
		// bagmap.getWidget());

		// List<BagMap> l=new ArrayList();
		// l.add(bagmap);
		// OutputDirector.mpane.put(HtmlOut.refreshList, l);

		// game.getHtmlOut().getList(HtmlOut.refreshList).add(bagmapname);

		// attach additional check that

		// 

		// Person person = new Person("fighter");
		// person.put(VConstants.team, "1");
		// // person.getTemplate().setRationalChild("type", "defend");
		// person.getTemplate().setRationalChild("main", "gatheritems");

		// (later)each set has their own family and leaders and they fight as a
		// team
		// game.put(VConstants.allskills, new PBase());
		// Person sp;
		PBase allSkills = getType(getType(game, VConstants.person),
				VConstants.allskills);

		{
			// sp = person.clone();
			// //game.put(VConstants.livingbeing, sp);
			// String type = "fighter";
			// sp.getStats().put(VConstants.strength, 5);
			// sp.put("gold", 50);
			//
			// sp.put(VConstants.level, 1);
			//
			// sp.getStats().put(VConstants.maxhealth, 100);
			//
			// sp.setType(type);
			// sp.put(VConstants.levelup, new LevelUp());
			//
			//			
			//			
			// createAttack("sword",VConstants.physical,1,1,10,sp,game);
			// game.getPersonMap().put(sp.getType(), sp);
			//
			// LivingBeing terra = sp.clone();
			// terra.getStats().put(VConstants.strength, 10);
			// terra.getStats().put(ApplyDamage.pevade, 30);
			// terra.put(VConstants.image, "/images/terra.png");
			// terra.put(VConstants.main, true);
			// terra.setName("terra");
			// Item item = addItem(game,"fancysword",false);
			// item.put(VConstants.type, VConstants.weapon);
			// item.put(VConstants.damageadded, 4);
			// terra.getItems().put(item);
			// // AttachUtil.attach(AttachUtil.gamestart, new PutMap( 5, 5,
			// // VConstants.livingbeing, terra, VConstants.person), game);
			// // game.getMapArea().getMap().getData(0, 0).initIfNeeded();
			//
			// getType(game, VConstants.stored).put("hero", terra);
			//			
			// LivingBeing enemy = sp.clone();
			// enemy.put(VConstants.image, "/images/orc.png");
			// enemy.put(VConstants.team, "orc");
			// LivingBeing enemygeneral = sp.clone();
			// enemygeneral.put(VConstants.image, "/images/evil.png");
			// enemygeneral.put(VConstants.team, "orc");
			// getType(game, VConstants.stored).put("enemy", enemy);
			// getType(game, VConstants.stored).put("enemy general",
			// enemygeneral);
			// sp.getStats().put(VConstants.maxhealth, 100);
			// PBase pb;
			//
			// pb = new PBase();
			// pb.put(VConstants.name, "Strength");
			// pb.put(VConstants.experience, 100);
			// pb.put(VConstants.strength, 10);
			// pb.put(VConstants.rechargetime, 30);
			// pb.put(VConstants.target, VConstants.self);
			// pb.put(VConstants.abilityai, new
			// CheckDamage(pb,100,VConstants.damage));
			//			
			//			
			// addAbility(pb, game);
			// allSkills.getListCreate(type).add(pb);
			//			
			// pb = new PBase();
			// pb.put(VConstants.name, "Dodge");
			// pb.put(VConstants.experience, 70); // adds 10% to physical evade
			// pb.put(ApplyDamage.pevade, 10);
			//			
			// addAbility(pb, game);
			// allSkills.getListCreate(type).add(pb);
			//			
			// sp.put(VConstants.percenttake, 5);

			// AttachUtil.attach(AttachUtil.gamestart, new PutMap(bagmapname, 0,
			// 0,
			// VConstants.livingbeing, "fighter", VConstants.person), game);
		}

		{
			// sp = person.clone();
			// String type = "mage";
			//
			// sp.put("gold", 30);
			//
			// sp.put(VConstants.level, 1);
			//			
			// sp.getStats().put(VConstants.maxhealth, 50);
			//
			// sp.setType(type);
			// sp.put(VConstants.levelup, new LevelUp());
			//
			//			
			// createAttack("zap",VConstants.magic,1,4,5,sp,game);
			// game.getPersonMap().put(sp.getType(), sp);
			//
			// // game.getMapArea().getMap().getData(0, 0).initIfNeeded();
			//
			// PBase pb = new PBase();
			// pb.put(VConstants.name, "fireball");
			// pb.put(VConstants.damagetype, VConstants.fire);
			// pb.put(VConstants.experience, 50);
			// pb.put(VConstants.damage, 20);
			// pb.put(VConstants.radius, 2);
			// pb.put(VConstants.range,4);
			// pb.put(VConstants.rechargetime, 3);
			// pb.put(VConstants.target, VConstants.enemy);
			// pb.put(VConstants.abilityai, new
			// CheckAttack(pb,AttachUtil.runpersonbefore));
			//			
			// //once used if disabled then set prev attack as default
			//			
			// addAbility(pb, game);
			// allSkills.getListCreate(type).add(pb);
			//			
			// // pb = new PBase();
			// // pb.put(VConstants.name, "fireball 2");
			// // //maybe put type fireball
			// // pb.put(VConstants.experience, 400);
			// // pb.put(VConstants.strength, 10);
			// // pb.put(VConstants.rechargetime, 30);
			// // sp.getList(VConstants.allskills).add(pb);
			//
			//
			// sp.put(VConstants.percenttake, 5);

			// String addp = addG("addbagperson2" + type, game, new PutMap(
			// bagmapname, 1, 0, VConstants.livingbeing, type,
			// VConstants.person));
			// AttachUtil.attach(AttachUtil.gamestart, addp, game);
		}

		{
			// sp = person.clone();
			// String type = "archer";
			//
			// sp.put("gold", 50);
			//
			// sp.put(VConstants.level, 1);
			//
			// sp.getStats().put(VConstants.maxhealth, 70);
			//
			// sp.setType(type);
			// sp.put(VConstants.levelup, new LevelUp());
			//
			//			
			// game.getPersonMap().put(sp.getType(), sp);
			//
			// sp.put(VConstants.percenttake, 5);

			// String addp = addG("addbagperson2" + type, game, new PutMap(
			// bagmapname, 2, 0, VConstants.livingbeing, type,
			// VConstants.person));
			// AttachUtil.attach(AttachUtil.gamestart, addp, game);

			// Item item=addItem(game, "bow", false);
			// item.put(VConstants.type, "weapon");
			// item.put(VConstants.image, "/images/doll/bow.png");
			// sp.getAlterHolder().put("weapon",item.clone());
		}

		{
			// sp = person.clone();
			// String type = "healer";
			//
			// sp.put("gold", 50);
			//
			// sp.put(VConstants.level, 1);
			//			
			// sp.getStats().put(VConstants.maxhealth, 80);
			//
			// sp.setType(type);
			// sp.put(VConstants.levelup, new LevelUp());
			//
			// createAttack("mace",VConstants.physical,1,1,7,sp,game);
			//
			// game.getPersonMap().put(sp.getType(), sp);
			//
			// // game.getMapArea().getMap().getData(0, 0).initIfNeeded();
			//
			// PBase pb = new PBase();
			// pb.put(VConstants.name, "heal");
			// pb.put(VConstants.experience, 20);
			// pb.put(VConstants.damage, -20);
			// pb.put(VConstants.damagetype, VConstants.white);
			// pb.put(VConstants.rechargetime, 3);
			//			
			// CheckDamage action = new
			// CheckDamage(pb,99,GameUtil.getPlayerTeam(),VConstants.damage);
			// action.put(VConstants.actor, VConstants.self);
			// pb.put(VConstants.abilityai, action);
			//			
			// // finds party member to heal and creates attack with special
			// desc
			// // give attack a param that disables and puts on runturn
			//			
			// addAbility(pb, game);
			// allSkills.getListCreate(type).add(pb);
			// sp.getListCreate(VConstants.abilitysetup).add("heal");
			// sp.put(VConstants.percenttake, 5);
			//
			// // String addp = addG("addbagperson" + type, game, new PutMap(
			// // bagmapname, 3, 0, VConstants.livingbeing, type,
			// // VConstants.person));
			// // AttachUtil.attach(AttachUtil.gamestart, addp, game);
			// getType(game, VConstants.stored).put("healer", sp);
		}

		// there is a sell button, when it is hit the person dissapears for a
		// number of turns and the percent take is subtracted from the returning
		// value before being added to total gold. The percent take is computed
		// among all of the people and displayed next to the total player gold.

		// puts initial defender for testing

		// game.getMapArea().getMap().getData(0, 0).getData(22, 2).put(
		// new SimpleMD(HuntAndGather.GATHERPOINT,
		// HuntAndGather.GATHERPOINT));

		// bagmap.put(BagMap.depositpoint, list(7, 7));

		AttachUtil.attach(AttachUtil.clickfmd, new IfKey(
				VConstants.livingbeing, new PlaySound("greetings")), game
				.getMapArea());
		TemplateDisableDisplay param = new TemplateDisableDisplay();
		AttachUtil.attach(AttachUtil.clickfmd, new IfKey(
				VConstants.livingbeing, param), game.getMapArea());

		// game.getMapInitList().add(new AddPack(0, 0, VConstants.human));

		// village2(game);

		// could add food here

		// change to symbolic map, add recursive running

		// AttachUtil.attach(AttachUtil.personStartOnMap, new
		// RandomTypeCreation(VConstants.person,4,"quokka","rat","goblin"),
		// game.getMapArea().getMap().getData(1,0));

		// String score = addG(VConstants.percenttake, game, new Score(
		// VConstants.percenttake, "percentage taken by mercs"));
		// String goldscore = addG("goldscore", game, new Score(VConstants.gold,
		// "gold : ", "game.human.gold"));
		// EntryPoint.game.getHtmlOut().flextable.setWidget(0,2,score.getWidget());
		// game.getHtmlOut().put(
		// VConstants.flextable,
		// list(
		// //list(list(0, 2), score),
		// list(list(0, 3), goldscore)));
		String updateptake = addG("updateptake", game, new AddTogetherPeople(
				VConstants.percenttake));
		AttachUtil.attach(AttachUtil.personadded, updateptake, game
				.getMapArea().getMap());
		AttachUtil.attach(AttachUtil.death, updateptake, game.getMapArea()
				.getMap());
		// AttachUtil.attach(AttachUtil.gamestart, goldscore, game);
		AttachUtil.attach(AttachUtil.gamestart, updateptake, game);
		// AttachUtil.attach(AttachUtil.personadded, score, game.getMapArea()
		// .getMap());
		// AttachUtil.attach(AttachUtil.death, score,
		// game.getMapArea().getMap());
		// AttachUtil.attach(AttachUtil.gamestart, score, game);
		// quokka,goblin,orc for second level maybe

		game.getPBase(VConstants.human).put("gold", 100);
		// game.getList("topHeader").add(new Score("game.human.gold"));
		// VExpression vexp = new VExpression(
		// "game.maparea.map.gold = game.maparea.map.gold - object.gold");
		//
		// AttachUtil.attach(AttachUtil.clickfmd, vexp, bagmap);
		// AttachUtil.attach(AttachUtil.clickfmd, new CloneDeposit(
		// "game.maparea.map.gold"), bagmap);
		// AttachUtil.attach(AttachUtil.clickfmd, goldscore, bagmap);

		String itemBuyDisplay = addG("buy", game, new MapDataBuyDisplay(
				VConstants.gold));
		AttachUtil.attach(AttachUtil.exit, new SellOne(), game.getMapArea()
				.getMap());

		// AttachUtil.attach(AttachUtil.exit, itemBuyDisplay, game.getMapArea()
		// .getMap());
		// AttachUtil.attach(AttachUtil.selectTab, new
		// VEquals(itemBuyDisplay,2), EntryPoint.game.getHtmlOut());

		// AttachUtil.attach(AttachUtil.gamestart, new PutMap( 0, 10,
		// VConstants.gate, , null), game);
		// gathers party (if within a range, otherwise calls sound)

		return game;
	}

	public static Game defaultGameStuff() {
		Game game = new Game();

		// game.getMapInitList().add(new ExitTile(0,0,10,0));
		game.put(VConstants.main, true);
		game.put(VConstants.name, "Basic");
		game.setMapArea(new MapArea());
		game.getMapArea().setMap(new SymbolicMap());
		game.getMapArea().getMap().put(VConstants.xfull, 17);
		game.getMapArea().getMap().put(VConstants.yfull, 15);
		addG(VConstants.runturn, game, new RunTurn());
		game.getMapArea().put(VConstants.turnbased, false);
		AttachUtil.attach(AttachUtil.runpersonbefore, VConstants.runturn, game
				.getMapArea());
		game.put(VConstants.applydamage, new ApplyDamage());
		String[] type = new String[] { "weapon", "shield", "glove", "armor",
				"head" };
		int b = 0;
		for (String a : items) {
			Item it = addItem(game, a, false);
			it.put(VConstants.type, type[b]);
			if (b > 0) {
				it.put(VConstants.armor, 1);
			} else {
				it.put(VConstants.damageadded, 1);
			}
			b++;
		}

		Item gold = addItem(game, "gold", false);
		gold.setAmount(10);
		gold.setItemValue(1);
		String itemequipDisplay = addG("itemequip", game,
				new ItemEquipDisplay());
//		AttachUtil.attach(AttachUtil.selectTab, new VEquals(VConstants.vparams,
//				itemequipDisplay + "." + VConstants.personchoicedisplay, 1),
//				game.getHtmlOut());
//
//		LevelUpDisplay lupDisplay = new LevelUpDisplay(VConstants.experience);
//		String levelUpDisplay = addG("levelup", game, lupDisplay);
//
//		AttachUtil.attach(AttachUtil.selectTab, new VEquals(VConstants.vparams,
//				levelUpDisplay + "." + VConstants.personchoicedisplay, 2), game
//				.getHtmlOut());

//		AttachUtil.attach(AttachUtil.gamestart, new PlaySound("battlemusic2",
//				true), game);

//		game.getHtmlOut()
//				.put(
//						VConstants.tab,
//						Arrays.asList(new Object[] { itemequipDisplay,
//								levelUpDisplay }));
		String setupAbilities = addG("setupAbilities", game,
				new SetupAbilities());
//		AttachUtil.attach(AttachUtil.personadded, setupAbilities, game);
//		AttachUtil.attach(AttachUtil.selected, setupAbilities, lupDisplay);

		return game;
	}

	protected static PBase createAttack(String string, String physical,
			int radius, int range, int damage, PBase game) {
		PBase defaultAttack = new PBase();

		defaultAttack.put(VConstants.name, string);
		addAbility(defaultAttack, game);
		defaultAttack.put(VConstants.damagetype, physical);
		defaultAttack.put(VConstants.radius, radius);
		defaultAttack.put(VConstants.range, range);
		defaultAttack.put(VConstants.damage, damage);

		return defaultAttack;

	}

	protected static void addAbility(PBase defaultAttack, PBase game) {
		getType(getType(game, VConstants.person), VConstants.ability).put(
				defaultAttack.getS(VConstants.name), defaultAttack);
	}

	public static PBase doTowerDefense() {
		PBase game = new PBase();
		game.put(VConstants.name, "undeaddefense");
		game.put(VConstants.classname, Game.class.getName());

		addItem(game, "amulet_of_yendor", false);

		AttachUtil.attach(AttachUtil.gamestart, new PutMap(5, 5,
				VConstants.livingbeing, null, VConstants.person), game);
		AttachUtil.attach(AttachUtil.gamestart, new AlterFilter("get",
				"person", "filter", VConstants.team, GameUtil.getPlayerTeam(),
				"get", "template", "get", "templatemap", "set", "main",
				"defend"), game);

		addG("gototrophy", game, new MoveClosest("amulet_of_yendor", null, 30));
		addG("gotocastle", game, new MoveClosest(VConstants.gate, "castle", 30));
		PTemplate attackmove = addTemplate(game, "attackmove");
		addG("carry", game, new Carry("amulet_of_yendor", "exit"));
		addAction(attackmove, "gototrophy");
		addAction(attackmove, "carry");
		attackmove.setMetaoobj(new AttackEnemyMeta());

		PTemplate enemygeneral = addTemplate(game, "enemygeneral");
		CreateListTurns clt = new CreateListTurns(11, 11, "attackmove");

		clt.add(1, "skeleton");
		clt.add(5, "zombie");
		clt.add(10, "vampire");
		clt.add(11, "vampire");

		addG("createlistturns", game, clt);
		addAction(enemygeneral, "createlistturns");

		PTemplate defend = addTemplate(game, "defend");

		addAction(defend, "gotocastle");

		defend.setMetaoobj(new AttackEnemyMeta());

		// game.getMapArea().getMap().getData(0, 0).getData(7,
		// 7).getItems().put(
		// trophy.clone());
		{
			;
			AttachUtil.attach(AttachUtil.gamestart, addG("addtrophy", game,
					new PutMap(2, 2, VConstants.items, "amulet_of_yendor",
							VConstants.item)), game);
		}

		// make sure that the children templates accept the parent template meta
		String movetonearestitemnottrophy = addG("movetonearestitemnottrophy",
				game, new MoveClosestNot("amulet_of_yendor", 20));

		String pickitemsup = addG("pickitemsup", game, new PickUp());
		// defend
		PTemplate gatheritems = addTemplate(game, "gatheritemsdefendchild");
		String gathitems = addG("gatheritemsnottrophy", game, new OobList(
				movetonearestitemnottrophy, pickitemsup));
		addAction(gatheritems, gathitems);

		Person person = new Person("enemygeneral");
		person.getTemplate().setRationalChild("type", "enemygeneral");
		person.put(VConstants.team, "2");

		// game.getMapArea().getMap().getData(0, 0).getData(0,
		// 0).putLivingBeing(
		// person);
		{
			String addp = addG("addgeneral", game, new PutMap(12, 12,
					VConstants.livingbeing, person, VConstants.person));
			AttachUtil.attach(AttachUtil.gamestart, addp, game);

		}
		{
			String addp = addG("addexit", game, new PutMap(12, 5,
					VConstants.gate, new SimpleMD(VConstants.gate, "exit"),
					null));
			AttachUtil.attach(AttachUtil.gamestart, addp, game);
		}

		String addp = addG("addcastle", game, new PutMap(4, 4, VConstants.gate,
				new SimpleMD(VConstants.gate, "castle"), null));
		AttachUtil.attach(AttachUtil.gamestart, addp, game);
		return game;
	}

	private static void village2(Game game) {

		AttachUtil.attach(AttachUtil.runpersonbefore, new AddFood(), game
				.getMapArea());
		AttachUtil.attach(AttachUtil.runpersonafter, new RemoveFood(), game
				.getMapArea());
		PercentageMap spring = new PercentageMap(
				"Shellfishing, clams, shortnecked clams , corbiculae ,granular ark");
		PercentageMap summer = new PercentageMap(
				"Shellfishing, clams, shortnecked clams , corbiculae ,granular ark,salmon",
				5, "mackerels, tunas , bonitos,Katsuwonus", 5, "whale,dolphin",
				5);
		PercentageMap fall = new PercentageMap("salmon", 5);
		String grains = "millet, barley, buckwheat, rice";
		String nuts = "chestnuts, acorns, horsechestnuts, hazelnuts,walnuts";
		String smallAnimals = "flying squirrels, foxes, monkeys , hares";
		String largeAnimals = "wild boar, deer";
		String birds = "duck , pheasant";
		String berries = "raspberry, elderberry, mulberry ,wild grapes,wild edible mushrooms";
		String herbs = "Gourd , shiso beefsteak herb";
		String roots = "arrowroot,burdock , taro, yam ";
		String misc = "salt,yeast";

		addFoods(game, grains, "grain");
		addFoods(game, nuts, "nut");
		addFoods(game, smallAnimals, "animal");
		addFoods(game, largeAnimals, "large");
		addFoods(game, birds, "bird");
		addFoods(game, berries, "berry");
		addFoods(game, herbs, "herb");
		addFoods(game, roots, "root");
		addFoods(game, misc, "misc");

		PercentageMap winter = new PercentageMap(nuts, 0, grains, 0,
				smallAnimals, 3, largeAnimals, 20, birds, 3, berries, 0, herbs,
				0, roots, 0);
		// probably hunted wild boar and deer during other seasons of the year
		// to supplement their food supply, they hunted more of these animals
		// later in the year (from October to December). This is because wild
		// boar and deer form large herds during the winter season.

		PercentageMap cultivatedRoots = new PercentageMap(
				"yamaimo mountain yam, taro yam", 50);
		PercentageMap slashAndBurn = new PercentageMap(
				"burdock, green gram, hemp, kuzu vine", 30);

		// note hunt would ignore boar and deer depending on season
		PercentageMap defaultplant = new PercentageMap(nuts, 50, grains, 10,
				smallAnimals, 5, largeAnimals, 10, misc, 5, birds, 5, berries,
				5, herbs, 5, roots, 5);

		PercentageMap defaultfish = new PercentageMap(
				"snapper,black snapper,sea bass",
				5,
				"Shellfishing, clams, shortnecked clams , corbiculae ,granular ark,salmon",
				0, "mackerels, tunas , bonitos,Katsuwonus", 0, "whale,dolphin",
				0);

		// then could optionally have for specific plants

		game.getMapArea().put(VConstants.spring, spring);
		game.getMapArea().put(VConstants.summer, summer);
		game.getMapArea().put(VConstants.fall, fall);
		game.getMapArea().put(VConstants.winter, winter);
		game.getMapArea().put(VConstants.defaultplant, defaultplant);
		game.getMapArea().put(VConstants.cultivated, cultivatedRoots);
		game.getMapArea().put(VConstants.slashandburn, slashAndBurn);
		game.getMapArea().put(VConstants.defaultfish, defaultfish);

		for (String name : mapmap.keySet()) {
			game.getFMDMap().put(name,
					buildjson.buildFmd(game, mapmap.get(name), null));
		}

		game.getInitList().add(new Structure());
		game.getInitList().add(new FormFamily());
		GGroup familyGroup = new GGroup(null, "family");
		game.getLivingBeingGroupMap().put(VConstants.person, familyGroup);
	}

	protected static void village1(Game game) {
		game.setMapArea(new MapArea(game, null, 3, 3));
		game.getOOMap().put("buildpot",
				new MakeComplexItem(20, 0, "pot", "kiln"));

		// metaoob always puts ingredients in storage or carries them to the
		// next step if needed
		// also takes them out of storage

		// tomakewith //properties
		game.getOOMap().put("buildpot",
				new MakeComplexItem(20, 0, "pot", "kiln"));

		game.getOOMap()
				.put(
						"make nut flour",
						new MakeComplexItem(20, 0, "nut flour",
								"grinding stone", "nut"));
		// the rest gets handled by a meta object. setting up all of the items
		// and moving to the right place

		game.getOOMap().put(
				"make dough",
				new MakeComplexItem(5, 0, "dough", "pot", "nut flour", "yeast",
						"salt"));
		// active and let sit
		game.getOOMap().put("bake",
				new MakeComplexItem(5, 45, "bread", "fire", "pot", "dough"));
		// if the fire needs wood or starting the meta object handles that as
		// well

		game.getOOMap().put("bakebread",
				new OobList("make nut flour", "make dough", "bake"));

		// need to make a wait oobject that simply describes the waiting process
		// in a detailed manner involving several steps
		game.getOOMap().put("layacornsouttodry",
				new MakeComplexItem(5, 400, "dried acorn", "sunny", "acorn"));
		game.getOOMap().put(
				"wrapincloth",
				new MakeComplexItem(5, 0, "wrapped acorn", "cloth",
						"dried acorn"));
		game.getOOMap().put(
				"leaveinwater",
				new MakeComplexItem(5, 400, "leeched acorn", "river",
						"wrapped acorn"));

		OobList leechacorns = new OobList("layacornsouttodry", "wrapincloth",
				"leaveinwater");

		game.getOOMap()
				.put(
						"cook hemp",
						new MakeComplexItem(5, 45, "cooked hemp", "fire",
								"pot", "hemp"));
		game.getOOMap().put(
				"layhempouttodry",
				new MakeComplexItem(5, 400, "cloth threads", "sunny", "flat",
						"cooked hemp"));
		OobList makethreads = new OobList("cook hemp", "layhempouttodry");
		game.getOOMap().put("loom",
				new MakeComplexItem(5, 400, "cloth", "loom", "cloth threads"));
		game.getOOMap()
				.put(
						"make basket",
						new MakeComplexItem(5, 400, "basket", "weave",
								"cloth threads"));
		game.getOOMap().put("leechacorns", leechacorns);
		game.getOOMap().put("makethreads", makethreads);

		game.getOOMap().put("cooked meat",
				new MakeComplexItem(30, 0, "skinned deer"));

		// make the meta work off of a seperate consumable property not tied to
		// eating
		addItem(game, "nut flour", true);
		addItem(game, "dough", true);

		addItem(game, "grinding stone", 1, false, null, "large");
		addItem(game, "bread", true);
		addItem(game, "dried acorn", true);
		addItem(game, "wrapped acorn", true);
		addItem(game, "leeched acorn", true);
		addItem(game, "cooked hemp", true);
		addItem(game, "cloth threads", true);
		addItem(game, "cloth", false);
		addItem(game, "basket", false);

		// metaoob always puts ingredients in storage or carries them to the
		// next step if needed
		// also takes them out of storage

		// tomakewith //properties

		PTemplate eatl = addTemplate(game, "eat");

		game.getOOMap().put("eat", new Eat());
		// addAction(eatl, "eat", "hour0");

		PTemplate worker = addTemplate(game, "maleadult");
		PTemplate fchild = addTemplate(game, "femalechild");
		PTemplate mchild = addTemplate(game, "malechild");
		PTemplate thomemaker = addTemplate(game, "femaleadult");
		PTemplate createfood = addTemplate(game, "createfood");
		// PTemplate processdeer = addTemplate(game, "processdeer");

		game.getOOMap().put("hunt",
				new HuntAndGather("large", "animal", "bird"));
		game.getOOMap().put(
				"gather",
				new HuntAndGather("herb", "berry", "grain", "root", "nut",
						"misc"));
		addAction(worker, "hunt");
		addAction(thomemaker, "gather");
		addAction(createfood, "buildpot");
		addAction(createfood, "bakebread");
		addAction(createfood, "leechacorns");
		addAction(createfood, "makethreads");
		addAction(createfood, "loom");
		addAction(createfood, "make basket");
		createfood.setMetaoobj(new ItemCreateMeta());

		addItem(game, VConstants.corpse, 1, true);
		addItem(game, "milk", 1, true);
		addItem(game, "meat", 1, true);
		addItem(game, "eggs", 1, true);
		addItem(game, "boiled eggs", 5, true, "eggs", null);
		addItem(game, "cheese", 5, true, "milk", null);

		addItem(game, "butter", 5, true, "milk", null);

		addItem(game, "bread", 5, true, "wheat", null);

		addItem(game, "wheat", 1, false);
		addItem(game, "iron ore", 1, false);
		addItem(game, "wood", 1, false);
		addItem(game, "pot", 1, false);

		// game.getOOMap().put(
		// "break up deer",
		// new BreakUpItem(20, "deer", "deer bone", 4, "antler", 1,
		// "deer skin", 5, "meat", 5));
		// addAction(processdeer, "break up deer");
		// processdeer.setMetaoobj(new ItemCreateMeta());

		game.getOOMap().put("followparent", new FollowParent());
		addAction(fchild, "followparent");
		addAction(mchild, "followparent");
		fchild.setMetaoobj(new FollowParentMeta());
		mchild.setMetaoobj(fchild.getMetaoobj());
	}

	public static String addG(String string, PBase game, Object o) {
		// if (o instanceof UIList) {
		// ((PBase) game.get("uilist")).put(string, o);
		// return string;
		// }
		if (o instanceof VParams) {
			PBase pBase = game.getPBase(VConstants.vparams);
			if (pBase == null) {
				pBase = new PBase();
				game.put(VConstants.vparams, pBase);
			}
			pBase.put(string, (VParams) o);
			return string;
		}

		if (o instanceof OObject) {
			PBase pBase = game.getPBase(VConstants.oObject);
			if (pBase == null) {
				pBase = new PBase();
				game.put(VConstants.oObject, pBase);
			}
			pBase.put(string, (OObject) o);
			return string;
		}
		if (o instanceof LivingBeing) {
			PBase pBase = getType(game, VConstants.person);
			pBase.put(string, (LivingBeing) o);
			return string;
		}

		if (o instanceof Item) {
			PBase pBase = getType(game, VConstants.item);
			pBase.put(string, o);
		}

		if (o instanceof PBase) {
			game.put(string, o);
		}

		return string;
	}

	protected static PBase getType(PBase game, String person) {
		PBase pBase = game.getPBase(person);
		if (pBase == null) {
			pBase = new PBase();
			game.put(person, pBase);
		}
		return pBase;
	}

	protected static Item addItem(Game game, String name, int time,
			boolean consumable) {
		return addItem(game, name, time, consumable, null, null);
	}

	protected static Item addItem(PBase game, String name, boolean consumable) {
		return addItem(game, name, 0, consumable, null, null);
	}

	protected static Item addItem(PBase game, String name, int time,
			boolean consumable, String toMake, String type) {
		Item item = new Item();
		item.setName(name);
		// item.setConsumable(consumable);
		item.put(VConstants.type, type);
		item.setItemValue(7);
		// game.getPBase(VConstants.item).put(item.getKey(), item);
		addG(item.getKey(), game, item);
		String buildName = "defaultBuild" + name;
		if (toMake != null) {

			addG(buildName, game, new MakeDefaultItem(time, toMake, item));
			item.itemBuildAction = buildName;
		}
		return item;
	}

	public static PTemplate addTemplate(PBase pBase, String name) {

		PTemplate pTemplate = new PTemplate(name);
		// if(root != null){
		// root.addChild(pTemplate);
		// }
		getType(pBase, VConstants.template).put(name, pTemplate);

		return pTemplate;
	}

	public static void addAction(PTemplate al, String action) {
		ActionHolder a = new ActionHolder(action);
		al.getActionList().add(a);

		// for (String b : state) {
		// State st = new State(statename.get(b));
		// a.addState(st);
		// st.stateData.add(b);
		// }

	}

	public static List list(Object... objects) {
		return Arrays.asList(objects);
	}

	private static void addFoods(Game game, String grains, String type) {
		for (String a : grains.split(",")) {

			addItem(game, a.trim(), 1, true, null, type);

		}
	}

	protected static Map<String, String> mapmap = new HashMap<String, String>();

	static String pithouse = " .@@      .....\r\n" + " @s @     t....  \r\n"
			+ " @@ @\r\n" + " @  @    \r\n" + " @  @   o    G\r\n"
			+ " @dd@\r\n  " + ".        K\r\n" + ".   i   \r\n" + "";

	static String generic = "......FrFFF.......R\r\n"
			+ "...F.FFFrFFF.......\r\n" + ".....F.FrFFFFF.....\r\n"
			+ "...FRFFFrFF......F.\r\n" + "..F.FR.FrFFR..FF...\r\n"
			+ "...F.FFFr.FF..FF...\r\n" + "...FFRFFrFFFR...F..\r\n"
			+ "....RFFFrFFF.R.F...\r\n" + "..FF..FFrF.R.......\r\n";
	static {

		mapmap.put("pithouse", pithouse);
		mapmap.put("generic", generic);
	}

}
