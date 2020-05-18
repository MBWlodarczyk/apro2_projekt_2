package Client.GUI;

import Client.GUI.Screens.LoadScreen;
//import Client.GUI.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SwordGame extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public int size = 22;
    public int textureSize = 32;
    public SpriteBatch batch;
    public Skin skin;
    public String nick;
    public String ip;
    public String port;

    @Override
    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));
//        try {
//            setScreen(new PlayScreen(this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
