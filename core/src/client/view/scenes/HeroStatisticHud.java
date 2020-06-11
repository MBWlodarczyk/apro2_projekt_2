package client.view.scenes;

import client.view.utility.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class HeroStatisticHud extends Hud {


    public HeroStatisticHud(SpriteBatch spriteBatch, Skin skin) {
        super(spriteBatch, skin);
        Dialog dialog = new Dialog("", skin);
        dialog.setSize(288, 224);
        dialog.setPosition(Constants.HEIGHT, Constants.TEXTURE_SIZE);
        dialog.setFillParent(false);
        text = "";
        label = new Label(text, skin);
        label.setFontScale(0.8f); //scale font
        label.setAlignment(Align.center); //set at center
        dialog.getContentTable().add(label).left().size(288, 224);
        stage.addActor(dialog);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        if (!text.equals("")) { //draw only if the text is not empty
            update(delta);
            stage.draw();
        }
    }

    @Override
    public void update(float delta) {
        label.setText(text);
    }

    /**
     * Update text
     *
     * @param s
     */
    public void updateText(String s) {
        this.text = s;
    }

}

