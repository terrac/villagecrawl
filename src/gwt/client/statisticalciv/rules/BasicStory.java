package gwt.client.statisticalciv.rules;

import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.user.client.Window;

import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.UIVParams;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.UVLabel;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.ClientBuild;

public class BasicStory implements PBaseRule{

	public BasicStory() {
		// TODO Auto-generated constructor stub
	}
	String message;
	PBaseRule pbr;
	private Double chance;
	private PBase correlations;
	private String popup;


	public BasicStory(String popup,String message, PBaseRule pbr) {
		super();
		this.message = message;
		this.pbr = pbr;
		this.popup = popup;
	}
	
	public BasicStory(String popup,String message,double chance, PBaseRule pbr) {
		super();
		this.message = message;
		this.pbr = pbr;
		this.chance = chance;
		this.popup = popup;
	}
	public BasicStory(String popup,String message,PBase correlations, PBaseRule pbr) {
		super();
		this.message = message;
		this.pbr = pbr;
		this.correlations = correlations;
		this.popup = popup;
	}

	public static void runStory(List<PBaseRule> beginningStories,
			Demographics demo, HashMapData hmd, FullMapData fmd) {
		for(int a = 0; a < 2; a++){
			BasicStory bs = (BasicStory) VConstants.getRandomFromList(beginningStories);
			if(bs.chance != null){
				if(bs.chance > VConstants.getRandom().nextDouble()){
					bs.run(demo, hmd, fmd);
					return;
				}
				continue;
			}
			
			
			//compare each stat in the demo to the correlation and test
			int count = 0;
			for(Entry<String, Object> ent : bs.correlations.getObjMap().entrySet()){
				Double d = demo.getDouble(ent.getKey());
				if(d+.5 - (Double)ent.getValue() > VConstants.getRandom().nextDouble()){
					count++;
				}
			}
			if(count >= bs.correlations.getObjMap().size()-1){
				bs.run(demo, hmd, fmd);
			}
		}
	}

	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		
		if(pbr != null){
			if(pbr.run(p, hmd, fmd)){
				if(false){
					DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
							 new UImage("/images/"+popup)));
					displayPopup.displaypopup(hmd,hmd.getPosition(),  3);							
					
				} else {
					LogDisplay.log(message, 2);
				}
			}
		}
		//story message pops up with an option to pause on story popups
		//
		//associated list of demographic change by number or percentage
		//(probably by percentage)
		
		//So a normal story might be that you start out with 4 villages
		//All of them start off not in contact and send out people that explore
		//if the explorers find a good spot then they return and a new sub village is
		//founded.  
		//that village then trades with the main village
		//flooding occurs in the main village.  It happens to strike young women because
		//women happened to be in a ritual nearby
		//because of this conflict is greatly increased
		//the older men manage to send a group of young men off to kill an imaginary 
		//threat in the wilderness and order is restored
		// (alternatively they lose the random roll and the young men engage in an uprising
		// killing off a number of the elderly men.  They then start a fight with the
		// sub village.  Once the young men are killed off order is restored.
		
		//so to sum it up disasters/etc cause demographic shifts in ways that tell
		// a story.  The initial stories told should be like gilgamesh, etc
		//A story should be told about fundamentalism liberalism and disease
		//(that adds another demographic and fundamentalists are resistant to disease)
		//so technologies are added over time , and as technologies are added the 
		//demographics can potentially expand to take advantage of those technologies
		
		//bob had a prediection towards billy and thought that he would have a 
		//greater shot with him if he encouraged sexist views
		
		//add if statements and such in here to do basic elements through properties
		return true;
	}


}
