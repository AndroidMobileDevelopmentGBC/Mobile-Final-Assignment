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

public class DragBlocks extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Viewport viewport;
    private InputHandler inputHandler;
    private SpriteBatch batch;

	private BaseShape[] shapes;
	private BaseSlot[] slots;

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
	    shapes = new BaseShape[2];
	    slots = new BaseSlot[1];

        createSlots();
	    createShapes();

        Gdx.input.setInputProcessor(inputHandler); //Let GDX know that this class handles inputs
	}

    public void update(float deltaTime)
    {
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
        for (int i =0; i < 1; i++)
        {
            Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
            slots[i] = new BaseSlot(this, spawnPos, ShapeType.Square);
        }
    }

    private void createShapes()
    {
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

            offsetIndex++;
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
