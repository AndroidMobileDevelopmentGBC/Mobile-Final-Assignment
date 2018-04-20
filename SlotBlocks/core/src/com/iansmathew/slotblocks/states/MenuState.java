package com.iansmathew.slotblocks.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by iansm on 2018-04-19.
 */

public class MenuState extends State {

    private Texture backgroundTex;
    private Texture playTex;
    private Texture exitTex;

    private Sprite background;
    private Sprite playButton;
    private Sprite exitButton;

    public MenuState(StateMachine stateMachine) {
        super(stateMachine);
        Gdx.gl.glClearColor(1,0,0,1);
;
        backgroundTex = new Texture(Gdx.files.internal("menu/MainMenuBackground.png"));
        playTex = new Texture(Gdx.files.internal("menu/PlayButton.png"));
        exitTex = new Texture(Gdx.files.internal("menu/ExitButton.png"));

        background = new Sprite(backgroundTex);
        playButton = new Sprite(playTex);
        exitButton = new Sprite(exitTex);

        background.setScale(2.f);
        playButton.setScale(2.f);
        exitButton.setScale(2.f);

        background.setOriginCenter();
        playButton.setOriginCenter();
        exitButton.setOriginCenter();

        background.setOriginBasedPosition(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
        playButton.setOriginBasedPosition(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f - 300);
        exitButton.setOriginBasedPosition(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f - 700);
    }

    @Override
    protected void update(float dt) {
        if (Gdx.input.justTouched())
        {
            Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 mousePosInWorld = viewport.unproject(mousePos);

            if (playButton.getBoundingRectangle().contains(mousePosInWorld))
            {
                stateMachine.set(new PlayState(stateMachine));
            }
            else if (exitButton.getBoundingRectangle().contains(mousePosInWorld))
            {
                Gdx.app.exit();
            }
        }

    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.begin();
        background.draw(batch);
        playButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    protected void dispose() {
         backgroundTex.dispose();
         playTex.dispose();
         exitTex.dispose();
    }
}
