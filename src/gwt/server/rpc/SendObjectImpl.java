package gwt.server.rpc;

import gwt.client.rpc.SendObject;
import gwt.server.IStore;
import gwt.server.SDao;
import gwt.server.datamodel.SaveGame;
import gwt.server.rpc.stores.SaveBag;
import gwt.server.rpc.stores.SaveJsonData;
import gwt.server.rpc.stores.StoreSaveGame;

import gwt.server.rpc.stores.SaveJsonNames;
import gwt.shared.datamodel.ClientSaveGame;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.JsonDataList;

import java.util.HashMap;
import java.util.Map;



import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SendObjectImpl extends RemoteServiceServlet implements SendObject {

	public static Map<String, IStore> defaultMap = new HashMap<String, IStore>();
	static {
		defaultMap.put(JsonDataList.class.getName(),
				new SaveJsonNames());

		defaultMap.put(JsonData.class.getName(),
				new SaveJsonData());

//		defaultMap.put("gwt.client.map.FullMapData",
//				new SaveBag());
		defaultMap.put(ClientSaveGame.class.getName(), new StoreSaveGame());
//		defaultMap.put("saveBag",
//				new SaveBag());
//		
		
	}


	
	@Override
	public void deleteObjectDefault(String type, String parentName,
			String prevName) {
		IStore a = defaultMap.get(type);
		
		a.store(prevName,parentName, null);

	}
	
	public void sendObjectDefault(IClientObject obj, String parentName,
			String prevName) {
		System.out.println(obj);
		String name = obj.getClass().getName();
		
		IStore a = defaultMap.get(name);
		if(a == null){
			throw new IllegalArgumentException(name +" missing from sendObjectDefault"+ defaultMap.keySet());
		}
		a.store(prevName,parentName, obj);

	}	
	@Override
	public void sendObjectDefault(IClientObject[] obj, String parentName,
			String[] prevName) {
		
		for(int b = 0; obj.length > b;b++){
			IStore a = defaultMap.get(obj[b].getClass().getName());
			a.store(prevName[b],parentName, obj[b]);
		}
		

	}
	
	@Override
	public void sendObjectDefault(IClientObject[] obj, String parentName,
			String prevName) {
		
		for(int b = 0; obj.length > b;b++){
			IStore a = defaultMap.get(obj[b].getClass().getName());
			a.store(prevName,parentName, obj[b]);
		}
		

	}

	@Override
	public void sendObjectDefault(String obj, String parentName,
			String prevName) {
		IStore a = defaultMap.get(obj);
		//might not want null here later
		a.store(prevName,parentName, null);

	}

	




	
//	@Override
//	public void sendObjectDefault(final IClientObject[] obj,
//			final String parentName, final String[] prevName) {
//
//		Queue queue = QueueFactory.getDefaultQueue();
//		queue.add(TaskOptions.Builder.withPayload(new DeferredTask() {
//
//			@Override
//			public void run() {
//				for (int b = 0; obj.length > b; b++) {
//					IStore a = defaultMap.get(obj[b].getClass().getName());
//					a.store(prevName[b], parentName, obj[b]);
//				}
//			}
//		}));
//
//	}
//
//	@Override
//	public void sendObjectDefault(final String obj, final String parentName,
//			final String prevName) {
//		Queue queue = QueueFactory.getDefaultQueue();
//		queue.add(TaskOptions.Builder.withPayload(new DeferredTask() {
//
//			@Override
//			public void run() {
//				IStore a = defaultMap.get(obj);
//				// might not want null here later
//				a.store(prevName, parentName, null);
//			}
//		}));
//	}
}
