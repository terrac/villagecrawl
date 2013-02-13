package gwt.client.game.vparams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class AlterFilter extends VParams {

	public AlterFilter() {

	}

	public AlterFilter(String... strings) {
		getListCreate(VConstants.list).addAll(Arrays.asList(strings));
	}

	@Override
	public void execute(Map<String, Object> map) {
		List l = getListCreate(VConstants.list);

		PBase base = EntryPoint.game;

		filter(l, base, 0);
	}

	protected void filter(List l, PBase base, int a) {

		String command = (String) l.get(a);
		String key = (String) l.get(a + 1);
		if ("get".equals(command)) {

			base = (PBase) base.get(key);
			filter(l, base, a + 2);
		}

		if ("filter".equals(command)) {
			for (String b : base.getObjMap().keySet()) {
				PBase pb = (PBase) base.get(b);
				String pbvalue = pb.getS(key);
				if (pbvalue != null && pbvalue.equals(l.get(a + 2))) {
					filter(l, pb, a + 3);
				}
			}

		}
		if ("set".equals(command)) {
			// key/value doesn't have to be a string
			base.put(key, l.get(a + 2));
		}

	}

	@Override
	public PBase clone() {

		return new AlterFilter().copyProperties(this);
	}
}
