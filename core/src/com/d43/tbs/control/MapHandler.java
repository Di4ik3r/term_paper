package com.d43.tbs.control;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.Unit;

public abstract class MapHandler {

	CellMap map;
	Array<Unit> allies;
	Array<Unit> enemies;

	Unit pickedUnit;
	
	TextureAtlas textureAtlas;
	
	Array<Cell> availableCells;
	
	Unit movingUnit;
	
	public abstract boolean isPlaying();
	
	public abstract void pickUnit(Unit unit);
	public abstract void unitAttack(Polygon bounds);
	public abstract void unitMove(Polygon bounds);
}
