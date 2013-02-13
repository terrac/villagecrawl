package gwt.shared;

import java.util.List;

import gwt.client.game.AttachUtil;
import gwt.client.game.CreateListTurns;
import gwt.client.game.GameUtil;
import gwt.client.game.LevelUp;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CallTab;
import gwt.client.game.vparams.CategoryMap;
import gwt.client.game.vparams.CheckAttack;
import gwt.client.game.vparams.CheckDamage;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.ExitTile;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.ui.ChatWindow;
import gwt.client.item.SimpleMD;
import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

public class ClientBuildAvU {
	public static PBase doInitialAdventurers() {
		
		//beef up the mage with all kinds of summoning and direct damage abilities
		
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "initialadventurers");
	
		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("firstbattle");

		ClientBuild2.getType(pb, VConstants.templatemap).put(VConstants.type, "none");
		PBase charmap = new PBase();
		{
			PBase person = new PBase();
			charmap.put("W", person);
			person.put(VConstants.traits, "human wizard");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Terra");
			person.put(VConstants.team, "1");
			person.put(VConstants.image, "/images/terra.png");
			person.put(VConstants.intelligence, "defensive");
		}
		
		//all traits should be able to be described this way
		
		charmap.put("t", "tree");
		SimpleMD sm = new SimpleMD(VConstants.gate, "itemshop");
		sm.put(AttachUtil.personadded, new CallTab(new RandomItemCreation(3,
				ClientBuild2.items), "buy"));
		charmap.put("S", sm);
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"firstbattle")));
		BuildMap bm = new BuildMap(charmap, 
				"    ttt    \\n" + 
				"   t   t   \\n" + 
				"    ttt    \\n" + 
				"  W        \\n" + 
				" ttt       \\n" + 
				"  t  tStttt\\n" + 
				"  t  t     \\n" + 
				"     t    X");
		bm.put(VConstants.defaultimage, "/images/grass.png");
		CategoryMap categorymap = new CategoryMap();
		bm.getType(VConstants.resource)
		.put(VConstants.script, categorymap);
		categorymap.addLine("terra",
		"I should check out that itemshop as zombies are up ahead.");
		categorymap.addLine("system",
		"Left click to select and right click to move");
		

		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		
		
		
		AttachUtil.attach(AttachUtil.mapstart, new DisplayAndOk(new ChatWindow()), maparea);
		
		return pb;
	}
	
	public static PBase doFirstBattle() {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "firstbattle");
	
		
		pb.getType(VConstants.templatemap).put(VConstants.type, "default");
		
		PTemplate enemygeneral = ClientBuild2.addTemplate(pb, "enemygeneral");
		CreateListTurns clt = new CreateListTurns(5,0, "attack");

		clt.add(1, "zombie");
		clt.add(2, "zombie");
		clt.add(3, "zombie");
		clt.add(4, "zombie");
		
		clt.add(11, "skeleton");
		clt.add(12, "skeleton");
		clt.add(13, "skeleton");
		clt.add(14, "skeleton");

		ClientBuild2.addG("createlistturns", pb, clt);
		ClientBuild2.addAction(enemygeneral, "createlistturns");

		PBase charmap = new PBase();
		
		
		{
			PBase person = new PBase();
			charmap.put("N", person);
			person.put(VConstants.traits, "human necromancer");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Herald");
			person.put(VConstants.team, "undead");
			PBase templatemap = new PBase();
			templatemap.put(VConstants.main, "enemygeneral");
			person.put(VConstants.templatemap, templatemap);
		}

		
		//all traits should be able to be described this way
		
		charmap.put("t", "tree");
		charmap.put("p", "plant");

		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		BuildMap bm = new BuildMap(charmap, 
				"            N\\n" + 
				"ttt      t p \\n" + 
				"  tp ttt     \\n" + 
				"             \\n" + 
				"   tt      pp\\n" + 
				"    pttttt pp\\n" + 
				"     p     pp\\n" + 
				"t    \\n" + 
				"      t   p  \\n" + 
				"X            ");
		bm.put(VConstants.defaultimage, "/images/grass.png");

		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		AttachUtil.attach(AttachUtil.mapstart, new RandomTypeCreation(
				new RandomItemCreation(2,ClientBuild2.items), 5), maparea);
		
		
		AttachUtil.attach(AttachUtil.mapstart, new DisplayAndOk(new ChatWindow()), maparea);
		
		return pb;
	}
	
	//do a story wherein there are a number of randomly generated areas with item shops in between
	//mage summons and fires fireballs

	
	public static PBase doPeople() {
		PBase game = new PBase();

		game.put(VConstants.main, true);
		game.put(VConstants.classname, Game.class.getName());
		game.put(VConstants.name, "people");

		PBase person = game.getType(VConstants.person);
		PBase classes =person.getType(VConstants.templatemap).getType(VConstants.classes);		
		ClientBuild2.createTemplate(classes, "wizard",VConstants.intelligence, 20,VConstants.defaultattack,"zap");		
		ClientBuild2.addDescription(classes,"wizard","wizards have many abilities");
		//ClientBuild2.createAttack("zap",VConstants.magic,1,3,5,game);

				
		PBase allSkills = person.getType(VConstants.allskills);
		

		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "fireball2");
			pb.put(VConstants.image, "fireball");
			pb.put(VConstants.damagetype, VConstants.fire);
			pb.put(VConstants.experience, 50);
			pb.put(VConstants.damage, 20);
			pb.put(VConstants.radius, 2);
			pb.put(VConstants.range,4);
			pb.put(VConstants.rechargetime, 10);			
			pb.put(VConstants.target, VConstants.enemy);
			pb.put(VConstants.abilityai, new CheckAttack(pb,AttachUtil.runpersonbefore));
			
			//once used if disabled then set prev attack as default
			
			ClientBuild2.addAbility(pb, game);			
			allSkills.getListCreate("wizard").add(pb);	
			
		}
		
		
		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "summon wolf2");
			pb.put(VConstants.experience, 20);
			
			
			pb.put(VConstants.rechargetime, 400);
			PBase sabil=new PBase();
			pb.put(VConstants.summon,sabil);
			sabil.put(VConstants.name, VConstants.summon);
			sabil.put(VConstants.summon, true);
			sabil.put(VConstants.rechargetime, 400);
			pb.put(VConstants.template, "summon");
			pb.put(VConstants.target,"wolf");
			ClientBuild2.addAbility(pb, game);			
			allSkills.getListCreate("wizard").add(pb);
			

		}
		
				
		

		return game;
	}
}
