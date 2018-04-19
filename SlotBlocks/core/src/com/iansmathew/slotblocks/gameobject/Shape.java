package com.iansmathew.slotblocks.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.iansmathew.slotblocks.ShapeType;
import com.iansmathew.slotblocks.SlotBlocks;
import com.iansmathew.slotblocks.states.PlayState;

/**
 * Created by iansm on 2018-04-17.
 */

public class Shape {
    //Game Ref
    private PlayState game;

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
    public ShapeType getShapeType() {
        return shapeType;
    }


    public Shape(PlayState _game, Vector2 _spawnPos, ShapeType _shapeType)
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
                texture = new Texture(Gdx.files.internal("shapes/redSquare.png"));
                break;
            case Circle:
                texture = new Texture(Gdx.files.internal("shapes/redCircle.png"));
                break;
            case Triangle:
                texture = new Texture(Gdx.files.internal("shapes/redTriangle.png"));
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
        if (shapeType == ShapeType.Square)
        {
            Gdx.app.log("Position", Float.toString(position.y));
        }
    }

    public void dispose()
    {
        texture.dispose();
    }
}
