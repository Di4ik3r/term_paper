package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Orc extends RangeUnit {

	public Orc(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(2);
		this.setRangeAttack(4);
		this.setHp(42);
		this.setDamage(19);
		this.setIsEnemy(true);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
