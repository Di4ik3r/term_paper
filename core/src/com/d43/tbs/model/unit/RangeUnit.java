package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d43.tbs.control.MapPlaying;

public abstract class RangeUnit extends Unit{

	public RangeUnit(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
	}

}
