package gwt.client.output;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.VisualDamage;
import gwt.client.game.display.DemographicDisplay;
import gwt.client.game.display.HMDDisplay;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.display.PauseDisplay;
import gwt.client.game.display.SpeedDisplay;
import gwt.client.game.display.UIVParams;
import gwt.client.item.Item;
import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.output.html.Click;
import gwt.client.output.html.GCanvas;
import gwt.client.rpc.IExecute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

public class HtmlOut extends MainPanel<GCanvas> {

	String[] equipA = new String[] { VConstants.cloak, VConstants.base,
			VConstants.body, VConstants.head, VConstants.leg,
			VConstants.gloves, VConstants.shield, VConstants.weapon };

	public static CssColor red;
	public static CssColor green;

	{
		if (GWT.isClient()) {
			green = CssColor.make(0, 255, 0);
			red = CssColor.make(255, 0, 0);
		}

	}

	public static final String DISPLAY_MAP_DATA = "displayMapData";
	public static final String refreshList = "refreshlist";
	public static final int HtmlOutRow = 2;

	public FlexTable flextable;
	public Label gdp;
	Boolean prevPause;

	// Resources resources;
	public static Map<String, CanvasElement> imap = new HashMap<String, CanvasElement>();

	// static String[] files = {
	// "/images/barley.png\r\n",
	// "/images/butter-churn.png\r\n",
	// // pull all the images from what starts out on all the different
	// // maps
	//
	// "/images/"+ItemEquipCanvas.EMPTYEQUIPBLOCK+".png\r\n",
	//
	// "/images/exit.png\r\n", "/images/gold.png\r\n",
	//
	// "/images/enemygeneral.png\r\n", "/images/trophy.png\r\n",
	//
	// "/images/firefighter.png\r\n", "/images/earthfighter.png\r\n",
	// "/images/airfighter.png\r\n", "/images/waterfighter.png\r\n",
	//
	// "/images/firerange.png\r\n", "/images/earthrange.png\r\n",
	// "/images/airrange.png\r\n", "/images/waterrange.png\r\n",
	//
	// "/images/firedamage.png\r\n", "/images/earthdamage.png\r\n",
	// "/images/airdamage.png\r\n", "/images/waterdamage.png\r\n",
	//
	// "/images/chair.png\r\n", "/images/grinding-stone.png\r\n",
	// "/images/pot.png\r\n", "/images/fish.png\r\n",
	// "/images/water.png\r\n", "/images/soil.png\r\n",
	// "/images/multiItem.png\r\n", "/images/chicken.png\r\n",
	// "/images/storage.png\r\n", "/images/kiln.png\r\n",
	// "/images/cow.png\r\n", "/images/deer.png\r\n",
	// "/images/dining-room.png\r\n", "/images/door.png\r\n",
	// "/images/femaleadult.png\r\n", "/images/femalechild.png\r\n",
	// "/images/malechild.png\r\n", "/images/forest.png\r\n",
	// "/images/gate.png\r\n", "/images/grass.png\r\n",
	// "/images/halfgrass.png\r\n", "/images/kitchen.png\r\n",
	// "/images/maleadult.png\r\n", "/images/milk.png\r\n",
	// "/images/none.png\r\n", "/images/null.png\r\n",
	// "/images/nullnull.png\r\n", "/images/oats.png\r\n",
	// "/images/firepit.png\r\n", "/images/person.png\r\n",
	// "/images/plains.png\r\n", "/images/question.png\r\n",
	// "/images/river.png\r\n", "/images/rock.png\r\n",
	// "/images/sorgum.png\r\n", "/images/table.png\r\n",
	// "/images/wall.png\r\n", "/images/wheat.png\r\n",
	// "/images/wild-onions.png\r\n", "/images/wolf.png\r\n", };
	//
	// public static void setupImages() {
	// imap = new HashMap();
	//
	//
	// }

	public TabPanel panel;

