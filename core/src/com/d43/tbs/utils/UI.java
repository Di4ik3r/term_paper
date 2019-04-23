package com.d43.tbs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class UI {
	
	private TextureAtlas atlas;
	
	private Stage stage;
	private Skin skin;
	private Label labelX, labelY, labelSpeed;
	
	private TextButton btnConfirm;
	
	public UI(TextureAtlas atlas) {
		this.atlas = atlas;
		
		this.stage = new Stage(new StretchViewport(1366, 768));
		Gdx.input.setInputProcessor(stage);

		
		this.skin = new Skin(Gdx.files.internal("skin.json"));
		
		this.labelX = this.createLabel(10, 80);
		this.labelY = this.createLabel(10, 40);
		this.labelSpeed = this.createLabel(10, 110);
		
		this.btnConfirm = createButton("Confirm", 300, 20);
	}
	
	private TextButton createButton(String text, float x, float y) {
		BitmapFont font = new BitmapFont();
		Skin skinn = new Skin();
		skinn.addRegions(this.atlas);
		TextButtonStyle styleBtn = new TextButtonStyle();
		
		styleBtn.font = font;
		styleBtn.up = skinn.getDrawable("cell");
        styleBtn.down = skinn.getDrawable("cell");
        styleBtn.checked = skinn.getDrawable("cell");
        
        TextButton btn = new TextButton(text, styleBtn);
        btn.setPosition(x, y);
        this.stage.addActor(btn);
        
        btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.log("tag", "ConfirmButton pressed");
            }
        });
        
        return btn;
	}
	
	private Label createLabel(float x, float y) {
		Label label = new Label("", this.skin.get("default", LabelStyle.class));
		label.setPosition(x, y);
		label.setFontScale(0.5f);
		
		this.stage.addActor(label);
		
		return label;
	}
	
	public void setLabelX(String input) {
		this.labelX.setText("x: " + input);
	}
	
	public void setLabelY(String input) {
		this.labelY.setText("y: " + input);
	}
	
	public void setLabelSpeed(String input) {
		this.labelSpeed.setText("Speed: " + input);
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
