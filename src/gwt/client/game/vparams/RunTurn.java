package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.Pop;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class RunTurn extends VParams {
	List<PBase> pblist = new ArrayList<PBase>();
	@Override
	public void execute(Map<String, Object> map) {
		for(PBase md :pblist){
			List lpb = md.getListCreate(VConstants.pbase);
			List<Integer> ltu = md.getListCreate(VConstants.turn);
			for (int a = 0; a < ltu.size(); a++) {
				if (EntryPoint.game.getTurn() >= ltu.get(a)) {
					PBase pb = (PBase) lpb.remove(a);
					ltu.remove(a);
					if (pb instanceof Pop) {
						((Pop) pb).pop();
						continue;
					}
				}
			}

		}
		
		// take the current turn, if any of the held objects match the current
		// turn then remove them
		//
		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		List lpb = lb.getListCreate(VConstants.pbase);
		List<Integer> ltu = lb.getListCreate(VConstants.turn);
		for (int a = 0; a < ltu.size(); a++) {
			if (EntryPoint.game.getTurn() >= ltu.get(a)) {
				PBase pb = (PBase) lpb.remove(a);
				ltu.remove(a);
				if (pb instanceof Pop) {
					((Pop) pb).pop();
					continue;
				}
				// pb.remove(VConstants.disabled);
				lb.toggleDisabled(pb.getS(VConstants.name));

				lb.getAlterHolder().remove(pb.getS(VConstants.name));

				// Integer in=(Integer) pb.get(VConstants.repeat);
				// if(in != null&& in != 0){
				// add(lb, pb, in);
				// }
			}
		}

	}

	public void onDeath(LivingBeing lb){
		for(Object pb :lb.getListCreate(VConstants.pbase)){
			if (pb instanceof Pop) {
				((Pop) pb).pop();
			}
		}
	}
	public void add(LivingBeing lb, PBase pbase) {

		lb.getListCreate(VConstants.pbase).add(pbase);
		lb.getListCreate(VConstants.turn).add(
				EntryPoint.game.getTurn()
						+ pbase.getInt(VConstants.rechargetime));
	}

	public void add(LivingBeing lb, PBase pbase, int turns) {

		lb.getListCreate(VConstants.pbase).add(pbase);
		lb.getListCreate(VConstants.turn)
				.add(EntryPoint.game.getTurn() + turns);
	}
	
	public void add(PBase md, PBase pbase, int turns) {

		md.getListCreate(VConstants.pbase).add(pbase);
		md.getListCreate(VConstants.turn)
				.add(EntryPoint.game.getTurn() + turns);
		pblist.add(md);
	}

	@Override
	public PBase clone() {

		return new RunTurn().copyProperties(this);
	}
}
