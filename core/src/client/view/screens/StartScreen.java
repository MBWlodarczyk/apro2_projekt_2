package client.view.screens;

import client.view.SwordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

public class StartScreen extends AbstractScreen {

    private Sprite background;
    private Sprite text;

    private float elapsed;
    private int direction;
    private TextField textArea;

    public StartScreen(SwordGame swordGame) {
        super(swordGame);
        background = new Sprite(swordGame.background, 0, 0, swordGame.background.getWidth(), swordGame.background.getHeight());
        text = new Sprite(swordGame.nameTexture, 0, 0, swordGame.nameTexture.getWidth(), swordGame.nameTexture.getHeight());
        text.setSize(400, 400);
        text.setPosition(300, 250);
        textArea = new TextField("Press any button",swordGame.skin);
        textArea.setAlignment(Align.center);
        textArea.setPosition(362,50);
        textArea.setSize(300,100);
        elapsed = 0;
        direction = -1;
    }

    @Override
    public void update(float delta) {
        elapsed += delta;
        if (elapsed > 2) {
            elapsed = 0.00001f;
            if (direction == 1)
                direction = -1;
            else
                direction = 1;
        }
        text.setY(text.getY() + (direction * 0.5f) * elapsed);

        if (Gdx.input.isTouched()) {
            swordGame.setScreen(new LoadScreen(swordGame));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        swordGame.batch.begin();

        background.draw(swordGame.batch);
        text.draw(swordGame.batch);
        textArea.draw(swordGame.batch,1);

        swordGame.batch.end();
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
