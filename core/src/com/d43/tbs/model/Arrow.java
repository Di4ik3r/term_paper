package com.d43.tbs.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.d43.tbs.model.unit.Unit;

public class Arrow extends GameObject {

	protected Vector2 from, to;
	protected boolean moving;
	
	private float x, y, speed;
	
	private Unit unitTo;

	public Arrow(TextureRegion textureRegion, Vector2 from, Vector2 to, float width, float height) {
		super(textureRegion, from.x, from.y, width, height);
		this.from = from;
		this.to = to;
		this.moving = true;
		x = from.x;
		y = from.y;
		speed = 17;
	}
	
	public void setUnit(Unit unitTo) {
		this.unitTo = unitTo;
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		if(this.moving) {
			float a = (float)(Math.atan2(this.to.y - this.from.y, this.to.x - this.from.x) * 180 / Math.PI);
			a -= 90;
			float angle = (a < 0) ? a + 360 : a;
			this.bounds.setRotation(angle);
			
			this.bounds.setPosition(this.x, this.y);
			
			this.x += (float) ((to.x - from.x)/Math.sqrt(((to.x - from.x) * (to.x - from.x)) + ((to.y - from.y) * (to.y - from.y)))) * speed;
			this.y += (float) ((to.y - from.y)/Math.sqrt(((to.y - from.y) * (to.y - from.y)) + ((to.x - from.x) * (to.x - from.x)))) * speed;
			
				
			if(this.unitTo.getBounds().contains(this.x, this.y)) {
				moving = false;
			}
			
//			this.x += speed;
//			this.y = ((this.to.y - this.from.x) * (this.x - this.from.x)) / (this.to.x - this.from.x);
		}
		else {
			this.bounds.setPosition(1366, 768);
		}
	}
}
