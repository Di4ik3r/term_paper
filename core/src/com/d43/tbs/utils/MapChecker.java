package com.d43.tbs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.DefeatedZone;
import com.d43.tbs.model.unit.Unit;

public class MapChecker {

	private CellMap map;
	private Array<Unit> allies;
	private Array<Unit> enemies;

	private Unit pickedUnit;
	
	private TextureAtlas textureAtlas;
	
	private Array<Cell> availableCells;
	
	private DefeatedZone defeatedZone;
	
	private Bot bot;

	public MapChecker(CellMap cells, DefeatedZone defeatedZone, Array<Unit> allies, Array<Unit> enemies) {
		this.map = cells;
		this.allies = allies;
		this.enemies = enemies;
		
		this.defeatedZone = defeatedZone;

		this.pickedUnit = null;
		
		this.bot = new Bot(this.allies, this.enemies, this, this.map);
	}

	// ********************** Map Control *********************************

	public void calculateRange() {
		Cell cell = pickedUnit.getCell();
		Vector2 coord = map.findCellCoord(cell.getBounds());

		if (this.pickedUnit != null) {
			CellCalculator calculator = new CellCalculator(true, this.map, coord, this.pickedUnit.getRangeMovement(), this.pickedUnit.getRangeAttack());
			String[] str = calculator.getStringRepresentation();

//			Gdx.app.log("tag", "");
//			for(int i = 0; i < str.length; i++)
//				Gdx.app.log("tag", str[i]);
			
			this.availableCells = calculator.getAvailableCellsForMove();
			Array<Cell> attackCells = calculator.getAvailableCellsForAttack();
			for(int i = 0; i < attackCells.size; i++)
				this.availableCells.add(attackCells.get(i));
		}
	}
	
	private void paintAvailableCell() {
		for(int i = 0; i < this.availableCells.size; i++)
			this.availableCells.get(i).setRegion(this.availableCells.get(i).containsUnit() ? this.textureAtlas.findRegion("cellPickable") : this.textureAtlas.findRegion("cellPickable"));
	}
	
	private boolean cellIsAvailable(Cell cell) {
		return this.availableCells.contains(cell, true) ? true : false;
	}
	
	private void paintToDefault() {
		for(int i = 0; i < this.map.getRows(); i++)
			for(int j = 0; j < this.map.getCols(); j++)
				if(this.map.getCell(i, j).getUnit() == null || this.map.getCell(i, j).getUnit().isEnemy()/* && this.cellIsAvailable(this.map.getCell(i, j))*/)
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

	public void unitMove(Polygon bounds) {
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
					bot.makeBotsMove();
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
	
	public void unitAttack(Polygon bounds) {
		if (this.pickedUnit != null) {
			Cell cell = map.findCell(bounds);
			if(this.cellIsAvailable(cell)) {
				Unit enemy = cell.getUnit();
				enemy.damage(this.pickedUnit.getDamage());
					
				this.pickedUnit.getCell().setRegion(this.textureAtlas.findRegion("cellBlocked"));
				this.pickedUnit.getCell().changeTextureRegion(this.textureAtlas.findRegion("cellBlocked"));
				
				this.pickedUnit.setReplaceability(false);
				
				this.pickedUnit = null;
				this.availableCells = null;
				this.paintToDefault();
				
				if (this.unitsAreDone()) {
					bot.makeBotsMove();
					this.makeUnitsRaplaceable();
				}
			}
		}
		for (int i = 0; i < enemies.size; i++) {
			if(enemies.get(i).getHp() < 1) {
				defeatedZone.addEnemies(enemies.get(i));
//				enemies.removeIndex(i);
			}
		}
	}
	
	public void checkAllies() {
		for (int i = 0; i < allies.size; i++) {
			if(allies.get(i).getHp() < 1) {
				defeatedZone.addAllies(allies.get(i));
//				allies.removeIndex(i);
				this.paintToDefault();
			}
		}
	}
	
	public void killUnit(Unit unit) {
		if(unit.isEnemy())
			defeatedZone.addEnemies(unit);
		else defeatedZone.addAllies(unit);
	}

	private boolean unitsAreDone() {
		int k, j;
		k = 0;
		j = 0;
		for (int i = 0; i < allies.size; i++)
			if(allies.get(i).isAlive()) {
				j += !allies.get(i).isReplaceable() ? 1 : 0;
				k++;
			}
//		Gdx.app.log("tag", Integer.toString(i) + " : " + Integer.toString(j));
		return k == j ? true : false;
	}

	private void makeUnitsRaplaceable() {
		for (int i = 0; i < allies.size; i++)
		{
			allies.get(i).getCell().setRegion(this.textureAtlas.findRegion("cell"));
			allies.get(i).setReplaceability(true);
		}
	}
}
