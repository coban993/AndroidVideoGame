package com.igrica.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.igrica.common.GameManager;
import com.igrica.config.DifficultyLevel;
import com.igrica.config.GameConfig;
import com.igrica.entity.Background;
import com.igrica.entity.Obstacle;
import com.igrica.entity.Player;

public class GameController {

    private static final Logger log = new Logger(Viewport.class.getName(), Logger.DEBUG);

    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private Background background;
    private float obstacleTimer;
    private float scoreTimer;
    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;
    private Pool<Obstacle> obstaclePool;
    private final float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
    private final float startPlayerY = 1 - GameConfig.PLAYER_SIZE / 2f;

    public GameController() {
        init();
    }

    private void init(){

        player = new Player();

        player.setPositions(startPlayerX, startPlayerY);

        obstaclePool = Pools.get(Obstacle.class, 40);

        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
    }

    //==PUBLIC==
    //metoda u kojj pozivamo sve odgovarajuce update metode za azuriranje
    //igraca,prepreka, skora igraca itd.
    public void update(float delta){
        if(isGameOver()){
            log.debug("Game Over");
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if(isPlayerCollidingWithObstacle()){
            log.debug("Collision detected");
            lives--;

            if(isGameOver()){
                log.debug("GAME OVER!!!");
                GameManager.instance.updateHighScore(score);
            }else{
                restart();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() { return background; }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public boolean isGameOver(){
        return lives <= 0;
    }

    //==PRIVATE==
    private void restart(){
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
        player.setPositions(startPlayerX, startPlayerY);
    }

    //metoda u kojoj proveravamo da li su se igrac i prepreka dodirnuli
    private boolean isPlayerCollidingWithObstacle(){
        for (Obstacle obstacle : obstacles){
            if(obstacle.isNotHit() && obstacle.isPlayerColliding(player)){
                return true;
            }
        }

        return false;
    }

    private void updatePlayer(){

        float xSpeed = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        player.setX(player.getX() + xSpeed);

        blockPlayerFromLeavingTheWorld();
    }

    //blokiranje da ispadne sa ikrana
    private void blockPlayerFromLeavingTheWorld(){
        float playerX = MathUtils.clamp(
                player.getX(),
                0,
                GameConfig.WORLD_WIDTH - player.getWidth());

        player.setPositions(playerX, player.getY());

    }

    private void updateObstacles(float delta){

        for(Obstacle obstacle : obstacles){
            obstacle.update();
        }

        createNewObstacle(delta);

        removePassedObstacles();

    }

    //kreiranje prepreka
    private void createNewObstacle(float delta){
        obstacleTimer += delta;

        if(obstacleTimer > GameConfig.OBSTACLE_SPAWN_TIME){
            float min = 0;
            float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE ;

            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            DifficultyLevel difficultyLevel = GameManager.instance.getDifficultyLevel();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpreed());
            obstacle.setPositions(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void removePassedObstacles(){
        if(obstacles.size > 0){
            Obstacle first = obstacles.first();

            float minObstacleY = -GameConfig.OBSTACLE_SIZE;

            if(first.getY() < minObstacleY){
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    private void updateScore(float delta){
        scoreTimer += delta;

        if(scoreTimer >= GameConfig.SCORE_MAX_TIME){
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    //mnozenje delta sa 60 (60 frejmova ima) a delta je deo
    //izmedji 2 frejma sto je 1/60 pa je to 1 i ona skor raste elegantno
    private void updateDisplayScore(float delta){
        if(displayScore < score){
            displayScore = Math.min(
                    score,
                    displayScore + (int) (60 * delta)
            );
        }
    }

}
