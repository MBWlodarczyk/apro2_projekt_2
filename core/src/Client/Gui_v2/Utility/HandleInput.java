package Client.Gui_v2.Utility;

import Client.Controller.DistanceValidator;
import Client.Controller.Move;
import Client.Gui_v2.Screens.PlayScreen;
import Client.Gui_v2.SwordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class HandleInput implements InputProcessor {
    private boolean heroChosen;
    private boolean skillChosen;
    private PlayScreen game;
    private int x,y;

    public HandleInput(PlayScreen game) {
        this.heroChosen = false;
        this.skillChosen = false;
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int[] temp = getCord(screenX,screenY);
        if(!heroChosen && game.client.getReceived().getMap()[temp[0]][temp[1]].getHero()!= null) {
           heroChosen = true;
           this.y = temp[0];
           this.x = temp[1];
           return true;
            //System.out.println(game.client.getReceived().getMap()[temp[0]][temp[1]].getHero().getSkills());
        }
        if(heroChosen) {
            Move move = new Move(game.client.getReceived().getMap()[y][x].getHero(), game.client.getReceived().getMap()[temp[0]][temp[1]], game.client.getReceived().getMap()[y][x], game.client.getReceived().getMap()[y][x].getHero().getSkills().get(0));
            if (DistanceValidator.isValid(game.client.getReceived(), move)) {
                System.out.println("trying to send");
                game.client.setSend(move);
                heroChosen = false;
                return true;
            }
        }


        return false;
    }

    private int[] getCord(int x, int y){
        int[] result = new int[2];
        for (int i = 0; i < game.size;i++) {
            for ( int j = 0; j < game.size; j++) {
                if (game.getGameObjects()[i][j].contains(x, 704 - y)) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
