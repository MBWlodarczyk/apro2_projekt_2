package Client.Gui_v2;

import Client.Gui_v2.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SwordGame extends Game {

    public int size = 22;
    public int textureSize = 32;
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        try {
            setScreen(new PlayScreen(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
