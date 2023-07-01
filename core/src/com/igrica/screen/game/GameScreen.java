package com.igrica.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.igrica.GameG;
import com.igrica.assets.AssetDescriptors;
import com.igrica.screen.menu.MenuScreen;

public class GameScreen implements Screen {

    private final GameG game;
    private final AssetManager assetManager;
    private GameController controller;
    private GameRenderer renderer;

    public GameScreen(GameG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        controller = new GameController();
        renderer = new GameRenderer(game.getBatch(),assetManager,controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);

        if(controller.isGameOver())
            game.setScreen(new MenuScreen(game));
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
