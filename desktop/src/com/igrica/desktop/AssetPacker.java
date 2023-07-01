package com.igrica.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static boolean DRAW_DEBUG_OTLINE = false;

    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings setting = new TexturePacker.Settings();
        setting.debug = DRAW_DEBUG_OTLINE;
        setting.pot = false;

        TexturePacker.process(setting,
                RAW_ASSET_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "/gameplay");

        TexturePacker.process(setting,
                RAW_ASSET_PATH + "/ui",
                ASSETS_PATH + "/ui",
                "/ui");

        TexturePacker.process(setting,
                RAW_ASSET_PATH + "/skin",
                ASSETS_PATH + "/ui",
                "uiskin");
    }
}
