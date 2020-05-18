package Client.GUI.Screens;

import Client.GUI.SwordGame;
import Client.GUI.Utility.Assets;
import Client.Model.Heroes.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadScreen implements Screen {

    private SwordGame swordGame;
    private Stage stage;
    private ArrayList<ImageButton> buttons;
    private Assets assets;
    private Texture paladinTexture, warriorTexture, archerTexture,necromancerTexture,priestTexture,wizardTexture,
            paladinTexture_dark, warriorTexture_dark, archerTexture_dark,necromancerTexture_dark,priestTexture_dark,wizardTexture_dark;
    private ArrayList<Texture> textures_white;
    private ArrayList<Texture> textures_dark;
    private TextField ipField;
    private TextField portField;
    private TextField nickField;




    public LoadScreen(SwordGame swordGame) {
        this.swordGame = swordGame;
        stage = new Stage();
        buttons = new ArrayList<>();

        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();
        if (assets.manager.update()) {
            loadData();
        }

        textures_white = new ArrayList<>();
        textures_white.add(archerTexture);
        textures_white.add(necromancerTexture);
        textures_white.add(paladinTexture);
        textures_white.add(priestTexture);
        textures_white.add(warriorTexture);
        textures_white.add(wizardTexture);

        textures_dark = new ArrayList<>();
        textures_dark.add(archerTexture_dark);
        textures_dark.add(necromancerTexture_dark);
        textures_dark.add(paladinTexture_dark);
        textures_dark.add(priestTexture_dark);
        textures_dark.add(warriorTexture_dark);
        textures_dark.add(wizardTexture_dark);


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
        portField = new TextField("",swordGame.skin);
        portField.setMessageText("Podaj port");
        portField.setPosition(280,100);
        portField.setSize(200,40);
        stage.addActor(portField);
        swordGame.port = portField.getText();
    }

    private void ipInput() {
        ipField = new TextField("",swordGame.skin);
        ipField.setMessageText("Podaj ip");
        ipField.setPosition(50,100);
        ipField.setSize(200,40);
        stage.addActor(ipField);
        swordGame.ip = ipField.getText();
    }


    private void nickInput(){
        nickField = new TextField("",swordGame.skin);
        nickField.setMessageText("Podaj nick");
        nickField.setPosition(510,100);
        nickField.setSize(200,40);
        stage.addActor(nickField);
        swordGame.nick = nickField.getText();
    }

    private void heroes(){
        addHeroe(archerTexture_dark,10,400);
        addHeroe(necromancerTexture_dark,180,400);
        addHeroe(paladinTexture_dark,330,400);
        addHeroe(priestTexture_dark,480,400);
        addHeroe(warriorTexture_dark,640,400);
        addHeroe(wizardTexture_dark,800,400);
    }

    private void addHeroe(final Texture texture, int x, int y){
        TextureRegion textureRegion = new TextureRegion(texture);
        final Image background = new Image(textureRegion);
        background.setSize(150,150);
        background.setPosition(x,y);
        background.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int i;
                petelka:for(i = 0; i < textures_dark.size(); i++){
                    if(texture == textures_dark.get(i))
                        break petelka;
                }
                if(!swordGame.chosen[i]){
                    swordGame.chosen[i] = true;
                    background.setDrawable(new SpriteDrawable(new Sprite(textures_white.get(i))));
                }
                else {
                    swordGame.chosen[i] = false;
                    background.setDrawable(new SpriteDrawable(new Sprite(textures_dark.get(i))));
                }
                return  true;
        }
        });
        stage.addActor(background);

    }


    private boolean amountTrue(){
        int count = 0;
        for(int i =0; i < swordGame.chosen.length;i ++){
            if(swordGame.chosen[i])
                count++;
        }
        return count == 4;
    }


    private void nextScreenButton(){
        TextButton button = new TextButton("Next",swordGame.skin);
        button.setSize(100,100);
        button.setPosition(750,50);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    swordGame.ip = ipField.getText();
                    swordGame.nick = nickField.getText();
                    swordGame.port = portField.getText();
                    if(amountTrue() && !swordGame.ip.equals("") & !swordGame.nick.equals("") & !swordGame.port.equals("")) {
                        swordGame.setScreen(new PlayScreen(swordGame));
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

        paladinTexture_dark = assets.manager.get("heroes/paladin_nygga.png", Texture.class);
        warriorTexture_dark = assets.manager.get("heroes/warrior_nygga.png", Texture.class);
        archerTexture_dark = assets.manager.get("heroes/archer_nygga.png", Texture.class);
        necromancerTexture_dark = assets.manager.get("heroes/necromancer_nygga.png", Texture.class);
        priestTexture_dark = assets.manager.get("heroes/priest_nygga.png", Texture.class);
        wizardTexture_dark = assets.manager.get("heroes/wizard_nygga.png",Texture.class);
    }
}
