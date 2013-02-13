//package gwt.test;
//
//import gwt.client.main.AddAnimals;
//import gwt.client.main.Game;
//import gwt.client.main.VConstants;
//import gwt.client.personality.ConstantStats;
//import gwt.server.build.buildjson;
//import gwt.server.datamodel.ServerGame;
//
//import com.google.gwt.junit.client.GWTTestCase;
//import com.google.gwt.user.client.Window;
//
//public class test1 extends GWTTestCase {                       // (1)
//
//	  /**
//	   * Must refer to a valid module that sources this class.
//	   */
//	  public String getModuleName() {                                         // (2)
//	    return "gwt.villagedc";
//	  }
//
//	  /**
//	   * Add as many tests as you like.
//	   */
//	  public void testSimple() {                                              // (3)
//			Game g = new Game();
//			ConstantStats.mstats.get(VConstants.animal).setPregnancyTime(0);
//			
//			g.speed = 30;
//			
//			buildjson.setVars();
//			buildjson.test1(g);
//			g.mapInitList.clear();
//			g.mapInitList.add(new AddAnimals());
//			testhtml.setup(g);
//			
//			g.execute("");
//			
//			
//			
//	  }
//
//	}