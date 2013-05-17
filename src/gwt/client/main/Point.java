package gwt.client.main;

import gwt.client.game.util.PointBase;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;


import java.io.Serializable;
import java.util.List;



public class Point implements Serializable, IPhysical{
	public int x;
	public int y;
	
	public static boolean outsideRect(Point upperLeft,Point lowerRight,IPhysical toCheck){

		
		if (lowerRight.x <= toCheck.getX() || lowerRight.y <= toCheck.getY()) {
			return true;
		}
		if (upperLeft.x > toCheck.getX() || upperLeft.y > toCheck.getY()) {
			return true;
		}
		return false;
	}
	    public static double distance(double X1, double Y1,
			                   double X2, double Y2) {
			     X1 -= X2;
			    Y1 -= Y2;
			     return Math.sqrt(X1 * X1 + Y1 * Y1);
			    }

	

	/*     */   public Point(Point paramPoint)
	/*     */   {
	/*  64 */     this(paramPoint.x, paramPoint.y);
	/*     */   }
	/*     */ 
	/*     */   public Point(int paramInt1, int paramInt2)
	/*     */   {
	/*  75 */     this.x = paramInt1;
	/*  76 */     this.y = paramInt2;
	/*     */   }
	/*     */ 
	public Point(List l){
		this.x = (Integer) l.get(0);
		this.y = (Integer) l.get(1);
	}
	
	/*     */   public int getX()
	/*     */   {
	/*  84 */     return this.x;
	/*     */   }
	/*     */ 
	/*     */   public int getY()
	/*     */   {
	/*  92 */     return this.y;
	/*     */   }
	/*     */ 
	/*     */   public Point getLocation()
	/*     */   {
	/* 106 */     return new Point(this.x, this.y);
	/*     */   }
	/*     */ 
	/*     */   public void setLocation(Point paramPoint)
	/*     */   {
	/* 119 */     setLocation(paramPoint.x, paramPoint.y);
	/*     */   }
	/*     */ 
	/*     */   public void setLocation(int paramInt1, int paramInt2)
	/*     */   {
	/* 136 */     move(paramInt1, paramInt2);
	/*     */   }
	/*     */ 
	/*     */   public void setLocation(double paramDouble1, double paramDouble2)
	/*     */   {
	/* 152 */     this.x = (int)Math.floor(paramDouble1 + 0.5D);
	/* 153 */     this.y = (int)Math.floor(paramDouble2 + 0.5D);
	/*     */   }
	/*     */ 
	/*     */   public void move(int paramInt1, int paramInt2)
	/*     */   {
	/* 165 */     this.x = paramInt1;
	/* 166 */     this.y = paramInt2;
	/*     */   }
	/*     */ 
	/*     */   public void translate(int paramInt1, int paramInt2)
	/*     */   {
	/* 181 */     this.x += paramInt1;
	/* 182 */     this.y += paramInt2;
	/*     */   }
	/*     */ 
	/*     */   public boolean equals(Object paramObject)
	/*     */   {
	/* 196 */     if (paramObject instanceof Point) {
	/* 197 */       Point localPoint = (Point)paramObject;
	/* 198 */       return ((this.x == localPoint.x) && (this.y == localPoint.y));
	/*     */     }
	/* 200 */     return super.equals(paramObject);
	/*     */   }
	/*     */ 
	/*     */   


	public String toString() {
		// TODO Auto-generated method stub
		return "x:"+x+" y:"+y;
	}

	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Point clone(){
		return new Point(x,y);
	}
	public static int distance(IPhysical p1, IPhysical p2){
		return (int) distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static Point middle(int x, int y){
		return new Point(x/2, y/2);
	}



	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return this;
	}



	@Override
	public void setParent(MapData fullMapData, int x, int y) {
		
	}



	@Override
	public void setX(int x) {
		this.x = x;
	}



	@Override
	public void setY(int y) {
		this.y = y;
	}
	public static Point getPoint(PointBase object) {
		return new Point(object.getX(),object.getY());
	}
	public static PBase getPBase(int x, int y) {
		return new  PointBase(x,y);
	}
	public static PBase getPBase(Point p) {
		return getPBase(p.x, p.y);
	}
	public static boolean nextTo(IPhysical p1, IPhysical p2) {
		return distance(p1, p2) < 2;
	}
}
