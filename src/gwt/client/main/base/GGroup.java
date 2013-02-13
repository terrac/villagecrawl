package gwt.client.main.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gwt.client.item.Item;
import gwt.client.main.LocalTemplate;
import gwt.client.main.MList;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.MapData;
import gwt.client.personality.Stats;

public class GGroup extends HMapData{
	
	GGroup parent;


	public GGroup() {
	
	}

	// gets shallow copied rather than deep copied
	Map<String,Object> variables;

	

	public GGroup(GGroup parent,  String type) {
		super();
		this.parent = parent;
		this.type = type;
	}
	
	public GGroup(GGroup parent,  String type,Map<String,Object> map,Map<String,Object> m2) {
		this(parent,  type);
		
		getObjMap().putAll(map);
		
		this.variables = m2;
	}

	public GGroup getParent() {
		return parent;
	}

	public void setParent(GGroup parent) {
		this.parent = parent;
	}
	
	public void synchronizeGroups(LocalTemplate lt){
		lt.setRationalChild(type,type);
		if(getParent() == null){
			return;
		}
		getParent().synchronizeGroups(lt);
	}
	String type;
	
	@Override
	public MapData get(String key) {
		MapData md=super.getMapData(key);
		if(md == null&&getParent() != null){
			md=getParent().get(key);
		}
		return md;
	}

	public MList<LivingBeing> getTribe() {
		return (MList<LivingBeing>) get(VConstants.tribe);				
	}
	public MList<LivingBeing> getFriends() {
		
		MList<LivingBeing> mList = (MList<LivingBeing>) get(VConstants.friends);
		if(mList == null){
			put(VConstants.friends, new MList<LivingBeing>());
			mList = (MList<LivingBeing>) get(VConstants.friends);
		}
		return mList;				
	}
	public void askAll(OObject object,LivingBeing LivingBeing, List<LivingBeing> list) {
		askAll(new OObject[]{object}, LivingBeing, list);
	}
	public void askAll(OObject[] objects,LivingBeing LivingBeing, List<LivingBeing> list) {
		LivingBeing.setVariable(VConstants.doer, LivingBeing);
		LivingBeing.setVariable(VConstants.doees, list);
		
	
		
		for(LivingBeing p : list){
			p.getTemplate().clear();
		}
		LivingBeing.getTemplate().clear();
		for(OObject oo : objects){
			LivingBeing.getTemplate().push(oo);
			for(LivingBeing p : list){
				p.getTemplate().push(oo);
				
			}	
		}
		
		for(LivingBeing p : list){
		
			p.setVariable(VConstants.doer, LivingBeing);
			p.getTemplate().push(new Unset());
			
		}
		LivingBeing.getTemplate().push(new Unset());
		
	}
	public void setDoer(OObject obj,LivingBeing LivingBeing, List<LivingBeing> list) {
		
		LivingBeing.setVariable(VConstants.doer, LivingBeing);
		for(LivingBeing p : list){
			p.setVariable(VConstants.doer, LivingBeing);
		}
		
	}

	public LivingBeing getRelationship() {
		return (LivingBeing) get(VConstants.relationship);
		
	}
	
	public boolean readyForBaby(LivingBeing LivingBeing) {
//		if(!LivingBeing.getStats().getSex().equals(Stats.Sex.Female.name())){
//			return false;
//		}
		if(LivingBeing.get("pregnancy") != null){
			return false;
		}
		for(LivingBeing p :getFamily()){
			if(p.getYearsAge() < 2){
				return false;
			}
		}
		
		
		
		return true;
	}

	public List<LivingBeing> getFamily() {
		List<LivingBeing> mList = (List<LivingBeing>) get(VConstants.family);
		if(mList == null){
			put(VConstants.family, new MList<LivingBeing>());
			mList = (List<LivingBeing>) get(VConstants.family);
		}
		return mList;
	}
	
	public void setFamily(List<LivingBeing> mList) {
		
			put(VConstants.family, new MList<LivingBeing>());
		
		
	}
	@Override
	public GGroup clone() {
		if(getParent() == null){
			return new GGroup(null, type,getObjMap(),variables);
		}
		GGroup ggr=new GGroup(getParent().clone(),  type,getObjMap(),variables);
		return ggr;
	}

	public void putStatic(String name, Object obj) {
		if(variables == null){
			variables = new HashMap<String, Object>();			
		}
		variables.put(name, obj);
	}
	public Object getStatic(String key) {
		if(variables == null){
			variables = new HashMap<String, Object>();			
		}
		Object md=variables.get(key);
		if(md == null&&getParent() != null){
			md=getParent().getStatic(key);
		}
		return md;
	}
	public Object getStatic(String key, Object con){
		Object obj= getStatic(key);
		if(obj == null){
			obj = con;
	
		}
		return obj;
	}

	
	public class Unset extends OObject{
		@Override
		public Returnable execute(FullMapData fullMapData,
				LivingBeing LivingBeing) {
			LivingBeing.unsetVariable(VConstants.doer);
			LivingBeing.unsetVariable(VConstants.doees);
			for(LivingBeing p: LivingBeing.getGroup().getFriends()){
				p.unsetVariable(VConstants.doer);
			}
			return null;
			
		}
		@Override
		public String toString() {
			
			return "unset";
		}
		@Override
		public OObject clone() {
			
			return new Unset();
		}
	};
	
}

