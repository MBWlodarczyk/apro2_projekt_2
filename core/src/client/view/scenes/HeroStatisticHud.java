package client.view.scenes;

import client.view.utility.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class HeroStatisticHud extends Hud {


    private String s = "";

    public HeroStatisticHud(SpriteBatch spriteBatch, Skin skin) {
        super(spriteBatch, skin);
        s = "";
        label = new Label(s, skin);

        Dialog dialog = new Dialog("", skin);
        dialog.setSize(288, 192);
        dialog.setPosition(Constants.HEIGHT, Constants.TEXTURE_SIZE);
        dialog.setFillParent(false);

        label.setFontScale(0.8f);
        label.setAlignment(Align.center);

        dialog.getContentTable().add(label).left().size(288, 40);

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
        label.setText(s);
    }

    public void updateText(String s) {
        this.s = s;
    }

}

