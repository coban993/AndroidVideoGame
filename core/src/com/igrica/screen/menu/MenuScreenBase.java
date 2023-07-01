package com.igrica.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.igrica.GameG;
import com.igrica.config.GameConfig;
import com.igrica.util.GdxUtils;

public abstract class MenuScreenBase extends ScreenAdapter {

    protected final GameG game;
    protected final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    public MenuScreenBase(GameG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDHT,GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        stage.addActor(initUi());
    }

    protected abstract Actor initUi();

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
