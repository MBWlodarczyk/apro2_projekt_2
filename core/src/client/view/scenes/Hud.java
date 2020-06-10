package client.view.scenes;

import client.view.utility.Constants;
import client.view.utility.Drawable;
import client.view.utility.Updatable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Abstract instance of HUD
 */
abstract class Hud implements Updatable, Drawable {
    Stage stage;
    Viewport viewport;
    Label label;

    Hud(SpriteBatch spriteBatch, Skin skin) {
        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
    }

}
