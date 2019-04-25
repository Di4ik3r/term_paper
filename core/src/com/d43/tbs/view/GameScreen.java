package com.d43.tbs.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.DefeatedZone;
import com.d43.tbs.model.unit.Archer;
import com.d43.tbs.model.unit.Knight;
import com.d43.tbs.model.unit.Orc;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.model.unit.Zombie;
import com.d43.tbs.utils.MapChecker;
import com.d43.tbs.utils.Rnd;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureAtlas textureAtlas;
	public static float delta;
	private UI ui;
	
//	private BadLogic badLogic;
	Array<Unit> allies;
	Array<Unit> enemies;
	private CellMap map;
	DefeatedZone defeatedZone;
	
	private MapChecker mapChecker;
	 
	@Override
	public void show() {
		batch = new SpriteBatch();
//		badLogic = new BadLogic(textureAtlas.findRegion("0"), 0, 0, 1f, 1.7f);

		map = new CellMap(this.textureAtlas, textureAtlas.findRegion("0"), -5f, 10f, 1f, 1f);
		int a, b;
		a = 16;
		b = 10;
		map.initCells(a, b);
		for(int i = 0; i < a; i++)
			for(int j = 0; j < b; j++)
				map.getCell(i, j).setCell();
		
		float unitSize = 40f;
		
		Unit archer = new Archer(textureAtlas.findRegion("archer"), -360, 210, unitSize, unitSize * 70/35);
		Unit archer1 = new Archer(textureAtlas.findRegion("archer"), -390, 210, unitSize, unitSize * 70/35);
		Unit knight = new Knight(textureAtlas.findRegion("knight"), -390, 210, unitSize, unitSize * 70/35);
		Unit knight1 = new Knight(textureAtlas.findRegion("knight"), -390, 210, unitSize, unitSize * 70/35);
		allies = new Array<Unit>();
		allies.add(archer);
		allies.add(archer1);
		allies.add(knight);
		allies.add(knight1);
		this.initUnits(allies, false);
		
//		for(int i = 0; i < allies.size; i++)
//		{
//			allies.get(i).setUnitToControl();
//			
//			int row = Rnd.generate(0, map.getRows());
//			int col = Rnd.generate(0, map.getCols());
//			
//			if(map.getCell(row, col).getUnit() == null)
//				allies.get(i).setCell(map.getCell(row, col));
//		}
		
		Unit zombie = new Zombie(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * 68/41);
		Unit zombie1 = new Zombie(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * 68/41);
		Unit zombie2 = new Zombie(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * 68/41);
		Unit orc = new Orc(textureAtlas.findRegion("orc"), -390, 210, unitSize, unitSize * 70/35);
		Unit orc1 = new Orc(textureAtlas.findRegion("orc"), -390, 210, unitSize, unitSize * 70/35);
		enemies = new Array<Unit>();
		enemies.add(zombie);
		enemies.add(zombie1);
		enemies.add(zombie2);
		enemies.add(orc);
		enemies.add(orc1);
		this.initUnits(enemies, true);
		
		defeatedZone = new DefeatedZone(this.textureAtlas, textureAtlas.findRegion("0"), -5f, 10f, 1f, 1f);
		defeatedZone.initCells(this.allies.size, this.enemies.size);
		for(int i = 0; i < this.allies.size; i++)
			defeatedZone.getAlliesCell(i).setCell();
		for(int i = 0; i < this.enemies.size; i++)
			defeatedZone.getEnemiesCell(i).setCell();
		
		this.mapChecker = new MapChecker(map, defeatedZone, allies, enemies);
		this.mapChecker.setAtlas(this.textureAtlas);
		
		map.setMapChecker(this.mapChecker);
		for(int i = 0; i < allies.size; i++) {
			allies.get(i).setMapChecker(this.mapChecker); 
		}
		map.placeUnits(allies);
		for(int i = 0; i < enemies.size; i++) {
			enemies.get(i).setMapChecker(this.mapChecker);
		}
		map.placeUnits(enemies);
		
		ui = new UI(this.textureAtlas);
		ui.setUnits(allies, enemies);
		
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}
	
	private void initUnits(Array<Unit> units, boolean isEnemy) {
		int row = 0, col = 0;
		
		for(int i = 0; i < units.size; i++)
		{			
			if(isEnemy) {
				row = Rnd.generate(map.getRows() - map.getRows()/3, map.getRows()-1);
				col = Rnd.generate(0, map.getCols()-1);
			}
			else {
				row = Rnd.generate(0, map.getRows()/3);
				col = Rnd.generate(0, map.getCols()-1);
			}
			
//			generateCoord(row, col, isEnemy);
			
			if(map.getCell(row, col).getUnit() == null)
				units.get(i).setCell(map.getCell(row, col));
		}
	}
	
//	private void generateCoord(int row, int col, boolean isEnemy) {
//		if(isEnemy) {
//			row = Rnd.generate(map.getRows() - map.getRows()/3, map.getRows()-1);
//			col = Rnd.generate(0, map.getCols()-1);
//		}
//		else {
//			row = Rnd.generate(0, map.getRows()/3);
//			col = Rnd.generate(0, map.getCols()-1);
//		}
//		
//		if(map.getCell(row, col).getUnit() != null)
//			generateCoord(row, col, isEnemy);
//		else return;
//	}
	
	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		GameScreen.delta = delta;
//		BadLogic.currentFrame = BadLogic.animation.getKeyFrame(stateTime, true);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
//			badLogic.draw(batch);
			map.draw(batch);
			defeatedZone.draw(batch);
			for(int i = 0; i < allies.size; i++)
				allies.get(i).draw(batch);
						
//				units.get(1).draw(batch);
			for(int i = 0; i < enemies.size; i++)
				enemies.get(i).draw(batch);
		batch.end();
		
		ui.draw();
		ui.attachLabels();
//		ui.setLabelX(Float.toString(Gdx.input.getX()));
//		ui.setLabelY(Float.toString(Gdx.input.getY()));
		
//		ui.setLabelX(String.format("%.3g%n", badLogic.getBounds().getX()));
//		ui.setLabelY(String.format("%.3g%n", badLogic.getBounds().getY()));
//		ui.setLabelSpeed(String.format("%.3g%n", badLogic.getSpeed()));
	}

	@Override
	public void resize(int width, int height) {
//		float aspectRation = (float)height/width;		
		camera = new OrthographicCamera(1377f, 768f);
		
		camera.update();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		ui.dispose();
	}
}
