package gwt.client.game.vparams.random;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.PercentageMap;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.Items;
import gwt.shared.datamodel.VParams;

public class Formula extends VParams {

	public Formula() {

	}

	
	public Formula(int xscale, double yscale, double sharpness, int random) {
		put(VConstants.xsize,xscale);
		put(VConstants.ysize,yscale);
		put(VConstants.sharpness,sharpness);
		put(VConstants.random,random);
	}

	/**
	 * 1 up and down formula
	 * 
	 * a has a scale
	 * 
	 * b has a sharpness (ie sudden changes or gradual)
	 * 
	 * c has a randomness element (IE occasionally temporarily changes the
	 * sharpness randomly)
	 * 
	 * d has an x scale and a y scale
	 * 
	 * e stickyness (ie when things are going up it either goes up or flat, when
	 * things are going down it either goes down or flat
	 */
	public void execute(java.util.Map<String, Object> map) {
		int xsize = getInt(VConstants.xsize);
		double ysize = (Double) get(VConstants.ysize);
		double sharpness = (Double) get(VConstants.sharpness);
		int random = getInt(VConstants.random);
		
		int turn = EntryPoint.game.getTurn();
		turn = turn % xsize;
		double yHeight =(double) turn / xsize;
		yHeight = yHeight * ysize;
		
		yHeight += VConstants.getRandom().nextDouble()*sharpness;
		
		Double formula = (Double) map.get(VConstants.formula);
		if(formula == null){
			formula =  1.0;
		}
		formula = (Double) (yHeight * formula);
		map.put(VConstants.formula, formula);
	}

	@Override
	public PBase clone() {

		return new Formula().copyProperties(this);
	}

}
