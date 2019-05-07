package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.utils.Animation;

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
	
	public Unit clone() {
		return new Knight(this.getTextureRegion(), this.getBounds().getX(), this.getBounds().getY(), this.getBounds().getBoundingRectangle().width, this.getBounds().getBoundingRectangle().height);
	}
	
	@Override
	public void initAnimations(TextureAtlas atlas) {
		Array<TextureRegion> regions = new Array<TextureRegion>();
//		for(int i = 1; i <= 3; i++)
//			regions.add(atlas.findRegion("archer_idle_" + Integer.toString(i)));
		regions.add(atlas.findRegion("knight_idle", 1));
		regions.add(atlas.findRegion("knight_idle", 2));
		regions.add(atlas.findRegion("knight_idle", 3));
		regions.add(atlas.findRegion("knight_idle", 4));
		regions.add(atlas.findRegion("knight_idle", 5));
//		regions.add(atlas.findRegion("knight"));
//		regions.add(atlas.findRegion("zombie"));
		

		this.idle = new Animation(regions, this, 1.5f, true);
		this.idle.setSize(35, 73);
		this.current = idle;
	}

}
