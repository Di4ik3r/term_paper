package com.d43.tbs.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.Unit;

public class Bot {
	
	Array<Unit> allies;
	Array<Unit> enemies;
	CellMap map;
	
	public Bot(Array<Unit> allies, Array<Unit> enemies, CellMap map) {
		this.allies = allies;
		this.enemies = enemies;
		this.map = map;
	}
	
	public void makeMove() {
		for(int i = 0; i < this.enemies.size; i++) {
			Vector2 coord = map.findCellCoord(this.enemies.get(i).getCell().getBounds());
			CellCalculator calculator = new CellCalculator(this.map, coord, this.enemies.get(i).getRangeMovement());
			this.enemies.get(i);
		}
	}
}
