package client.view.screens;

import client.controller.*;
import client.model.heroes.*;
import client.model.map.Field;
import client.model.obstacles.Trap;
import client.model.obstacles.Wall;
import client.model.terrain.Grass;
import client.view.SwordGame;
import client.view.scenes.HeroStatisticHud;
import client.view.scenes.QueueStateHud;
import client.view.scenes.SendRemoveButtonHud;
import client.view.scenes.SkillOptionsHud;
import client.view.sprites.*;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static client.controller.Inputs.tab;


public class PlayScreen extends AbstractScreen {
    public Client client;
    private Field[][] map;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private MouseSprite mouseSprite;
    private SkillPanelSprite skillPanelSprite;
    private MoveDistanceSprite skillDistanceSprite;
    private ArrayList<ObstacleSprite> wallSprite,trapSprite;
    private ArrayList<TerrainSprite> grassSprites;
    private ArrayList<HeroSprite> heroesSprites;
    private HandleInput handleInput;
    private HeroStatisticHud heroStatisticHud;
    private SkillOptionsHud skillOptionsHud;
    private QueueStateHud queueStateHud;
    private SendRemoveButtonHud sendRemoveButtonHud;


    public PlayScreen(SwordGame swordGame, Client client) {
        super(swordGame);
        this.client = client;
        this.map = client.getReceived().getMap().getFieldsArray();
        this.gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(Constants.WIDTH, Constants.HEIGHT, gameCam);
        this.handleInput = new HandleInput(this);
        this.heroStatisticHud = new HeroStatisticHud(swordGame.batch, swordGame.skin);
        this.skillOptionsHud = new SkillOptionsHud(swordGame.batch, swordGame.skin);
        this.queueStateHud = new QueueStateHud(swordGame.batch, swordGame.skin);
        this.sendRemoveButtonHud = new SendRemoveButtonHud(swordGame.batch,swordGame.skin,handleInput);
        addMusic();
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        mouseSprite = new MouseSprite(swordGame.edgeTexture);
        skillPanelSprite = new SkillPanelSprite(swordGame.skillPanelTexture);
        skillDistanceSprite = new MoveDistanceSprite(swordGame.moveTexture);
        wallSprite = new ArrayList<>();
        trapSprite = new ArrayList<>();
        grassSprites = new ArrayList<>();
        heroesSprites = new ArrayList<>();
        rewriteMap();
        Gdx.input.setInputProcessor(handleInput);
    }

    private void addMusic() {
        swordGame.inGameTheme.setVolume(0.25f);
        swordGame.inGameTheme.setLooping(true);
        swordGame.inGameTheme.play();
    }

    private void rewriteMap() {
        for (int i = 0; i < Constants.GAME_SIZE; i++) {
            for (int j = 0; j < Constants.GAME_SIZE; j++) {
                if (map[i][j].getObstacle() instanceof Wall) {
                    wallSprite.add(new ObstacleSprite(map[i][j].getObstacle(), swordGame.wallTexture));
                }
                if (map[i][j].getTerrain() instanceof Grass) {
                    grassSprites.add(new TerrainSprite(map[i][j].getTerrain(), swordGame.grassTexture));
                }
                if (map[i][j].getObstacle() instanceof Trap) {
                    trapSprite.add(new ObstacleSprite(map[i][j].getObstacle(), swordGame.trapTexture));
                }
                if (map[i][j].getHero() != null) {
                    heroesSprites.add(new HeroSprite(map[i][j].getHero(), checkHero(i, j),swordGame.heroOwnershipTexture, heroOwnership(i,j)));
                }
            }
        }
    }

    private boolean heroOwnership(int i, int j){
        return map[i][j].getHero().getOwner().equals(client.player);
    }

    private Texture checkHero(int i, int j) {
        if (map[i][j].getHero() instanceof Paladin)
            return swordGame.paladinTexture;
        if (map[i][j].getHero() instanceof Warrior)
            return swordGame.warriorTexture;
        if (map[i][j].getHero() instanceof Archer)
            return swordGame.archerTexture;
        if (map[i][j].getHero() instanceof Necromancer)
            return swordGame.necromancerTexture;
        if (map[i][j].getHero() instanceof Wizard)
            return swordGame.wizardTexture;
        if (map[i][j].getHero() instanceof Priest)
            return swordGame.priestTexture;
        return null;
    }

    private void distanceMove() {
        int x = Inputs.x;
        int y = Inputs.y;
        Move move = new Move(map[y][x].getHero(), map[tab[0]][tab[1]], map[y][x], map[y][x].getHero().getSkills().get(Inputs.skillChosen));
        boolean[][] marked = GameEngine.getValid(client.getReceived().getMap(), move);
        skillDistanceSprite.setSprites(marked);
    }

    private void clear(){
        skillDistanceSprite.clear();
        wallSprite.clear();
        trapSprite.clear();
        grassSprites.clear();
        heroesSprites.clear();
        handleInput.getRectangles().clear();
    }

    @Override
    public void update(float delta) {
        this.map = client.getReceived().getMap().getFieldsArray();
        clear();
        gameCam.update();
        rewriteMap();
        if (handleInput.currentState == HandleInput.ControllerState.PERFORM_SKILL)
            distanceMove();
        queueStateHud.updateText(client.getSend().toString());
        skillOptionsHud.update(delta);
        if (handleInput.currentState == HandleInput.ControllerState.HERO_CHOSEN)
            skillOptionsHud.skillOptions(handleInput, map, swordGame.skin);
        if (client.getReceived().getWinner()!=null){
            String winner = client.getReceived().getWinner().getNick();
            swordGame.setScreen(new MessageScreen(swordGame,"Player "+winner +"won!",new LoadScreen(swordGame)));
            swordGame.inGameTheme.dispose();
            client.dispose();
            this.dispose();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        swordGame.batch.begin();

        skillPanelSprite.draw(swordGame.batch); //draw skill panel
        grassSprites.forEach(n -> n.draw(swordGame.batch, delta)); //draw grass
        wallSprite.forEach(n -> n.draw(swordGame.batch, delta));  //draw walls
        trapSprite.forEach(n -> n.draw(swordGame.batch, delta));  //draw traps
        heroesSprites.forEach(n -> n.draw(swordGame.batch, delta)); //draw heroes
        skillDistanceSprite.draw(swordGame.batch); //draw dfs marked fields
        mouseSprite.draw(swordGame.batch);  //draw mouse

        swordGame.batch.end();

        sendRemoveButtonHud.draw(swordGame.batch,delta);
        skillOptionsHud.draw(swordGame.batch, delta);
        queueStateHud.draw(swordGame.batch, delta);

        if (handleInput.currentState == HandleInput.ControllerState.HERO_CHOSEN || Inputs.anyHeroChosen) {
            String s = map[tab[0]][tab[1]].getHero().description();
            heroStatisticHud.updateText(s);
            heroStatisticHud.draw(swordGame.batch, delta);
        }
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
}
