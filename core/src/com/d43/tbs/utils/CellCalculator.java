package com.d43.tbs.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;

public class CellCalculator {

	private CellMap map;
	private String[] strMap;
	private Vector2 location;
	private int range;

	public CellCalculator(CellMap map, Vector2 location, int range) {
		this.map = map;
		this.location = location;
		this.range = range;

		this.strMap = toString(map);
	}

	public Array<Cell> getAvailableCellsForMove() {
		Array<Cell> cells = new Array<Cell>();

		for (int i = 0; i < this.strMap.length; i++) {
			for (int j = 0; j < this.strMap[i].length(); j++) {
				if (this.strMap[i].charAt(j) == '+')
					cells.add(this.map.getCell(i, j));
			}
		}

		return cells;
	}

	public Array<Cell> getAvailableCellsForAttack() {
		Array<Cell> cells = new Array<Cell>();

		for (int i = 0; i < this.strMap.length; i++) {
			for (int j = 0; j < this.strMap[i].length(); j++) {
				if (this.strMap[i].charAt(j) == '+')
					cells.add(this.map.getCell(i, j));
			}
		}

		return cells;
	}

	private String[] toString(CellMap map) {
//		Gdx.app.log("tag", this.location.toString());
		Cell[][] cells = map.getCells();
		String[] str = new String[cells.length];
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[i].length; j++)
				if (j == 0)
					str[i] = cells[i][j].containsUnit()
							? i == (int) this.location.x && j == (int) this.location.y ? "0" : "o"
							: Math.pow(i - this.location.x, 2) + Math.pow(j - this.location.y, 2) <= Math
									.pow(this.range, 2) ? "+" : "*";
				else
					str[i] += cells[i][j].containsUnit()
							? i == (int) this.location.x && j == (int) this.location.y ? "0" : "o"
							: Math.pow(i - this.location.x, 2) + Math.pow(j - this.location.y, 2) <= Math
									.pow(this.range, 2) ? "+" : "*";
//					str[i] += cells[i][j].containsUnit() ? i == (int)this.location.x && j == (int)this.location.y ? "0" : "o" : "*";
		return str;
	}

	public String[] getStringRepresentation() {
		return strMap;
	}
}