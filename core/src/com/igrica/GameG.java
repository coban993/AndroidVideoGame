package com.igrica;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.igrica.screen.game.GameScreen;
import com.igrica.screen.loading.LoadingScreen;

public class GameG extends com.badlogic.gdx.Game {
	//exteduje game zato sto omogucava vise ekrana

	private AssetManager assetManager;
	private SpriteBatch batch;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		assetManager = new AssetManager();
		assetManager.getLogger().setLevel(Logger.DEBUG);

		batch = new SpriteBatch();

		setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		batch.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