	public void init() {

		RootPanel rootPanel = RootPanel.get("mainpage");
		if(rootPanel == null){
			return;
		}
		// panel = new FlexTable();
		// editPage = new HtmlEditPage();
		// editPage.init(panel);

		panel = new TabPanel();

		flextable = new FlexTable();
		rootPanel.add(panel);

		panel.add(flextable, "map");

		panel.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Game game = EntryPoint.game;

				if (event.getSelectedItem() == 0) {
					if (prevPause != null && !prevPause) {
						game.pauseToggle();
					}
					prevPause = null;
					return;
				}
				if (prevPause == null) {
					prevPause = game.pause;
				}

				game.pause();
				AttachUtil.run(AttachUtil.selectTab, event.getSelectedItem(),
						HtmlOut.this);

			}
		});

		displayTopPanel();
		panel.setSize("90%", "90%");

		List<String> uvlist = getListCreate(VConstants.tab);

		for (String up : uvlist) {
			panel.add(((UIVParams) EntryPoint.game.getVParams().get(up))
					.getWidgetAndInit(), up);
		}

		for (List l : (List<List>) EntryPoint.game
				.getListCreate(VConstants.flextable)) {
			List p = (List) l.get(0);

			UIVParams up = null;
			Object o = l.get(1);
			;
			if (o instanceof UIVParams) {
				up = (UIVParams) o;
			} else {
				up = (UIVParams) EntryPoint.game.getVParams().get(o);
			}
			flextable.setWidget((Integer) p.get(0), (Integer) p.get(1),
					up.getWidgetAndInit());
			// up.update();
			// addRefreshOnLoad(up);
		}


		final VerticalPanel vp = new VerticalPanel();
		panel.add(vp, "options");
		SpeedDisplay speedDisplay = new SpeedDisplay();

		vp.add(speedDisplay.getWidgetAndInit());
		try {
			String speed = Cookies.getCookie("speed");
			if (speed != null) {
				speedDisplay.tb.setText(speed);
			}
		} catch (NumberFormatException e) {

		}
		boolean shouldMute = false;
		if ("true".equals(Cookies.getCookie("mute"))) {
			shouldMute = false;
		}
		CheckBox cb = new CheckBox("mute");
		cb.setValue(shouldMute);
		EntryPoint.game.put(VConstants.sound, !shouldMute);
		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				EntryPoint.game.put(VConstants.sound, !event.getValue());
				if (event.getValue()) {
					OutputDirector.soundPlayer.stopMusic();
					Cookies.setCookie("mute", "true");
				} else {
					OutputDirector.soundPlayer.playCurrentMusic();
					Cookies.setCookie("mute", "false");
				}
			}
		});
		vp.add(cb);

		OutputDirector.timer.executeList.add(new IExecute() {

			@Override
			public void execute() {
				panel.selectTab(0);
				EntryPoint.game.getRandom();

				vp.add(new Label(EntryPoint.game.getS(VConstants.seed)));
			}
		});
	}

	public Set<String> missingImages = new HashSet<String>();
	// String[] equipOrder = new String[] { VConstants.weapon, VConstants.gloves
	// };

	private static boolean loadNew = false;

	public void drawAreaMap(AreaMap parent, final GCanvas symbolicShell2) {
		drawAreaMap(parent, symbolicShell2, imagesize);
	}

	public void drawAreaMap(AreaMap parent, final GCanvas symbolicShell2,
			int imagesize) {
		drawAreaMap(parent, symbolicShell2, imagesize, parent.xdisplay,
				parent.ydisplay, parent.displaysize, parent.displaysize, 0, 0,
				true);
	}

	public void drawAreaMap(AreaMap parent, final GCanvas symbolicShell2,
			int imagesize, int xs, int ys, int xe, int ye, int showX,
			int showY, boolean cache) {
		//dcount = 0;
		ImageCache imgCache = parent.getImgCache();	

		
		if (symbolicShell2 == null) {
			return;
		}
		
		// label = (Label) shell.getChildren()[0];
		loadMissing(null);

		if (ye > parent.getYsize()) {
			ye = parent.getYsize();
		}
		if (xe > parent.getXsize()) {
			xe = parent.getXsize();
		}

		for (int y = showX; y < ye; y++) {
			for (int x = showY; x < xe; x++) {

				MapData data = parent.getData(xs + x, ys + y);

				String value = null;
				if (data != null) {
					try {
						
						value = data.getImage();
					} catch (Exception e) {
						Window.alert(data.getClass().getName()
								+ " threw a exception on get image");
					}

				}

				if (data instanceof HashMapData) {

					HashMapData hashMapData = (HashMapData) data;

					String underValue = hashMapData.getUnderValue();

					if (cache && imgCache.isCached(x, y)
							&& imgCache.cacheEquals(x, y, value)) {

						continue;

					}

					drawImage(symbolicShell2, y, x, underValue);

					if (underValue.equals(value)) {
						imgCache.setCache(x, y, value);
						continue;
					}
					MapData gate = hashMapData.getMapData(VConstants.gate);
					if(gate != null&&gate.containsKey(VConstants.overlay)){
						drawOverlay(symbolicShell2, y, x, gate.getS(VConstants.overlay));

					}
					LivingBeing lb = hashMapData.getLivingBeing();

					if (lb == null){
						
						if(drawImage(symbolicShell2, y, x, value, imagesize)) {
							imgCache.setCache(x, y, value);
						}
					
					}

					if (lb != null) {
						imgCache.setCache(x, y, lb.getId());


						if (cfList.contains(lb)) {
							if (lb.getParent() == null) {
								continue;
							}

							// Point p=lb.getPosition();
							imgCache.addCache(x, y, "selected");
							drawImage(symbolicShell2, y, x,
									"/images/selected.png");

							OObject current = lb.getTemplate().getCurrent();
							if (current != null) {
								IPhysical ipy = current.getTopOParent()
										.getDisplayPosition();
								if (ipy != null) {
									if (ipy instanceof HashMapData) {
										if (parent.equals(((HashMapData) ipy)
												.getParent())) {
											imgCache.addCache(x, y,
													"displayimg");
											drawImage(symbolicShell2,
													ipy.getY() - ys, ipy.getX()
															- xs, current
															.getTopOParent()
															.getDisplayImage());
										}
									}

								}
							}

						}
						Context2d c2d = symbolicShell2.getContext2d();
						c2d.setLineWidth(5);
						Integer inter = (Integer) lb.getStats().get(
								VConstants.health);

//						c2d.setStrokeStyle(red);
//						c2d.beginPath();
//						c2d.moveTo(x * imagesize + 5, y * imagesize + 5);
//						c2d.lineTo(x * imagesize + imagesize - 5, y * imagesize
//								+ 5);
//						c2d.closePath();
//						c2d.stroke();

						double d = 1;
						if (inter != null
								&& lb.getStats().containsKey(
										VConstants.maxhealth)) {
							d = (double) inter
									/ lb.getStats()
											.getInt(VConstants.maxhealth);
						}

						c2d.setStrokeStyle(green);
						c2d.beginPath();
						int beginX= x * imagesize + 5;
						c2d.moveTo(beginX, y * imagesize + 5);

						c2d.lineTo(Math.max(x * imagesize + 5 + (imagesize * d - 10),beginX), y
								* imagesize + 5);
						c2d.closePath();
						c2d.stroke();

						OObject current = lb.getTemplate().getCurrent();
						if (current != null) {
							String overlayname = current.getS(
									VConstants.overlay);
							if (overlayname == null) {
								overlayname = current.getTopOParent().getS(
										VConstants.overlay);
							}
							
							drawOverlay(symbolicShell2, y, x, overlayname);

						}
						drawPerson(symbolicShell2, y, x, lb);


					}

				} else {
					if (drawImage(symbolicShell2, y, x, value, imagesize)) {
						imgCache.setCache(x, y, value);
					}

				}

			}

		}

		if(parent instanceof FullMapData){
			for(LivingBeing lb : ((FullMapData)parent).people){
				if(lb.getTemplate().getCurrent() instanceof Move){
					Move m = (Move) lb.getTemplate().getCurrent();
					if(m.showMove()){
						Point p = m.getTo().getPosition();
						drawImage(symbolicShell2, p.y, p.x, MapData.getImageString("overlay/singing"));
					}
					
					
				}
			}

		}
		//showArrows(parent, symbolicShell2, xs, ys, xe, ye);

	}

	public void drawOverlay(final GCanvas symbolicShell2, int y, int x,
			String overlayname) {
		if (overlayname != null) {
			drawImage(symbolicShell2, y, x,
					"/images/overlay/" + overlayname
							+ ".png");
		}
	}

	public void loadMissing(UIVParams uiv) {
		if (missingImages.size() > 0) {
			List<String> l = new ArrayList();
			for (String a : missingImages) {
				if (a.contains(".")) {
					l.add(a);
					continue;
				}

				String imgpath = "/images/" + a.replace(' ', '-') + ".png";
				l.add(imgpath);
			}
			loadImages(l.toArray(new String[0]), uiv);
		}
		missingImages.clear();
	}

	protected void drawPerson(final GCanvas symbolicShell2, int y, int x,
			LivingBeing lb) {
		
		if (lb.getB(VConstants.humanoid)&&!lb.getB(VConstants.dontdrawequipment)) {
			drawEquipment(symbolicShell2, y, x, lb);
		} else {
			drawImage(symbolicShell2, y, x, lb.getImage());
			
			
		}
		if(lb.containsKey(VConstants.visualdamage)){
			drawImage(symbolicShell2, y, x, MapData.getImageString(lb.getS(VConstants.visualdamage)));
			lb.remove(VConstants.visualdamage);
		}
	}

	protected void drawEquipment(final GCanvas symbolicShell2, int y, int x,
			LivingBeing lb) {

		if (!lb.containsKey(VConstants.imagecache)) {

			GCanvas canvas = new GCanvas(imagesize, imagesize);
			for (String type : equipA) {

				if (VConstants.base.equals(type)) {
					if (!drawImage(canvas, 0, 0,
							lb.getImage("doll/base/" + lb.getType()))) {
						return;
					}
					continue;
				}
				// get the item
				Item item = (Item) lb.getAlterHolder().getPBase(type);
				if (item == null) {
					continue;
				}
				// get the assoc image
				String tDraw = item.getPersonImage(type);
				if (tDraw != null) {
					if (!drawImage(canvas, 0, 0, tDraw)) {
						return;
					}
				}
				// if none then dont draw
			}
			String face = lb.getS(VConstants.face);
			if (face != null) {
				drawImage(canvas, 0, 0, lb.getImage("doll/face/" + face));
			}

			String hair = lb.getS(VConstants.hair);
			if (hair != null) {
				drawImage(canvas, 0, 0, lb.getImage("doll/hair/" + hair));
			}
			String id = lb.getEquipmentImage();
			imap.put(id, canvas.getCanvasElement());
			lb.put(VConstants.imagecache, id);

		}
		drawImage(symbolicShell2, y, x, lb.getS(VConstants.imagecache));

	}

	private void showArrows(AreaMap parent, final GCanvas symbolicShell2,
			int xs, int ys, int xe, int ye) {
		// show arrows to make sure people know there is
		ImageCache imgCache = parent.getImgCache();	

		// moreto see
		if (parent.getWidth() > parent.displaysize
				&& parent.getWidth() != xs + xe) {
			if (imgCache.contains(parent.displaysize - 1, 0, "arrow")) {
				return;
			}
			for (int y = 0; y < parent.displaysize; y++) {
				drawImage(symbolicShell2, y, parent.displaysize - 1,
						"/images/rightarrow.png");
				imgCache.addCache(parent.displaysize - 1, y, "arrow");
				break;
			}
		}
		if (parent.getHeight() > parent.displaysize
				&& parent.getHeight() - 1 != ys + ye) {
			if (imgCache.contains(0, parent.displaysize - 1, "arrow")) {
				return;
			}
			for (int x = 0; x < parent.displaysize; x++) {
				drawImage(symbolicShell2, parent.displaysize - 1, x,
						"/images/downarrow.png");
				imgCache.addCache(x, parent.displaysize - 1, "arrow");
				break;
			}
		}
	}

	public boolean drawImage(GCanvas symbolicShell2, int y, int x, String text) {

		return drawImage(symbolicShell2, y, x, text, imagesize);

	}
	//static int dcount = 0;
	public boolean drawImage(GCanvas symbolicShell2, int y, int x, String text,
			int imagesize) {
		
		
//		dcount++;
//		System.out.println(x+" "+y);
//		System.out.println(text);
//		System.out.println(dcount);
		if (imap == null) {
			return false;
		}
		boolean flag = true;
		if (imap.get(text) == null) {
			if (text != null) {
				System.out.println("img:"+text);
				if (!imap.containsKey(text) && !text.startsWith("local")) {
					missingImages.add(text);
				}
				flag = false;
			}
			// have a type map here
			text = "/images/question.png";

		}
		final CanvasElement ie = imap.get(text);
		if (ie != null) {
			try {
				symbolicShell2.getContext2d().drawImage(ie, x * imagesize,
						y * imagesize, imagesize, imagesize);

				// symbolicShell2.getContext2d().putImageData(ie, x* imagesize,
				// y* imagesize);
			} catch (Exception e) {
			}
		}
		return flag;

	}

	ErrorHandler imageerrorhandler = new ErrorHandler() {

		@Override
		public void onError(ErrorEvent event) {

			Image img = (Image) event.getSource();

		}
	};

	int displays = 0;

	public void displayTopPanel() {

		// hp.add(new ExitDisplay().getWidgetAndInit());
		Widget pause = new PauseDisplay().getWidgetAndInit();
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(pause);
		//gdp = new Label("Score");
		//hp.add(gdp);
		UIVParams uivpar = (UIVParams)EntryPoint.game.get(VConstants.score);
		if(uivpar != null){
			hp.add(uivpar.getWidgetAndInit());
		}
		VerticalPanel vp = new VerticalPanel();
		flextable.setWidget(0, 0, vp);
		vp.add(hp);
		canvasPanel = new HorizontalPanel();
		
		BagMap bagMap = new BagMap(1, 5, imagesize * 2);
		bagMap.put(VConstants.remove,true);
		//flextable.setWidget(1, 0, bagMap.getWidgetAndInit());
		EntryPoint.game.put(VConstants.bagmap, bagMap);
		bagMap.put(VConstants.selectionname, "bagselection");
		//flextable.setWidget(1, 1,bagMap.getWidgetAndInit());
		
		canvasPanel.add(bagMap.getWidgetAndInit());
		vp.add(canvasPanel);
//		flextable.setWidget(1, 0, canvasPanel);
//		flextable.getCellFormatter().getElement(1, 0).getStyle().setDisplay(Display.BLOCK);
		VerticalPanel displayData = new VerticalPanel();
		HMDDisplay hmdDisplay = new DemographicDisplay();
		AttachUtil.attach(DISPLAY_MAP_DATA, hmdDisplay, this);
//		flextable.getElement().getStyle().setTableLayout(TableLayout.FIXED);
//		flextable.setPixelSize(800, 600);
		LogDisplay ld = new LogDisplay();
		displayData.add(hmdDisplay.getWidgetAndInit());

		displayData.add(ld.getWidgetAndInit());

		EntryPoint.game.put(VConstants.log, ld);
		flextable.setWidget(0, 1,displayData);
//		panel.add(displayData,"info");
		// PersonChoiceDisplay personChoiceDisplay = new PersonChoiceDisplay();
		//
		// AttachUtil.attach(AttachUtil.personlist,personChoiceDisplay,
		// EntryPoint.game);
		// flextable.setWidget(1, 0, personChoiceDisplay.getWidgetAndInit());
		// AttachUtil.attach(AttachUtil.clickfmd, new
		// ClickSelect(),personChoiceDisplay.getBm());
		// Label label = new Label(dhmd, SWT.WRAP);
		// label.setFont(new Font(Log.display, "Courier", 10, SWT.NORMAL));

	}
	public void setGDP(int gdpn){
		EntryPoint.game.put(VConstants.GDP,gdpn);
		
	}
	public void addGDP(int gdpn){
		int gdpc = EntryPoint.game.getInt(VConstants.GDP);
		gdpc += gdpn;
		EntryPoint.game.put(VConstants.GDP,gdpc);
		
	}
	public void displayMapData(HashMapData mapData) {
//		if(mapData == null){
//			LivingBeing lb = mapData.getPerson();
//		}

		// setCurrentlyFollwedUnset(mapData);
										//selectedTile
		AttachUtil.run(DISPLAY_MAP_DATA, mapData, this);
		displays++;
	}

	@Override
	public void remove(GCanvas canvas) {
		// for (int row = 0; row < panel.getRowCount(); row++) {
		// for (int col = 0; col < panel.getCellCount(row); col++) {
		// Widget w = panel.getWidget(row, col);
		// if (w == canvas) {
		// panel.setWidget(row, col, null);
		// scount--;
		// }
		// }
		// }

		// canvas.getContext2d().clea
		clearedList.add(canvas);
		//imgCache.clear();
	}

	List<GCanvas> clearedList = new ArrayList<GCanvas>();
	int scount = 0;
	HorizontalPanel canvasPanel;

	@Override
	protected void clear(GCanvas symbolicShell2) {
		symbolicShell2.clear();
	}

	@Override
	protected GCanvas addShell(AreaMap parent, int sizex, int sizey,
			SEventClick ml) {
		if (scount > getFmdSize()) {

			GCanvas remove = clearedList.remove(0);
			remove.setCoordinateSpaceHeight(sizey);
			remove.setCoordinateSpaceWidth(sizex);
			return remove;
		}
		GCanvas gwtcanvas;
		if (parent instanceof SymbolicMap) {
			gwtcanvas = new GCanvas(sizex, sizey);
		} else {
			gwtcanvas = new GCanvas(this, sizex, sizey);
		}

		//
		// CanvasGradientImplDefault canvasGradientImplDefault = new
		// CanvasGradientImplDefault();
		// gwtcanvas.setStrokeStyle(canvasGradientImplDefault);
		// canvasGradientImplDefault.addColorStop(0, new Color(71, 108, 108));
		//
		// gwtcanvas.getContext2d().setBackgroundColor(new Color(40, 40, 40));
		// gwtcanvas.getContext2d().fill();
		// add one that calls the event handler
		Click c = new Click(ml);
		gwtcanvas.addDomHandler(c, MouseDownEvent.getType());

		// if (scount == 0) {
		// VerticalPanel vp = new VerticalPanel();
		// HorizontalPanel hp = new HorizontalPanel();
		// Image img = new Image("/images/templateicon.png");
		// hp.add(img);
		// img.addClickHandler(new StartEdit("templatetree"));
		// img = new Image("/images/itemicon.png");
		// img.addClickHandler(new StartEdit("itemlist"));
		// hp.add(img);
		// img = new Image("/images/oobjecticon.png");
		// img.addClickHandler(new StartEdit("oobjectlist"));
		// hp.add(img);
		// img = new Image("/images/mapicon.png");
		// img.addClickHandler(new StartEdit("map"));
		// hp.add(img);
		// vp.add(hp);
		// vp.add(canvasPanel);
		// panel.setWidget(HtmlOutRow, 1, vp);
		// }
		scount++;
		canvasPanel.add(gwtcanvas);
		return gwtcanvas;

	}

	protected int getFmdSize() {

		return 1;
	}

	@Override
	public void say(String name, String say) {

	}

	public HtmlOut() {

	}

	@Override
	public void refreshFmds() {
		
		refreshFmds(false);
	}
	
	public void refreshFmds(boolean undoImageCache) {
		if(undoImageCache&&currentFMD.imgCache != null){
			currentFMD.imgCache.clear();
			for(FullMapData fmd : extraMaps.keySet()){
				fmd.imgCache.clear();
			}
		}
		List<String> list = getListCreate(refreshList);
		for (String gwt : list) {
			UIVParams uv = (UIVParams) EntryPoint.game.getVParams().get(gwt);
			uv.update();
		}
		super.refreshFmds();
	}

	public static void loadImages(final String[] files, final UIVParams uiv) {
		for (String file : files) {
			// if(file)
			// EntryPoint.game.getHtmlOut().imap.put(file.substring(8,
			// file.length() - 4),
			// imageElements[a]);

			EntryPoint.game.getHtmlOut().imap.put(file, null);
		}
		ImageLoader.loadImages(files, new ImageLoader.CallBack() {

			@Override
			public void onImagesLoaded(ImageElement[] imageElements) {
				// TODO Auto-generated method stub

				// ImageElement img = imageElements[0];

				int a = 0;
				for (String file : files) {
					// if(file)
					// EntryPoint.game.getHtmlOut().imap.put(file.substring(8,
					// file.length() - 4),
					// imageElements[a]);

					EntryPoint.game.getHtmlOut().imap.put(file,
							scaleImage(imageElements[a]));
					// System.out.println(file);
					a++;
				}
				OutputDirector.timer.setWait(false);
				EntryPoint.game.getHtmlOut().refreshFmds(true);

				// for (UIVParams uv : uvlist) {
				// uv.update();
				// }
				// uvlist.clear();
				if (uiv != null) {
					uiv.update();
					System.out.println("aoeu");
					System.out.println(uiv);
				}

			}
		});
	}

	public LivingBeing getCurrentOrLastSelectedPerson() {

		if (getCurrentlyFollowed() != null) {
			return getCurrentlyFollowed();
		}
		return lastSelected;
	}

	@Override
	public PBase clone() {

		return this;
	}

	static Set<UIVParams> uvlist = new HashSet();

	public void addRefreshOnLoad(UIVParams itemEquipCanvas) {
		itemEquipCanvas.update();
		uvlist.add(itemEquipCanvas);

	}

	

	public static int getAvatarSize() {
		return imagesize * 2;
	}

	public static CanvasElement scaleImage(ImageElement imageElement) {

		// System.out.println("PanoTiler.scaleImag()e: scaleToRatio=" +
		// scaleToRatio + " width=" + width + " x height=" + height);

		Canvas canvasTmp = Canvas.createIfSupported();
		Context2d context = canvasTmp.getContext2d();

		double ch = imagesize; // 100 is offset so it doesn't throw
		double cw = imagesize;

		canvasTmp.setCoordinateSpaceHeight((int) ch);
		canvasTmp.setCoordinateSpaceWidth((int) cw);

		// draw image to canvas
		context.drawImage(imageElement, 0, 0, imagesize, imagesize);

		// ImageData imageData = context.getImageData(0, 0,imagesize,imagesize);
		// // this won't get the extra 100

		return canvasTmp.getCanvasElement();
	}

	
	public Map<FullMapData,GCanvas> extraMaps = new HashMap<FullMapData, GCanvas>();
	//make betterlater
	public void addFMD(FullMapData fmd){
		GCanvas shell = addShell(fmd,fmd.getWidth() * imagesize,fmd.getHeight() * imagesize,full);
		canvasPanel.add(shell);
		extraMaps.put(fmd, shell);
	}
	
	public void clearFMDs(){
		canvasPanel.clear();
	}
	public void runExtra(MapArea ma){
		for(Entry<FullMapData,GCanvas> extra : extraMaps.entrySet()){
			ma.runFmd(extra.getKey());
			drawAreaMap(extra.getKey(),extra.getValue() );
		}
	}

	public void closeExtra() {
		for(Entry<FullMapData,GCanvas> extra : extraMaps.entrySet()){
			canvasPanel.remove(extra.getValue());
			
		}
			extraMaps.clear();
	}
}
