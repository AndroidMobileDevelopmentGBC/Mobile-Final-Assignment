package com.iansmathew.dragblocks.slots;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.iansmathew.dragblocks.DragBlocks;

/**
 * Created by iansm on 2018-04-15.
 */

public class BaseSlot {
    //Member properties
    private Texture texture;
    private Sprite sprite;

    private Vector2 startPos;

    public BaseSlot(DragBlocks game, Vector2 _spawnPos, String _fileName)
    {
        texture = new Texture(_fileName);
        sprite = new Sprite(texture);

        startPos = _spawnPos;
        setPosition(startPos.x, startPos.y);;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setPosition(float _x, float _y)
    {
        float x = _x - texture.getWidth() / 2.f;
        float y = _y - texture.getHeight() / 2.f;

        sprite.setPosition(x, y);
    }

    public Vector2 getPosition()
    {
        return new Vector2(sprite.getX(), sprite.getY());
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
