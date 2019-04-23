package com.d43.tbs.model.map;

import java.awt.Dimension;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.CellController;
import com.d43.tbs.model.GameObject;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.MapChecker;

public class Cell extends GameObject{
	
	private CellController controller;
	private Unit unit;
	
	private Dimension size;
	private Array<TextureRegion> regions;
	private TextureRegion textureRegion;
	
	private boolean forDefeated;
	
	public Cell(Array<TextureRegion> regions, float x, float y, float width, float height) {
		super(regions.get(0), x, y, width, height);
		
		this.unit = null;
		this.controller = new CellController(this.getBounds());
		this.size = new Dimension((int)width, (int)height);
		
		this.regions = regions;
		this.textureRegion = regions.get(0);
		
		this.forDefeated = false;
	}
	
	public boolean isForDefeated() {
		return this.forDefeated;
	}
	
	public void setForDefeated(boolean forDefeated) {
		this.forDefeated = forDefeated;
	}
	
	public Array<TextureRegion> getRegions() {
		return this.regions;
	}
	
	public TextureRegion getRegion() {
		return this.textureRegion;
	}
	
	public void setRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public void setCell() {
		this.controller.setCell(this);
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
		this.controller.setUnit(unit);
	}
	
	public Unit getUnit() {
		return this.unit;
	}
	
	public boolean containsUnit() {
		return this.unit != null ? true : false;
	}
	
	public void setMapChecker(MapChecker mapChecker) {
		this.controller.setMapChecker(mapChecker);
	}
	
//	public Polygon getLocation() {
//		return this.getBounds();
//	}
	
	public Dimension getSize() {
		return this.size;
	}
	
	public CellController getController() {
		return this.controller;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		controller.handle();
	}
}
