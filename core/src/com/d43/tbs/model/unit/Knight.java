package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight extends MeleeUnit{

	public Knight(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(3);
		this.setRangeAttack(1);
		this.setHp(72);
		this.setDamage(19);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}

}
