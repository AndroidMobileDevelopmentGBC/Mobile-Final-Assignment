package com.iansmathew.slotblocks.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by iansm on 2018-04-19.
 */

public class StateMachine {
    private Stack<State> states;
    public int getStateLength() {return states.size(); }

    public StateMachine()
    {
        states = new Stack<State>();
    }


    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void resize(int width, int height)
    {
        states.peek().resize(width, height);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
