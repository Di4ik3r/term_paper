package com.d43.tbs.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.d43.tbs.TurnBasedStrategy;

public class MenuUI {
	private TextureAtlas atlas;

	private Stage stage;
	private Skin skin;
	private BitmapFont font;

	private TextButton btnNew, btnContinue, btnExit;

	private TurnBasedStrategy game;

	public MenuUI(TextureAtlas atlas) {
		this.atlas = atlas;

		this.stage = new Stage(new StretchViewport(1366, 768));
		Gdx.input.setInputProcessor(stage);

		font = new BitmapFont();

		this.skin = new Skin(Gdx.files.internal("skin.json"));
		skin.addRegions(this.atlas);

		initButtons();

//		stage.addActor(table);
	}

	private void initButtons() {
		float centreX, centreY;
		centreX = centreCoord().x;
		centreY = centreCoord().y;

//		btnNew = createButton("New Game", centreX, centreY - 100);
//		btnContinue = createButton("New Game", centreX, centreY);
//		btnExit = createButton("New Game", centreX, centreY + 100);
		btnNew = createButton("New Game", 0, +100);
		btnNew.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				clickedOnStart();
			}
		});
		btnContinue = createButton("Continue", 0, 0);
		btnExit = createButton("Exit", 0, -100);
	}

	private void clickedOnStart() {
		this.game.startNewGame();
	}

	private Vector2 centreCoord() {
		return new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	private Label createLabel(String text, float fontScale, float x, float y) {
		Label label = new Label(text, this.skin.get("default", LabelStyle.class));
		label.setPosition(x, y);
		label.setFontScale(fontScale);

		this.stage.addActor(label);

		return label;
	}

	private TextButton createButton(String text, float x, float y) {
		int lr = 40;
		int ud = 20;

		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = font;
		btnStyle.up = skin.getDrawable("cell");
		btnStyle.down = skin.getDrawable("cellMouseOnBlocked");
		btnStyle.over = skin.getDrawable("cellMouseOn");
//		btnStyle.checked = skin.getDrawable("cellMouseOnBlocked");
		btnStyle.pressedOffsetX = 1;
		btnStyle.pressedOffsetY = -1;

		TextButton btn = new TextButton(text, btnStyle);
//		btn.setPosition(x, y);
		btn.pad(ud, lr, ud, lr);

		Table table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setPosition(x, y);
		table.add(btn);
		this.stage.addActor(table);
		/*
		 * btn.addListener(new ChangeListener() {
		 * 
		 * @Override public void changed(ChangeEvent event, Actor actor) {
		 * Gdx.app.log("tag", "ConfirmButton pressed"); } });
		 */
		return btn;
	}

	public void setGame(TurnBasedStrategy game) {
		this.game = game;
	}

	public void draw() {
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		stage.act();
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}
}
