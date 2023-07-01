package com.igrica.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
import com.igrica.config.DifficultyLevel;

public class OptionsScreen extends MenuScreenBase {

    private ButtonGroup<CheckBox> checkBox;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen(GameG game) {
        super(game);
    }

    @Override
    protected Actor initUi() {
        Table table =  new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backGroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backGroundRegion));

        Label label = new Label("DIFFICULTY", uiskin);

        easy = checkbox(DifficultyLevel.EASY.name(), uiskin);
        medium = checkbox(DifficultyLevel.MEDIUM.name(), uiskin);
        hard = checkbox(DifficultyLevel.HARD.name(), uiskin);

        checkBox = new ButtonGroup<CheckBox>(easy, medium, hard);

        DifficultyLevel difficultyLevel = GameManager.instance.getDifficultyLevel();
        checkBox.setChecked(difficultyLevel.name());

        TextButton backButton = new TextButton("BACK", uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);

        Table contentTable = new Table(uiskin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(label).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add(backButton);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void difficultyChanged() {
        CheckBox checked = checkBox.getChecked();

        if(checked == easy){
            GameManager.instance.updateDifficulty(DifficultyLevel.EASY);
        }else if(checked == medium){
            GameManager.instance.updateDifficulty(DifficultyLevel.MEDIUM);
        }else if(checked == hard){
            GameManager.instance.updateDifficulty(DifficultyLevel.HARD );
        }
    }

    private static CheckBox checkbox(String text, Skin skin) {
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(18);

        return checkBox;
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
