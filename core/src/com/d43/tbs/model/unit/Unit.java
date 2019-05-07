package com.d43.tbs.model.unit;

import java.awt.Dimension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.MapHandler;
import com.d43.tbs.control.MapPlaying;
import com.d43.tbs.control.UnitController;
import com.d43.tbs.model.GameObject;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.utils.Animation;
import com.d43.tbs.utils.IdCounter;

public abstract class Unit extends GameObject {

//	public static float SPEED_X = 8, SPEED_Y = 7;
	public static float SPEED_X = 9, SPEED_Y = 8;
//	public static float SPEED_X = 4, SPEED_Y = 4;
	public static Vector2 BASIC_SIZE = new Vector2(40f, 70f / 35f);

	private int id, hp, rangeAttack, rangeMovement, damage;
	private UnitController controller;
	protected Cell cell;
	private boolean isReplaceable;
	private boolean isEnemy;
	private boolean isAlive;

	private Dimension size;

	private boolean isMoving;
	private Vector2 locationToMove;

	private TextureRegion textureRegion;

	private boolean isForChoose;

	private Vector2 location;

	protected Animation current, idle, attack;
	
	protected boolean delayed;
	protected float delay, finalDelay;
	
	protected boolean markEndBotMove;

	public Unit(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);

		this.id = IdCounter.lastId++;
		this.controller = new UnitController(this.id, this.getBounds());

		this.hp = 0;
		this.rangeAttack = 0;
		this.rangeMovement = 0;
		this.damage = 0;
		this.isReplaceable = true;
		this.isAlive = true;
		this.isEnemy = false;

		this.size = new Dimension((int) width, (int) height);

		this.isMoving = false;
		this.locationToMove = null;

		this.textureRegion = textureRegion;

		this.isForChoose = false;
		
		this.delayed = false;
		this.delay = 0;
		
