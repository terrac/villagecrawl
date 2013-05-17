package gwt.server.rpc.gets;

import java.util.Map;

import gwt.server.IGet;
import gwt.server.SDao;
import gwt.shared.datamodel.CString;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;

public class GetJsonData implements IGet {

	@Override
	public IClientObject get(String parentName,
			Map<String, IClientObject> prevParams) {
		if (parentName == null) {
			return null;
		}
		JsonData jd=SDao.getJsonDataDao().getRN(Long.parseLong(parentName));
		if (jd == null) {
			return null;
		}
		
		return new CString(new String(jd.getData().getBytes()));

	}
}
