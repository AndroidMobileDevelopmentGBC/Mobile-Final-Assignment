package com.iansmathew.slotblocks.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.iansmathew.slotblocks.InputHandler;
import com.iansmathew.slotblocks.ShapeType;
import com.iansmathew.slotblocks.SlotBlocks;
import com.iansmathew.slotblocks.gameobject.Shape;
import com.iansmathew.slotblocks.gameobject.Slot;

/**
 * Created by iansm on 2018-04-19.
 */

public class PlayState extends State {
    //World Objects
    private InputHandler inputHandler;

    //GameObjects
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

    public PlayState(StateMachine stateMachine) {
        super(stateMachine);
        Gdx.gl.glClearColor(1,1,0,1);

        //Initialize members
        inputHandler = new InputHandler(this);
        shapes = new Shape[SlotBlocks.NUM_SHAPES];
        slots = new Slot[SlotBlocks.NUM_SLOTS];

        createShapes();
        createSlots();

        scoreText = new BitmapFont();
        scoreText.getData().setScale(5.0f);

        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    protected void update(float dt) {
        for (Shape shape : shapes)
            shape.update(dt);

        for (Slot slot : slots)
            slot.update(dt);

        checkCollision();

        Slot.movementSpeed -= 0.05f;
    }

    @Override
    protected void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (Shape shape: shapes)
            shape.render(batch);

        for (Slot slot : slots)
            slot.render(batch);

        updateScore(batch);
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

    private void updateScore(SpriteBatch batch)
    {
        scoreText.draw(batch, "Score: " + Integer.toString(score),
                0,
                Gdx.graphics.getHeight() - 10);
    }

    private void createShapes()
    {
        float gapSize = 256;
        for (int i = 0; i < SlotBlocks.NUM_SHAPES; i++)
        {
            Vector2 spawnPos = new Vector2();
            spawnPos.y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() *  90) / 100.f; //set sprites to 10% from the lower part of screen
            //spawnPos.x = (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() *  80.f) / 100.f);

            switch (i)
            {
                case 0:
                    spawnPos.x = (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() *  80.f) / 100.f);
                    shapes[i] = new Shape(this, spawnPos, ShapeType.Square);
                    break;
                case 1:
                    spawnPos.x = (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() *  50.f) / 100.f);
                    shapes[i] = new Shape(this, spawnPos, ShapeType.Triangle);
                    break;
                case 2:
                    spawnPos.x = (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() *  20.f) / 100.f);
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

        int[] spawnPercents = {20, 40, 60, 80};

        for (int i =0; i < SlotBlocks.NUM_SLOTS; i++)
        {
            Vector2 spawnPos = new Vector2();

            int randIndex = MathUtils.random(spawnPercents.length - 1);
            spawnPos.x = (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() *  spawnPercents[randIndex]) / 100.f);

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
    protected void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    protected void dispose() {
        for (Shape shape : shapes)
            shape.dispose();

        for (Slot slot : slots)
            slot.dispose();
    }
}
