package com.d43.tbs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.Unit;

public class MapChecker {

	private CellMap map;
	private Array<Unit> allies;
	private Array<Unit> enemies;

	private Unit pickedUnit;
	
	private TextureAtlas textureAtlas;
	
	private Array<Cell> availableCells;

	public MapChecker(CellMap cells, Array<Unit> units) {
		this.map = cells;
		this.allies = units;

		this.pickedUnit = null;
	}

	// ********************** Map Control *********************************

	public void calculateRange() {
		Cell cell = pickedUnit.getCell();
		Vector2 coord = map.findCellCoord(cell.getBounds());

		if (this.pickedUnit != null) {
			CellCalculator calculator = new CellCalculator(this.map, coord, this.pickedUnit.getRangeMovement());
//			String[] str = calculator.getStringRepresentation();

//			Gdx.app.log("tag", "");
//			for(int i = 0; i < str.length; i++)
//				Gdx.app.log("tag", str[i]);
			
			this.availableCells = calculator.getAvailableCellsForMove();
		}
	}
	
	private void paintAvailableCell() {
		for(int i = 0; i < this.availableCells.size; i++)
			this.availableCells.get(i).setRegion(this.textureAtlas.findRegion("cellPickable"));
	}
	
	private boolean cellIsAvailable(Cell cell) {
		return this.availableCells.contains(cell, true) ? true : false;
	}
	
	private void paintToDefault() {
		for(int i = 0; i < this.map.getRows(); i++)
			for(int j = 0; j < this.map.getCols(); j++)
				if(this.map.getCell(i, j).getUnit() == null/* && this.cellIsAvailable(this.map.getCell(i, j))*/)
					this.map.getCell(i, j).setRegion(this.textureAtlas.findRegion("cell"));
//				else 
//					this.map.getCell(i, j).setRegion(this.textureAtlas.findRegion("cell"));
		
	}
	
	public void setAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}
	
	
	
	
	// ********************* Enemy Control ********************************
	
	
	
	
	
	// ********************** Unit Control ********************************

	public void pickUnit(Unit unit) {
		this.paintToDefault();
		this.pickedUnit = unit;

//		if (this.unitsAreDone()) {
//			this.makeUnitsRaplaceable();
//			Gdx.app.log("tag", "units are replaceable");
//		} else if (!this.pickedUnit.isReplaceable()) {
//			this.pickedUnit = null;
//			return;
//		}
		
		if (!this.pickedUnit.isReplaceable()) {
			this.pickedUnit = null;
			return;
		}
		
		this.calculateRange();
		this.paintAvailableCell();
	}

	public void replaceUnit(Polygon bounds) {
		if (this.pickedUnit != null) {
			Cell cell = map.findCell(bounds);
			if(this.cellIsAvailable(cell)) {
				this.pickedUnit.setCell(cell);
				this.pickedUnit.getCell().setRegion(this.textureAtlas.findRegion("cellBlocked"));
				this.pickedUnit.getCell().changeTextureRegion(this.textureAtlas.findRegion("cellBlocked"));
				
				this.pickedUnit.setReplaceability(false);
				
				this.pickedUnit = null;
				this.availableCells = null;
				this.paintToDefault();
				
				if (this.unitsAreDone()) {
					this.makeUnitsRaplaceable();
//				Gdx.app.log("tag", "units are replaceable");
				}
			}
			else {
				this.pickedUnit = null;
				this.paintToDefault();
			}
		}
		
//		Gdx.app.log("tag", "");
//		this.map.returnMap();
	}

	private boolean unitsAreDone() {
		int i, j;
		i = 0;
		j = 0;
		for (; i < allies.size; i++)
			j += !allies.get(i).isReplaceable() ? 1 : 0;
//		Gdx.app.log("tag", Integer.toString(i) + " : " + Integer.toString(j));
		return i == j ? true : false;
	}

	private void makeUnitsRaplaceable() {
		for (int i = 0; i < allies.size; i++)
		{
			allies.get(i).getCell().setRegion(this.textureAtlas.findRegion("cell"));
			allies.get(i).setReplaceability(true);
		}
	}
}
