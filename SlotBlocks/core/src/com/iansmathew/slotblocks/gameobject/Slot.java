package com.iansmathew.slotblocks.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.iansmathew.slotblocks.ShapeType;
import com.iansmathew.slotblocks.SlotBlocks;

/**
 * Created by iansm on 2018-04-17.
 */

public class Slot {
    private SlotBlocks game;
    private float movementSpeed = -300.f;

    private Vector2 initalPos;
    private Vector2 position;
    private Vector2 velocity;

    private Texture texture;
    private Sprite sprite;
    private ShapeType shapeType;

    public Sprite getSprite() {
        return sprite;
    }
    public ShapeType getShapeType() {
        return shapeType;
    }

    public Slot(SlotBlocks _game, Vector2 _spawnPos, ShapeType _shapeType)
    {
        game = _game;
        position = new Vector2();
        initalPos = _spawnPos;
        velocity = new Vector2(0, 0);
        shapeType = _shapeType;

        createSprite();
        setPosition(initalPos.x, initalPos.y);
    }

    private void createSprite()
    {
        switch (shapeType)
        {
            case Square:
                texture = new Texture(Gdx.files.internal("slots/blackSquare.png"));
                break;
            case Circle:
                texture = new Texture(Gdx.files.internal("slots/blackCircle.png"));
                break;
            case Triangle:
                texture = new Texture(Gdx.files.internal("slots/blackTriangle.png"));
        }
        sprite = new Sprite(texture);
        sprite.setOriginCenter();
    }

    public void setPosition(float x, float y)
    {
        position.set(x, y);
        sprite.setOriginBasedPosition(position.x, position.y);
    }

    private void move(float deltaTime)
    {
        velocity.set(0, 0);
        velocity.add(0, movementSpeed);
        velocity.scl(deltaTime);
        position.add(velocity);
        velocity.scl(1/deltaTime);
    }

    public void randomizeNewSpawn()
    {
        Vector2 newSpawn = new Vector2();
        newSpawn.x = Gdx.graphics.getWidth() / game.NUM_SHAPES + 1f;
        newSpawn.x += Math.floor((Math.random() * (3))) * 256;
        newSpawn.y = (float)(Gdx.graphics.getHeight() + Math.floor((Math.random() * (5)) * 256));

        setPosition(newSpawn.x, newSpawn.y);
    }

    public void render(SpriteBatch spriteBatch)
    {
        sprite.draw(spriteBatch);
    }

    public void update(float deltaTime)
    {
        setPosition(position.x, position.y);
        move(deltaTime);

        if (position.y < (Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() *  80) / 100.f))
        {
            randomizeNewSpawn();
        }
    }

    public void dispose()
    {
        texture.dispose();
    }
}
