package client.view.screens;

import client.controller.*;
import client.model.map.Field;
import client.view.SwordGame;
import client.view.scenes.HeroStatisticHud;
import client.view.scenes.QueueStateHud;
import client.view.scenes.SendRemoveButtonHud;
import client.view.scenes.SkillOptionsHud;
import client.view.sprites.*;
import client.view.utility.TextureManager;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import static client.controller.Inputs.tab;


public class PlayScreen extends AbstractScreen {
    public Client client;
    private Field[][] map;
    private MouseSprite mouseSprite;
    private SkillPanelBackgroundSprite skillPanelBackgroundSprite;
    private SkillDistanceSprite skillDistanceSprite;
    private TextureManager textureManager;
    private ArrayList<ObstacleSprite> obstacleSprites;
    private ArrayList<TerrainSprite> terrainSprites;
    private ArrayList<HeroSprite> heroesSprites;
    private HandleInput handleInput;
    private HeroStatisticHud heroStatisticHud;
    private SkillOptionsHud skillOptionsHud;
    private QueueStateHud queueStateHud;
    private SendRemoveButtonHud sendRemoveButtonHud;


    public PlayScreen(SwordGame swordGame, Client client) {
        super(swordGame);
        this.client = client;
        map = client.getReceived().getMap().getFieldsArray();
        handleInput = new HandleInput(this);
        heroStatisticHud = new HeroStatisticHud(swordGame.batch, swordGame.skin);
        skillOptionsHud = new SkillOptionsHud(swordGame.batch, swordGame.skin);
        queueStateHud = new QueueStateHud(swordGame.batch, swordGame.skin);
        sendRemoveButtonHud = new SendRemoveButtonHud(swordGame.batch, swordGame.skin, handleInput);
        textureManager = new TextureManager(swordGame);
        mouseSprite = new MouseSprite(swordGame.edgeTexture);
        skillPanelBackgroundSprite = new SkillPanelBackgroundSprite(swordGame.skillPanelTexture);
        skillDistanceSprite = new SkillDistanceSprite(swordGame.moveTexture);
        obstacleSprites = new ArrayList<>();
        terrainSprites = new ArrayList<>();
        heroesSprites = new ArrayList<>();
        Gdx.input.setInputProcessor(handleInput);
    }

    private void addMusic() {
        swordGame.inGameTheme.setVolume(0.25f);
        swordGame.inGameTheme.setLooping(true);
        swordGame.inGameTheme.play();
    }

    private void clear() {
        skillDistanceSprite.clear();
        obstacleSprites.clear();
        terrainSprites.clear();
        heroesSprites.clear();
        handleInput.getRectangles().clear();
    }

    @Override
    public void update(float delta) {
        clear();
        this.map = client.getReceived().getMap().getFieldsArray();
        textureManager.rewriteMap(map, client.player, obstacleSprites, terrainSprites, heroesSprites);

        if (handleInput.currentState == HandleInput.ControllerState.PERFORM_SKILL)
            textureManager.skillDistance(map,client.getReceived().getMap(),skillDistanceSprite);

        queueStateHud.updateText(client.getSend().toString());
        skillOptionsHud.update(delta);

        if (handleInput.currentState == HandleInput.ControllerState.HERO_CHOSEN)
            skillOptionsHud.skillOptions(handleInput, map, swordGame.skin);

        if (client.getReceived().getWinner() != null) {
            String winner = client.getReceived().getWinner().getNick();
            swordGame.setScreen(new MessageScreen(swordGame, "Player " + winner + " won!", new LoadScreen(swordGame)));
            swordGame.inGameTheme.dispose();
            client.dispose();
            this.dispose();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        swordGame.batch.begin();

        skillPanelBackgroundSprite.draw(swordGame.batch); //draw skill panel
        terrainSprites.forEach(n -> n.draw(swordGame.batch, delta)); //draw terrain
        obstacleSprites.forEach(n -> n.draw(swordGame.batch, delta));  //draw walls
        heroesSprites.forEach(n -> n.draw(swordGame.batch, delta)); //draw heroes
        skillDistanceSprite.draw(swordGame.batch); //draw dfs marked fields
        mouseSprite.draw(swordGame.batch);  //draw mouse

        swordGame.batch.end();

        sendRemoveButtonHud.draw(swordGame.batch, delta);
        skillOptionsHud.draw(swordGame.batch, delta);
        queueStateHud.draw(swordGame.batch, delta);

        if (Inputs.anyHeroChosen) {
            String s = map[tab[0]][tab[1]].getHero().description();
            heroStatisticHud.updateText(s);
            heroStatisticHud.draw(swordGame.batch, delta);
        }
    }

    @Override
    public void resize(int width, int height) {
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