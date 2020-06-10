package client.view.scenes;

import client.controller.Client;
import client.controller.ControllerState;
import client.controller.HandleInput;
import client.controller.Inputs;
import client.model.map.Field;
import client.view.SwordGame;
import client.view.utility.Drawable;
import client.view.utility.Updatable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static client.controller.Inputs.currentState;
import static client.controller.Inputs.tab;

public class HudManager implements Updatable, Drawable {

    private HeroStatisticHud heroStatisticHud;
    private SkillOptionsHud skillOptionsHud;
    private QueueStateHud queueStateHud;
    private SendRemoveButtonHud sendRemoveButtonHud;
    private SwordGame swordGame;

    public HudManager(SwordGame swordGame, HandleInput handleInput) {
        this.swordGame = swordGame;
        heroStatisticHud = new HeroStatisticHud(swordGame.batch, swordGame.skin);
        skillOptionsHud = new SkillOptionsHud(swordGame.batch, swordGame.skin);
        queueStateHud = new QueueStateHud(swordGame.batch, swordGame.skin);
        sendRemoveButtonHud = new SendRemoveButtonHud(swordGame.batch, swordGame.skin, handleInput);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        sendRemoveButtonHud.draw(swordGame.batch, delta);
        skillOptionsHud.draw(swordGame.batch, delta);
        queueStateHud.draw(swordGame.batch, delta);
        if (Inputs.anyHeroChosen) {
            heroStatisticHud.draw(swordGame.batch, delta);
        }
    }

    @Override
    public void update(float delta) {
        skillOptionsHud.update(delta);
    }

    public void updateText(Client client, HandleInput handleInput) {
        queueStateHud.updateText(client.getSend().toString());
        Field[][] map = client.getReceived().getMap().getFieldsArray();
        if (currentState == ControllerState.HERO_CHOSEN)
            skillOptionsHud.skillOptions(handleInput, map, swordGame.skin);
        if (Inputs.anyHeroChosen) {
            heroStatisticHud.updateText(map[tab[0]][tab[1]].getHero().description());
        }
    }
}
