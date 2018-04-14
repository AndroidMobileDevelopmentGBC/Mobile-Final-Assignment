package com.iansmathew.dragblocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.iansmathew.dragblocks.shapes.BaseShape;

import java.util.HashMap;

public class DragBlocks extends ApplicationAdapter implements InputProcessor{
    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch batch;

	private BaseShape[] shapes;

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

	    shapes[0] = new BaseShape(this, "badlogic.jpg");
	    shapes[1] = new BaseShape(this, "badlogic.jpg");
		//Set shapes to middle of screen
		shapes[0].setPosition(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
		shapes[1].setPosition(shapes[0].getSprite().getX() - shapes[1].getSprite().getTexture().getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);

		//Create hashmap
        shapeMouseTable = new HashMap<Integer, MouseStruct>();
        for (int i = 0; i < 2; i++)
        {
            shapeMouseTable.put(i, new MouseStruct());
        }

        Gdx.input.setInputProcessor(this);
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (BaseShape shape : shapes)
            shape.draw(batch);

		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
        for (BaseShape shape : shapes)
            shape.dispose();
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

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
        shapeMouseTable.get(pointer).isTouched = false;
        shapeMouseTable.get(pointer).shapeToMove = null;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = 0; i < shapeMouseTable.size(); i++)
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
