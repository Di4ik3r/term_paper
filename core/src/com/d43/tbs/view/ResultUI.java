package com.d43.tbs.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ResultUI {
	
	private Stage stage;
	private Skin skin;
	
	private String[] result;
	private Label[] labels;
	
	public ResultUI() {
		
		this.stage = new Stage(new StretchViewport(1366, 768));
		Gdx.input.setInputProcessor(stage);

		
		this.skin = new Skin(Gdx.files.internal("skin.json"));
	}
	
	public void setResult(String[] result) {
		this.result = result;
		
		float multiplier = 70f;
		labels = new Label[result.length];
//		for(int i = labels.length-1; i >= 0; i--)
		for(int i = 0; i < labels.length; i++)
			labels[i] = createLabel(result[labels.length - i - 1], 330f,  (80 + i * multiplier));
	}
	
//	public void initLabels() {
//		for(int i = 0; i < units.size; i++) {
//			Label label = createLabel(0, 0);
//		}
//	}
	
//	public void attachLabels() {
//		for(int i = 0; i < units.size; i++) {
////			hps.get(i).setPosition(Gdx.graphics.getWidth() - units.get(i).getBounds().getX(), Gdx.graphics.getHeight()-units.get(i).getBounds().getY());
////			hps.get(i).setPosition(Gdx.input.getX(), Gdx.input.getY());
////			if(camera != null)
//			
////			hps.get(i).setPosition(Gdx.graphics.getWidth()/2 + units.get(i).getBounds().getX(), Gdx.graphics.getHeight()/2 +  units.get(i).getBounds().getY());
//			hps.get(i).setPosition(Gdx.graphics.getWidth()/2 + units.get(i).getBounds().getX() + 25, Gdx.graphics.getHeight()/2 + units.get(i).getBounds().getY() + 5);
//			if(units.get(i).getHp() > 0) {
//				hps.get(i).setText(units.get(i).getHp());
//			}
//			else
//				hps.get(i).setText("");
//		}
//	}
	
//	public void setUnits(Array<Unit> allies, Array<Unit> enemies) {
//		units = new Array<Unit>();
//		for(int i = 0; i < allies.size; i++)
//			units.add(allies.get(i));
//		for(int i = 0; i < enemies.size; i++)
//			units.add(enemies.get(i));
//		
//		this.initLabels();
//	}
	
	private Label createLabel(String text, float x, float y) {
		Label label = new Label(text, this.skin.get("default", LabelStyle.class));
		label.setPosition(x, y);
		label.setFontScale(0.6f);
		
		this.stage.addActor(label);
		
		return label;
	}
	
//	private TextButton createButton(String text, float x, float y) {
//		BitmapFont font = new BitmapFont();
//		Skin skinn = new Skin();
//		skinn.addRegions(this.atlas);
//		TextButtonStyle styleBtn = new TextButtonStyle();
//		
//		styleBtn.font = font;
//		styleBtn.up = skinn.getDrawable("cell");
//		styleBtn.down = skinn.getDrawable("cell");
//		styleBtn.checked = skinn.getDrawable("cell");
//		
//		TextButton btn = new TextButton(text, styleBtn);
//		btn.setPosition(x, y);
//		this.stage.addActor(btn);
//		
//		btn.addListener(new ChangeListener() {
//			@Override
//			public void changed (ChangeEvent event, Actor actor) {
//				Gdx.app.log("tag", "ConfirmButton pressed");
//			}
//		});
//		
//		return btn;
//	}
	
	public void draw() {
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage.act();
		stage.draw();
		
//		attachLabels();
	}
	
	public void dispose() {
		stage.dispose();
	}
}
