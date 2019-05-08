package com.d43.tbs.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.TurnBasedStrategy;
import com.d43.tbs.control.MapPlaying;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.DefeatedZone;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.Rnd;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	public static TextureAtlas textureAtlas;
	public static float delta;
	private UI ui;

	Array<Unit> allies;
	Array<Unit> enemies;
	Array<Unit> units;
	private CellMap map;
	DefeatedZone defeatedZone;

	private MapPlaying mapPlaying;
	
	private TurnBasedStrategy game;

	@Override
	public void show() {
		batch = new SpriteBatch();
//		badLogic = new BadLogic(textureAtlas.findRegion("0"), 0, 0, 1f, 1.7f);

//		this.background = new TextureRegion(this.textureAtlas.findRegion("grass"), 0, 0, 1366, 768);

		// *********************************************************** MAP
		// ***********************************************************
//		map = new CellMap(this.textureAtlas, textureAtlas.findRegion("grass"), -5f, 10f, 1f, 1f);
		map = new CellMap(this.textureAtlas, textureAtlas.findRegion("dirt"), -Gdx.graphics.getWidth() / 2,
				-Gdx.graphics.getHeight() / 2, 1366, 768);
		int a, b;
		a = 16;
		b = 10;
		map.initCells(a, b);
		for (int i = 0; i < a; i++)
			for (int j = 0; j < b; j++)
				map.getCell(i, j).setCell();

//		float unitSize = 40f;

		// *********************************************************** ALLIES
		// ***********************************************************
//		float archerKoef = 70f / 35f;
//		float knightKoef = 73f / 35f;
//		Unit archer = new Archer(textureAtlas.findRegion("archer"), -360, 210, unitSize, unitSize * archerKoef);
//		Unit archer1 = new Archer(textureAtlas.findRegion("archer"), -390, 210, unitSize, unitSize * archerKoef);
//		Unit knight = new Knight(textureAtlas.findRegion("knight"), -390, 210, unitSize, unitSize * knightKoef);
//		Unit knight1 = new Knight(textureAtlas.findRegion("knight"), -390, 210, unitSize, unitSize * knightKoef);
//		allies = new Array<Unit>();
//		allies.add(archer);
//		allies.add(archer1);
//		allies.add(knight);
//		allies.add(knight1);
//		this.initUnits(allies, false);

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

		// *********************************************************** ENEMIES
		// ***********************************************************
//		float zombieKoef = 68f / 41f;
//		float orcKoef = 70f / 35f;
//		Unit zombie = new Zombie(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * zombieKoef);
//		Unit zombie1 = new Zombie(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * zombieKoef);
//		Unit zombie2 = new Zombie(textureAtlas.findRegion("zombie"), -390, 210, unitSize, unitSize * zombieKoef);
//		Unit orc = new Orc(textureAtlas.findRegion("orc"), -390, 210, unitSize, unitSize * orcKoef);
//		Unit orc1 = new Orc(textureAtlas.findRegion("orc"), -390, 210, unitSize, unitSize * orcKoef);
//		enemies = new Array<Unit>();
//		enemies.add(zombie);
//		enemies.add(zombie1);
//		enemies.add(zombie2);
//		enemies.add(orc);
//		enemies.add(orc1);
//		this.initUnits(enemies, true);

		// *********************************************************** DEFEATE ZONE
		// ***********************************************************
		defeatedZone = new DefeatedZone(this.textureAtlas, textureAtlas.findRegion("0"), -5f, 10f, 1f, 1f);
		defeatedZone.initCells(this.units.size);
		defeatedZone.setAllies(allies);
		for (int i = 0; i < this.units.size; i++)
			defeatedZone.getCell(i).setCell();

		// *********************************************************** MAP CHECKER
		// *************************************************************
		this.mapPlaying = new MapPlaying(map, defeatedZone, allies, enemies);
		this.mapPlaying.setAtlas(this.textureAtlas);
		this.mapPlaying.setGame(game);
		this.mapPlaying.setScreen(this);

		map.setMapHandler(this.mapPlaying);
		for (int i = 0; i < allies.size; i++) {
			allies.get(i).setMapHandler(this.mapPlaying);
		}
		map.placeUnits(allies);
		for (int i = 0; i < enemies.size; i++) {
			enemies.get(i).setMapHandler(this.mapPlaying);
		}
		map.placeUnits(enemies);

		// *********************************************************** UI
		// ***********************************************************
		ui = new UI(this.textureAtlas);
		ui.setUnits(allies, enemies);

		// *********************************************************** UNITS CONTAINER
		// ***********************************************************
//		units = new Array<Unit>();
//		for (int i = 0; i < allies.size; i++)
//			units.add(allies.get(i));
//		for (int i = 0; i < enemies.size; i++)
//			units.add(enemies.get(i));
//		this.sortUnits();

		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void setGame(TurnBasedStrategy game) {
		this.game = game;
	}
	
	public void setUnits(Array<Unit> units) {
		this.units = units;
		this.allies = new Array<Unit>();
		this.enemies = new Array<Unit>();
		for (int i = 0; i < units.size; i++) {
//			units.get(i).setCell(map.getCell((int)units.get(i).getLocation().x, (int)units.get(i).getLocation().y));
//			units.get(i).changeSpeed(8f, 7f);
			if (units.get(i).isEnemy())
				enemies.add(units.get(i));
			else
				allies.add(units.get(i));
		}
//		this.sortUnits();
	}

	private void sortUnits() {
		for (int j = 0; j < units.size; j++)
			for (int i = 0; i < units.size - 1; i++)
				if (units.get(i).getCell() != null && units.get(i + 1).getCell() != null)
					if (map.cellExist(units.get(i).getCell().getBounds())
							&& map.cellExist(units.get(i + 1).getCell().getBounds()))
						if (map.findCellCoord(units.get(i).getCell().getBounds()).y < map
								.findCellCoord(units.get(i + 1).getCell().getBounds()).y)
							units.swap(i, i + 1);

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
		batch.draw(this.textureAtlas.findRegion("game_behind"), -Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);
//			for (int i = 0; i < allies.size; i++)
//				allies.get(i).draw(batch);

		for (int i = 0; i < units.size; i++)
//			if (units.get(i).isAlive())
				units.get(i).draw(batch, delta);
		
//			units.get(1).draw(batch);

//			for (int i = 0; i < enemies.size; i++)
//				enemies.get(i).draw(batch);

		batch.draw(this.textureAtlas.findRegion("choose_above"), -Gdx.graphics.getWidth() / 2,-Gdx.graphics.getHeight() / 2);
		batch.end();

		ui.draw();

		this.sortUnits();
	}

	public void endGame(String[] result) {
		ui.setResult(result);
	}
	
	@Override
	public void resize(int width, int height) {
//		float aspectRation = (float)height/width;		
		camera = new OrthographicCamera(1366f, 768f);

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

	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	private void initUnits(Array<Unit> units, boolean isEnemy) {
		int row = 0, col = 0;

		for (int i = 0; i < units.size; i++) {
			if (isEnemy) {
				row = Rnd.generate(map.getRows() - map.getRows() / 3, map.getRows() - 1);
				col = Rnd.generate(0, map.getCols() - 1);
			} else {
				row = Rnd.generate(0, map.getRows() / 3);
				col = Rnd.generate(0, map.getCols() - 1);
			}

//			generateCoord(row, col, isEnemy);

			if (map.getCell(row, col).getUnit() == null)
				units.get(i).setCell(map.getCell(row, col));
		}
	}
}
