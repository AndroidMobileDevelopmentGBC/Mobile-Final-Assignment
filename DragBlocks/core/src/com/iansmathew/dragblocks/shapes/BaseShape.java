package com.iansmathew.dragblocks.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.iansmathew.dragblocks.DragBlocks;
import com.iansmathew.dragblocks.ShapeType;

/**
 * Created by iansm on 2018-04-14.
 */

public class BaseShape {
    private DragBlocks game;

    //Member properties
    private Texture texture;
    private Sprite sprite;
    private Vector2 startPos;
    private ShapeType shapeType;

    public BaseShape(DragBlocks _game, Vector2 _spawnPos, ShapeType _type)
    {
        game = _game;
        shapeType = _type;
        startPos = _spawnPos;
        setTexture();
        sprite = new Sprite(this.texture);
        setPosition(startPos.x, startPos.y);;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    private void setTexture()
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
    }

    public void setPosition(float _x, float _y)
    {
        float x = _x - texture.getWidth() / 2.f;
        float y = _y - texture.getHeight() / 2.f;

        sprite.setPosition(x, y);
    }

    public void resetPosition()
    {
        setPosition(startPos.x, startPos.y);
    }

    public void onMouseUp()
    {
        //TODO: Check if overlapping with slots
            //Increase score
            //Wrong slot. Reset game
        resetPosition();
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public void update(float deltaTime)
    {

    }

    public void dispose()
    {
        texture.dispose();
    }
}
