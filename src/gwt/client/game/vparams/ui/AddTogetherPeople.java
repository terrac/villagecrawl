package gwt.client.game.vparams.ui;

import java.util.Map;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.display.UIVParams;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.shared.datamodel.VParams;

public class AddTogetherPeople extends VParams{


	public AddTogetherPeople() {	
	}
	public AddTogetherPeople(String type) {
		super();
		put(VConstants.type,type);
	}
	public String getType(){
		return getS(VConstants.type);
	}
	
	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd;
		if(map.get(AttachUtil.OBJECT) == null){
			return;
		}
		if(map.get(AttachUtil.OBJECT) instanceof LivingBeing){			
			LivingBeing lb= (LivingBeing) map.get(AttachUtil.OBJECT);
			if(lb.getParent() == null|| lb.getParent().getParent() == null){
				return;
			}
			fmd = lb.getParent().getParent();
		} else {
			fmd = (FullMapData) map.get(AttachUtil.OBJECT);
		}
		int total = 0;
		for(LivingBeing per:GameUtil.getPlayerTeam(fmd.people)){
			Integer integer = (Integer)per.get(getType());
			if(integer == null){
				//if there is an item with the name then add it on.
				if(per.getItems() != null){
					continue;
				}
				Item item = per.getItems().getItem(getType());
				if(item == null){
					continue;
				}
				integer=item.getAmount();								
			}
			total+=integer;
		}
		PBase pb=(PBase) map.get(AttachUtil.attacher);
		pb.put(getType(), total);
	}
	

	@Override
	public PBase clone() {
		
		return new AddTogetherPeople().copyProperties(this);
	}	
}
