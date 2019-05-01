package com.d43.tbs.utils;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.MapPlaying;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.Unit;

public class Bot {
	
	Array<Unit> allies;
	Array<Unit> enemies;
	CellMap map;
	MapPlaying mapChecker;
	
	public Bot(Array<Unit> allies, Array<Unit> enemies, MapPlaying mapChecker, CellMap map) {
		this.allies = allies;
		this.enemies = enemies;
		this.mapChecker = mapChecker;
		this.map = map;
	}
	
	public void makeBotsMove() {
		Array<Cell> moveCells, attackCells;
		for(int i = 0; i < this.enemies.size; i++) {
			if(!enemies.get(i).isAlive())
				continue;
			Vector2 coord = map.findCellCoord(this.enemies.get(i).getCell().getBounds());
//			CellCalculator calculator = new CellCalculator(this.map, coord, this.enemies.get(i).getRangeMovement());
//			this.enemies.get(i)
			
			CellCalculator calculator = new CellCalculator(false, this.map, coord, this.enemies.get(i).getRangeMovement(), this.enemies.get(i).getRangeAttack());
			String[] str = calculator.getStringRepresentation();

//			Gdx.app.log("tag", "");
//			for(int i = 0; i < str.length; i++)
//				Gdx.app.log("tag", str[i]);
			
			moveCells = calculator.getAvailableCellsForMove();
			attackCells = calculator.getAvailableCellsForAttack();
			
			if(attackCells.size > 0) {
				attackCells.get(0).getUnit().damage(enemies.get(i).getDamage());
				this.mapChecker.checkAllies();
			}
			else {
				enemies.get(i).setCell(moveCells.get(Rnd.generate(0, moveCells.size-1)));
			}
		}
	}
	
	
}
