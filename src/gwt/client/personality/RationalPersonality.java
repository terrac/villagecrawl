package gwt.client.personality;

import gwt.client.EntryPoint;
import gwt.client.main.Economy;
import gwt.client.main.Game;
import gwt.client.main.LocalTemplate;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.ActionHolder;
import gwt.client.main.base.ActionObject;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MetaOObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.client.output.OutputDirector;
import gwt.client.state.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.io.Serializable;

public class RationalPersonality implements Serializable {

	// FullMapData memory = new FullMapData();

	public RationalPersonality() {
	}

	public void runAI(LivingBeing person, Map<String, OObject> oobmap, Game game) {
		// This is completely changing

		// It will grab the state map off of template and check the state

		// (Note we are dealing with pojos identical to the xmlbeans

		// then it will see if the states match. If they do then it updates the
		// states and
		// runs the action (no more list)
		// if the states don't match (like if there is extra state) run through
		// the basic states
		// and find an action

		//

		LocalTemplate template = person.getTemplate();

		// could be optional

		if (template.stack.isEmpty()) {

			Economy economy = person.getEconomy();
//			if(economy != null){
//				economy.useNextNeed(person);				
//			}
			
			for (Entry<String, Object> et : template.getRationalMap()
					.getObjMap().entrySet()) {
				if (template.disableSet.contains(et.getKey())) {
					continue;
				}
				doStuff(person, oobmap, game.getTemplateMap()
						.get(et.getValue()));
			}

			if (!template.stack.isEmpty()) {
				person.getTemplate().stack.peek().oInit(person);

				runPending(person, person.getTemplate().getCurrent());

			}
		}

	}

	private void doStuff(LivingBeing person, Map<String, OObject> oobmap,
			PTemplate defaultChild) {
		if (defaultChild == null) {
			return;
		}
		Double rand = (Double) defaultChild.get(VConstants.random);
		if(rand != null){
			if(rand < VConstants.getRandom().nextDouble()){
				return;
			}
		}
		for (ActionHolder ah : defaultChild.getActionList()) {
			// boolean flag = true;
			// for(State st :ah.statesNeeded){
			// if(!st.gatherState(person)){
			// flag = false;
			// break;
			// }
			// }
			// if(flag||ah.statesNeeded.size() == 0){

			OObject action = oobmap.get(ah.getAction());
			if (action == null) {
				throw new IllegalArgumentException(ah.getAction()
						+ " is missing");
			}
			action = action.clone();
			action.setParent(defaultChild);
			person.getTemplate().stack.push(action);

			// }

		}
	}

	public int runSegment(LivingBeing person) {
		int returnable = 1;
		OObject oo = person.getTemplate().stack.peek();

		// could create a similar stacked method to be able to see things as if
		// they were on a stack

		if (person.getTemplate().getCurrent() != null) {
			FullMapData fullMapData = person.getParent().getParent();
			OObject currentoob = person.getTemplate().getCurrent();
			PTemplate cptemplate = person.getTemplate().getCurrent()
					.getTParent();
			MetaOObject metaoobj = null;
			if (cptemplate != null) {
				metaoobj = person.getTemplate().getCurrent().getTParent()
						.getMetaoobj();
			}
			if (metaoobj != null) {
				Returnable mreturn = metaoobj.preExecute(fullMapData, person,
						currentoob);
				runPending(person, cptemplate);
				if (null == mreturn) {

					return 1;
				}

			}
			Returnable ret = currentoob.execute(fullMapData, person);
			if (ret == null) {
				ret = new Returnable();
			}

			postExecute(person, ret);

			returnable = ret.getTimesegment();

			runPending(person, currentoob);
			if (cptemplate != null && !ret.isShouldcontinue()) {
				currentoob.firePostEvent(cptemplate, fullMapData, person, ret);
			}
			if (!ret.isBreakchain()&&person.getTemplate().stack.size() > 0) {
				person.getTemplate().stack.peek().oInit(person);
				runPending(person, person.getTemplate().getCurrent());

			}
		}

		return returnable;
	}

	protected void postExecute(LivingBeing person, 
			Returnable ret) {
		if (ret.isBreakchain()) {
			person.getTemplate().stack.clear();
		} else if (!ret.isShouldcontinue()) {
			if (!person.getTemplate().stack.isEmpty()) {
				person.getTemplate().stack.pop();
			}

			

		}
	}

	protected void runPending(LivingBeing person, ActionObject currentoob) {
		if (person.getTemplate().pending.size() != 0) {
			Collections.reverse(person.getTemplate().pending);
			List<OObject> otlist = new ArrayList<OObject>();
			for (OObject oob : person.getTemplate().pending) {
				if (currentoob instanceof PTemplate) {
					oob.setParent(currentoob);
				} else {
					((OObject) currentoob).addChild(oob);
				}

				otlist.add(oob);
			}
			person.getTemplate().stack.pushList(otlist);
			person.getTemplate().pending.clear();
		}
	}

}
