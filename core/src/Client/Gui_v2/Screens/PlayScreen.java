package Client.Gui_v2.Screens;

import Client.GUI.GameObject;
import Client.Gui_v2.SwordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

    private SwordGame swordGame;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
//    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameObject[][] gameObjects;
    private int size;

    public PlayScreen(SwordGame swordGame){
        this.swordGame = swordGame;
        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(704,704 ,gameCam);
        //hud = new Hud(swordGame.batch);

        size = 20;

        mapLoader  = new TmxMapLoader();
        map = mapLoader.load("projekt_textury.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        gameObjects = new GameObject[704/20 - 2][704/20 -2];

    }

//    /**
//     * Init all of GameObjcets
//     */
//    private void rewriteMap() {
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                gameObjects[i][j] = new GameObject(checkTexture(i,j),checkHero(i,j),null);
//                gameObjects[i][j].x = i * gameObjects[i][j].height;
//                gameObjects[i][j].y = 512 - (j+1) * gameObjects[i][j].width;
//            }
//        }
//    }



    @Override
    public void show() {

    }


    public void handleInput(float delta){
        if(Gdx.input.isTouched())
            gameCam.position.x += 100 * delta;
    }


    public void update(float delta){
        handleInput(delta);
        gameCam.update();
        renderer.setView(gameCam);
        mouseUpdate();
    }

    private void mouseUpdate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameObjects[i][j].contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
                    sprite.setPosition(gameObjects[i][j].x, gameObjects[i][j].y*camera.zoom);
                }
            }
        }
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //swordGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
