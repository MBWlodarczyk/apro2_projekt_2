package client.view;

import client.model.Player;
import client.view.screens.LoadScreen;
import client.view.utility.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    @Override
    public void create() {
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

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
}
