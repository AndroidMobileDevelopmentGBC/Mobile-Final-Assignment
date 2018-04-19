package com.iansmathew.slotblocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.iansmathew.slotblocks.states.MenuState;
import com.iansmathew.slotblocks.states.StateMachine;

public class SlotBlocks extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final int NUM_SHAPES = 3;
	public static final int NUM_SLOTS = 9;

	public static final String TITLE = "Flappy Bird";

	private StateMachine stateMachine;
	private SpriteBatch batch;

	@Override
	public void create() {
		stateMachine = new StateMachine();
		batch = new SpriteBatch();

		Gdx.gl.glClearColor(1,0,0,1);
		stateMachine.push(new MenuState(stateMachine));
	}

	@Override
	public void resize(int width, int height) {
		stateMachine.resize(width, height);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateMachine.update(Gdx.graphics.getDeltaTime());
		stateMachine.render(batch);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
