package gwt.client.game.vparams.requirements;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.TagGenerator;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class CultureStats extends VParams {
	public CultureStats(String stat,int statover,VParams vp) {
		put(VConstants.stats,stat);
		put(VConstants.size,statover);
		put(VConstants.vparams,vp);
	}
	
	public CultureStats(String stat,int statover,VParams vp,boolean not) {
		this(stat, statover, vp);
		put(VConstants.not,true);
	}
	
	public CultureStats() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void execute(Map<String, Object> map) {
		
		int stat=EntryPoint.game.getPBase(VConstants.culture).getPBase(VConstants.stats).getInt(getS(VConstants.stats));
		if(getInt(VConstants.size)< stat||getB(VConstants.not)){
			((VParams) get(VConstants.vparams)).execute(map);
		}
	}
	@Override	
	public PBase clone() {
		
		return new CultureStats().copyProperties(this);
	}
	
}
