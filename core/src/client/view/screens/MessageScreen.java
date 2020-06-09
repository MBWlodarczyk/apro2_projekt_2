package client.view.screens;

import client.view.SwordGame;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class MessageScreen extends AbstractScreen {

    private Stage stage;
    private Screen nextScreen;

    public MessageScreen(SwordGame swordGame, String message, Screen nextScreen) {
        super(swordGame);
        this.nextScreen = nextScreen;
        stage = new Stage();

        Image image = new Image(swordGame.background);
        stage.addActor(image);

        Label label = new Label("", swordGame.skin);
        int size = 300;
        Dialog dialog = new Dialog("", swordGame.skin);
        dialog.setPosition(Constants.WIDTH / 2 - size / 2, Constants.HEIGHT / 2 - size / 2);
        dialog.setSize(size, size);
        dialog.setFillParent(false);

        message += "\n\n\n\n\n" + "Click to exit"; //MORE \n MORE

        label.setText(message);
        label.setFontScale(1.1f);
        label.setAlignment(Align.center);

        dialog.getContentTable().add(label).left().size(size, size);

        stage.addActor(dialog);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isTouched()) {
            swordGame.setScreen(nextScreen);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
