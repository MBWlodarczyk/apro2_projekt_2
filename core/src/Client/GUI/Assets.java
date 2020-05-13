package Client.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 */
public class Assets implements Disposable {

    public final AssetManager manager = new AssetManager();

    public void lodad() {
        manager.load("grass.png", Texture.class);
        manager.load("water.png", Texture.class);
        manager.load("wall.png", Texture.class);
        manager.load("forest.png", Texture.class);
        manager.load("bush.png", Texture.class);
        manager.load("rock.png", Texture.class);
        manager.load("covered/grass_covered.png",Texture.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
