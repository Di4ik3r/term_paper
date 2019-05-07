package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.utils.Animation;

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
	
	public Unit clone() {
		return new Orc(this.getTextureRegion(), this.getBounds().getX(), this.getBounds().getY(), this.getBounds().getBoundingRectangle().width, this.getBounds().getBoundingRectangle().height);
	}

	@Override
	public void initAnimations(TextureAtlas atlas) {
		Array<TextureRegion> regions = new Array<TextureRegion>();
		regions.add(atlas.findRegion("orc"));

		this.idle = new Animation(regions, this, 2f, true);
		this.idle.setSize(36, 67);
		this.current = idle;
	}
}
