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
    private float movementSpeed = -1.f;

    private Vector2 initalPos;
    private Vector2 position;
    private Vector2 velocity;

    private Texture texture;
    private Sprite sprite;
    private ShapeType shapeType;

    public Slot(SlotBlocks _game, Vector2 _spawnPos, ShapeType _shapeType)
    {
        position = initalPos = _spawnPos;
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
        velocity.add(0, movementSpeed);
        velocity.scl(deltaTime);
        position.add(velocity);
        velocity.scl(1/deltaTime);
    }

    public void render(SpriteBatch spriteBatch)
    {
        sprite.draw(spriteBatch);
    }

    public void update(float deltaTime)
    {
        setPosition(position.x, position.y);
        move(deltaTime);
    }

    public void dispose()
    {
        texture.dispose();
    }
}
