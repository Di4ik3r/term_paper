package com.d43.tbs.view.screen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.d43.tbs.TurnBasedStrategy;
import com.d43.tbs.control.MapChoosing;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.ChoosingZone;
import com.d43.tbs.model.unit.Archer;
import com.d43.tbs.model.unit.Knight;
import com.d43.tbs.model.unit.Orc;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.model.unit.Zombie;
import com.d43.tbs.view.ui.ChoosingUI;

public class ChooseScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureAtlas textureAtlas;
	public static float delta;
	private ChoosingUI ui;
	
	private TurnBasedStrategy game;

//	private BadLogic badLogic;
	ArrayList<Unit> allies;
	ArrayList<Unit> enemies;
	ArrayList<Unit> units;
	private CellMap map;
	ChoosingZone choosingZone;

	private MapChoosing mapChoosing;
	
	@Override
	public void show() {
		batch = new SpriteBatch();
//		badLogic = new BadLogic(textureAtlas.findRegion("0"), 0, 0, 1f, 1.7f);
		
//		this.background = new TextureRegion(this.textureAtlas.findRegion("grass"), 0, 0, 1366, 768);

		// *********************************************************** MAP
		// ***********************************************************
//		map = new CellMap(this.textureAtlas, textureAtlas.findRegion("grass"), -5f, 10f, 1f, 1f);
		map = new CellMap(this.textureAtlas, textureAtlas.findRegion("dirt"),  -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, 1366, 768);
		int a, b;
		a = 16;
		b = 10;
		map.initCells(a, b);
		for (int i = 0; i < a; i++)
			for (int j = 0; j < b; j++)
				map.getCell(i, j).setCell();
		
		map.initChooseScreen(this);

		float unitSize = 40f;

		// *********************************************************** ALLIES
		// ***********************************************************
		float archerKoef = 70f/35f;
		float knightKoef = 73f/35f;
		Unit archer = new Archer(textureAtlas.findRegion("archer"), -360, 210, unitSize, unitSize * archerKoef);
		archer.setForChoose(true);
		archer.initAnimations(this.textureAtlas);
		Unit knight = new Knight(textureAtlas.findRegion("knight"), -390, 210, unitSize, unitSize * knightKoef);
		knight.setForChoose(true);
		knight.initAnimations(this.textureAtlas);
		allies = new ArrayList<Unit>();
		allies.add(archer);
		allies.add(knight);

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
		float zombieKoef = 68f / 41f;
		float orcKoef = 70f / 35f;
		Unit zombie = new Zombie(textureAtlas.findRegion("zombie"), 390, 210, unitSize, unitSize * zombieKoef);
		zombie.setForChoose(true);
		zombie.initAnimations(this.textureAtlas);
		Unit orc = new Orc(textureAtlas.findRegion("orc"), 390, 210, unitSize, unitSize * orcKoef);
		orc.setForChoose(true);
		orc.initAnimations(this.textureAtlas);
		enemies = new ArrayList<Unit>();
		enemies.add(zombie);
		enemies.add(orc);

		// *********************************************************** CHOOSING ZONE
		// ***********************************************************
		choosingZone = new ChoosingZone(this.textureAtlas, textureAtlas.findRegion("0"), -5f, 10f, 1f, 1f);
		choosingZone.initCells(this, this.allies.size(), this.enemies.size());
		for (int i = 0; i < this.allies.size(); i++) {
			choosingZone.getAlliesCell(i).setCell();
//			choosingZone.getAlliesCell(i).setMapHandler(mapChoosing);
		}
		for (int i = 0; i < this.enemies.size(); i++) {
			choosingZone.getEnemiesCell(i).setCell();
//			choosingZone.getEnemiesCell(i).setMapHandler(mapChoosing);
		}

		// *********************************************************** MAP HANDLER
		// *************************************************************
		this.mapChoosing = new MapChoosing(map, choosingZone, allies, enemies);
		this.mapChoosing.setAtlas(this.textureAtlas);

		map.setMapHandler(this.mapChoosing);
		for (int i = 0; i < allies.size(); i++) {
			allies.get(i).setMapHandler(this.mapChoosing);
			choosingZone.addAllies(allies.get(i));
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).setMapHandler(this.mapChoosing);
			choosingZone.addEnemies(enemies.get(i));
		}

		// *********************************************************** UI
		// ***********************************************************
		ui = new ChoosingUI(this.textureAtlas);
		ui.setUnits(allies, enemies);
		
		
		// *********************************************************** UNITS CONTAINER
		// ***********************************************************
		units = new ArrayList<Unit>();
		for(int i = 0; i < allies.size(); i++)
			units.add(allies.get(i));
		for(int i = 0; i < enemies.size(); i++)
			units.add(enemies.get(i));
		this.sortUnits();
		
		
		// *********************************************************** HANDLER EXCEPTION CLUE
		// ***********************************************************
		this.choosingZone.setMapHandler(mapChoosing);
		
		
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
}
	
	public void setGame(TurnBasedStrategy game) {
		this.game = game;
	}

	private void sortUnits() {
		for(int j = 0; j < units.size(); j++)
			for(int i = 0; i < units.size()-1; i++)
				if(units.get(i).getCell() != null && units.get(i+1).getCell() != null)
					if(map.cellExist(units.get(i).getCell().getBounds()) && map.cellExist(units.get(i+1).getCell().getBounds()))
						if(map.findCellCoord(units.get(i).getCell().getBounds()).y < map.findCellCoord(units.get(i+1).getCell().getBounds()).y) {
							Unit buff = units.get(i);
							units.set(i, units.get(i + 1));
							units.set(i + 1, buff);
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
			batch.draw(this.textureAtlas.findRegion("choose_behind"), -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
			
			choosingZone.draw(batch);
			
//			for (int i = 0; i < allies.size; i++)
//				allies.get(i).draw(batch);
	
			for (int i = 0; i < units.size(); i++)
				units.get(i).draw(batch, delta);
			
//			units.get(1).draw(batch);
			
//			for (int i = 0; i < enemies.size; i++)
//				enemies.get(i).draw(batch);
		
			batch.draw(this.textureAtlas.findRegion("choose_above"), -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		batch.end();
		

		ui.draw();
//		ui.attachLabels();

//		ui.setLabelX(String.format("%.3g%n", badLogic.getBounds().getX()));
//		ui.setLabelY(String.format("%.3g%n", badLogic.getBounds().getY()));
//		ui.setLabelSpeed(String.format("%.3g%n", badLogic.getSpeed()));
		
		this.sortUnits();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			ArrayList<Unit> unitsToExport = new ArrayList<Unit>();
			for(int i = 0; i < map.getRows(); i++)
				for(int j = 0; j < map.getCols(); j++)
					if(map.getCell(i, j).containsUnit())
						unitsToExport.add(map.getCell(i, j).getUnit());
			
//			try {
//				 FileInputStream fis = new FileInputStream("temp.out");
//				 ObjectInputStream oin = new ObjectInputStream(fis);
//				 unitsToExport = (ArrayList<Unit>)oin.readObject();
//			}catch(Exception ex) {
//				Gdx.app.log("file read", ex.toString());
//				return;
//			}
						
			this.game.setGameUnits(unitsToExport);
			this.game.play();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			ArrayList<Unit> unitsToExport = new ArrayList<Unit>();
			for(int i = 0; i < map.getRows(); i++)
				for(int j = 0; j < map.getCols(); j++)
					if(map.getCell(i, j).containsUnit())
						unitsToExport.add(map.getCell(i, j).getUnit());
			try {
				FileOutputStream fos = new FileOutputStream("temp.out");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
			  	oos.writeObject(unitsToExport);
				oos.flush();
				oos.close();
			} catch(Exception ex) {
				Gdx.app.log("file write", ex.toString());
				return;
			}
		}
	}
	
//	public void addUnit(Unit unit) {
//		if(units.contains(unit, true))
//			return;
//		units.add(unit);
//	}
//	
//	public void removeUnit(Unit unit) {
//		this.units.removeValue(unit, true);
//	}

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
	
	public void addUnit(Unit unit) {
//		if(units.contains(unit, true))
		if(units.contains(unit))
			return;
			units.add(unit);
		}
	
	public void removeUnit(Unit unit) {
//		this.units.removeValue(unit, true);
		this.units.remove(unit);
	}

}
