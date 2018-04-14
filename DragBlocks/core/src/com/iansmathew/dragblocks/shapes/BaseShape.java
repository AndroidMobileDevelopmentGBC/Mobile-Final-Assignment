package com.iansmathew.dragblocks.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.iansmathew.dragblocks.DragBlocks;

/**
 * Created by iansm on 2018-04-14.
 */

public class BaseShape {
    private Texture texture;

    public Sprite getSprite() {
        return sprite;
    }

    private Sprite sprite;

    public BaseShape(DragBlocks game, String _fileName)
    {
        texture = new Texture(_fileName);
        sprite = new Sprite(texture);
    }

    public void setPosition(float _x, float _y)
    {
        float x = _x - texture.getWidth() / 2.f;
        float y = _y - texture.getHeight() / 2.f;

        sprite.setPosition(x, y);
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
