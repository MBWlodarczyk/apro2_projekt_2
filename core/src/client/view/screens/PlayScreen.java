package client.view.screens;

import client.controller.Client;
import client.controller.GameEngine;
import client.controller.HandleInput;
import client.controller.Move;
import client.model.heroes.*;
import client.model.map.Field;
import client.model.obstacles.Wall;
import client.model.terrain.Grass;
import client.view.SwordGame;
import client.view.sprites.*;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    public Client client;
    public SwordGame swordGame;
    private Field[][] map;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Texture grassTexture, wallTexture, waterTexture, forestTexture, bushTexture, rockTexture,
            paladinTexture, warriorTexture, archerTexture, necromancerTexture, priestTexture, wizardTexture,
            moveTexture, edgeTexture, healthTexture, skillPanelTexture, trapTexture;

    private MouseSprite mouseSprite;
    private SkillPanelSprite skillPanelSprite;
    private MoveDistanceSprite moveDistanceSprite;
    private ArrayList<ObstacleSprite> wallSprite;
    private ArrayList<TerrainSprite> grassSprites;
    private ArrayList<HeroSprite> heroesSprites;
    private ArrayList<TextField> textFields;

    private HandleInput handleInput;

//    private InputMultiplexer multiplexer;
//    private Stage stage;


    public PlayScreen(SwordGame swordGame, boolean init) throws Exception {
        this.swordGame = swordGame;
        this.client = new Client(swordGame, init);
        this.map = client.getReceived().getMap().getFieldsArray();
        this.gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(Constants.WIDTH, Constants.HEIGHT, gameCam);
        loadData();
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        mouseSprite = new MouseSprite(edgeTexture);
        skillPanelSprite = new SkillPanelSprite(skillPanelTexture);
        moveDistanceSprite = new MoveDistanceSprite(moveTexture);

        wallSprite = new ArrayList<>();
        grassSprites = new ArrayList<>();
        heroesSprites = new ArrayList<>();
        textFields = new ArrayList<>();

        rewriteMap();

        handleInput = new HandleInput(this);
        Gdx.input.setInputProcessor(handleInput);
//        stage = new Stage();
//
//        multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(handleInput);
//        multiplexer.addProcessor(stage);
//
//        Gdx.input.setInputProcessor(multiplexer);
    }

    private void rewriteMap() {
        this.map = client.getReceived().getMap().getFieldsArray(); //Update map every time
        wallSprite.clear();
        grassSprites.clear();
        heroesSprites.clear();
        for (int i = 0; i < Constants.GAME_SIZE; i++) {
            for (int j = 0; j < Constants.GAME_SIZE; j++) {
                if (map[i][j].getObstacle() instanceof Wall) {
                    wallSprite.add(new ObstacleSprite(map[i][j].getObstacle(), wallTexture));
                }
                if (map[i][j].getTerrain() instanceof Grass) {
                    grassSprites.add(new TerrainSprite(map[i][j].getTerrain(), grassTexture));
                }
                if (map[i][j].getHero() != null) {
                    heroesSprites.add(new HeroSprite(map[i][j].getHero(), checkHero(i, j)));
                }
            }
        }
    }

    private Texture checkHero(int i, int j) {
        if (map[i][j].getHero() instanceof Paladin)
            return paladinTexture;
        if (map[i][j].getHero() instanceof Warrior)
            return warriorTexture;
        if (map[i][j].getHero() instanceof Archer)
            return archerTexture;
        if (map[i][j].getHero() instanceof Necromancer)
            return necromancerTexture;
        if (map[i][j].getHero() instanceof Wizard)
            return wizardTexture;
        if (map[i][j].getHero() instanceof Priest)
            return priestTexture;
        return null;
    }

    private void distanceMove() {
        if (handleInput.heroChosen) {
            int[] tab = handleInput.getTab();
            int x = handleInput.getX();
            int y = handleInput.getY();
            Move move = new Move(map[y][x].getHero(), map[tab[0]][tab[1]], map[y][x], map[y][x].getHero().getSkills().get(0));
            boolean[][] marked = GameEngine.getValid(client.getReceived().getMap(), move);
            moveDistanceSprite.setSprites(marked);
        } else {
            moveDistanceSprite.clear();
        }
    }

    private void skillOptions() {
        TextField text;
        String s;
        int x = 750, y = 50, height = 50, width = 200;
        int[] tab = handleInput.getTab();
        int size = map[tab[0]][tab[1]].getHero().getSkills().size();
        for (int i = 0; i < size + 1; i++) { //adding one in order to add exit
            if(i < size)
                s = map[tab[0]][tab[1]].getHero().getSkills().get(i).toString();
            else
                s = "Exit";
            text = new TextField(s, swordGame.skin);
            text.setSize(width, height);
            text.setPosition(x, Constants.HEIGHT - y * (i + 2));
            handleInput.addRectangles(x, y * (i + 1), width, height);
            textFields.add(text);
        }
    }


    private void update(float delta) {
        gameCam.update();
        this.map = client.getReceived().getMap().getFieldsArray();
        rewriteMap();
        distanceMove();

        textFields.clear();
        if (handleInput.heroChosen)
            skillOptions();
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        swordGame.batch.begin();

        skillPanelSprite.draw(swordGame.batch);
        grassSprites.forEach(n -> n.draw(swordGame.batch, delta));
        wallSprite.forEach(n -> n.draw(swordGame.batch, delta));
        heroesSprites.forEach(n -> n.draw(swordGame.batch, delta));
        moveDistanceSprite.draw(swordGame.batch);
        mouseSprite.draw(swordGame.batch);

        textFields.forEach(n -> n.draw(swordGame.batch, 1));

        swordGame.batch.end();
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
        grassTexture = swordGame.assets.manager.get("field/grass.png", Texture.class);
        waterTexture = swordGame.assets.manager.get("field/water.png", Texture.class);
        forestTexture = swordGame.assets.manager.get("field/forest.png", Texture.class);
        wallTexture = swordGame.assets.manager.get("field/wall.png", Texture.class);
        bushTexture = swordGame.assets.manager.get("field/bush.png", Texture.class);
        rockTexture = swordGame.assets.manager.get("field/rock.png", Texture.class);

        paladinTexture = swordGame.assets.manager.get("heroes/paladin.png", Texture.class);
        warriorTexture = swordGame.assets.manager.get("heroes/warrior.png", Texture.class);
        archerTexture = swordGame.assets.manager.get("heroes/archer.png", Texture.class);
        necromancerTexture = swordGame.assets.manager.get("heroes/necromancer.png", Texture.class);
        wizardTexture = swordGame.assets.manager.get("heroes/wizard.png", Texture.class);
        priestTexture = swordGame.assets.manager.get("heroes/priest.png", Texture.class);

        moveTexture = swordGame.assets.manager.get("special/move.png", Texture.class);
        edgeTexture = swordGame.assets.manager.get("special/edge.png", Texture.class);
        healthTexture = swordGame.assets.manager.get("special/health.png", Texture.class);
        skillPanelTexture = swordGame.assets.manager.get("special/skillPanel.png", Texture.class);

        trapTexture = swordGame.assets.manager.get("obstacles/trap.png", Texture.class);
    }
}
