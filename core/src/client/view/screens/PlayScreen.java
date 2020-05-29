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
import client.view.scenes.HeroStatisticHud;
import client.view.scenes.SkillOptionsHud;
import client.view.sprites.*;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private HeroStatisticHud heroStatisticHud;
    private SkillOptionsHud skillOptionsHud;

    private BitmapFont bitmapFont;
    private Music ingameTheme;

    public PlayScreen(SwordGame swordGame, Client client) {
        this.swordGame = swordGame;
        this.client = client;
        this.map = client.getReceived().getMap().getFieldsArray();
        this.gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(Constants.WIDTH, Constants.HEIGHT, gameCam);
        this.heroStatisticHud = new HeroStatisticHud(swordGame.batch, swordGame.skin);
        this.skillOptionsHud = new SkillOptionsHud(swordGame.batch,swordGame.skin);
        loadData();
        addMusic();

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        mouseSprite = new MouseSprite(edgeTexture);
        skillPanelSprite = new SkillPanelSprite(skillPanelTexture);
        moveDistanceSprite = new MoveDistanceSprite(moveTexture);

        wallSprite = new ArrayList<>();
        grassSprites = new ArrayList<>();
        heroesSprites = new ArrayList<>();
        textFields = new ArrayList<>();

        bitmapFont = new BitmapFont();

        rewriteMap();

        handleInput = new HandleInput(this);
        Gdx.input.setInputProcessor(handleInput);
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
    private void addMusic(){
        ingameTheme.setVolume(0.25f);
        ingameTheme.setLooping(true);
        ingameTheme.play();
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
        if (handleInput.currentState == HandleInput.ControllerState.PERFORM_SKILL) {
            int[] tab = handleInput.getTab();
            int x = handleInput.getX();
            int y = handleInput.getY();
            Move move = new Move(map[y][x].getHero(), map[tab[0]][tab[1]], map[y][x], map[y][x].getHero().getSkills().get(handleInput.getSkillChosen()));
            boolean[][] marked = GameEngine.getValid(client.getReceived().getMap(), move);
            moveDistanceSprite.setSprites(marked);
        } else {
            moveDistanceSprite.clear();
        }
    }


    private void update(float delta) {
        gameCam.update();
        this.map = client.getReceived().getMap().getFieldsArray();
        rewriteMap();
        distanceMove();
        textFields.clear();

        handleInput.getRectangles().clear();

        skillOptionsHud.update(delta);
        if (handleInput.currentState == HandleInput.ControllerState.HERO_CHOSEN)
            skillOptionsHud.skillOptions(handleInput, map, swordGame.skin);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        swordGame.batch.begin();

        skillPanelSprite.draw(swordGame.batch); //draw skill panel
        grassSprites.forEach(n -> n.draw(swordGame.batch, delta)); //draw grass
        wallSprite.forEach(n -> n.draw(swordGame.batch, delta));  //draw walls
        heroesSprites.forEach(n -> n.draw(swordGame.batch, delta)); //draw heroes
        moveDistanceSprite.draw(swordGame.batch); //draw dfs marked fields
        mouseSprite.draw(swordGame.batch);  //draw mouse

        bitmapFont.draw(swordGame.batch, client.getSend().toString(), Constants.HEIGHT - 20, 300); //draw queue od moves

        swordGame.batch.end();


        if (handleInput.currentState == HandleInput.ControllerState.HERO_CHOSEN || handleInput.anyHeroChosen) {
            String s = map[handleInput.getTab()[0]][handleInput.getTab()[1]].getHero().description();
            heroStatisticHud.updateText(s);
            heroStatisticHud.draw(swordGame.batch, delta);
        }

        skillOptionsHud.draw(swordGame.batch,delta);

        swordGame.batch.setProjectionMatrix(heroStatisticHud.stage.getCamera().combined);
        swordGame.batch.setProjectionMatrix(skillOptionsHud.stage.getCamera().combined);
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
        ingameTheme = swordGame.assets.manager.get("sound/IngameMainTheme.mp3", Music.class);
    }
}
