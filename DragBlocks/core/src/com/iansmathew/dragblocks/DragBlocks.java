package com.iansmathew.dragblocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.iansmathew.dragblocks.shapes.BaseShape;
import com.iansmathew.dragblocks.slots.BaseSlot;

import java.util.HashMap;

public class DragBlocks extends ApplicationAdapter implements InputProcessor{
    private static final int NUM_FINGER_TOUCHES = 1;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch batch;

	private BaseShape[] shapes;
	public BaseSlot[] slots;

	private class MouseStruct
    {
        private boolean isTouched = false;
        private BaseShape shapeToMove = null;
    }

    private HashMap<Integer, MouseStruct> shapeMouseTable;

	@Override
	public void create () {
	    //Initialize members
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply(true);

        //Initialize drawables
	    batch = new SpriteBatch();
	    shapes = new BaseShape[2];
	    slots = new BaseSlot[1];

	    //Initialize shapes
	    int offsetIndex = 0;
        Texture shapeTex = new Texture("badlogic.jpg");
        float texWidth = shapeTex.getWidth();
        shapeTex.dispose();

	    for (int i = 0; i < 2; i++)
        {
            Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / 5.f, Gdx.graphics.getHeight() / 2.f);
            spawnPos.y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() *  90) / 100.f; //set sprites to 10% from the lower part of screen
            spawnPos.x += offsetIndex * texWidth;
            shapes[i] = new BaseShape(this, spawnPos, "badlogic.jpg");

            offsetIndex++;
        }

        //Initialize slots
        for (int i =0; i < 1; i++)
        {
            Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
            slots[i] = new BaseSlot(this, spawnPos, "badlogic.jpg");
        }

		//Create hashmap
        shapeMouseTable = new HashMap<Integer, MouseStruct>();
        for (int i = 0; i < NUM_FINGER_TOUCHES; i++)
        {
            shapeMouseTable.put(i, new MouseStruct());
        }

        Gdx.input.setInputProcessor(this); //Let GDX know that this class handles inputs
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
	public void render () {
	    update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

        for (BaseSlot slot : slots)
            slot.draw(batch);

		for (BaseShape shape : shapes)
            shape.draw(batch);

		batch.end();

	}

	public void update(float deltaTime)
    {
        for (int shapeIndex = 0; shapeIndex < shapes.length; shapeIndex++)
        {
            for (int slotIndex = 0; slotIndex < slots.length; slotIndex++)
            {
                if (shapes[shapeIndex].getSprite().getBoundingRectangle().contains(slots[slotIndex].getPosition()))
                {
                    shapeMouseTable.get(0).shapeToMove = null;
                    shapeMouseTable.get(0).isTouched = false;
                    shapes[shapeIndex].resetPosition();
                }
            }
        }
    }
	
	@Override
	public void dispose () {
		batch.dispose();
        for (BaseShape shape : shapes)
            shape.dispose();
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (pointer >= NUM_FINGER_TOUCHES) //making sure that only NUM_FINGER_TOUCHES ARE SUPPORTED
            return false;

	    Vector2 mouseTouchPos = viewport.unproject(new Vector2(screenX, screenY));

	    //Check if any of the shapes are under the touch
	    for (BaseShape shape : shapes)
        {
            if (shape.getSprite().getBoundingRectangle().contains(mouseTouchPos))
            {
                //Acitvate that mouse touch
                shapeMouseTable.get(pointer).isTouched = true;
                shapeMouseTable.get(pointer).shapeToMove = shape;
                break;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer >= NUM_FINGER_TOUCHES) //making sure that only NUM_FINGER_TOUCHES ARE SUPPORTED
            return false;

        if (shapeMouseTable.get(pointer).shapeToMove != null)
            shapeMouseTable.get(pointer).shapeToMove.resetPosition();

        shapeMouseTable.get(pointer).isTouched = false;
        shapeMouseTable.get(pointer).shapeToMove = null;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (pointer >= NUM_FINGER_TOUCHES) //making sure that only NUM_FINGER_TOUCHES ARE SUPPORTED
            return false;

        for (int i = 0; i < NUM_FINGER_TOUCHES; i++)
        {
            if (shapeMouseTable.get(i).isTouched)
            {
                Vector2 pos = viewport.unproject(new Vector2(screenX, screenY));
                shapeMouseTable.get(i).shapeToMove.setPosition(pos.x, pos.y);
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
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

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
