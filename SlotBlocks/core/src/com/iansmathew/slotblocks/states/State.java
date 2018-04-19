package com.iansmathew.slotblocks.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by iansm on 2018-04-19.
 */

public abstract class State {
    protected StateMachine stateMachine;
    protected OrthographicCamera camera;
    protected Viewport viewport;

    public State(StateMachine stateMachine)
    {
        this.stateMachine = stateMachine;
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply(true);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    protected abstract void resize(int width, int height);
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch batch);
    protected abstract void dispose();
}
