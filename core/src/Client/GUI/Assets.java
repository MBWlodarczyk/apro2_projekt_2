package Client.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 */
public class Assets implements Disposable {

    public final AssetManager manager = new AssetManager();

    public void load() {
        loadField();
        loadHeroes();
        loadSpecial();
    }

    /**
     * Load fields
     */
    private void loadField(){
        manager.load("field/grass.png", Texture.class);
        manager.load("field/water.png", Texture.class);
        manager.load("field/wall.png", Texture.class);
        manager.load("field/forest.png", Texture.class);
        manager.load("field/bush.png", Texture.class);
        manager.load("field/rock.png", Texture.class);
    }

    /**
     * Load heroes
     */
    private void loadHeroes(){
        manager.load("heroes/paladin.png",Texture.class);
    }

    /**
     * load Special effects
     */
    private void loadSpecial(){
        manager.load("special/edge.png",Texture.class);
    }


    @Override
    public void dispose() {
        manager.dispose();
    }
}
