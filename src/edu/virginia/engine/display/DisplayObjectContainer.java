package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject{

	private ArrayList<DisplayObject> childObjects;
	public boolean drawChildren;
	
	public DisplayObjectContainer(String id) {
		super(id);
		this.drawChildren = true;
		this.childObjects = new ArrayList<DisplayObject>();
	}

	public DisplayObjectContainer(String id, String imageFileName) {
		super(id, imageFileName);
		this.drawChildren = true;
		this.childObjects = new ArrayList<DisplayObject>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisplayObjectContainer other = (DisplayObjectContainer) obj;
		if (childObjects == null) {
			if (other.childObjects != null)
				return false;
		} else if (!childObjects.equals(other.childObjects))
			return false;
		if (drawChildren != other.drawChildren)
			return false;
		return true;
	}

	public boolean isDrawChildren() {
		return drawChildren;
	}

	public void setDrawChildren(boolean drawChildren) {
		this.drawChildren = drawChildren;
	}

	public boolean addChild(DisplayObject child) {
		return this.childObjects.add(child);
	}
	
	public void addChildAtIndex(int index, DisplayObject child) {
		this.childObjects.add(index, child);
	}
	
	public boolean removeChild(DisplayObject child) {
		return this.childObjects.remove(child);
	}
	
	public DisplayObject removeByIndex(int index) {
		return this.childObjects.remove(index);
	}
	
	public void removeAll(DisplayObject child) {
		this.childObjects.clear();
		
	}
	
	public boolean contains(DisplayObject child) {
		return this.childObjects.contains(child);
	}
	
	public DisplayObject getChild(String stringid) {
		for (DisplayObject obj : this.childObjects) {
			if (obj.getId().equals(stringid)) {
				return obj; 
			}
		}
		return null;
	}
	
	public DisplayObject getChild(int index) {
		return this.childObjects.get(index);
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		
	}
	
	@Override
	public void draw(Graphics g) {
			
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
			
			if (this.getDisplayImage() != null && this.isVisible()) {
				/* Actually draw the image, perform the pivot point translation here */
				g2d.drawImage(this.getDisplayImage(), 0, 0,
						(int) (getUnscaledWidth()),
						(int) (getUnscaledHeight()), null);
			}
			
			if (this.drawChildren) {
				for(DisplayObject obj : this.childObjects) {
					obj.draw(g);
				}
			}
			
			reverseTransformations(g2d);
		}
		
	
}
