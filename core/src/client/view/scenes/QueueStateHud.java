package client.view.scenes;

import client.view.utility.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class QueueStateHud extends Hud {


    public QueueStateHud(SpriteBatch spriteBatch, Skin skin) {
        super(spriteBatch, skin);
        label = new Label("", skin);

        Dialog dialog = new Dialog("", skin);
        dialog.setSize(288, 128);
        dialog.setPosition(Constants.HEIGHT, 9 * Constants.TEXTURE_SIZE);
        dialog.setFillParent(false);
        label.setFontScale(0.5f);
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label).left().size(288, 128);
        stage.addActor(dialog);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        if (!text.equals("")) {
            update(delta);
            stage.draw();
        }
    }

    @Override
    public void update(float delta) {
        label.setText(text);
    }

    public void updateText(String s) {
        this.text = s;
    }
}
