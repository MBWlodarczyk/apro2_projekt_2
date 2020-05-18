package Client.GUI;

import Client.GUI.Screens.LoadScreen;
//import Client.GUI.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SwordGame extends Game {

    public int size = 22;
    public int textureSize = 32;
    public SpriteBatch batch;
    public Skin skin;
    public static String nick;
    public static String ip;
    public static String port;
    public static boolean[] chosen = new boolean[6];

    @Override
    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));
        setScreen(new LoadScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        //batch.dispose();
    }
}
