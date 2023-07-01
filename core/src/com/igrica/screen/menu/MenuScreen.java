package com.igrica.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.igrica.GameG;
import com.igrica.assets.AssetDescriptors;
import com.igrica.assets.RegionNames;
import com.igrica.screen.game.GameScreen;

public class MenuScreen extends MenuScreenBase {

    public MenuScreen(GameG game) {
        super(game);
    }

    @Override
    protected Actor initUi() {
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //---BUTTONS---
        TextButton playButton = new TextButton("PLAY", uiskin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        TextButton highScore = new TextButton("HIGHSCORE", uiskin);
        highScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        TextButton optionsButton = new TextButton("OPTIONS", uiskin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });

        TextButton quitButton = new TextButton("QUIT", uiskin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        //setup table
        Table buttonTable = new Table(uiskin);
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(RegionNames.PANEL);

        buttonTable.add(playButton).row();
        buttonTable.add(highScore).row();
        buttonTable.add(optionsButton).row();
        buttonTable.add(quitButton);

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void quit() {
        Gdx.app.exit();
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private void showHighScore() {
        game.setScreen(new HighScoreScreen(game));
    }

    private void showOptions() {
        game.setScreen(new OptionsScreen(game));
    }
}
