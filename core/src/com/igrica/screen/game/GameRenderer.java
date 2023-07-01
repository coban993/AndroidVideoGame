package com.igrica.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.igrica.assets.AssetDescriptors;
import com.igrica.assets.RegionNames;
import com.igrica.config.GameConfig;
import com.igrica.entity.Background;
import com.igrica.entity.Obstacle;
import com.igrica.entity.Player;
import com.igrica.util.GdxUtils;
import com.igrica.util.ViewPortUtils;
import com.igrica.util.debug.DebugCameraControler;

public class GameRenderer implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewPort;

    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private DebugCameraControler debugCameraControler;
    private final GameController controller;

    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private TextureRegion playerTexture;
    private TextureRegion obstacleTexture;
    private TextureRegion backgroundTexture;

    public GameRenderer(SpriteBatch batch,AssetManager assetManager, GameController controller) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    private void init(){

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewPort = new FitViewport(GameConfig.HUD_WIDHT, GameConfig.HUD_HEIGHT, hudCamera);

        font = assetManager.get(AssetDescriptors.FONT);

        debugCameraControler = new DebugCameraControler();
        debugCameraControler.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        //izvlacenje slika za prepreke, igraca i pozadinu
        TextureAtlas gamePlayAtlas =  assetManager.get(AssetDescriptors.GAME_PLAY);
        playerTexture = gamePlayAtlas.findRegion(RegionNames.PLAYER);
        obstacleTexture = gamePlayAtlas.findRegion(RegionNames.OBSTACLE);
        backgroundTexture = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
    }

    //==PUBLIC==
    public void render(float delta){

        debugCameraControler.handleDebugInput(delta);
        debugCameraControler.applyTo(camera);

        //prveravanje da li je dotaknut ekran telefon tj implementacija
        //da radi i na tac skrin
        if(Gdx.input.isTouched() && !controller.isGameOver()){
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            Player player = controller.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x, 0,
                    GameConfig.WORLD_WIDTH - player.getWidth()
            );
            player.setX(worldTouch.x);
        }

        GdxUtils.clearScreen();

        renderGamePlay();

        renderUi();

        renderDebug();

    }
    //azuriranje
    public void resize(int width, int height) {
        viewport.update(width, height,true);
        hudViewPort.update(width,height, true);
        ViewPortUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    //==PRIVATE==
    private void renderGamePlay(){
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Background background = controller.getBackground();
        batch.draw(backgroundTexture,
                background.getX(), background.getY(),
                background.getWidth(), background.getHeight()
        );

        Player player = controller.getPlayer();
        batch.draw(playerTexture,
                player.getX(), player.getY(),
                player.getWidth(), player.getHeight()
        );

        for (Obstacle obstacle : controller.getObstacles()){
            batch.draw(obstacleTexture,
                    obstacle.getX(), obstacle.getY(),
                    obstacle.getWidth(), obstacle.getHeight()
            );
        }

        batch.end();
    }

    //ui = user interface
    private void renderUi(){
        hudViewPort.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);

        font.draw(batch, livesText,
                20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        String scoreText = "SCORE: " + controller.getDisplayScore();
        layout.setText(font, scoreText);

        font.draw(batch, scoreText,
                GameConfig.HUD_WIDHT - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        batch.end();
    }

    private void renderDebug(){
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewPortUtils.drawGrid(viewport, renderer);
    }

    //crtanje
    public void drawDebug(){
        Player player = controller.getPlayer();
        player.drawDebug(renderer);

        Array<Obstacle> obstacles = controller.getObstacles();

        for(Obstacle obstacle : obstacles){
            obstacle.drawDebug(renderer);
        }
    }


}
