package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;

import java_cup.shift_action;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.display.ItemsDisplay;
import gwt.client.game.vparams.adding.AddBuilding;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.MapDataAreaMap;
import gwt.shared.datamodel.VParams;

public class Mercernaries extends VParams {

	public Mercernaries() {
		// TODO Auto-generated constructor stub
	}
	FullMapData currentMercFmd;
	boolean ran = false;
	public void execute(java.util.Map<String, Object> map) {
		String team = null;
		if(currentMercFmd != null){
			boolean done = true;
			for(LivingBeing lb : currentMercFmd.people){
				if(team == null){
					team = lb.getTeam();
				}
				if(team != null&&!team.equals(lb.getTeam())){
					done = false;
				}
				
			}
			if(done){
				EntryPoint.game.getHtmlOut().closeExtra();
				currentMercFmd =null;
			}
		}
		
		if(ran){
			return;
		}
		ran = true;
		FullMapData fmd = EntryPoint.game.getHtmlOut().currentFMD;
		
		HashMapData hashMapData =fmd.getData(1, 1);
		hashMapData = fmd.getNearestEmpty(hashMapData);
	
		PBase person = new PBase();
		person.put(VConstants.traits, "human male fighter");
		person.put(VConstants.name, "Bob the Beermaker");
		person.put(VConstants.team, "1");
		person.put(VConstants.avatar, "bobthebeermaker");
		person.put(VConstants.hair, "short red");
		person.put(VConstants.face, "full red");
		person.getListCreate(VConstants.equipment).add("leather armor");

		PBase q = new PBase();
		q.put(VConstants.type, "beermaker");

		// q should have a price
		// an item
		// and possibly a list of lines if you dissagree (or more
		// complicated later maybe
		q.getListCreate(VConstants.list).add("offering services to clear out local rats and snakes");
		q.getListCreate(VConstants.list).add("bob disagree line 1");
		AttachUtil.attach(VConstants.success, new VParams(){
			@Override
			public void execute(Map<String, Object> map) {
				
				VParams snakesAndRats=new RandomTypeCreation(new RandomPersonCreation("enemy",VConstants.random,"snake","rat","goblin"), 300);
				VParams friendlyteam=new RandomTypeCreation(new RandomPersonCreation("merc",VConstants.list,"dwarf male","dwarf male","dwarf male"), 300);
				
				PBase charmap = new PBase();

				BuildMap ma = new BuildMap(charmap,
						"              \n"
					  + "              \n" + 
						"              \n"
					  + "              \n" + 
						"              \n" + 
						"              ");
				ma.put(VConstants.defaultimage, "/images/grass.png");
				
				FullMapData fmd = new FullMapData();
				Map<String, Object> rma = AttachUtil.createMap(fmd, fmd);
				ma.execute(rma);
				snakesAndRats.execute(rma);
				friendlyteam.execute(rma);
				EntryPoint.game.getHtmlOut().addFMD(fmd);
				currentMercFmd = fmd;
			}
		}, q);
		q.put(VConstants.person, person);

		q.put(VConstants.item, new Item("scroll", 5, 10));
	
		
		hashMapData.putLivingBeing(RandomPersonCreation
				.createPerson(q.getPBase(VConstants.person)));
		LivingBeing livingBeing = hashMapData.getLivingBeing();
		livingBeing.put(VConstants.quest, q);
		livingBeing.setTemplate("specialhaggle");
		ComplexCityGenerator.addEquipment(hashMapData,q.getS(VConstants.type));
		livingBeing.put(VConstants.exit, "dwarfentrance");
		
	}

	@Override
	public PBase clone() {

		return new Mercernaries().copyProperties(this);
	}
}
