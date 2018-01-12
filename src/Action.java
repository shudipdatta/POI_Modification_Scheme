import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Action {
	Window window;
	
	int actualPoi;
	int hiddenPoi;
	int coverageThreshold;	
	int totalPhoto;
	int deleteThreshold;
	int addThreshold;
	O o;
	
	ArrayList<O.POI> actualPoiList;
	ArrayList<O.POI> hiddenPoiList;
	ArrayList<O.Photo> photoList;
	
	ArrayList<O.Photo> pairedPhotoList;
	ArrayList<O.Photo> undefinedPhotoList;
	ArrayList<O.POI> addedPoiList;
	
	ArrayList<O.POI> nonDeletedPoiList;
	ArrayList<O.POI> deletedPoiList;
	
	HashMap<O.Photo, Integer> FocusAspectCoverage;
	
	public Action(Window window) {		
		this.window = window;
		
		this.actualPoiList = new ArrayList<O.POI>();
		this.hiddenPoiList = new ArrayList<O.POI>();
		this.photoList = new ArrayList<O.Photo>();
		
		this.pairedPhotoList = new ArrayList<O.Photo>();
		this.undefinedPhotoList = new ArrayList<O.Photo>();
		this.addedPoiList = new ArrayList<O.POI>();
		
		this.nonDeletedPoiList = new ArrayList<O.POI>();
		this.deletedPoiList = new ArrayList<O.POI>();
		
		this.FocusAspectCoverage = new HashMap<O.Photo, Integer>();
	}
	
	private boolean IsPointCoverage(O.POI poi, O.Photo photo) {
		int pointX = poi.x-photo.x;
		int pointY = poi.y-photo.y;
		
		int point1X = photo.point1.x-photo.x;
		int point1Y = photo.point1.y-photo.y;
		
		int point2X = photo.point2.x-photo.x;
		int point2Y = photo.point2.y-photo.y;
		
		int projectionP1 = -point1X*pointY + point1Y*pointX;
		int projectionP2 = -point2X*pointY + point2Y*pointX;
		int radiusSquared = pointX*pointX + pointY*pointY;
		
		if(projectionP1>0 && projectionP2<=0 && radiusSquared <= Constant.range*Constant.range) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private double PhotoPOIDirectionAngle(O.POI poi, O.Photo photo) {
		double radian = Math.atan2( (photo.pointDir.y-photo.y), (photo.pointDir.x-photo.x)) - Math.atan2((poi.y-photo.y), (poi.x-photo.x));
		if(radian < 0) radian += 2 * Math.PI;
		return radian;
	}
	
	private int CalculateTotalAspectCoverage(O.Photo photo) {
		ArrayList<Integer> directions = new ArrayList<Integer>();
		O.POI poi = photo.pointFocus;
		for(O.Photo undefinedPhoto: this.undefinedPhotoList) {
			if(this.IsPointCoverage(poi, undefinedPhoto)) {
				double dir = this.PhotoPOIDirectionAngle(poi, undefinedPhoto);
				int dirAngle = (int) (180 * (dir / Math.PI));
				int lowerDirAngle = dirAngle-this.coverageThreshold;
				if(lowerDirAngle < 0) lowerDirAngle+=360;
				directions.add(lowerDirAngle);
			}
		}
		Collections.sort(directions, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
	    });
		int sum=0;
		int upper=0;
		for(int direction: directions) {
			if(direction < upper){
				sum += this.coverageThreshold - (upper-direction);
			}
			else {
				sum += this.coverageThreshold;
			}
			upper = direction + this.coverageThreshold;
		}
		return sum;
	}
	
 	public void DrawScenario(int actualPoi,
			int hiddenPoi,
			int coverageThreshold,
			int totalPhoto,
			int deleteThreshold,
			int addThreshold) {
		this.actualPoi=actualPoi;
		this.hiddenPoi=hiddenPoi;
		this.coverageThreshold=coverageThreshold;
		this.totalPhoto=totalPhoto;
		this.deleteThreshold=deleteThreshold;
		this.addThreshold=addThreshold;
		
		o = new O();		
		this.actualPoiList = new ArrayList<O.POI>();
		this.hiddenPoiList = new ArrayList<O.POI>();
		this.photoList = new ArrayList<O.Photo>();
	
		for(int i=0; i<this.actualPoi+this.hiddenPoi; i++) {
			int x = (Constant.range) + (int)(Math.random() * ((Constant.maxX - Constant.minX - 2 * Constant.range) + 1));
			int y = (Constant.range) + (int)(Math.random() * ((Constant.maxY - Constant.minY - 2 * Constant.range) + 1));
			if(i<this.actualPoi) this.actualPoiList.add(o.new POI(x,y));
			else this.hiddenPoiList.add(o.new POI(x,y));
		}
		
		for(int i=0; i<this.totalPhoto; i++) {
			int rand = (int) (Math.random() * (this.actualPoi+this.hiddenPoi));
			int randX = rand<this.actualPoi? this.actualPoiList.get(rand).x: this.hiddenPoiList.get(rand-this.actualPoi).x;
			int randY = rand<this.actualPoi? this.actualPoiList.get(rand).y: this.hiddenPoiList.get(rand-this.actualPoi).y;
			
			int x = (int)(Math.random() * (Constant.range + 1)) + (randX - Constant.range/2);
			int y = (int)(Math.random() * (Constant.range + 1)) + (randY - Constant.range/2);
			
			double radian = Math.atan2(y-randY, x-randX);
			if(radian < 0) radian += 2 * Math.PI;
			int dirAngle = (int) (180 * (radian / Math.PI));
			dirAngle -= Constant.angle/2;
			dirAngle += (int) (Math.random() * Constant.angle) - Constant.Angle/2;
			if(dirAngle < 0) dirAngle+=360;
			//if(dirAngle >= 360) dirAngle-=360;
			int d = dirAngle;
			
			int p = rand<this.actualPoi? rand: -1;
			this.photoList.add(o.new Photo(i, x,y,d,p));
		}
	}

	public void POIPhotoPair() {
		
		this.pairedPhotoList = new ArrayList<O.Photo>();
		this.undefinedPhotoList = new ArrayList<O.Photo>();
		for(O.POI poi: this.actualPoiList) {
			poi.photoIdList = new ArrayList<Integer>();
		}
		
		for(int p=0; p<this.photoList.size(); p++) {
			O.Photo photo = this.photoList.get(p);
			if(photo.poiIndex != -1 && IsPointCoverage(this.actualPoiList.get(photo.poiIndex),photo)) {
				this.pairedPhotoList.add(photo);
				this.actualPoiList.get(photo.poiIndex).photoIdList.add(photo.id);
			}
			else {
				photo.poiIndex = -1;
				this.undefinedPhotoList.add(photo);
			}
		}
		
		for(int p=this.undefinedPhotoList.size()-1; p>=0; p--) {
			O.Photo photo = this.undefinedPhotoList.get(p);
			int minIndex=-1;
			double minAngle=999999;
			for(int i=0; i<this.actualPoiList.size(); i++) {
				O.POI poi = this.actualPoiList.get(i);
				
				if(IsPointCoverage(poi,photo)) {
					double angle = PhotoPOIDirectionAngle(poi, photo);
					if(angle < minAngle) {
						minAngle = angle;
						minIndex = i;
					}
				}
			}
			if(minIndex != -1) {
				photo.poiIndex = minIndex;
				this.pairedPhotoList.add(photo);
				this.undefinedPhotoList.remove(p);
				this.actualPoiList.get(photo.poiIndex).photoIdList.add(photo.id);
			}
		}
	}

	public void DeletePOI() {
		this.nonDeletedPoiList = new ArrayList<O.POI>();
		this.deletedPoiList = new ArrayList<O.POI>();
		
		for(O.POI poi: this.actualPoiList) {
			if(poi.photoIdList.size() <= this.deleteThreshold) {
				this.deletedPoiList.add(poi);
			}
			else {
				this.nonDeletedPoiList.add(poi);
			}
		}
	}

	public void AddPOI() {
		this.addedPoiList = new ArrayList<O.POI>();
		this.FocusAspectCoverage = new HashMap<O.Photo, Integer>();
		
		for(O.Photo photo: this.undefinedPhotoList) {
			FocusAspectCoverage.put(photo, this.CalculateTotalAspectCoverage(photo));
		}
		
		List<Entry<O.Photo, Integer>> list = new LinkedList<Entry<O.Photo, Integer>>(FocusAspectCoverage.entrySet());
		Collections.sort(list, new Comparator<Entry<O.Photo, Integer>>()
        {
            public int compare(Entry<O.Photo, Integer> o1,
                    Entry<O.Photo, Integer> o2)
            {
            	return o2.getValue().compareTo(o1.getValue());
            }
        });
		this.FocusAspectCoverage = new HashMap<O.Photo, Integer>();
        for (Entry<O.Photo, Integer> entry : list) {
        	if(entry.getValue() >= this.addThreshold) {
        		FocusAspectCoverage.put(entry.getKey(), entry.getValue());
        	}
        }
        
        ArrayList<O.Photo> consideredPhotos = new ArrayList<O.Photo>();
        Iterator<Entry<O.Photo, Integer>> it = this.FocusAspectCoverage.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
			HashMap.Entry pair = (HashMap.Entry)it.next();
            consideredPhotos.add((O.Photo) pair.getKey());
            it.remove();
        }
        
        for(int i=0; i<consideredPhotos.size(); i++) {
        	O.Photo thisPhoto = consideredPhotos.get(i);
        	if(thisPhoto.poiIndex == -1) {
	        	O.POI poi = o.new POI(thisPhoto.pointFocus.x, thisPhoto.pointFocus.y);
	        	for(int j=i+1; j<consideredPhotos.size(); j++) {
	        		O.Photo photo = consideredPhotos.get(j);
	        		if(photo.poiIndex == -1 && this.IsPointCoverage(poi, photo)) {
	        			 photo.poiIndex = i;
	        		}
	        	}
	        	this.addedPoiList.add(o.new POI(thisPhoto.pointFocus.x, thisPhoto.pointFocus.y));
        	}
        }
	}
}
