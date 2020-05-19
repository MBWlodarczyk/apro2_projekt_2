package Client.Controller;

import Client.GUI.Utility.Constants;
import Client.GUI.Screens.PlayScreen;
import com.badlogic.gdx.InputProcessor;

import java.util.Arrays;


public class HandleInput implements InputProcessor {

    public boolean heroChosen;
    private boolean skillChosen;
    private PlayScreen game;
    private int size;
    private int x, y;
    private int[] tab = new int[2];

    public int[] getTab() {
        return tab;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public HandleInput(PlayScreen game,int size) {
        this.heroChosen = false;
        this.skillChosen = false;
        this.game = game;
        this.size = size;
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
        if(screenX<=704) {
            getCord(screenX, screenY);
            getCord(screenX, screenY); //zwraca cordy gdzie przycisnelismy
            if (!heroChosen&&game.client.getReceived().getMap()[tab[0]][tab[1]].getHero()!=null && game.client.getReceived().getMap()[tab[0]][tab[1]].getHero().getOwner().getNick().equals(game.swordGame.player.getNick())) {
                heroChosen = true;
                this.y = tab[0];
                this.x = tab[1];
                return true;
            }
            if (heroChosen) {
                Move move = new Move(game.client.getReceived().getMap()[y][x].getHero(), game.client.getReceived().getMap()[tab[0]][tab[1]], game.client.getReceived().getMap()[y][x], game.client.getReceived().getMap()[y][x].getHero().getSkills().get(0));
                if (DistanceValidator.isValid(game.client.getReceived(), move)) {
                    game.client.getSend().addMove(move);
                    System.out.println("Adding move...");
                    System.out.println(game.client.getSend().getMoves().size());
                    heroChosen = false;
                    return true;
                }
            }
        }
        return false;
    }

    public void getCord(int x, int y) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (game.getGameObjects()[i][j].contains(x, Constants.HEIGHT - y)) {
                    tab[0] = i;
                    tab[1] = j;
                }
            }
        }
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
