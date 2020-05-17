package Client.GUI.Screens;

import Client.Controller.Client;
import Client.Controller.DistanceValidator;
import Client.Controller.Move;
import Client.GUI.Constans;
import Client.GUI.Scenes.Hud;
import Client.GUI.SwordGame;
import Client.GUI.Utility.Assets;
import Client.GUI.Utility.GameObject;
import Client.Controller.HandleInput;
import Client.Model.Heroes.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PlayScreen implements Screen {

    public int size;
    public Client client;
    private SwordGame swordGame;
    private Hud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Texture paladinTexture, warriorTexture, archerTexture,necromancerTexture,priestTexture,wizardTexture,
            edgeTexture, healthTexture;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameObject[][] gameObjects;
    private Sprite sprite;
    private ArrayList<Sprite> sprites;
    private Assets assets;
    private HandleInput handleInput;
    private Skin skin;

    private ArrayList<Button> skills;

    public PlayScreen(SwordGame swordGame) throws Exception {
        //client = new Client();
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));

        this.swordGame = swordGame;
        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(Constans.WIDTH, Constans.HEIGHT, gameCam);

        hud = new Hud(swordGame.batch);
        gameObjects = new GameObject[22][22];

        size = 22;

        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();




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
        skills = new ArrayList<>();

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
                gameObjects[i][j].y = Constans.HEIGHT - (j + 1) * gameObjects[i][j].width;
            }
        }
    }

    private Texture checkHero(int i, int j) {
        if (client.getReceived().getMap()[i][j].getHero() != null) { //tutaj jest problem jesli nie ma hero
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Paladin.class))
                return paladinTexture;
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Warrior.class))
                return warriorTexture;
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Archer.class))
                return archerTexture;
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Necromancer.class))
                return necromancerTexture;
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Wizard.class))
                return wizardTexture;
            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Priest.class))
                return priestTexture;
        }
        return null;
    }

    private void distanceMove() {
        if(handleInput.heroChosen){
            int[] tab = handleInput.getTab();
            int x = handleInput.getX();
            int y = handleInput.getY();
            Move move = new Move(client.getReceived().getMap()[y][x].getHero(), client.getReceived().getMap()[tab[0]][tab[1]], client.getReceived().getMap()[y][x], client.getReceived().getMap()[y][x].getHero().getSkills().get(0));
            Sprite s;

            boolean[][] marked = DistanceValidator.getValid(client.getReceived(),move);
            for(int i = 0; i < size; i ++){
                for(int j = 0; j < size; j++){
                    if(marked[i][j]){
                        s = new Sprite(edgeTexture, 0, 0, edgeTexture.getWidth(), edgeTexture.getHeight());
                        s.setPosition(i * 32, 704 - (j + 1) * 32);
                        sprites.add(s);
                    }
                }
            }
        }
        else{
            sprites = new ArrayList<>(); //clean up
        }
    }

    private void skillOptions(){
        TextButton button;
        String s;
        if(handleInput.heroChosen){
            int[] tab = handleInput.getTab();
            int size = client.getReceived().getMap()[tab[0]][tab[1]].getHero().getSkills().size();
            for(int i = 0; i < size; i++ ){
                s = client.getReceived().getMap()[tab[0]][tab[1]].getHero().getSkills().get(i).toString();
                button = new TextButton(s,skin);
                button.setSize(250,100);
                button.setPosition(720,Constans.HEIGHT - 100*(i+2));
                skills.add(button);
            }
        }
        else {
            skills = new ArrayList<>();
        }
    }

    private void mouseUpdate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameObjects[i][j].contains(Gdx.input.getX(), Constans.HEIGHT - Gdx.input.getY())) {
                    sprite.setPosition(gameObjects[i][j].x, gameObjects[i][j].y);
                }
            }
        }
    }


    private void update(float delta) {
        rewriteMap();
        gameCam.update();
        renderer.setView(gameCam);
        mouseUpdate();
        distanceMove();
        skillOptions();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        swordGame.batch.begin();
        //render textures
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameObjects[i][j].draw(swordGame.batch);
            }
        }

        //render sprites
        for (Sprite value : sprites) value.draw(swordGame.batch);
        //render list skill
        for (Button b : skills) b.draw(swordGame.batch, 1);
        //draw mouse position
        sprite.draw(swordGame.batch);

        swordGame.batch.end();

//        swordGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {

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
        necromancerTexture = assets.manager.get("heroes/necromancer.png", Texture.class);
        wizardTexture = assets.manager.get("heroes/wizard.png", Texture.class);
        priestTexture = assets.manager.get("heroes/priest.png", Texture.class);

        edgeTexture = assets.manager.get("special/edge.png", Texture.class);
        healthTexture = assets.manager.get("special/health.png", Texture.class);
    }
}
