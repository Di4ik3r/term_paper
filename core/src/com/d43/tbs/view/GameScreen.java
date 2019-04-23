package com.d43.tbs.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.Archer;
import com.d43.tbs.model.unit.Knight;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.MapChecker;
import com.d43.tbs.utils.Rnd;
import com.d43.tbs.utils.UI;

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
		
		Unit zombie = new Archer(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * 68/41);
		Unit orc = new Archer(textureAtlas.findRegion("orc"), -390, 210, unitSize, unitSize * 70/35);
		enemies = new Array<Unit>();
		enemies.add(zombie);
		enemies.add(orc);
		this.initUnits(enemies, true);
		
		
		this.mapChecker = new MapChecker(map, allies);
		this.mapChecker.setAtlas(this.textureAtlas);
		
		map.setMapChecker(this.mapChecker);
		for(int i = 0; i < allies.size; i++) {
			allies.get(i).setMapChecker(this.mapChecker); 
			allies.get(i).setIsEnemy(false);
		}
		map.placeUnits(allies);
		for(int i = 0; i < enemies.size; i++) {
			enemies.get(i).setMapChecker(this.mapChecker);
			enemies.get(i).setIsEnemy(true);
			enemies.get(i).setReplaceability(false);
		}
		map.placeUnits(enemies);
		
		ui = new UI(this.textureAtlas);
		
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stateTime = 0f;
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
			
			if(map.getCell(row, col).getUnit() == null)
				units.get(i).setCell(map.getCell(row, col));
		}
	}
	
	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	float stateTime;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		GameScreen.delta = delta;
		
		stateTime += Gdx.graphics.getDeltaTime();
//		BadLogic.currentFrame = BadLogic.animation.getKeyFrame(stateTime, true);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
//			badLogic.draw(batch);
			map.draw(batch);
			for(int i = 0; i < allies.size; i++)
				allies.get(i).draw(batch);
//				units.get(1).draw(batch);
			for(int i = 0; i < enemies.size; i++)
				enemies.get(i).draw(batch);
		batch.end();
		
		ui.draw();
		ui.setLabelX(Float.toString(camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x));
		ui.setLabelY(Float.toString(camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y));
//		ui.setLabelX(String.format("%.3g%n", badLogic.getBounds().getX()));
//		ui.setLabelY(String.format("%.3g%n", badLogic.getBounds().getY()));
//		ui.setLabelSpeed(String.format("%.3g%n", badLogic.getSpeed()));
	}

	@Override
	public void resize(int width, int height) {
		float aspectRation = (float)height/width;
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
