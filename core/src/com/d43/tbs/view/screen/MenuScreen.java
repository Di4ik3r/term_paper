package com.d43.tbs.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.d43.tbs.TurnBasedStrategy;
import com.d43.tbs.view.ui.MenuUI;

public class MenuScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureAtlas textureAtlas;
	public static float delta;
	private MenuUI ui;
	
	private TurnBasedStrategy game;

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		this.ui = new MenuUI(this.textureAtlas);
		this.ui.setGame(this.game);
	}	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch.begin();
		
		batch.end();
		
		ui.draw();
	}
	

	@Override
	public void resize(int width, int height) {
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
//		ui.dispose();
	}

	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}
	
	public void setGame(TurnBasedStrategy game) {
		this.game = game;
	}
}
