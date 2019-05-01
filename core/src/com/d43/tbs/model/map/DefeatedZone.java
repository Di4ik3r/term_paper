package com.d43.tbs.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.MapPlaying;
import com.d43.tbs.model.GameObject;
import com.d43.tbs.model.unit.Unit;

public class DefeatedZone extends GameObject {
	private Cell[] cells;
	
	private TextureAtlas textureAtlas;
	
	public DefeatedZone(TextureAtlas textureAtlas, TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.textureAtlas = textureAtlas;
	}
	
	public void initCells(int count) {
		this.cells = new Cell[count];
		
		Array<TextureRegion> regions = new Array<TextureRegion>(); 
		regions.add(this.textureAtlas.findRegion("cell"));
		regions.add(this.textureAtlas.findRegion("cellMouseOn"));
		regions.add(this.textureAtlas.findRegion("cellMouseOnBlocked"));
		
		int multiplier = 80;
		
		for(int i = 0; i < cells.length; i++) {
//			Cell cell = new Cell(regions, (-8 + i*0.99f)*multiplier, (-3.5f + 11f*0.6f)*multiplier, 1f * multiplier, 0.625f * multiplier);
//			Cell cell = new Cell(regions, (-8 + i*1f)*multiplier, (-3.5f + 11*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
			Cell cell = new Cell(regions, (-8 + i*1f)*multiplier, (-3.5f + 10.1f*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
			cell.setForDefeated(true);
			this.cells[i] = cell;
		}
	}
	
	public void addUnit(Unit unit) {
		unit.setAlive(false);
		for(int i = cells.length-1; i >= 0; i--)
			if(!cells[i].containsUnit()) {
				unit.setCell(cells[i]);
				unit.setHp(0);
//				return;
			}
	}
	
	public int getCount() {
		return this.cells.length;
	}	
	
	@Override
	public void draw(SpriteBatch batch) {
		for(int i = 0; i < cells.length; i++) 
			cells[i].draw(batch);
	}
	
	public Cell[] getCells() {
		return this.cells;
	}
	
	public Cell getCell(int x) {
		return this.cells[x];
	}
	
//	public Cell findCell(Vector2 location) {
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				if(cells[i][j].getController().isBelong(location.x, location.y))
//					return cells[i][j];
//		return null;
//	}
//	
//	public String findCellString(Vector2 location) {
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				if(cells[i][j].getController().isBelong(location.x, location.y))
//					return new String(Integer.toString(i)+" : "+Integer.toString(j));
//		return "";
//	}
}
