package client.view.scenes;

import client.view.utility.Constants;
import client.view.utility.Drawable;
import client.view.utility.Updatable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HeroStatisticHud implements Drawable, Updatable {


    public Stage stage;
    private Viewport viewport;
    private Label statistic;
    private String s;

    public HeroStatisticHud(SpriteBatch spriteBatch, Skin skin) {
        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch); //do organizaji widgetów (label)
        statistic = new Label("", skin);

        skin.get("health", ProgressBarStyle.class).knobBefore = skin.getTiledDrawable("progress-bar-health-knob");
        skin.get("health", ProgressBarStyle.class).knobBefore.setMinHeight(50);
        skin.get("health", ProgressBarStyle.class).knobBefore.setMinHeight(200);


        Dialog dialog = new Dialog("", skin);
        dialog.setSize(288, 192);
        dialog.setPosition(Constants.HEIGHT, Constants.TEXTURE_SIZE);
        dialog.setFillParent(false);


//        ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin, "health");
//        progressBar.setValue(100);
//        dialog.getContentTable().add(progressBar).left().size(200,50);


//        dialog.getContentTable().add(progressBar);


        statistic.setFontScale(0.9f);
        statistic.setAlignment(Align.center);

        dialog.getContentTable().add(statistic).left().size(288, 40);

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
        statistic.setText(s);
    }

    public void updateText(String s) {
        this.s = s;
    }

}

