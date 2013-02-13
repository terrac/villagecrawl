package gwt.client.edit;

import gwt.client.EntryPoint;
import gwt.client.item.Item;
import gwt.client.main.Game;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.output.OutputDirector;
import gwt.client.rpc.GetObject;
import gwt.client.rpc.GetObjectAsync;
import gwt.client.rpc.IExecute;
import gwt.client.rpc.SendObject;
import gwt.client.rpc.SendObjectAsync;
import gwt.shared.datamodel.IClientObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HtmlEditPage extends EditPage<FlexTable>{
//	SendObjectAsync soa = GWT.create(SendObject.class);
//	GetObjectAsync goa = GWT.create(GetObject.class);
//	public static final int HtmlEditPageRow = 0;
//	VerticalPanel activeBox = new VerticalPanel();
//	FlexTable flextable = new FlexTable();
//	BagMap editMap = null;
//	MenuBar menuBar = new MenuBar();
//	EditInit editInit = new EditInit();
//	public HtmlEditPage() {
//		
//		
//		
//		
//	}
//	
//	@Override
//	public void init(String type) {
//		if("map".equals(type)){
//			init();
//			return;
//		}
//		editInit.init(type, null);
//	}
//	@Override
//	public void init(FlexTable panel) {
//		panel.setWidget(HtmlEditPageRow, 0, flextable);
//		panel.setWidget(4, 0, editInit.flextable);
//		RootPanel.get("side").add(menuBar);
//		
//		
//	}
//	public void init() {
//		if(OutputDirector.timer.getWait()){
//			OutputDirector.timer.executeList.add(new IExecute() {
//				
//				@Override
//				public void execute() {
//					init();
//				}
//			});
//			return;
//		}
//		edit = true;
//		Game game = EntryPoint.game;
//		
//		
//		HorizontalPanel items = new HorizontalPanel();
//		
//		//flextable.setWidget(0,0,flextable);
//		
//		flextable.setWidget(1,0,items);
//		
//		flextable.setWidget(0,1, activeBox);
//		
//		MenuBar io = new MenuBar();
//		
//		
//		final MenuBar maps = new MenuBar();
//		final MenuBar bags = new MenuBar();
//		
//		menuBar.addItem("IO", io);
//		menuBar.addItem("Maps", maps);
//		menuBar.addItem("Bags", bags);
//		
//		io.addItem("Save",new Command() {
//			
//			
//			@Override
//			public void execute() {
//				
//				
//				soa.sendObjectDefault( EntryPoint.game,"" ,"notdoneyet" , new AsyncCallback<Void>() {
//					
//					@Override
//					public void onSuccess(Void result) {
//						Window.alert("saved!");
//						
//						MenuBar name1 = new MenuBar();
//						String name = EntryPoint.game.getName();
//						maps.addItem(name, name1);
//						name1.addItem("load map", new LoadMap(name));
//						
//						
//						//name1.addItem("load bag",new LoadBag(name));
//						//add item to load map if doesn't exist
//					}
//					
//					@Override
//					public void onFailure(Throwable caught) {
//						caught.printStackTrace();
//						throw new RuntimeException(caught);
//					}
//				});
//				//post to persons personal map list
//				
//				//if this is say a default thing then it just saves with the same name
//				
//				
//			}
//		});
//		
////		io.addItem("saveBag",new Command() {
////			
////			@Override
////			public void execute() {
////				soa.sendObjectDefault(editMap.bagMap, editMap.bagName.getText(), "notdoneyet", new AsyncCallback<Void>() {
////
////					@Override
////					public void onSuccess(Void result) {
////						Window.alert("Saved bag!");
////						MenuBar name1 = new MenuBar();
////						String name = editMap.bagName.getText();
////						bags.addItem(name, name1);
////						name1.addItem("load bag", new LoadBag(name));
////						
////						
////					}
////					@Override
////					public void onFailure(Throwable caught) {
////						Window.alert(""+caught);
////						throw new RuntimeException(caught);
////						
////					}
////
////					
////				});
////				
////			}
////		});
//		
//		TextBox gameName = new TextBox();
//		Label gameLab = new Label("gamename");
//		gameName.setText(EntryPoint.game.getName());
//		gameName.addChangeHandler(new ChangeHandler() {
//			
//			@Override
//			public void onChange(ChangeEvent event) {
//				TextBox gameName=(TextBox) event.getSource();
//				EntryPoint.game.setName( gameName.getText());
//			}
//		});
//		items.add(gameLab);
//		items.add(gameName);
//		
//		editMap.init();
//	}
//	
//	class LoadMap  implements Command{
//		String name;
//		public LoadMap(String name) {
//			super();
//			this.name = name;
//		}
//		@Override
//		public void execute() {
//			//sets to opengameservlet which then starts the specified game
//			
//			//need to create a static link to share.
//			Window.open("/?name="+name, "_self", "");
//
//				
//		}
//	};
//	
//	class LoadBag  implements Command{
//		String name;
//		public LoadBag(String name2) {
//			name = name2;
//		}
//		@Override
//		public void execute() {
//			goa.getObject("bag", name, new AsyncCallback<Map<String,IClientObject>>() {
//				
//				@Override
//				public void onSuccess(Map<String, IClientObject> result) {
//					//editMap.bagMap = (AreaMap<MapData, MapData>) result.get(name);
//					
//					//editMap.setupBag();
//					EntryPoint.game.getHtmlOut().refreshFmds();
//				}
//				
//				@Override
//				public void onFailure(Throwable caught) {
//					Window.alert(""+caught);
//					caught.printStackTrace();
//				}
//			});
//			
//		}
//	};



//	@Override
//	public void displayMapData(HashMapData md) {
//		editMap.mainMapClicked(md);
//	}
}
