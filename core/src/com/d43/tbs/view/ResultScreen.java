package com.d43.tbs.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ResultScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureAtlas textureAtlas;
	public static float delta;
	private ResultUI ui;

	String[] result;

	@Override
	public void show() {
		batch = new SpriteBatch();

		// *********************************************************** UI
		// ***********************************************************
		ui = new ResultUI();
		ui.setResult(this.result);

		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void setResult(String[] result) {
		this.result = result;
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
			batch.draw(this.textureAtlas.findRegion("dirt"), -Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);
			batch.draw(this.textureAtlas.findRegion("choose_behind"), -Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);
			batch.draw(this.textureAtlas.findRegion("choose_above"), -Gdx.graphics.getWidth() / 2,-Gdx.graphics.getHeight() / 2);
		batch.end();

		ui.draw();
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
}
