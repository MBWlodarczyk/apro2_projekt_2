package client.view.screens;

import client.view.SwordGame;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class HelloScreen implements Screen {

    private SwordGame swordGame;
    private Stage stage;
    private Texture background, name;
    private int WIDTH = 200;
    private int HEIGHT = 80;

    public HelloScreen(SwordGame swordGame) {
        this.swordGame = swordGame;
        stage = new Stage();  //initialize stage
        loadData(); //load all textures using AssetManager
        Gdx.input.setInputProcessor(stage);  //set stage as a input processor
        init();
    }

    /**
     * Init all methods
     */
    private void init() {
        addBackground();
        addName(name, 50, 150, 100);
    }

    private void addBackground() {
        TextureRegion textureRegion = new TextureRegion(background);
        final Image background = new Image(textureRegion);
        background.setSize(Constants.WIDTH, Constants.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }

    private void addName(final Texture texture, int x, int y, int size) {
        TextureRegion textureRegion = new TextureRegion(texture);
        final Image background = new Image(textureRegion);
        background.setSize(size, size);
        background.setPosition(x, y);
        stage.addActor(background);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        swordGame.batch.begin();
        stage.draw();
        swordGame.batch.end();
        update();
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
        stage.dispose();
    }

    private void loadData() {
        background = swordGame.assets.manager.get("special/background.png", Texture.class);
        name = swordGame.assets.manager.get("special/name.png", Texture.class);
    }

    private void update() {
        swordGame.setScreen(new LoadScreen(swordGame));
    }
}
