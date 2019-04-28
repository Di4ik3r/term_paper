package com.d43.tbs.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.MapChecker;

public class UnitController {

	private int id;
	private Polygon bounds;
	private Cell cell;

	private Unit unit;

	private MapChecker mapChecker;

	public UnitController(int id, Polygon bounds) {
		this.id = id;
		this.bounds = bounds;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public void setCell(Cell cell) {
		if (this.unit == null)
			return;
		this.cell = cell;
//		this.bounds.setPosition(
//				this.cell.getBounds().getX() + this.cell.getSize().width / 2 - this.unit.getSize().width / 2,
//				this.cell.getBounds().getY() + this.cell.getSize().height / 2 - this.cell.getSize().height / 4);
	}

	public void setMapChecker(MapChecker mapChecker) {
		this.mapChecker = mapChecker;
	}

	public void handle() {
		if (this.bounds.contains(Gdx.input.getX() - Gdx.graphics.getWidth() / 2,
				-Gdx.input.getY() + Gdx.graphics.getHeight() / 2) && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

//			this.textureRegion = (TextureRegion)this.textureAtlas.findRegion("archer");
//			Gdx.app.log("tag", this.textureRegion.toString());
		}
	}

	public void pickedUp() {

	}
}
