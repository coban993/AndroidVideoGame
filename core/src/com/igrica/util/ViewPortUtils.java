package com.igrica.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import sun.rmi.runtime.Log;

public class ViewPortUtils {

    private static final Logger log = new Logger(Viewport.class.getName(), Logger.DEBUG);

    private static final int DEFAULT_CELL_SIZE = 1;

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer) {
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize) {
        //validacija parametara
        if(viewport == null){
            throw new IllegalArgumentException("viewport paremeter is required");
        }

        if(renderer == null){
            throw new IllegalArgumentException("renderer paremeter is required");
        }

        if(cellSize < DEFAULT_CELL_SIZE){
            cellSize = DEFAULT_CELL_SIZE;
        }

        //kopiranje srare boje iz renderera
        Color oldColor = new Color(renderer.getColor());

        int worldWidith = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleworldWidith = worldWidith * 2;
        int doubleworldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        //crtanje vertikalnih linija
        for(int x  = -doubleworldWidith; x < doubleworldWidith; x+=cellSize) {
            renderer.line(x, -doubleworldHeight, x, doubleworldHeight);
        }

        //crtanje horizontalnih linija
        for(int y  = -doubleworldHeight; y < doubleworldHeight; y+=cellSize) {
            renderer.line(-doubleworldWidith, y, doubleworldWidith, y);
        }

        //crtanje x-y osne linije
        renderer.setColor(Color.RED);
        renderer.line(0, -doubleworldHeight, 0, doubleworldHeight);
        renderer.line(-doubleworldWidith, 0, doubleworldWidith, 0);

        //crtanje ivica(granica) igrice
        renderer.setColor(Color.GREEN);
        renderer.line(0, worldHeight, worldWidith, worldHeight);
        renderer.line(worldWidith, 0, worldWidith, worldHeight);

        renderer.end();

        renderer.setColor(oldColor);


    }

    public static void debugPixelPerUnit(Viewport viewport){
        if(viewport == null){
            throw new IllegalArgumentException("viewport paremeter is required");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //PPU => pixeli po jedinici igrice koju smo definisali
        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("x PPU= " + xPPU + "y PPU= " + yPPU);
    }

    private ViewPortUtils() {

    }

}
