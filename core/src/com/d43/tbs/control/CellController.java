package com.d43.tbs.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.MapChecker;

public class CellController {
	
	private Polygon bounds;
	private Unit unit;
	private Cell cell;
	
	private MapChecker mapChecker;
	
	public CellController(Polygon bounds) {
		this.bounds = bounds;
		
		this.unit = null;
	}
	
	public void setMapChecker(MapChecker mapChecker) {
		this.mapChecker = mapChecker;
	}

	public void handle() {
		float x = Gdx.input.getX()-Gdx.graphics.getWidth()/2, y = -Gdx.input.getY()+Gdx.graphics.getHeight()/2;
		if(this.bounds.contains(x, y))  {
//			Gdx.app.log("tag", this.unit.toString());
			this.cell.changeTextureRegion(this.cell.getRegions().get(1));
			if(this.unit != null && !this.unit.isReplaceable() && !this.unit.isEnemy())
				this.cell.changeTextureRegion(this.cell.getRegions().get(2));
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				if(this.unit != null)
					mapChecker.pickUnit(this.unit);
				else {
					mapChecker.replaceUnit(this.bounds);
				}
			}
		}
		else this.cell.changeTextureRegion(this.cell.getRegion());
	}
	
	public void setCell(Cell cell) {
		this.cell=cell;
	}
	
	public Cell getCell() {
		return this.cell;
	}

	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public Unit getUnit() {
		return this.unit;
	}
	
	public boolean isBelong(float x, float y) {
		return this.bounds.contains(x, y) ? true : false; 
	}
	
	public boolean isMouseCollide(float x, float y) {
		return this.bounds.contains(x-Gdx.graphics.getWidth()/2, y-Gdx.graphics.getHeight()/2) ? true : false;
	}
}