		this.markEndBotMove = false;
	}
	
	public void lastEnemy() {
		this.markEndBotMove = true;
	}
	
	public void setDelay(Float delay) {
		this.finalDelay = delay;
		this.delay = 0;
		this.delayed = true;
	}

	public abstract void initAnimations(TextureAtlas atlas);

	public Array<Animation> getAnimations() {
		Array<Animation> animations = new Array<Animation>();
		animations.add(current);
		animations.add(idle);
		animations.add(attack);

		return animations;
	}

	public void setAttack() {
		this.current = this.attack;
	}

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

	public void setIdle() {
		this.getBounds().setPosition(
				this.cell.getBounds().getX() + this.cell.getSize().width / 2 - this.getSize().width / 2,
				this.cell.getBounds().getY() + this.cell.getSize().height / 2 - this.cell.getSize().height / 4);
		this.idle.refresh();
		this.current = idle;
	}

	public boolean equals(Unit unit) {
		if (this.getClass() == unit.getClass())
			return true;
		return false;
	}

	public void changeSpeed(float x, float y) {
		this.SPEED_X = x;
		this.SPEED_Y = y;
	}

	public boolean isForChoose() {
		return this.isForChoose;
	}

	public void setForChoose(boolean isForChoose) {
		this.isForChoose = isForChoose;
	}

	public TextureRegion getTextureRegion() {
		return this.textureRegion;
	}

	public abstract Unit clone();

	public void damage(int damage) {
		this.hp -= damage;
		if (hp < 1)
			this.isAlive = false;
	}

	public boolean isEnemy() {
		return this.isEnemy;
	}

	public void setIsEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

	public boolean isReplaceable() {
		return this.isReplaceable;
	}

	public void setReplaceability(boolean isReplaceable) {
		this.isReplaceable = isReplaceable;
	}

	public void setUnitToControl() {
		this.controller.setUnit(this);
	}

	public void setMapHandler(MapHandler mapHandler) {
		this.controller.setMapHandler(mapHandler);
	}

	public void pickedUp() {
		controller.pickedUp();
	}

	public void setCell(Cell cell) {
//		if(this.previousLocation == null)
//			this.previousLocation = new Vector2(cell.locationForUnit().x, cell.locationForUnit().y);
		if (this.cell != null)
			this.cell.setUnit(null);
		this.cell = cell;
		this.setUnitToControl();
		this.controller.setCell(cell);
		this.cell.setUnit(this);

		this.moveTo(new Vector2(this.cell.getBounds().getX() + this.cell.getSize().width / 2 - this.getSize().width / 2,
				this.cell.getBounds().getY() + this.cell.getSize().height / 2 - this.cell.getSize().height / 4));
	}

	public Cell getCell() {
		return cell;
	}

	public int getId() {
		return id;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getRangeAttack() {
		return this.rangeAttack;
	}

	public void setRangeAttack(int range) {
		this.rangeAttack = range;
	}

	public int getRangeMovement() {
		return this.rangeMovement;
	}

	public void setRangeMovement(int range) {
		this.rangeMovement = range;
	}

	public Dimension getSize() {
		return this.size;
	}

	public boolean isMoving() {
		return this.isMoving;
	}

	public void moveTo(Vector2 locationToMove) {
		this.locationToMove = locationToMove;

		this.isMoving = true;
	}

	public Vector2 getLocation() {
		return this.location;
	}

	public void setLocation(float x, float y) {
		this.location = new Vector2(x, y);
	}

	public void draw(SpriteBatch batch, float delta) {
		this.current.update(delta);
		this.changeTextureRegion(this.current.getFrame());
		this.getObject().setSize(this.current.getSize().x, this.current.getSize().y);

		super.draw(batch);

		if(this.delayed) {
			this.delay += delta;
			if(this.finalDelay <= this.delay) {
				this.delayed = false;
				this.delay = 0;
			}
			else return;
		}
		
		if(this.markEndBotMove == true) {
			this.markEndBotMove = false;
			((MapPlaying)this.controller.getMapHandler()).makeUnitsRaplaceable();
		}
		
		controller.handle();
//		Gdx.app.log("log",
//				"(" + Float.toString(this.previousLocation.x) + ", " + Float.toString(this.previousLocation.y) + ") : ("
//						+ Float.toString(this.locationToMove.x) + ", " + Float.toString(this.locationToMove.y) + ");");
		if (this.isMoving) {
			if (this.cell.getBounds().contains(
					this.getBounds().getX() + this.getBounds().getBoundingRectangle().width / 2,
					this.getBounds().getY() + this.getBounds().getBoundingRectangle().height / 4)) {
//			if (this.cell.getBounds().contains(this.getBounds().getX(), this.getBounds().getY())) {
				this.getBounds().setPosition(this.locationToMove.x, this.locationToMove.y);
				this.isMoving = false;
				this.locationToMove = null;
			} else {
				float speedX = this.getBounds().getX() < this.locationToMove.x ? Unit.SPEED_X : -Unit.SPEED_X;
				float speedY = this.getBounds().getY() < this.locationToMove.y ? Unit.SPEED_Y : -Unit.SPEED_Y;

				this.getBounds().setPosition(this.getBounds().getX() + speedX, this.getBounds().getY() + speedY);

				if (this.locationToMove.x - 10 < this.getBounds().getX()
						&& this.getBounds().getX() < this.locationToMove.x + 10)
					this.getBounds().setPosition(this.locationToMove.x, this.getBounds().getY());
				if (this.locationToMove.y - 10 < this.getBounds().getY()
						&& this.getBounds().getY() < this.locationToMove.y + 10)
					this.getBounds().setPosition(this.getBounds().getX(), this.locationToMove.y);
			}
//			else if (this.previousLocation.x < this.locationToMove.x) {
//				this.getBounds().setPosition(this.getBounds().getX() + Unit.SPEED_X,
//						this.straight(this.getBounds().getX() + Unit.SPEED_X));
//			} else if (this.previousLocation.x > this.locationToMove.x) {
//				this.getBounds().setPosition(this.getBounds().getX() - Unit.SPEED_X,
//						this.straight(this.getBounds().getX() - Unit.SPEED_X));
//			}

//			else if (this.getBounds().getX() < this.locationToMove.x) {
//				if (this.getBounds().getY() < this.locationToMove.y)
//					this.getBounds().setPosition(this.getBounds().getX() + Unit.SPEED_X,
//							this.getBounds().getY() + Unit.SPEED_Y);
//				else if (this.getBounds().getY() > this.locationToMove.y)
//					this.getBounds().setPosition(this.getBounds().getX() + Unit.SPEED_X,
//							this.getBounds().getY() - Unit.SPEED_Y);
//				else
//					this.getBounds().setPosition(this.getBounds().getX() + Unit.SPEED_X, this.getBounds().getY());
//			} else if (this.getBounds().getX() > this.locationToMove.x) {
//				if (this.getBounds().getY() < this.locationToMove.y)
//					this.getBounds().setPosition(this.getBounds().getX() - Unit.SPEED_X,
//							this.getBounds().getY() + Unit.SPEED_Y);
//				else if (this.getBounds().getY() > this.locationToMove.y)
//					this.getBounds().setPosition(this.getBounds().getX() - Unit.SPEED_X,
//							this.getBounds().getY() - Unit.SPEED_Y);
//				else
//					this.getBounds().setPosition(this.getBounds().getX() - Unit.SPEED_X, this.getBounds().getY());
//			} 
		}
//		Gdx.app.log("log", Boolean.toString(this.isMoving));
	}
}
