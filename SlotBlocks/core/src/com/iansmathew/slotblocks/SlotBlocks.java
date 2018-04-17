package com.iansmathew.slotblocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.iansmathew.slotblocks.gameobject.Shape;
import com.iansmathew.slotblocks.gameobject.Slot;

public class SlotBlocks extends ApplicationAdapter {
	//World Objects
	private OrthographicCamera camera;
	private Viewport viewport;
	private InputHandler inputHandler;
	private SpriteBatch batch;

	//GameObjects
	public final int NUM_SHAPES = 3;
	public final int NUM_SLOTS = 9;
	private Shape[] shapes;
	private Slot[] slots;

	private BitmapFont scoreText;
	private int score = 0;

	//Public getters
	public Viewport getViewport() {
		return viewport;
	}
	public Shape[] getShapeContainer() {
		return shapes;
	}
	public Slot[] getSlotContainer() {
		return slots;
	}

	@Override
	public void create () {
		//Initialize members
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		viewport.apply(true);
		inputHandler = new InputHandler(this);
		batch = new SpriteBatch();

		shapes = new Shape[NUM_SHAPES];
		slots = new Slot[NUM_SLOTS];
		createShapes();
		createSlots();

		scoreText = new BitmapFont();
		scoreText.getData().setScale(5.0f);

		Gdx.input.setInputProcessor(inputHandler);
	}

	public void update(float deltaTime)
	{
		for (Shape shape : shapes)
			shape.update(deltaTime);

		for (Slot slot : slots)
			slot.update(deltaTime);

		checkCollision();
	}

	@Override
	public void render () {
		update (Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for (Shape shape: shapes)
			shape.render(batch);

		for (Slot slot : slots)
			slot.render(batch);

		updateScore();

		batch.end();
	}

	public void checkCollision()
	{
		for (Shape shape : shapes)
		{
			for (Slot slot : slots)
			{
				if (shape.getSprite().getBoundingRectangle().overlaps(slot.getSprite().getBoundingRectangle()))
				{
					if (shape.getShapeType() == slot.getShapeType())
					{
						score++;
						slot.randomizeNewSpawn();
						inputHandler.touchUp(0, 0, 0, 0);
					}
				}
			}
		}
	}

	private void updateScore()
	{
		scoreText.draw(batch, "Score: " + Integer.toString(score),
						0,
						Gdx.graphics.getHeight() - 10);
	}

	private void createShapes()
	{
		float gapSize = 256;
		for (int i = 0; i < NUM_SHAPES; i++)
		{
			Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / NUM_SHAPES + 1f, Gdx.graphics.getHeight() / 2.f);
			spawnPos.y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() *  90) / 100.f; //set sprites to 10% from the lower part of screen
			spawnPos.x += i * gapSize;

			switch (i)
			{
				case 0:
					shapes[i] = new Shape(this, spawnPos, ShapeType.Square);
					break;
				case 1:
					shapes[i] = new Shape(this, spawnPos, ShapeType.Triangle);
					break;
				case 2:
					shapes[i] = new Shape(this, spawnPos, ShapeType.Circle);
					break;
			}
		}
	}

	private void createSlots()
	{
		int count = 1;
		float gapSize = 256;
		float heightGap = 300;
		float lastSpawnY = Gdx.graphics.getHeight();;
		for (int i =0; i < NUM_SLOTS; i++)
		{
			Vector2 spawnPos = new Vector2(Gdx.graphics.getWidth() / NUM_SHAPES + 1f, Gdx.graphics.getHeight() / 2.f);
			spawnPos.x += Math.floor((Math.random() * (3))) * gapSize;
			spawnPos.y = lastSpawnY + heightGap;
			lastSpawnY = spawnPos.y;

			switch (count % 3)
			{
				case 0:
					slots[i] = new Slot(this, spawnPos, ShapeType.Square);
					break;
				case 1:
					slots[i] = new Slot(this, spawnPos, ShapeType.Triangle);
					break;
				case 2:
					slots[i] = new Slot(this, spawnPos, ShapeType.Circle);
					break;
			}
			count++;
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose () {
		batch.dispose();
		for (Shape shape : shapes)
			shape.dispose();

		for (Slot slot : slots)
			slot.dispose();
	}
}
