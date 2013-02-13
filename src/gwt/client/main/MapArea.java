package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.ExecuteOnTrue;
import gwt.client.game.GEvent;
import gwt.client.game.GameUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.main.base.under.Plant;
import gwt.client.map.AreaMap;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IAge;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.client.personality.ImpulsivePersonality;
import gwt.client.personality.RationalPersonality;
import gwt.client.personality.Stats;
import gwt.shared.datamodel.VParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.google.gwt.user.client.Window;
import com.googlecode.objectify.annotation.Serialized;

public class MapArea extends MapData {

	// public static boolean showAnimals = false;

	public static final String pointlist = "pointlist";

	List<MapData> toRemove = new ArrayList<MapData>();

	public MapArea() {

	}

	@Transient
	public Game game;

	public OObject getClone(String name) {
		return game.getOOMap().get(name).clone();
	}

	public SymbolicMap getMap() {
		return (SymbolicMap) get(VConstants.map);
	}

	public void setMap(SymbolicMap map) {
		put(VConstants.map, map);
	}

	public MapArea(Game game, AreaMap def) {
		setMap(new SymbolicMap(this));
		getMap().put(VConstants.template, def);

		this.game = game;

	}

	public MapArea(Game game, AreaMap def, int xsize, int ysize) {
		setMap(new SymbolicMap(this, xsize, ysize));
		getMap().put(VConstants.template, def);

		this.game = game;

	}

	public MapArea(int xsize, int ysize) {
		setMap(new SymbolicMap(this, xsize, ysize));

	}

	// List<LivingBeing> animals= new ArrayList();
	// @Transient
	// transient AreaMap<DiffFullMapData, MapData> diffs;

	public void runTurn(int numTurn) {
		// Report.reportTemplates(map);
		HtmlOut htmlOut = EntryPoint.game.getHtmlOut();
		htmlOut.displaySymbolicMap(getMap());

		AttachUtil.run(AttachUtil.runbefore, null, this);

		for (FullMapData fmd : getMap()) {
			runFmd(fmd);
			if(fmd.people.size() > 0){
				htmlOut.displayAreaMap(fmd);
			}
		}
		htmlOut.runExtra(this);
		
		playSound(htmlOut);
		
		for(MapData m : toRemove){
			m.getParent().remove(m.getKey());
		}
	}

	public void playSound(HtmlOut htmlOut) {
		long ctime = new Date().getTime();
		if (ctime - 2000 > lastTime) {
			lastTime = ctime;
			List<LivingBeing> lb = htmlOut.currentFMD.people;
			if (lb.size() != 0) {
				LocalTemplate template = VConstants.getRandomFromList(lb)
						.getTemplate();
				if (template.getCurrent() != null) {
					PTemplate tParent = template.getCurrent().getTParent();
					if (tParent != null) {

						String soundToPlay = tParent.getS(VConstants.sound);
						if (soundToPlay == null) {
							soundToPlay = tParent.getTemplateName();
						}
						if (!soundToPlay.equals(VConstants.none)) {
							OutputDirector.soundPlayer.playOnce(soundToPlay);

						}

					}
				}

			}

		}
	}

	public void runFmd(FullMapData fmd) {
		HtmlOut htmlOut = EntryPoint.game.getHtmlOut();
		
		// get all people on this full map data

		// call impulsive personality

		if (fmd == null) {
			return;
		}
		runAllMapDatas(fmd);

		LivingBeing[] runList = fmd.people.toArray(new LivingBeing[0]);

		if (runList.length == 0) {
			return;
		}
		AttachUtil.run(AttachUtil.runbefore, fmd, fmd);

		int index = 0;
		Integer turnindex = (Integer) get(VConstants.turnindex);
		if (turnindex != null && turnindex >= runList.length) {
			turnindex = 0;
		}

		String runname = AttachUtil.runpersonbefore;
		PBase team = runTeam(fmd, runname);
		for (LivingBeing per : runList) {
			index++;
			// ImpulsivePersonality.runAI(difadds,per);
			if (turnindex != null && turnindex > index) {
				continue;
			}
			Stats stats = per.getStats();

			stats.setNextTurn(stats.getNextTurn() - stats.getSpeed());
			if (stats.getNextTurn() < 0) {
				if (per.getParent() == null) {
					continue;
				}
				per.runAI();
				//System.out.println(per.getTemplate().getRationalMap().getObjMap());
				if (per.getParent() == null) {
					continue;
				}
				// if(per.isPerson()){
				// if(per != null&&per.getTemplate().getCurrent() != null){
				// String ssound=(String) per.get(VConstants.sound);
				// if(ssound != null){
				// OutputDirector.soundPlayer.playOnce(ssound);
				// }
				// per.remove(VConstants.sound);
				// }
				// }
				stats.resetNextTurn();

				if (getB(VConstants.turnbased)) {
					put(VConstants.turnindex, index);
					// if(htmlOut.currentFMD ==
					// null||htmlOut.currentFMD.people.size() == 0){
					// htmlOut.currentFMD = fmd;
					// }


					break;
				}

			}

		}
		// if(htmlOut.currentFMD == null||htmlOut.currentFMD.people.size() ==
		// 0){
		// htmlOut.currentFMD = fmd;
		// }
		if (htmlOut.getCurrentlyFollowed() != null
				&& htmlOut.getCurrentlyFollowed().getParent() != null) {
			htmlOut.displayAreaMap(htmlOut.getCurrentlyFollowed().getParent()
					.getParent());
		} else {
			htmlOut.displayAreaMap(htmlOut.currentFMD);
		}

	}

