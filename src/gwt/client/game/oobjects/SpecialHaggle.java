package gwt.client.game.oobjects;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UImage;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.TagGenerator;
import gwt.client.item.Item;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.output.OutputDirector;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class SpecialHaggle extends Haggle {

	public SpecialHaggle() {

	}
	PBase quest;
	int sLineCount = 0;
	@Override
	protected void getItem(LivingBeing person) {
		// choose a pbase from the quest list
		quest = person.getPBase(VConstants.quest);
		item = (Item) quest.get(VConstants.item);
	}

	public String getLine(String specific) {
		List<String> lines = quest.getList(VConstants.list);
		if(lines != null&&lines.size() > sLineCount){
			
			String a= lines.get(sLineCount);
			sLineCount++;
			return a;
	
		}
		return super.getLine(specific);
	}
	@Override
	protected String getInitialOffer() {
		List<String> lines = quest.getList(VConstants.list);
		if(lines != null&&lines.size() > sLineCount){
			
			String a= lines.get(sLineCount);
			sLineCount++;
			return a;
	
		}
		return super.getInitialOffer();
	}
	
	@Override
	protected void runAccept() {
		AttachUtil.run(VConstants.success, null, quest);
	}
	
	@Override
	protected void addImage(HorizontalPanel hp, LivingBeing person) {
		hp.add(new Image(person.getAvatar()));
	}
	@Override
	public OObject clone() {
		return new SpecialHaggle().copyProperties(this);
	}
}