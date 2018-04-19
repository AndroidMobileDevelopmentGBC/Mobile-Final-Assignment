package com.iansmathew.slotblocks;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.iansmathew.slotblocks.gameobject.Shape;
import com.iansmathew.slotblocks.states.PlayState;

/**
 * Created by iansm on 2018-04-17.
 */

public class InputHandler implements InputProcessor{
    private static final int NUM_FINGERS_SUPPORTED = 1;

    private PlayState game;
    private Shape currentShapeToHandle;

    public InputHandler(PlayState gameRef)
    {
        this.game = gameRef;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer >= NUM_FINGERS_SUPPORTED) //if more fingers than supported, do not take action
            return false;

        Vector2 mousePosInWorld = game.getViewport().unproject(new Vector2(screenX, screenY)); //convert touch coords to world space
        //Check if touch intersects with shapes
        for (Shape shape : game.getShapeContainer())
        {
            if (shape.getSprite().getBoundingRectangle().contains(mousePosInWorld))
            {
                currentShapeToHandle = shape;
                break;
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer >= NUM_FINGERS_SUPPORTED) //if more fingers than supported, do not take action
            return false;

        if (currentShapeToHandle != null)
        {
            currentShapeToHandle.resetPosition();
        }

        currentShapeToHandle = null;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer >= NUM_FINGERS_SUPPORTED) //if more fingers than supported, do not take action
            return false;

        if (currentShapeToHandle != null)
        {
            Vector2 currentMousePosInWorld = game.getViewport().unproject(new Vector2(screenX, screenY));
            currentShapeToHandle.setPosition(currentMousePosInWorld.x, currentMousePosInWorld.y);
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
