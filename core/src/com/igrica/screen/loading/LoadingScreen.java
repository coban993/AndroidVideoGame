package com.igrica.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.igrica.GameG;
import com.igrica.assets.AssetDescriptors;
import com.igrica.config.GameConfig;
import com.igrica.entity.Obstacle;
import com.igrica.screen.game.GameScreen;
import com.igrica.screen.menu.MenuScreen;
import com.igrica.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDHT / 2f;
    private static final float PROGRESS_BAR_HEIGHT = 60;

    private OrthographicCamera camera;
    private Viewport viewPort;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean scangeScreen;

    private final GameG game;
    private final AssetManager assetManager;

    public LoadingScreen(GameG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewPort = new FitViewport(GameConfig.HUD_WIDHT,GameConfig.HUD_HEIGHT,camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.UI_SKIN);
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewPort.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if(scangeScreen)
            game.setScreen(new MenuScreen(game));
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height,true);
    }

    @Override
    public void hide() {
        //screen mora rucno da se disposuje, samo se sakreije po defaultu
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        renderer = null;
    }

    private void update(float delta) {
        progress = assetManager.getProgress();

        if(assetManager.update()){
            waitTime -= delta;

            if(waitTime <= 0){
                scangeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = (GameConfig.HUD_WIDHT - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX,progressBarY,progress * PROGRESS_BAR_WIDTH,PROGRESS_BAR_HEIGHT);
    }
}
