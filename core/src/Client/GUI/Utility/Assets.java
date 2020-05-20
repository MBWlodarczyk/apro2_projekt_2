package Client.GUI.Utility;

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
        loadObstacles();
    }

    /**
     * Load fields
     */
    private void loadField() {
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
    private void loadHeroes() {
        manager.load("heroes/paladin.png", Texture.class);
        manager.load("heroes/warrior.png", Texture.class);
        manager.load("heroes/archer.png", Texture.class);
        manager.load("heroes/necromancer.png", Texture.class);
        manager.load("heroes/priest.png", Texture.class);
        manager.load("heroes/wizard.png", Texture.class);

        manager.load("heroes/paladin_nygga.png", Texture.class);
        manager.load("heroes/warrior_nygga.png", Texture.class);
        manager.load("heroes/archer_nygga.png", Texture.class);
        manager.load("heroes/necromancer_nygga.png", Texture.class);
        manager.load("heroes/priest_nygga.png", Texture.class);
        manager.load("heroes/wizard_nygga.png", Texture.class);
    }

    /**
     * load Special effects
     */
    private void loadSpecial() {
        manager.load("special/edge.png", Texture.class);
        manager.load("special/health.png", Texture.class);
        manager.load("special/background.png", Texture.class);
        manager.load("special/bord.png", Texture.class);
        manager.load("special/cross.png", Texture.class);
        manager.load("special/skillPanel.png", Texture.class);
    }

    private void loadObstacles() {
        manager.load("obstacles/trap.png", Texture.class);
    }


    @Override
    public void dispose() {
        manager.dispose();
    }
}
