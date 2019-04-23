package com.d43.tbs.model.unit;

import java.awt.Dimension;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.d43.tbs.control.UnitController;
import com.d43.tbs.model.GameObject;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.utils.IdCounter;
import com.d43.tbs.utils.MapChecker;

public class Unit extends GameObject {

	private int id, hp, rangeAttack, rangeMovement, damage;
	private UnitController controller;
	private Cell cell;
	private boolean isReplaceable;
	private boolean isEnemy;
	
	private Dimension size;
	
	
	public Unit(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.id = IdCounter.lastId++;
		this.controller = new UnitController(this.id, this.getBounds());
		
		this.hp = 0;
		this.rangeAttack = 0;
		this.rangeMovement = 0;
		this.damage = 0;
		this.isReplaceable = true;

		this.size = new Dimension((int)width, (int)height);
	}
	
	public boolean isEnemy() {
		return this.isEnemy;
	}
	
	public void setIsEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}
	
	public boolean isReplaceable() {
		return this.isReplaceable;
	}
	
	public void setReplaceability(boolean isReplaceable) {
		this.isReplaceable = isReplaceable;
	}
	
	public void setUnitToControl() {
		this.controller.setUnit(this);
	}
	
	public void setMapChecker(MapChecker mapChecker) {
		this.controller.setMapChecker(mapChecker);
	}
	
	
	public void pickedUp() {
		controller.pickedUp();
	}
	
	public void setCell(Cell cell) {
		if(this.cell != null)
			this.cell.setUnit(null);
		this.cell = cell;
		this.setUnitToControl();
		this.controller.setCell(cell);
		this.cell.setUnit(this);
	}
	
	public Cell getCell() {
		return cell;
	}
	
	public int getId() {
		return id;
	}
	
	public int getRangeAttack() {
		return this.rangeAttack;
	}
	
	public void setRangeAttack(int range) {
		this.rangeAttack = range;
	}
	
	public int getRangeMovement() {
		return this.rangeMovement;
	}
	
	public void setRangeMovement(int range) {
		this.rangeMovement = range;
	}
	
	public Dimension getSize() {
		return this.size;
	}
	
	
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		controller.handle();
	}
}
