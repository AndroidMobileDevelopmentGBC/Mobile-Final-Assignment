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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.iansmathew.dragblocks.shapes.BaseShape;
import com.iansmathew.dragblocks.slots.BaseSlot;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DragBlocks extends ApplicationAdapter {
    //Statics
    private static final int NUM_SHAPES = 3;
    private static final int NUM_SLOTS = 15;

    //World objects
    private OrthographicCamera camera;
    private Viewport viewport;
    private InputHandler inputHandler;
    private SpriteBatch batch;

    //Game Objects
	private BaseShape[] shapes;
	private BaseSlot[] slots;

	//Getters
    public Viewport getViewport() {
        return viewport;
    }
    public BaseShape[] getShapes() {
        return shapes;
    }

	@Override
	public void create () {
	    //Initialize members
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply(true);
        inputHandler = new InputHandler(this);

        //Initialize drawables
	    batch = new SpriteBatch();
	    shapes = new BaseShape[NUM_SHAPES];
	    slots = new BaseSlot[NUM_SLOTS];

        createSlots();
	    createShapes();

        Gdx.input.setInputProcessor(inputHandler); //Let GDX know that this class handles inputs

        //TODO: Run update on its own thread within fixed time steps
    }

    public void update(float deltaTime)
    {
        //Update the game objects
        for (BaseSlot slot : slots)
            slot.update(deltaTime);
        for (BaseShape shape : shapes)
            shape.update(deltaTime);

//        for (int shapeIndex = 0; shapeIndex < shapes.length; shapeIndex++)
//        {
//            for (int slotIndex = 0; slotIndex < slots.length; slotIndex++)
//            {
//                if (shapes[shapeIndex].getSprite().getBoundingRectangle().contains(slots[slotIndex].getPosition()))
//                {
//                    shapeMouseTable.get(0).shapeToMove = null;
//                    shapeMouseTable.get(0).isTouched = false;
//                    shapes[shapeIndex].resetPosition();
//                }
//            }
//        }
    }

    @Override
	public void render () {

        update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        //draw game objects
        for (BaseSlot slot : slots)
            slot.draw(batch);

		for (BaseShape shape : shapes)
            shape.draw(batch);

		batch.end();

	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void createSlots()
    {
        int count = 1;
        float gapSize = 256;
        for (int i =0; i < NUM_SLOTS; i++)
        {
            Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / NUM_SHAPES + 1f, Gdx.graphics.getHeight() / 2.f);
            spawnPos.x += Math.floor((Math.random() * (3))) * gapSize;
            spawnPos.y = Gdx.graphics.getHeight() + (float)Math.floor((Math.random() * (50))) ;

            switch (count % 3)
            {
                case 0:
                    slots[i] = new BaseSlot(this, spawnPos, ShapeType.Square);
                    break;
                case 1:
                    slots[i] = new BaseSlot(this, spawnPos, ShapeType.Triangle);
                    break;
                case 2:
                    slots[i] = new BaseSlot(this, spawnPos, ShapeType.Circle);
                    break;
            }
            count++;
        }
    }

    private void createShapes()
    {
        //Initialize shapes
        float gapSize = 256;
        for (int i = 0; i < NUM_SHAPES; i++)
        {
            Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / NUM_SHAPES + 1f, Gdx.graphics.getHeight() / 2.f);
            spawnPos.y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() *  90) / 100.f; //set sprites to 10% from the lower part of screen
            spawnPos.x += i * gapSize;

            String imageFileName = "shapes/shape" + i+1 + ".png";

            switch (i)
            {
                case 0:
                    shapes[i] = new BaseShape(this, spawnPos, ShapeType.Square);
                    break;
                case 1:
                    shapes[i] = new BaseShape(this, spawnPos, ShapeType.Triangle);
                    break;
                case 2:
                    shapes[i] = new BaseShape(this, spawnPos, ShapeType.Circle);
                    break;
            }
        }
    }
	
	@Override
	public void dispose () {
		batch.dispose();
        for (BaseShape shape : shapes)
            shape.dispose();

        for (BaseSlot slot : slots)
            slot.dispose();
	}
}
