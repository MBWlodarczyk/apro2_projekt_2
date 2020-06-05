package client.view.scenes;

import client.controller.HandleInput;
import client.model.map.Field;
import client.view.utility.Constants;
import client.view.utility.Drawable;
import client.view.utility.Updatable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static client.controller.Inputs.tab;

public class SkillOptionsHud implements Drawable, Updatable {

    public Stage stage;
    private Viewport viewport;

    public SkillOptionsHud(SpriteBatch spriteBatch, Skin skin) {
        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        stage.draw();
    }

    public void skillOptions(HandleInput handleInput, Field[][] map, Skin skin) {
        TextField text;
        String s;
        int x = 704, y = 32, height = 32, width = 288;
        if (map[tab[0]][tab[1]].getHero() != null) {
            int size = map[tab[0]][tab[1]].getHero().getSkills().size();
            for (int i = 0; i < size; i++) {
                s = map[tab[0]][tab[1]].getHero().getSkills().get(i).toString();
                text = new TextField(s, skin);
                text.setSize(width, height);
                text.setPosition(x, Constants.HEIGHT - y * (i + 2));
                text.setAlignment(Align.center);
                text.scaleBy(0.6f, 0.6f);
                handleInput.addRectangles(x, y * (i + 1), width, height);
                stage.addActor(text);
            }
        } else {
            handleInput.currentState = HandleInput.ControllerState.IDLE;
        }
    }

    @Override
    public void update(float delta) {
        for (Actor actor : stage.getActors()) {
            actor.remove();
        }
    }
}
