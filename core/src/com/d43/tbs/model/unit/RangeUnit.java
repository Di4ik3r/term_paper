package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d43.tbs.control.MapPlaying;

public abstract class RangeUnit extends Unit{

	
	
	public RangeUnit(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
	}
	
	@Override
	public void attack(Unit unit) {
		if (this.attack.getDeltaPosition() != null)
			this.getBounds().setPosition(
					this.cell.getBounds().getX() + this.attack.getDeltaPosition().x + this.cell.getSize().width / 2
							- this.getSize().width / 2,
					this.cell.getBounds().getY() + this.attack.getDeltaPosition().y + this.cell.getSize().height / 2
							- this.cell.getSize().height / 4);
		this.current = this.attack;
		unit.damage(this.damage);
	}

}
