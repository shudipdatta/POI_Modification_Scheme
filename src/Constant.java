import java.awt.Color;

public class Constant {
	public static final int Height=718;
	public static final int Width=1366;
	
	public static final int DrawScenario = 0;
	public static final int POIPhotoPair = 1;
	public static final int DeletePOI = 2;
	public static final int AddPOI = 3;
	
	public static final int minX=227;
	public static final int maxX=1350;
	public static final int minY=0;
	public static final int maxY=679;
	
	public static final int Angle = 360;
	public static final int PointRadius = 10;
	public static final int PhotoRadius = 6;
	public static final int FocusRadius = 4;
	public static int focus;
	public static int range;
	public static int angle;
	
	public static Color ActualPOIColor = Color.BLACK;
	public static Color HiddenPOIColor = Color.YELLOW;
	public static Color PhotoPOIColor = new Color(0,0,200);
	public static Color PhotoFocusColor = new Color(100,200,255);
	public static Color PhotoLineColorLow = new Color(100,200,255,20);
	public static Color PhotoLineColorMed = new Color(100,200,255,60);
	public static Color PhotoLineColorHigh = new Color(100,200,255,90);
}
