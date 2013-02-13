package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.ui.CloneDeposit;
import gwt.client.game.vparams.ui.Score;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class LevelUpDisplay extends UIVParams {
	HorizontalPanel hp;

	ListBox lb;
	Map<String, Object> selectMap = new HashMap();

	Score score;

	public LevelUpDisplay() {
		// TODO Auto-gener ated constructor stub
	}

	public LevelUpDisplay(String type) {
		put(VConstants.type, type);
		// TODO Auto-generated constructor stub
		

	}

	public PersonChoiceDisplay getPcd() {
		return (PersonChoiceDisplay) get(VConstants.personchoicedisplay);
	}

	protected void populateList(Object obj) {
		LivingBeing lb2 = (LivingBeing) obj;
		put("levelupperson", lb2);
		List<PBase> allSkills = (List<PBase>) EntryPoint.game.getPBase(VConstants.person).getPBase(
				VConstants.allskills).getList(lb2.getAttributes().getS(VConstants.classes));
		// skillmaps are on game, rather than the person

		if (allSkills == null) {
			Window.alert("No skills");
			return;
		}
		for (PBase pb : allSkills) {
			if (lb2.getListCreate(VConstants.ability).contains(pb.get(VConstants.name))) {
				continue;
			}
			String skillDisplay = pb.get(VConstants.name) + " "
					+ pb.getInt(VConstants.experience);
			selectMap.put(skillDisplay, pb);
			lb.addItem(skillDisplay);

		}

	}

	protected boolean checkScore(Object obj) {
		LivingBeing pBase = (LivingBeing) get("levelupperson");
		Integer personexp = pBase.getInt(getS(VConstants.type));
		PBase skill = (PBase) obj;
		Integer skillexp = skill.getInt(getS(VConstants.type));
		personexp -= skillexp;
		if (personexp < 0) {
			Window.alert("not enough experience");
			return false;
		}
		pBase.put(VConstants.experience, personexp);
		// put in the pbase, unlike items these should just work as they are
		// just pbases for this purpose
		// pBase.getAlterHolder().put((String)((PBase)
		// obj).get(VConstants.name), obj);

		pBase.getListCreate(VConstants.abilitysetup).add(skill.getS(VConstants.name));
		AttachUtil.run(AttachUtil.selected, pBase, this);
		// put on ability list
		// run setup abilities

		return true;
	}

	@Override
	public UIVParams clone() {

		return new LevelUpDisplay().copyProperties(this);
	}

	@Override
	public Widget getWidget() {
		// isn't this just the one attach?

		lb.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int sel = lb.getSelectedIndex();
				if(sel == -1){
					return;
				}
				Object obj = selectMap.get(lb.getItemText(sel));
				if (!checkScore(obj)) {
					return;
				}

				lb.removeItem(sel);

				AttachUtil.run(AttachUtil.clicklist, get(AttachUtil.OBJECT),
						LevelUpDisplay.this);
				// subtract gold, add item to current person
			}
		});
		return hp;
	}

	@Override
	public void execute(final Map<String, Object> map) {

		lb.clear();
		Object obj = map.get(AttachUtil.OBJECT);
		if (obj instanceof Point) {
			return;
		}
		put(AttachUtil.OBJECT, obj);
		score.execute((PBase) obj);

		populateList(obj);

	}

	@Override
	public void init() {
		hp = new HorizontalPanel();

		lb = new ListBox(true);
		put(VConstants.personchoicedisplay, new PersonChoiceDisplay());
		hp.add(getPcd().getWidgetAndInit());
		hp.add(lb);
		
		
		score = new Score(getS(VConstants.type), getS(VConstants.type),
				AttachUtil.OBJECT);
		ShowStats showStats = new ShowStats();
		AttachUtil.attach(AttachUtil.clickfmd, new SetObject(AttachUtil.OBJECT,
				showStats), getPcd().getBm());

		
		AttachUtil.attach(AttachUtil.selected, showStats, this);

		hp.add(showStats.getWidgetAndInit());
		hp.add(score.getWidgetAndInit());

		AttachUtil.attach(AttachUtil.clickfmd, this, getPcd().getBm());

		AttachUtil.attach(AttachUtil.clicklist, score, this);

	}
}
