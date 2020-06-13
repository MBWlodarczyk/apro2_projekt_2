package client.view.screens;

import client.view.SwordGame;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

import static client.controller.Inputs.*;

public class LoadScreen extends AbstractScreen {

    private Stage stage;
    private ArrayList<Texture> textures_white;
    private ArrayList<Texture> textures_dark;
    private TextField ipField;
    private TextField portField;
    private TextField nickField;
    private TextField passwordField;
    private int WIDTH = 200;
    private int HEIGHT = 80;


    /**
     * Constructor which take sword game to use batch etc...
     *
     * @param swordGame instance of swordgame
     */
    public LoadScreen(SwordGame swordGame) {
        super(swordGame);
        this.swordGame = swordGame;
        stage = new Stage();  //initialize stage
        Gdx.input.setInputProcessor(stage);  //set stage as a input processor
        init();
    }

    @Override
    public void update(float delta) {

    }

    /**
     * Init all methods
     */
    private void init() {
        texturesToArrays();
        addBackground();
        heroes();
        //text inputs
        inputFields();
        //buttons
        nextScreenButton();
        reconnectButton();
        addMusic();
        //inscription
        text();
    }

    private void text() {
        TextField textField = new TextField("Chose your heroes!", swordGame.skin);
        textField.setAlignment(Align.center);
        textField.setSize(400, 100);
        textField.setPosition(312, 570);
        stage.addActor(textField);
    }

    private void texturesToArrays() {
        textures_white = new ArrayList<>();
        textures_white.add(swordGame.archerTexture);
        textures_white.add(swordGame.necromancerTexture);
        textures_white.add(swordGame.paladinTexture);
        textures_white.add(swordGame.priestTexture);
        textures_white.add(swordGame.warriorTexture);
        textures_white.add(swordGame.wizardTexture);

        textures_dark = new ArrayList<>();
        textures_dark.add(swordGame.archerTexture_dark);
        textures_dark.add(swordGame.necromancerTexture_dark);
        textures_dark.add(swordGame.paladinTexture_dark);
        textures_dark.add(swordGame.priestTexture_dark);
        textures_dark.add(swordGame.warriorTexture_dark);
        textures_dark.add(swordGame.wizardTexture_dark);
    }

    private void inputFields() {
        int x = 50, y = 150;
        ipInput(x, y);
        portInput(x += 230, y);
        nickInput(x += 230, y);
        passwordInput(x += 230, y);
    }

    private void addBackground() {
        TextureRegion textureRegion = new TextureRegion(swordGame.background);
        final Image background = new Image(textureRegion);
        background.setSize(Constants.WIDTH, Constants.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }

    private void addMusic() {
        swordGame.theme.setVolume(0.7f);
        swordGame.theme.setLooping(true);
        swordGame.theme.play();
    }

    private void ipInput(int x, int y) {
        ipField = new TextField("", swordGame.skin);
        ipField.setMessageText("IP");
        ipField.setPosition(x, y);
        ipField.setSize(WIDTH, HEIGHT);
        ipField.setAlignment(Align.center);
        stage.addActor(ipField);
    }

    private void portInput(int x, int y) {
        portField = new TextField("", swordGame.skin);
        portField.setMessageText("Port");
        portField.setPosition(x, y);
        portField.setSize(WIDTH, HEIGHT);
        portField.setAlignment(Align.center);
        stage.addActor(portField);
    }

    private void nickInput(int x, int y) {
        nickField = new TextField("", swordGame.skin);
        nickField.setMessageText("Nick");
        nickField.setPosition(x, y);
        nickField.setSize(WIDTH, HEIGHT);
        nickField.setAlignment(Align.center);
        stage.addActor(nickField);
    }

    private void passwordInput(int x, int y) {
        passwordField = new TextField("", swordGame.skin);
        passwordField.setMessageText("Password");
        passwordField.setPosition(x, y);
        passwordField.setSize(WIDTH, HEIGHT);
        passwordField.setAlignment(Align.center);
        stage.addActor(passwordField);
    }

    private void heroes() {
        int y = 350;        //y param of all heroes
        int x = 10;         //start x of heroes positions (then add 170 to all)
        int size = 150;     //size of a texture
        addHero(swordGame.archerTexture_dark, x, y, size);
        addHero(swordGame.necromancerTexture_dark, x += 170, y, size);
        addHero(swordGame.paladinTexture_dark, x += 170, y, size);
        addHero(swordGame.priestTexture_dark, x += 170, y, size);
        addHero(swordGame.warriorTexture_dark, x += 170, y, size);
        addHero(swordGame.wizardTexture_dark, x += 170, y, size);
    }

    private void addHero(final Texture texture, int x, int y, int size) {
        TextureRegion textureRegion = new TextureRegion(texture);
        final Image background = new Image(textureRegion);
        background.setSize(size, size);
        background.setPosition(x, y);
        background.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int i;
                for (i = 0; i < textures_dark.size(); i++) {
                    if (texture == textures_dark.get(i))
                        break;
                }
                if (!chosen[i]) {
                    chosen[i] = true;
                    background.setDrawable(new SpriteDrawable(new Sprite(textures_white.get(i))));
                } else {
                    chosen[i] = false;
                    background.setDrawable(new SpriteDrawable(new Sprite(textures_dark.get(i))));
                }
                return true;
            }
        });
        stage.addActor(background);
    }


    private boolean amountTrue() {
        int count = 0;
        for (int i = 0; i < chosen.length; i++) {
            if (chosen[i])
                count++;
        }
        return count == 4;
    }

    private void nextScreenButton() {
        TextButton button = new TextButton("Join new game", swordGame.skin);
        button.setSize(100, 100);
        button.setPosition(750, 20);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    ip = ipField.getText();
                    nick = nickField.getText();
                    port = portField.getText();
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    password = md.digest(passwordField.getText().getBytes(StandardCharsets.UTF_8));
                    if (amountTrue() && !ip.equals("") & !nick.equals("") & !port.equals("") & !passwordField.getText().equals("")) {
                        WaitScreen ws = new WaitScreen(swordGame, true);
                        if (ws.connected) {
                            swordGame.setScreen(ws);
                            swordGame.theme.dispose();
                        } else {
                            swordGame.setScreen(new MessageScreen(swordGame, "Can not connect!", new LoadScreen(swordGame)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }

    private void reconnectButton() {
        TextButton button = new TextButton("Reconnect", swordGame.skin);
        button.setSize(100, 100);
        button.setPosition(100, 20);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ip = ipField.getText();
                nick = nickField.getText();
                port = portField.getText();
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-512");
                    password = md.digest(passwordField.getText().getBytes(StandardCharsets.UTF_8));
                    if (!ip.equals("") & !nick.equals("") & !port.equals("") & !passwordField.getText().equals("")) {
                        WaitScreen ws = new WaitScreen(swordGame, false);
                        if (ws.connected) {
                            swordGame.setScreen(ws);
                            swordGame.theme.dispose();
                        } else {
                            swordGame.setScreen(new MessageScreen(swordGame, "Can not connect!", new LoadScreen(swordGame)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        swordGame.batch.begin();
        stage.draw();
        swordGame.batch.end();
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

    @Override
    public void show() {

    }
}
