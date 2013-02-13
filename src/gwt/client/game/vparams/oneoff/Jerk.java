package gwt.client.game.vparams.oneoff;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.adding.AddScore;
import gwt.client.game.vparams.requirements.TwoOptionEvent;
import gwt.client.main.Kill;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.shared.datamodel.VParams;

public class Jerk extends OObject {
	public Jerk() {
	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		PBase culture=EntryPoint.game.getPBase(VConstants.culture);
		// based on the relative niceness the jerk fires off more or less often
		int nice = culture.getPBase(VConstants.stats).getInt(VConstants.nice);
		if(nice > 70){
			Window.alert("this person doesn't like you.  They are leaving");
		}
		if(VConstants.getRandom().nextInt(10) != 0){
			return null;
		}
		int rand=VConstants.getRandom().nextInt(100);
		if(rand < nice){
			return null;
		}
		
		String question=(String)VConstants.getRandomFromList(culture.getList(VConstants.jerk));
		// b a picks a person on the fmd
		LivingBeing lb = VConstants.getRandomFromList(fullMapData.people);
		if(person.equals(lb)){
			return null;
		}
		// c pops up two choices of get rid of or don't
		VParams vp=new TwoOptionEvent(lb.getName()+" the"+ lb.getType()+" should leave."+question, new VParams(new Kill(lb),new AddScore(VConstants.nice, 3)),new AddScore(VConstants.nice, -20) )
		;
		vp.execute(null);
		// d not doing so adds a little niceness
		// e doing so removes a lot
		// f a lot of niceness makes the jerk leave
		
		
		// j a little bit makes the jerk a lot more efficient
		//
		
		return null;
	}

	@Override
	public OObject clone() {

		return new Jerk().copyProperties(this);
	}


}
