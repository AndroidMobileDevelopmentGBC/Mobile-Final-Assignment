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

public class Shape {
    //Game Ref
    private SlotBlocks game;

    private float movementSpeed = 0.f;

    private final Vector2 initalPos;
    private Vector2 position;
    private Vector2 velocity;

    private Texture texture;
    private Sprite sprite;
    private ShapeType shapeType;

    //Getters
    public Sprite getSprite()
    {
        return sprite;
    }

    public Shape(SlotBlocks _game, Vector2 _spawnPos, ShapeType _shapeType)
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
                texture = new Texture(Gdx.files.internal("shapes/blueSquare.png"));
                break;
            case Circle:
                texture = new Texture(Gdx.files.internal("shapes/blueCircle.png"));
                break;
            case Triangle:
                texture = new Texture(Gdx.files.internal("shapes/blueTriangle.png"));
                break;
        }
        sprite = new Sprite(texture);
        sprite.setOriginCenter();
    }

    public void setPosition(float x, float y)
    {
        position.set(x, y);
        sprite.setOriginBasedPosition(position.x, position.y);
    }

    public void resetPosition()
    {
        position.set(initalPos.x, initalPos.y);
        sprite.setOriginBasedPosition(position.x, position.y);
    }


    public void render(SpriteBatch spriteBatch)
    {
        sprite.draw(spriteBatch);
    }

    public void update(float deltaTime)
    {

    }

    public void dispose()
    {
        texture.dispose();
    }
}
