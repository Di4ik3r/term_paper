package com.d43.tbs.view.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.d43.tbs.model.unit.Unit;

public class ChoosingUI {
	private TextureAtlas atlas;

	private Stage stage;
	private Skin skin;
	private Label labelX, labelY, labelSpeed;

	private TextButton btnConfirm;

	private Array<Label> hps;
	private ArrayList<Unit> units;

	private String[] result;
	private Label[] resultLabels;
	private Array<Label> info;

	public ChoosingUI(TextureAtlas atlas) {
		this.atlas = atlas;

		this.stage = new Stage(new StretchViewport(1366, 768));
		Gdx.input.setInputProcessor(stage);

		this.skin = new Skin(Gdx.files.internal("skin.json"));

		hps = new Array<Label>();

		info = new Array<Label>();
	}

	public void setResult(String[] result) {
		this.result = result;

		float multiplier = 70f;
		resultLabels = new Label[result.length];
		for (int i = 0; i < resultLabels.length; i++)
			resultLabels[i] = createLabel(result[resultLabels.length - i - 1], 0.6f, 330f, (80 + i * multiplier));
	}

	private void initLabels() {
		hps = new Array<Label>();
		for (int i = 0; i < units.size(); i++) {
			Label label = createLabel("", 0.3f, 0, 0);
			hps.add(label);
		}
		
		float koef = 6;
		String info1 = "Press RIGHT CLICK to delete unit from map";
		Label lInfo1 = createLabel(info1, 0.3f, Gdx.graphics.getWidth()/2 - info1.length() * koef, Gdx.graphics.getHeight() - 170);
		String info2 = "Press ENTER to start play";
		Label lInfo2 = createLabel(info2, 0.3f, Gdx.graphics.getWidth()/2 - info2.length() * koef, Gdx.graphics.getHeight() - 210);
	}

	public void attachLabels() {
		for (int i = 0; i < units.size(); i++) {
			hps.get(i).setPosition(Gdx.graphics.getWidth() / 2 + units.get(i).getBounds().getX() + 25,
					Gdx.graphics.getHeight() / 2 + units.get(i).getBounds().getY() + 5);
			if (units.get(i).getHp() > 0) {
				hps.get(i).setText(units.get(i).getHp());
			} else
				hps.get(i).setText("");
		}
	}

	public void setUnits(ArrayList<Unit> allies, ArrayList<Unit> enemies) {
		units = new ArrayList<Unit>();
		for (int i = 0; i < allies.size(); i++)
			units.add(allies.get(i));
		for (int i = 0; i < enemies.size(); i++)
			units.add(enemies.get(i));

		this.initLabels();
	}

	private Label createLabel(String text, float fontScale, float x, float y) {
		Label label = new Label(text, this.skin.get("default", LabelStyle.class));
		label.setPosition(x, y);
		label.setFontScale(fontScale);

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

		this.attachLabels();
	}

	public void dispose() {
		stage.dispose();
	}
}
