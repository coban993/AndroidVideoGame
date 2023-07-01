package com.igrica.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.igrica.GameG;
import com.igrica.assets.AssetDescriptors;
import com.igrica.assets.RegionNames;
import com.igrica.common.GameManager;

public class HighScoreScreen extends MenuScreenBase {

    public HighScoreScreen(GameG game) {
        super(game);
    }

    @Override
    protected Actor initUi() {
        Table table =  new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label highScoreText = new Label("HIGHSCORE", uiskin);

        String highScoreString  = GameManager.instance.getHighScoreString();
        Label highScoreLable = new Label(highScoreString, uiskin);

        TextButton backButton = new TextButton("BACK", uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        Table contentTable = new Table(uiskin);
        contentTable.defaults().pad(20);
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(highScoreText).row();
        contentTable.add(highScoreLable).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }

}
