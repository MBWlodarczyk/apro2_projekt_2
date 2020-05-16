package client.gui.screens;

import client.controller.Client;
import client.controller.DistanceValidator;
import client.controller.Move;
import client.gui.SwordGame;
import client.gui.utility.Assets;
import client.gui.utility.GameObject;
import client.controller.HandleInput;
import client.model.heroes.Archer;
import client.model.heroes.Paladin;
import client.model.heroes.Warrior;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class PlayScreen implements Screen {

    public int size;
    public Client client;
    private SwordGame swordGame;
//    private Hud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Texture paladinTexture, warriorTexture, archerTexture,
            edgeTexture, healthTexture;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameObject[][] gameObjects;
    private Sprite sprite;
    private ArrayList<Sprite> sprites;
    private Assets assets;
    private HandleInput handleInput;

    public PlayScreen(SwordGame swordGame) throws Exception {
        client = new Client();

        this.swordGame = swordGame;
        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(1000, 704, gameCam);
        //hud = new Hud(swordGame.batch);

        size = 22;

        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();


        gameObjects = new GameObject[22][22];

        if (assets.manager.update()) {
            loadData();
            rewriteMap();
        }


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("projekt_textury.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        sprite = new Sprite(edgeTexture, 0, 0, edgeTexture.getWidth(), edgeTexture.getHeight());
        sprites = new ArrayList<>();

        handleInput = new HandleInput(this);
        Gdx.input.setInputProcessor(handleInput);

    }

    public GameObject[][] getGameObjects() {
        return gameObjects;
    }

    /**
     * Init all of GameObjcets
     */
    private void rewriteMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (checkHero(i, j) == null)
                    gameObjects[i][j] = new GameObject(null);
                else {
                    gameObjects[i][j] = new GameObject(checkHero(i, j), null, healthTexture, client.getReceived().getMap()[i][j].getHero().getHeathStatus());
                }
                gameObjects[i][j].x = i * gameObjects[i][j].height;
                gameObjects[i][j].y = 704 - (j + 1) * gameObjects[i][j].width;
            }
        }
    }

    private Texture checkHero(int i, int j) {
        if (client.getReceived().getMap()[i][j].getHero() != null) { //tutaj jest problem jesli nie ma hero
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


    public void update(float delta) {
        rewriteMap();
        gameCam.update();
        renderer.setView(gameCam);
        mouseUpdate();
        distanceMove();
    }

    private void distanceMove() {

        if(handleInput.heroChosen){
            int[] tab = handleInput.getCord(Gdx.input.getX(),Gdx.input.getY());
            int x = handleInput.getX();
            int y = handleInput.getY();
            Move move = new Move(client.getReceived().getMap()[y][x].getHero(), client.getReceived().getMap()[tab[0]][tab[1]], client.getReceived().getMap()[y][x], client.getReceived().getMap()[y][x].getHero().getSkills().get(0));

            boolean[][] marked = DistanceValidator.getValid(client.getReceived(),move);
            for(int i = 0; i < size; i ++){
                for(int j = 0; j < size; j++){
                    if(marked[i][j]){
                        sprites.add(new Sprite(edgeTexture, 0, 0, edgeTexture.getWidth(), edgeTexture.getHeight()));
                    }
                }
            }
            int count = 0;
            for(int i = 0; i < size; i ++) {
                for (int j = 0; j < size; j++) {
                    if(marked[i][j]) {
                        sprites.get(count).setPosition(i * 32, 704 - (j + 1) * 32);
                        count++;
                    }

                }
            }
        }
        else{
            System.out.println("ciage else");
            sprites = new ArrayList<>(); //clean up
        }
    }

    private void mouseUpdate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameObjects[i][j].contains(Gdx.input.getX(), 704 - Gdx.input.getY())) {
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

        for (Sprite value : sprites) value.draw(swordGame.batch);

//        handleInput.draw(swordGame.batch);

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
        paladinTexture = assets.manager.get("heroes/paladin.png", Texture.class);
        warriorTexture = assets.manager.get("heroes/warrior.png", Texture.class);
        archerTexture = assets.manager.get("heroes/archer.png", Texture.class);

        edgeTexture = assets.manager.get("special/edge.png", Texture.class);
        healthTexture = assets.manager.get("special/health.png", Texture.class);
    }
}
