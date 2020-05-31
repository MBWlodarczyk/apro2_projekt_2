package client.view.scenes;

import client.view.utility.Constants;
import client.view.utility.Drawable;
import client.view.utility.Updatable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class QueueStateHud implements Drawable, Updatable {

    public Stage stage;
    private Viewport viewport;
    private Label queueStatus;
    private String s;

    public QueueStateHud(SpriteBatch spriteBatch, Skin skin) {
        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        queueStatus = new Label("", skin);

        Dialog dialog = new Dialog("", skin);
        dialog.setSize(288, 128);
        dialog.setPosition(Constants.HEIGHT, 8 * Constants.TEXTURE_SIZE);
        dialog.setFillParent(false);

        queueStatus.setFontScale(0.5f);
        queueStatus.setAlignment(Align.center);
        dialog.getContentTable().add(queueStatus).left().size(288, 128);
        stage.addActor(dialog);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        if (!s.equals("")) {
            update(delta);
            stage.draw();
        }
    }

    @Override
    public void update(float delta) {
        queueStatus.setText(s);
    }

    public void updateText(String s) {
        this.s = s;
    }
}
