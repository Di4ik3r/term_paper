package com.d43.tbs.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.RangeUnit;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.CellCalculator;
import com.d43.tbs.utils.Rnd;

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

		sortEnemiesByType();
		sortEnemiesByAlive();

		for (int i = 0; i < this.enemies.size; i++) {
//			this.enemies.get(i).setDelay(i * 0.5f + 0.5f);
//			this.enemies.get(i).setDelay(this.enemies.size - i * 1f + 0.2f);

			if (!enemies.get(i).isAlive())
				continue;

			this.enemies.get(i).setDelay(i * 0.5f + 1f);

			Vector2 coord = map.findCellCoord(this.enemies.get(i).getCell().getBounds());
//			CellCalculator calculator = new CellCalculator(this.map, coord, this.enemies.get(i).getRangeMovement());
//			this.enemies.get(i)

			CellCalculator calculator = new CellCalculator(false, this.map, coord,
					this.enemies.get(i).getRangeMovement(), this.enemies.get(i).getRangeAttack());
//			String[] str = calculator.getStringRepresentation();

//			Gdx.app.log("tag", "");
//			for(int i = 0; i < str.length; i++)
//				Gdx.app.log("tag", str[i]);

			moveCells = calculator.getAvailableCellsForMove();
			attackCells = calculator.getAvailableCellsForAttack();

			if (attackCells.size > 0) {
//				attackCells.get(0).getUnit().damage(enemies.get(i).getDamage());
				this.enemies.get(i).setDelay(0f);
				enemies.get(i).attack(attackCells.get(0).getUnit());
				this.mapChecker.checkAllies();
			} else {
				enemies.get(i).setCell(moveCells.get(Rnd.generate(0, moveCells.size - 1)));
			}
		}

		int lastAliveIndex = findLastAliveEnemy();

//		if(i == this.enemies.size - 1)
		if (lastAliveIndex >= 0)
			this.enemies.get(lastAliveIndex).lastEnemy();

	}

	private void sortEnemiesByAlive() {
		for (int i = 0; i < this.enemies.size; i++) {
			for (int j = 0; j < this.enemies.size - 1; j++) {
				if (!enemies.get(j).isAlive() && enemies.get(j + 1).isAlive())
					enemies.swap(j, j + 1);
			}
		}
	}

	private void sortEnemiesByType() {
		for (int i = 0; i < this.enemies.size; i++)
			for (int j = 0; j < this.enemies.size - 1; j++)
				if (!RangeUnit.class.isAssignableFrom(enemies.get(j).getClass())
						&& RangeUnit.class.isAssignableFrom(enemies.get(j + 1).getClass()))
					enemies.swap(j, j + 1);

	}

	private int findLastAliveEnemy() {
		for (int i = 0; i < enemies.size; i++)
			if (!enemies.get(i).isAlive())
				return i - 1;
			else if (i == enemies.size - 1)
				return i;
		return -1;
	}

}
