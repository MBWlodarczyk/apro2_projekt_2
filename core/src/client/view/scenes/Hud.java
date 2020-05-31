package client.view.scenes;

import client.controller.HandleInput;
import client.view.utility.Constants;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public abstract class Hud {
    public Stage stage;
    private Viewport viewport;

    public Hud(SpriteBatch spriteBatch, Skin skin, HandleInput handleInput) {
        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
    }

}
