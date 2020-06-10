package client.view.screens;

import client.controller.Client;
import client.controller.HandleInput;
import client.view.SwordGame;
import client.view.scenes.HudManager;
import client.view.sprites.*;
import client.view.utility.TextureManager;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;


public class PlayScreen extends AbstractScreen {
    public Client client;
    private MouseSprite mouseSprite;
    private SkillPanelBackgroundSprite skillPanelBackgroundSprite;
    private SkillDistanceSprite skillDistanceSprite;
    private TextureManager textureManager;
    private ArrayList<ObstacleSprite> obstacleSprites; //move arrayLists to TextureManager
    private ArrayList<TerrainSprite> terrainSprites;
    private ArrayList<HeroSprite> heroesSprites;
    private HandleInput handleInput;
    private HudManager hudManager;


    PlayScreen(SwordGame swordGame, Client client) {
        super(swordGame);
        this.client = client;
        handleInput = new HandleInput(this);
        textureManager = new TextureManager(swordGame);
        mouseSprite = new MouseSprite(swordGame.edgeTexture);
        skillPanelBackgroundSprite = new SkillPanelBackgroundSprite(swordGame.skillPanelTexture);
        skillDistanceSprite = new SkillDistanceSprite(swordGame.moveTexture);
        obstacleSprites = new ArrayList<>();
        terrainSprites = new ArrayList<>();
        heroesSprites = new ArrayList<>();
        hudManager = new HudManager(swordGame, handleInput);
        Gdx.input.setInputProcessor(handleInput);
    }

    /**
     * Add music to game
     */
    private void addMusic() {
        swordGame.inGameTheme.setVolume(0.25f);
        swordGame.inGameTheme.setLooping(true);
        swordGame.inGameTheme.play();
    }

    /**
     * Clear all the arrayList before adding new elements to them
     */
    private void clear() {
        skillDistanceSprite.clear();
        obstacleSprites.clear();
        terrainSprites.clear();
        heroesSprites.clear();
        handleInput.getRectangles().clear();
    }

    /**
     * Check if there is winner on map
     */
    private void checkWinner() {
        if (client.getReceived().getWinner() != null) {
            String winner = client.getReceived().getWinner().getNick();
            swordGame.setScreen(new MessageScreen(swordGame, "Player " + winner + " won!", new LoadScreen(swordGame)));
            swordGame.inGameTheme.dispose();
            client.dispose();
            this.dispose();
        }
    }

    @Override
    public void update(float delta) {
        clear();
        textureManager.update(client, obstacleSprites, terrainSprites, heroesSprites, skillDistanceSprite);
        hudManager.update(delta);
        hudManager.updateText(client, handleInput);
        checkWinner();
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
        hudManager.draw(swordGame.batch, delta); //draw hud
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