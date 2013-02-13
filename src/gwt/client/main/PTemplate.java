package gwt.client.main;

import gwt.client.inter.TemplateIterate;
import gwt.client.main.base.ActionHolder;
import gwt.client.main.base.ActionObject;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MetaOObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.stats.Statistics;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;




public class PTemplate extends PBase implements Serializable, ActionObject{
	
	
	//PTemplate parent;
	//implement later if needed
	public PTemplate getParent() {
		return null;
	}
	public void setParent(String parent) {
		
	}

	
	//public List<PTemplate> children = new ArrayList<PTemplate>();
//	public void addChild(PTemplate ch){
//		for(PTemplate cch: this.children){
//			if(cch.getTemplateName().equals(ch.getTemplateName())){
//				throw new IllegalArgumentException("added two templates with same name");
//			}
//		}
//		
//		ch.parent = this.getTemplateName();
//		children.add(ch);
//	}
	 //String templateName;
	
	public String getTemplateName() {
		return (String) get(VConstants.name);
	}
	public void setTemplateName(String templateName) {
		put(VConstants.name,templateName);
	}

	//one per game, cloned off of this to run
	

	
	public List<ActionHolder> getActionList() {
		return (List<ActionHolder>) get(VConstants.actionlist);
	}
	public void setActionList(List<ActionHolder> actionList) {
		put(VConstants.actionlist,actionList);
	}
	public MetaOObject getMetaoobj() {
		return (MetaOObject) get(VConstants.metaoobject);
	}
	public void setMetaoobj(MetaOObject metaoobj) {
		put(VConstants.metaoobject,metaoobj);
	}
	public PTemplate(String name) {
		setTemplateName(name);
	}
	public PTemplate() {
		
	
	}
	{
		if(getActionList() == null){
			setActionList(new ArrayList<ActionHolder>());
		}
	}


	
	





	

	//eventually should be controlled by an xml document
	//the person registers this and other statistics
	//and if the statistics calls a match then this template
	//becomes domininant among many of a similar type.
	
//	public void iterate(TemplateIterate ti){
//		for(PTemplate ch : children){
//			
//			ch.iterate(ti);
//			
//		}
//		if(this.templateName != null){
//			ti.execute(this);
//		}
//	}
	public void postExecute(FullMapData fullMapData, LivingBeing person,
			OObject oObject, Returnable ret) {
		if(getMetaoobj() == null){
			return;
		}
	
		getMetaoobj().postExecute(fullMapData, person, oObject, ret);
		if(getParent() != null){
			getParent().postExecute(fullMapData, person, oObject, ret);
		}
	}
//	public PTemplate getChild(String name) {
//		for(PTemplate ch : children){
//			if(name.equals(ch.getTemplateName())){
//				return ch;
//			}
//		}
//		return null;
//	}
	@Override
	public String toString() {
		
		return getTemplateName() + getActionList();
	}
	
	@Override
	public PBase clone() {
		
		return new PTemplate().copyProperties(this);
	}
}
