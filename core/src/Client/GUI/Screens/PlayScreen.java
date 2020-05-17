package Client.GUI.Screens;

import Client.Controller.Client;
import Client.Controller.DistanceValidator;
import Client.Controller.Move;
import Client.GUI.Constans;
import Client.GUI.SwordGame;
import Client.GUI.Utility.Assets;
import Client.GUI.Utility.GameObject;
import Client.Controller.HandleInput;
import Client.Model.Heroes.Archer;
import Client.Model.Heroes.Paladin;
import Client.Model.Heroes.Warrior;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
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

    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(SwordGame swordGame) throws Exception {
        client = new Client();

        this.swordGame = swordGame;
        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(Constans.WIDTH / Constans.PPM, Constans.HEIGHT / Constans.PPM, gameCam);
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
        renderer = new OrthogonalTiledMapRenderer(map,1/ Constans.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        sprite = new Sprite(edgeTexture, 0, 0, edgeTexture.getWidth(), edgeTexture.getHeight());
        sprites = new ArrayList<>();

        handleInput = new HandleInput(this);
        Gdx.input.setInputProcessor(handleInput);


        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef =new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef  fdef = new FixtureDef();
        Body body;
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){  //wall
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/ 2)/Constans.PPM, (rect.getY() + rect.getHeight()/2)/Constans.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /Constans.PPM, rect.getHeight()/2/Constans.PPM);

            fdef.shape = shape;

            body.createFixture(fdef);

        }
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

        b2dr.render(world,gameCam.combined);

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
