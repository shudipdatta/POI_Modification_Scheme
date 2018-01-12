import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Drawing extends JPanel {
	Window window;
	int Operation;
	
	public Drawing(Window window) {
		this.window = window;
	}
	
	public void paintPhoto(Graphics g, O.Photo p) {
		g.setColor(Constant.PhotoPOIColor);
		g.fillRect(p.x - p.r/2, p.y - p.r/2, p.r, p.r);
		g.setColor(Constant.PhotoFocusColor);
		g.fillRect(p.pointFocus.x - p.pointFocus.r/2, p.pointFocus.y - p.pointFocus.r/2, p.pointFocus.r, p.pointFocus.r);			
		g.setColor(Constant.PhotoLineColorMed);
		g.drawLine(p.x, p.y, p.pointDir.x, p.pointDir.y);
		
		g.setColor(Constant.PhotoLineColorHigh);
		g.drawLine(p.x, p.y, p.point1.x, p.point1.y);
		g.drawLine(p.x, p.y, p.point2.x, p.point2.y);
		g.drawLine(p.pointDir.x, p.pointDir.y, p.point1.x, p.point1.y);
		g.drawLine(p.pointDir.x, p.pointDir.y, p.point2.x, p.point2.y);
	}
	
	@Override
	public void paintComponent(Graphics g) {			
		super.paintComponent(g);
		
		if(this.Operation == Constant.DrawScenario || this.Operation == Constant.POIPhotoPair) {
			g.setColor(Constant.ActualPOIColor);
			for(O.POI poi: window.action.actualPoiList) {
				g.fillOval(poi.x - poi.r/2, poi.y - poi.r/2, poi.r, poi.r);
			}
		}
		
		g.setColor(Constant.HiddenPOIColor);
		for(O.POI poi: window.action.hiddenPoiList) {
			g.fillOval(poi.x - poi.r/2, poi.y - poi.r/2, poi.r, poi.r);
		}
		
		if(this.Operation != Constant.AddPOI) {
			for(O.Photo p: window.action.photoList) {
				this.paintPhoto(g, p);
			}
		}
		
		if(this.Operation == Constant.POIPhotoPair) {
			g.setColor(Constant.ActualPOIColor);
			for(O.Photo p: window.action.pairedPhotoList) {
				g.drawLine(p.x, p.y, window.action.actualPoiList.get(p.poiIndex).x, window.action.actualPoiList.get(p.poiIndex).y);
			}
		}
		
		if(this.Operation == Constant.DeletePOI) {
			g.setColor(Constant.ActualPOIColor);
			for(O.POI poi: window.action.nonDeletedPoiList) {
				g.fillOval(poi.x - poi.r/2, poi.y - poi.r/2, poi.r, poi.r);
			}
			for(O.POI poi: window.action.deletedPoiList) {
				g.drawOval(poi.x - poi.r/2, poi.y - poi.r/2, poi.r, poi.r);
			}
		}
		
		if(this.Operation == Constant.AddPOI) {
			g.setColor(Constant.PhotoPOIColor);
			for(O.POI poi: window.action.addedPoiList) {
				g.fillOval(poi.x - Constant.PointRadius/2, poi.y - Constant.PointRadius/2, Constant.PointRadius, Constant.PointRadius);
			}
			for(O.Photo p: window.action.undefinedPhotoList) {
				this.paintPhoto(g, p);
			}
		}
	}
}