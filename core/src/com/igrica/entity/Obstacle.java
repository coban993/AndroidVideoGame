package com.igrica.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.igrica.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable{
    //poolable se koristi da se svi objetti koji se uniste recikliraju
    //da bi se stedela memorija
    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;

    public Obstacle() {
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    public void update(){

        setY(getY() - ySpeed);

    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();

        //intersektor je bibliotek sa metodama za proveravanje da li
        //se dva presecaju
        boolean overlaps =  Intersector.overlaps(playerBounds, this.getBounds());

        //kao if gde je hit = true
        hit = overlaps;
        return overlaps;
    }

    public boolean isNotHit(){ return !hit; }

    public void setYSpeed(float ySpeed){
        this.ySpeed = ySpeed;
    }

    @Override
    public void reset() {
        hit = false;
    }
}
