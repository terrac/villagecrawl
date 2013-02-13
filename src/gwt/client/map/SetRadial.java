package gwt.client.map;

import gwt.client.main.base.LivingBeing;
import gwt.client.map.runners.BaseSetFull;
import gwt.client.person.Building;

public class SetRadial extends BaseSetFull{
		
		public SetRadial(LivingBeing person ,Building building) {
			super();
			this.person = person;
			this.building = building;
		}
		Building building;
		@Override
		public void runSetup(FullMapData current, FullMapData fmd, int x, int y) {
			int number = fmd.getXsize() * fmd.getYsize();
			int increment = building.getHealth();
			int a = 0,b = 0;
			while(number > increment){
					a = increment / fmd.getXsize();
					b = increment % fmd.getYsize();
					
					
					
					HashMapData hmd = current.getData(a+x,b+y);
					runHMD(hmd);
					for(MapData md : fmd.getData(a,b).removableValues()){						
						runMD(hmd, md);
					}
				}
			}
		}
