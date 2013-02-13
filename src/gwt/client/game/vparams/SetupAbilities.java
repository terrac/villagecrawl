package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class SetupAbilities extends VParams {

	@Override
	public void execute(Map<String, Object> map) {

		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		List<PBase> l = EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.allskills).getListCreate(lb.getAttributes().getS(VConstants.classes));
		List<String> las=lb.getListCreate(VConstants.abilitysetup);
		List<String> fla=lb.getListCreate(VConstants.ability);
		for (PBase pb : l) {
			String pbname = (String) pb.get(VConstants.name);
			if(!las.contains(pbname)){
				continue;
			}
			fla.add(pbname);
			Integer rech = (Integer) pb.get(VConstants.rechargetime);
			if (rech == null) {
				lb.getAlterHolder().put(pb.getS(VConstants.name), pb);
			} else {
				// enable


				PBase object = pb.getPBase(VConstants.abilityai);
				String template=pb.getS(VConstants.template);
				if(template != null){
					//if(EntryPoint.game.getPBase(VConstants.person).containsKey((String) object)){
						lb.getTemplate().setRationalChild(VConstants.ability, template);
						lb.getType(VConstants.temporary).put(template,pb.getS(VConstants.name));
						
						
					//}
					
				}
				
				VParams pbase =(VParams) object;
				if(pbase != null){
					String action=pbase.getS(VConstants.action);
					String team = pbase.getS(VConstants.team);
					String actor = pbase.getS(VConstants.actor);
					if(VConstants.self.equals(actor)){
						pbase.put(AttachUtil.OBJECT, lb);
					}
					if(team != null){
						
						AttachUtil.attach(action, pbase, EntryPoint.game.getType(VConstants.team).getType(team));	
						
					} else {
						AttachUtil.attach(action, pbase, lb);
					}

				}
								
					
				
				
				

			}

		}

	}

	@Override
	public PBase clone() {

		return new SetupAbilities().copyProperties(this);
	}
}
