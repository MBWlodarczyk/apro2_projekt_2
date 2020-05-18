package Client.GUI.Screens;

import Client.GUI.SwordGame;
import Client.GUI.Utility.Assets;
import Client.Model.Heroes.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class LoadScreen implements Screen {

    private SwordGame swordGame;
    private Stage stage;
    private ArrayList<ImageButton> buttons;
    private Assets assets;
    private Texture paladinTexture, warriorTexture, archerTexture,necromancerTexture,priestTexture,wizardTexture;
    private ArrayList<Hero> heroes;


    public LoadScreen(SwordGame swordGame) {
        this.swordGame = swordGame;
        stage = new Stage();
        heroes = new ArrayList<>();
        buttons = new ArrayList<>();
        heroes = new ArrayList<>(4);

        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();
        if (assets.manager.update()) {
            loadData();
        }
        Gdx.input.setInputProcessor(stage);
        init();
    }


    private void init(){
        heroes();
        nextScreenButton();
        nickInput();
        ipInput();
        portInput();

    }

    private void portInput() {
        TextField textField = new TextField("",swordGame.skin);
        textField.setMessageText("Podaj port");
        textField.setPosition(280,100);
        textField.setSize(200,40);
        stage.addActor(textField);
        swordGame.port = textField.getText();
    }

    private void ipInput() {
        TextField textField = new TextField("",swordGame.skin);
        textField.setMessageText("Podaj ip");
        textField.setPosition(50,100);
        textField.setSize(200,40);
        stage.addActor(textField);
        swordGame.ip = textField.getText();
    }


    private void nickInput(){
        TextField textField = new TextField("",swordGame.skin);
        textField.setMessageText("Podaj nick");
        textField.setPosition(510,100);
        textField.setSize(200,40);
        stage.addActor(textField);
        swordGame.nick = textField.getText();
    }

    private void heroes(){
        addHeroe(archerTexture,10,400);
        addHeroe(necromancerTexture,180,400);
        addHeroe(paladinTexture,330,400);
        addHeroe(priestTexture,480,400);
        addHeroe(warriorTexture,640,400);
        addHeroe(wizardTexture,800,400);
    }

    private void addHeroe(Texture texture,int x, int y){
        TextureRegion textureRegion = new TextureRegion(texture);
        Image background = new Image(textureRegion);
        background.setSize(150,150);
        background.setPosition(x,y);
        background.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                System.out.println(x + " " + y + " "+ pointer);
                return  true;
        }
        });
        stage.addActor(background);

        //image, boolean isT=Selected,   if seclted sprite
    }


    private void nextScreenButton(){
        TextButton button = new TextButton("Next",swordGame.skin);
        button.setSize(100,100);
        button.setPosition(750,50);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    swordGame.setScreen(new PlayScreen(swordGame));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
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

    private void loadData() {
        paladinTexture = assets.manager.get("heroes/paladin.png", Texture.class);
        warriorTexture = assets.manager.get("heroes/warrior.png", Texture.class);
        archerTexture = assets.manager.get("heroes/archer.png", Texture.class);
        necromancerTexture = assets.manager.get("heroes/necromancer.png", Texture.class);
        wizardTexture = assets.manager.get("heroes/wizard.png", Texture.class);
        priestTexture = assets.manager.get("heroes/priest.png", Texture.class);
    }
}
