package client.view.screens;

import client.view.SwordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public abstract class AbstractScreen implements Screen {
    public SwordGame swordGame;

    public AbstractScreen(SwordGame swordGame) {
        this.swordGame = swordGame;
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
