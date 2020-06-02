package client.view;

import client.model.Player;
import client.view.screens.LoadScreen;
import client.view.utility.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SwordGame extends Game {

    public SpriteBatch batch;
    public Skin skin;
    public String nick;
    public String ip;
    public String port;
    public byte[] password;
    public boolean[] chosen = new boolean[6];
    public Player player;
    public Assets assets; //load textures manager
    public Texture grassTexture, wallTexture, waterTexture, forestTexture, bushTexture, rockTexture,
            paladinTexture, warriorTexture, archerTexture, necromancerTexture, priestTexture, wizardTexture,
            moveTexture, edgeTexture, healthTexture, skillPanelTexture, trapTexture, heroOwnershipTexture,
            paladinTexture_dark, warriorTexture_dark, archerTexture_dark, necromancerTexture_dark, priestTexture_dark, wizardTexture_dark,
            background;
    public Music inGameTheme,theme;

    @Override
    public void create() {
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

        load();

        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"));
        setScreen(new LoadScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void load(){
        grassTexture = assets.manager.get("field/grass.png", Texture.class);
        waterTexture = assets.manager.get("field/water.png", Texture.class);
        forestTexture = assets.manager.get("field/forest.png", Texture.class);
        wallTexture = assets.manager.get("field/wall.png", Texture.class);
        bushTexture = assets.manager.get("field/bush.png", Texture.class);
        rockTexture = assets.manager.get("field/rock.png", Texture.class);

        paladinTexture = assets.manager.get("heroes/paladin.png", Texture.class);
        warriorTexture = assets.manager.get("heroes/warrior.png", Texture.class);
        archerTexture = assets.manager.get("heroes/archer.png", Texture.class);
        necromancerTexture = assets.manager.get("heroes/necromancer.png", Texture.class);
        wizardTexture = assets.manager.get("heroes/wizard.png", Texture.class);
        priestTexture = assets.manager.get("heroes/priest.png", Texture.class);

        paladinTexture_dark = assets.manager.get("heroes/paladin_nygga.png", Texture.class);
        warriorTexture_dark = assets.manager.get("heroes/warrior_nygga.png", Texture.class);
        archerTexture_dark = assets.manager.get("heroes/archer_nygga.png", Texture.class);
        necromancerTexture_dark = assets.manager.get("heroes/necromancer_nygga.png", Texture.class);
        priestTexture_dark = assets.manager.get("heroes/priest_nygga.png", Texture.class);
        wizardTexture_dark = assets.manager.get("heroes/wizard_nygga.png", Texture.class);

        background = assets.manager.get("special/background.png", Texture.class);
        theme = assets.manager.get("sound/Induktancja1.mp3", Music.class);

        moveTexture = assets.manager.get("special/move.png", Texture.class);
        edgeTexture = assets.manager.get("special/edge.png", Texture.class);
        healthTexture = assets.manager.get("special/health.png", Texture.class);
        skillPanelTexture = assets.manager.get("special/skillPanel.png", Texture.class);
        heroOwnershipTexture = assets.manager.get("special/heroOwnership.png", Texture.class);

        trapTexture = assets.manager.get("obstacles/trap.png", Texture.class);
        inGameTheme = assets.manager.get("sound/IngameMainTheme.mp3", Music.class);
    }
}
