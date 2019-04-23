package com.d43.tbs.model.unit;

import java.io.Console;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d43.tbs.control.ArcherController;
import com.d43.tbs.utils.MapChecker;

public class Archer extends RangeUnit {

	private ArcherController controller;
	
	public Archer(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(2);
		this.setRangeAttack(8);
		this.setHp(33);
		this.setDamage(18);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
//		this.controller.handle();
	}
}
