package Client.Gui_v2.Screens;

import Client.Controller.Client;
import Client.Gui_v2.Utility.Assets;
import Client.Gui_v2.Utility.GameObject;
import Client.Gui_v2.SwordGame;
import Client.Gui_v2.Utility.HandleInput;
import Client.Model.Heroes.Archer;
import Client.Model.Heroes.Paladin;
import Client.Model.Heroes.Warrior;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;

public class PlayScreen implements Screen {

    private SwordGame swordGame;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
//    private Hud hud;

    private Texture paladinTexture, warriorTexture, archerTexture,
                    edgeTexture;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameObject[][] gameObjects;
    public int size;
    private Sprite sprite;
    public Client client;
    private Assets assets;
    private HandleInput handleInput;

    public GameObject[][] getGameObjects() {
        return gameObjects;
    }

    public PlayScreen(SwordGame swordGame) throws Exception {
        client = new Client();

        this.swordGame = swordGame;
        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(704,704 ,gameCam);
        //hud = new Hud(swordGame.batch);

        size = 22;

        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();


        gameObjects = new GameObject[22][22];

        if(assets.manager.update()){
            loadData();
            rewriteMap();
        }


        mapLoader  = new TmxMapLoader();
        map = mapLoader.load("projekt_textury.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        sprite = new Sprite(edgeTexture,0,0,edgeTexture.getWidth(),edgeTexture.getHeight());
        Gdx.input.setInputProcessor(new HandleInput(this));
        handleInput = new HandleInput(this);
    }

    /**
     * Init all of GameObjcets
     */
    private void rewriteMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                gameObjects[i][j] = new GameObject(checkHero(i, j), null);
                gameObjects[i][j].x = i * gameObjects[i][j].height;
                gameObjects[i][j].y = 704 - (j + 1) * gameObjects[i][j].width;
            }
        }
    }

    private Texture checkHero(int i, int j){
        if(client.getReceived().getMap()[i][j].getHero() != null) { //tutaj jest problem jesli nie ma hero
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Paladin.class))
                return paladinTexture; //tutuaj pozmieniać, dodać do

            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Warrior.class))
                return warriorTexture;
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Archer.class))
                return archerTexture;
        }
        return null;
    }




    @Override
    public void show() {

    }


    public void handleInput(float delta){
        if(Gdx.input.isTouched()) {
            int[] temp = getCord();
            if(client.getReceived().getMap()[temp[0]][temp[1]].getHero() != null) {
                System.out.println(client.getReceived().getMap()[temp[0]][temp[1]].getHero().getSkills());



                /**
                 * boolean heroChosen
                 * boolean skillChosen
                 */
            }
        }
    }

    private int[] getCord(){
        int[] result = new int[2];
        for (int i = 0; i < size; i++) {
            for ( int j = 0; j < size; j++) {
                if (gameObjects[i][j].contains(Gdx.input.getX(), 704 - Gdx.input.getY())) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }


    public void update(float delta){
        rewriteMap();
        //handleInput(delta);
        gameCam.update();
        renderer.setView(gameCam);
        mouseUpdate();
    }

    private void mouseUpdate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameObjects[i][j].contains(Gdx.input.getX(),704 - Gdx.input.getY())){
                    sprite.setPosition(gameObjects[i][j].x, gameObjects[i][j].y);
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

        swordGame.batch.begin();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameObjects[i][j].draw(swordGame.batch);
            }
        }

        sprite.draw(swordGame.batch);

        swordGame.batch.end();

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

    private void loadData() {
        paladinTexture = assets.manager.get("heroes/paladin.png",Texture.class);
        warriorTexture = assets.manager.get("heroes/warrior.png",Texture.class);
        archerTexture = assets.manager.get("heroes/archer.png",Texture.class);

        edgeTexture = assets.manager.get("special/edge.png",Texture.class);
    }
}
