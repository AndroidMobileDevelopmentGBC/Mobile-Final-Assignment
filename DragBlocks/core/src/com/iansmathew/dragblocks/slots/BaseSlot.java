package com.iansmathew.dragblocks.slots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.iansmathew.dragblocks.DragBlocks;
import com.iansmathew.dragblocks.ShapeType;

/**
 * Created by iansm on 2018-04-15.
 */

public class BaseSlot {
    private DragBlocks game;
    private float SPEED = 1.0f;

    //Member properties
    private Texture texture;
    private Sprite sprite;
    private Vector2 startPos;
    private ShapeType shapeType;

    private float rowPos;

    public BaseSlot(DragBlocks _game, Vector2 _spawnPos, ShapeType _type)
    {
        game = _game;
        shapeType = _type;
        startPos = _spawnPos;
        rowPos = _spawnPos.x;
        setTexture();
        sprite = new Sprite(this.texture);

        setPosition(startPos.x, startPos.y);;
    }

    private void setTexture()
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

    }

    public void update(float delaTime)
    {
        Vector2 currentPos = new Vector2(sprite.getX(), sprite.getY());
        currentPos.y -= SPEED * delaTime;
        setPosition(rowPos, currentPos.y);
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }


    public void dispose()
    {
        texture.dispose();
    }
}
