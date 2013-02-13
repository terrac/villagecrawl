package gwt.client.main;

import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.personality.StackObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




//one per living object
public class LocalTemplate extends PBase{
	// this holds all the data local to a specific person maybe?


	public LocalTemplate() {

	}

	

	
	

	public List<OObject> pending = new ArrayList<OObject>();

	transient public StackObject stack = new StackObject();

	

	public String getTemplateName() {
		return getS(VConstants.name);
	}




	public void setTemplateName(String templateName) {
		this.put(VConstants.name, templateName);
	}




	public OObject getCurrent() {
		if (stack.size() == 0) {
			return null;
		}
		return stack.peek();
	}

	public void push(OObject oo) {
		if (oo != null) {
			stack.push(oo);
		}

	}

	public void setDominantChild(String string) {
		
	}

//	public void registerStatistics(LivingBeing person) {
//		final List<Statistics> stats = new ArrayList<Statistics>();
//		root.iterate(new TemplateIterate() {
//
//			@Override
//			public void execute(PTemplate pTemplate) {
//				if(pTemplate.statistics == null){
//					return;
//				}
//				stats.add(pTemplate.statistics.clone());
//			}
//		});
//
//		person.setVariable(VConstants.update, stats);
//	}


	public void setRationalChild(String type,String dominiantChild) {
		getRationalMap().put(type, dominiantChild);
	}


	


	public PBase getRationalMap() {
		return (PBase) get(VConstants.templatemap);
	}


	{
		if(getRationalMap() == null){
			setRationalMap(new PBase());
		}
	}

	public void setRationalMap(PBase rationalMap) {
		put(VConstants.templatemap,rationalMap);
	}

	//change to disable property on template
	public Set<String> disableSet = new HashSet();;


	public void clear(){
		stack.clear();
	}

	@Override
	public String toString() {
		
		return ""+stack;
	}
	
	public LocalTemplate clone() {
		return new LocalTemplate().copyDeepProperties(this);
	}	
}
