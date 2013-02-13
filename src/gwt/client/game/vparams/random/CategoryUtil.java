package gwt.client.game.vparams.random;

import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

public class CategoryUtil {
	public static List<String> getAssoc(String category, PBase assocCat) {
		List<String> claa = new ArrayList<String>();

		for (Entry<String, Object> o : assocCat.getObjMap().entrySet()) {
			List<String> cat = ((PBase) o.getValue())
					.getListCreate(VConstants.categories);
			

			if (cat.contains(category)) {
				claa.add(o.getKey());
				

			}

			

		}
		return claa;
	}
}
