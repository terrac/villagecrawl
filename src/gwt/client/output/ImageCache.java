package gwt.client.output;

import gwt.client.EntryPoint;
import gwt.client.map.Direction;

public class ImageCache {
	public int size = 100;
	String[][] cached = new String[size][size];
	
	
	public boolean isCached(int x, int y){
		
		return cached[x][y] != null;
	}
	public boolean cacheEquals(int x, int y, String value) {

		return cached[x][y].equals(value);
	}
	public void setCache(int x, int y, String value) {
		cached[x][y] = value;
	}
	public void addCache(int x, int y, String value) {
		
		cached[x][y] += value;
		
	}
	public void clear() {
		cached = new String[size][size];		
	}
	public void clear(int x, int y){
		cached[x][y] = null;
	}
	
	public void clearPositional(int x, int y){
		x -= EntryPoint.game.getHtmlOut().currentFMD.xdisplay;
		y -= EntryPoint.game.getHtmlOut().currentFMD.ydisplay;
		if(x < 0|| y < 0||x >= size||y >=size){
			return;
		}
		cached[x][y] = null;
	}
	
	public void clearHorizontalLine(int pos){
		for(int x = 0; x < size; x++){
			clear(x, pos);
		}
	}
	public void clearVerticalLine(int pos){
		for(int y = 0; y < size; y++){
			clear(pos, y);
		}
	}
	public boolean contains(int i, int j, String string) {
		if(cached[i][j]== null){
			return false;
		}
		return cached[i][j].contains(string);
	}
}
