package gwt.client.statisticalciv.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;

public class Age implements PBaseRule{

	public static final String YOUNG_ADULT = "young adult";
	static List<String> names= Arrays.asList(new String[]{"infant","child",YOUNG_ADULT,"adult","middle aged","elderly"});
	static List<Integer> ranges = Arrays.asList(new Integer[]{0,5,12,21,34,65,99999});
	
	List<DemograpicMapping> mappingList = new ArrayList<Age.DemograpicMapping>();
	{
		mappingList.add(new DemograpicMapping(.5, VConstants.conflict, DCon.fundamentalism));
		mappingList.add(new DemograpicMapping(-.1, SConstants.disease, DCon.fundamentalism));
		mappingList.add(new DemograpicMapping(.05, VConstants.population, DCon.fundamentalism));
	}
	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		ageOneYear(p,hmd);

		return true;
	}
	public static void ageOneYear(PBase p, HashMapData hmd) {
		Demographics demo = (Demographics)p;
		birth(demo, .1,hmd);
		List<Integer> ageList =p.getListCreate(VConstants.age);
		
		int averageAge=30;
		//kill due to aging
		double infantMortality = .2;
		//a infant mortality rate is seperate
		for(int a = 0; a < 5&&a <ageList.size(); a++){
			if(VConstants.getRandom().nextDouble() < infantMortality){
				int number=ageList.get(a);
				if( number > 0){
					number--;
					ageList.set(a, number);
					
				}
			}
		}
		//for each age there is a % chance that the individual will die
		//aging deaths only start on average age
		
		
		for(int a = ageList.size()-1; a > averageAge; a--){
			int number=ageList.get(a);
			
			if(VConstants.getRandom().nextDouble() < (a-averageAge) * .01){
				if( number > 0){
					number--;
					ageList.set(a, number);
					
				}
			}
			if(number <= 0&& a == ageList.size()-1){
				ageList.remove(a);
			}
		}
		double size = 0;
		for(int a = 0; a < names.size(); a++){
			double c = 0;
			for(int b = ranges.get(a); b < ranges.get(a+1)&& b < ageList.size();b++){
				c += ageList.get(b);
			}
			size += c;
			p.put(names.get(a), c);				
			
		}
		for(String a : names){
			p.put(a, p.getDouble(a)/size);
		}
		p.put(VConstants.size, size);
	}
	//eventually have an option to hit others for a certain %
	public static void kill(PBase p,String name, double size){
		
		
		double subtracted=size;
		List<Integer> ageList =p.getListCreate(VConstants.age);
		for(int a = 0; a < names.size(); a++){
			if(names.get(a).equals(name)){
				p.put(Demographics.averageAge,(1.0-(double) a/names.size()));
				
				for(int b = ranges.get(a); b < ranges.get(a+1)&& b < ageList.size();b++){
					size -= ageList.get(b);
					
					if(size > 0){
						ageList.set(b, 0);
					} else{
						ageList.set(b,(int) (ageList.get(b)-size));
						PBase.decrement(p, VConstants.size, subtracted);												
						return;
					}
				}
			}
		}
		
		int count = 7;
		while(size > 0&&count > 0){
			count--;
			int a = VConstants.getRandom().nextInt(names.size());
			for(int b = ranges.get(a); b < ranges.get(a+1)&& b < ageList.size();b++){
				size -= ageList.get(b);
				if(size > 0){
					ageList.set(b, 0);
				} else{
					ageList.set(b,(int) (ageList.get(b)+size));
					PBase.decrement(p, VConstants.size, subtracted);
					return;
				}
			}
		}
		PBase.decrement(p, VConstants.size, subtracted-size);
	}
	public static void birth(Demographics p, double amt, HashMapData hmd){
		List<Integer> ageList =p.getListCreate(VConstants.age);
		double female = p.getDouble(Demographics.female);
		if(female >.5){
			female -= .01;
		} else {
			female += .01;
		}
		p.put(Demographics.female, female);
		p.put(Demographics.male, 1-female);
		
		double aa = p.getDouble(Demographics.averageAge);
		if(aa >.5){
			aa -= .01;
		} else {
			aa += .01;
		}
		p.put(Demographics.averageAge, aa);
		
		PBase leader = DemographicRule.getSingleton().getLeader(p);
		double maxsize=leader.getDouble(VConstants.maxsize);
		double tilemaxsize = Demographics.getTileSize(hmd);
		if(maxsize == 0){
			maxsize = 100;
			leader.put(VConstants.maxsize, maxsize);
		}
		if(tilemaxsize == 0){
			tilemaxsize = 1.0;
		}
		maxsize = maxsize * tilemaxsize;
		int amount = (int) (maxsize * amt);
		if(amount == 0){
			amount  = VConstants.getRandom().nextInt(3);
		}
		if(p.getSize() > maxsize){
			amount = 0;
		}
		ageList.add(0, amount);		
		
	}
	public static void remove(PBase p, String name,int size) {
		kill(p, name, size);
	}
	
	class DemograpicMapping{
		double ratio;
		String mapTo;
		String mapFrom;
		public DemograpicMapping(double ratio, String mapTo, String mapFrom) {
			super();
			this.ratio = ratio;
			this.mapTo = mapTo;
			this.mapFrom = mapFrom;
		}
		public void map(PBase demographic){
			PBase.increment(demographic,VConstants.computed+ mapTo, demographic.getDouble(mapFrom)*ratio);
		}
		public void clear(PBase demographic){
			demographic.remove(VConstants.computed+mapTo);
		}
	}

	public static void ageYears(int i, Demographics demo,HashMapData hmd) {
		for(int a = 0; a < 50; a++){
			ageOneYear(demo,hmd);
		}
	}
}
