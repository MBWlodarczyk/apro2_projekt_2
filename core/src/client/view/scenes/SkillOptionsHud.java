package client.view.scenes;

import client.controller.HandleInput;
import client.model.map.Field;
import client.view.utility.Constants;
import client.view.utility.Drawable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class SkillOptionsHud implements Drawable {

    public ArrayList<TextField> textFields;

    public SkillOptionsHud() {
        textFields = new ArrayList<>();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        textFields.forEach(n -> n.draw(batch, 1));
    }

    public void skillOptions(HandleInput handleInput, Field[][] map, Skin skin) {
        TextField text;
        String s;
        int x = 730, y = 60, height = 60, width = 250;
        int[] tab = handleInput.getTab();
        if (map[tab[0]][tab[1]].getHero() != null) {
            int size = map[tab[0]][tab[1]].getHero().getSkills().size();
            for (int i = 0; i < size + 1; i++) { //adding one in order to add exit
                if (i < size)
                    s = map[tab[0]][tab[1]].getHero().getSkills().get(i).toString();
                else
                    s = "Exit";
                text = new TextField(s, skin);
                text.setSize(width, height);
                text.setPosition(x, Constants.HEIGHT - y * (i + 2));
                text.setAlignment(Align.center);
                handleInput.addRectangles(x, y * (i + 1), width, height);
                textFields.add(text);
            }
        } else {
            handleInput.currentState = HandleInput.ControllerState.IDLE; // state to idle if we chose incorrect field
            //this problem could be also solved by changing in handleInput statement if(screenX > Constants.HEIGHT)  and change it to be more precise
        }
    }
}
