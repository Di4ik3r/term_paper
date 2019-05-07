package com.d43.tbs.model.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.ArcherController;
import com.d43.tbs.utils.Animation;

public class Archer extends RangeUnit {

	private ArcherController controller;
	
	public Archer(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(2);
		this.setRangeAttack(4);
		this.setHp(33);
		this.setDamage(11);
	}
	
	@Override
	public void initAnimations(TextureAtlas atlas) {
		Array<TextureRegion> regions = new Array<TextureRegion>();
//		for(int i = 1; i <= 3; i++)
//			regions.add(atlas.findRegion("archer_idle_" + Integer.toString(i)));
		regions.add(atlas.findRegion("archer_idle", 1));
		regions.add(atlas.findRegion("archer_idle", 2));
		regions.add(atlas.findRegion("archer_idle", 3));
//		regions.add(atlas.findRegion("knight"));
//		regions.add(atlas.findRegion("zombie"));
		

		this.idle = new Animation(regions, 2f);
		this.current = idle;
	}

	@Override
	public Unit clone() {
		return new Archer(this.getTextureRegion(), this.getBounds().getX(), this.getBounds().getY(), this.getBounds().getBoundingRectangle().width, this.getBounds().getBoundingRectangle().height);
	}
}
