package client.view.screens;

import client.controller.Client;
import client.view.SwordGame;
import client.view.utility.GifDecoder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.net.ConnectException;
import java.util.Objects;

public class WaitScreen implements Screen {

    private SwordGame swordGame;
    private Client client;
    private Animation<TextureRegion> animation;
    private float elapsed;

    public WaitScreen(SwordGame swordGame, boolean init) throws Exception {
        this.swordGame = swordGame;
        try {
            this.client = new Client(swordGame, init);
            animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("special/load.gif").read());
        } catch (ConnectException e){
            System.out.println("can't connect"); //TODO change screen to loadscreen here - problem with update
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        swordGame.batch.begin();
        swordGame.batch.draw(animation.getKeyFrame(elapsed), 272.0f, 112.0f);
        swordGame.batch.end();
    }

    private void update() {
             if (client.isReceived) {
                swordGame.setScreen(new PlayScreen(swordGame, client));
            }
             if (client.wrongPass) {
                client.dispose(); //TODO add popupwindow saying wrong pass
                swordGame.setScreen(new LoadScreen(swordGame));
            }
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
