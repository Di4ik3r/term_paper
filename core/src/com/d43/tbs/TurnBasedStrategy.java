package com.d43.tbs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.Assets;
import com.d43.tbs.view.ChooseScreen;
import com.d43.tbs.view.GameScreen;
import com.d43.tbs.view.ResultScreen;

public class TurnBasedStrategy extends Game {

	private Screen chooseScreen, gameScreen, resultScreen;
	private Assets assets;
	
	@Override
	public void create() {
		assets = new Assets();
		
		chooseScreen = new ChooseScreen();
		((ChooseScreen)chooseScreen).setTextureAtlas(assets.getManager().get("atlas.atlas", TextureAtlas.class));
		((ChooseScreen)chooseScreen).setGame(this);
		
		gameScreen = new GameScreen();
		((GameScreen)gameScreen).setTextureAtlas(assets.getManager().get("atlas.atlas", TextureAtlas.class));
		((GameScreen)gameScreen).setGame(this);
		
		resultScreen = new ResultScreen();
		((ResultScreen)resultScreen).setTextureAtlas(assets.getManager().get("atlas.atlas", TextureAtlas.class));
		
		this.setScreen(chooseScreen);
	}
	
	public void endGame(String[] result) {
		((ResultScreen)resultScreen).setResult(result);
		this.setScreen(resultScreen);
	}
	
	public void setGameUnits(Array<Unit> units) {
	 ((GameScreen)gameScreen).setUnits(units);
	}
	public void startPlay() {
		this.setScreen(gameScreen);
	}
	
	public void dispose() {
		super.dispose();
		this.getScreen().dispose();
		assets.dispose();
	}
	
//	SpriteBatch batch;
//	Texture img;
//	
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}
//
//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
