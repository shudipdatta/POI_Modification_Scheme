import java.util.ArrayList;

public class O {

	public class POI {
		int x;
		int y;
		int r;
		ArrayList<Integer> photoIdList;
		
		public POI(int x, int y) {
			this.x = x;
			this.y = y;
			this.r = Constant.PointRadius;
			this.photoIdList = new ArrayList<Integer>();
		}
	}
	
	public class Photo extends POI {
		int id;
		int focus;
		int range;
		int angle;
		
		int direction;
		int poiIndex;
		
		POI pointDir;
		POI pointFocus;
		POI point1;
		POI point2;
		
		public Photo(int id, int x, int y, int direction, int poiIndex) {
			super(x, y);
			this.id = id;
			this.r = Constant.PhotoRadius;
			this.direction=direction;
			this.focus=Constant.focus;
			this.range=Constant.range;
			this.angle=Constant.angle;
			this.poiIndex=poiIndex;

			CalculateEndPoints();
		}
		private void CalculateEndPoints() {
			double theta;
			int x, y;
			
			theta = Math.toRadians(this.direction);
			x = (int) (this.range * Math.cos(theta)) + this.x;
			y = (int) (this.range * Math.sin(theta)) + this.y;
			this.pointDir = new POI(x,y);
			
			theta = Math.toRadians(this.direction);
			x = (int) (this.focus * Math.cos(theta)) + this.x;
			y = (int) (this.focus * Math.sin(theta)) + this.y;
			this.pointFocus = new POI(x,y);
			this.pointFocus.r = Constant.FocusRadius;
			
			theta = Math.toRadians(this.direction + this.angle/2);
			x = (int) (this.range * Math.cos(theta)) + this.x;
			y = (int) (this.range * Math.sin(theta)) + this.y;
			this.point1 = new POI(x,y);
			
			theta = Math.toRadians(this.direction - this.angle/2);
			x = (int) (this.range * Math.cos(theta)) + this.x;
			y = (int) (this.range * Math.sin(theta)) + this.y;
			this.point2 = new POI(x,y);
		}
	}
}
