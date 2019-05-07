package com.d43.tbs.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Arrow extends GameObject {

	protected Vector2 from, to;
	protected boolean moving;

	public Arrow(TextureRegion textureRegion, Vector2 from, Vector2 to, float width, float height) {
		super(textureRegion, from.x, from.y, width, height);
		this.from = from;
		this.to = to;
		this.moving = true;
	}

	@Override
	public void draw(SpriteBatch batch) {
		
	}

}