	long lastTime;

	public static PBase runTeam(FullMapData fmd, String runname) {
		PBase team = EntryPoint.game.getPBase(VConstants.team);
		if (team != null) {
			for (Entry<String, Object> en : team.getObjMap().entrySet()) {
				if (en.getValue() instanceof PBase) {
					PBase value = (PBase) en.getValue();

					if (!value.containsKey(runname)) {
						continue;
					}
					for (LivingBeing lb : GameUtil.getTeam(fmd.people,
							en.getKey())) {

						AttachUtil.run(runname, lb, value);
					}

				}
			}
		}
		return team;
	}

	private void runAllMapDatas(FullMapData fmd) {
		fmd.initIfNeeded();
	}

	public void end() {

		System.out.println(getMap());

		for (FullMapData fmd : getMap()) {
			for (LivingBeing per : fmd.people) {
				if (per instanceof Person) {
					System.out.println(per.getType());
					System.out.println(per.getTemplate().getRationalMap());
					System.out.println(per.get("property"));
					System.out.println(per.getAge());
					System.out.println(per.getPosition());
				}
			}
		}

	}

	public Person addPerson(String type, int x, int y) {

		Person person = addPerson(type);
		getMap().getData(x, y).add(Direction.West, person);
		// addToEconomy((Person) person);

		return person;
	}

	public Person addPerson(String type) {
		// get default template
		// organize default template so that it has the worker under it, and
		// worker organizes
		// miner, etc
		// update updates the default template which updates the "current"
		// template
		// so there is a root level template object which holds the tree and the
		// current stuff.
		// each template holds a statistics object which determines behavior
		// (but the homemaker,
		// and worker are predetermined and have no object for now)

		// and additionally actions can gather statistics under update or
		// whatever

		// so essentially there is a statistics registering system that takes
		// the statistics and
		// puts them on the person

		// there is a statistics map for the templates or actions to gather
		// statistics and
		// parameters to determine if the statistics are met.

		LocalTemplate clone = game.getTemplateRootClone();
		if (type != null) {
			clone.setRationalChild("default", type);

			if (type.equals("femaleadult")) {
				clone.setRationalChild("wutwut", "createfood");
			}
			if (type.equals("maleadult")) {
				clone.setRationalChild("duh", "eat");
				clone.setRationalChild("duh2", "processdeer");
			}
		}
		// if(type2 != null){
		// clone.setRationalChild(VConstants.dominant, type2);
		// }
		Person person = new Person(type);

		if (game.getLivingBeingGroupMap().get(VConstants.person) != null) {
			person.setGroup(game.getLivingBeingGroupMap()
					.get(VConstants.person).clone());
			// person.getGroup().synchronizeGroups(person.getTemplate());
		}
		person.setTemplate(clone);
		return person;
	}

	public LivingBeing addAnimal(String string) {
		Stats stats = new Stats();
		if (string.equals("cow")) {
			stats.setSpeed(3);
			stats.setStamina(10);
		}
		if (string.equals("chicken")) {
			stats.setSpeed(8);
			stats.setStamina(3);
		}

		if (string.equals("deer")) {
			stats.setSpeed(6);
			stats.setStamina(6);
		}

		if (string.equals("wolf")) {
			stats.setSpeed(7);
			stats.setStamina(5);
		}
		LivingBeing lb = new Animal(string, stats);
		lb.getTemplate().setRationalChild(VConstants.instinct, string);
		lb.getTemplate().setRationalChild(VConstants.defaultT, "defaultanimal");

		lb.setGroup(game.getLivingBeingGroupMap().get(string).clone());
		lb.getGroup().synchronizeGroups(lb.getTemplate());

		return lb;
	}

	@Override
	public String toString() {

		return getMap().toString();
	}

	public MapData addPlant(String value) {
		Plant plant = new Plant();
		plant.type = value;

		return plant;
	}

	@Serialized
	RationalPersonality personality = new RationalPersonality();

	public void runAI(LivingBeing person) {
		// take the percent

		AttachUtil.run(AttachUtil.runpersonbefore, person, this);
		AttachUtil.run(AttachUtil.runpersonbefore, person, person);
		EntryPoint.game.getRunTurn()
				.execute(AttachUtil.createMap(person, null));
		if (!EntryPoint.game.getPBase(VConstants.templatemap).getB(
				VConstants.disabled)) {
			personality.runAI(person, game.getOOMap(), game);
		}
		//LogDisplay.log(person.getTemplate().getCurrent(), 2);

		int a = 1;
		while (a > 0 && !person.getTemplate().stack.isEmpty()
				&& person.getParent() != null) {
			a -= personality.runSegment(person);
		}
		AttachUtil.run(AttachUtil.runpersonafter, person, this);

	}

	public String getState(String key) {
		return null;
		// return game.statedata.get(key);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return VConstants.maparea;
	}
	
	public void addToRemove(MapData md){
		toRemove.add(md);
	}

	public MapArea clone() {
		return new MapArea().copyProperties(this);
	}
}
