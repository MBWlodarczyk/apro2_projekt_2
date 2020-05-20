package Client.Controller;

import Client.GUI.Screens.PlayScreen;
import Client.GUI.Utility.Constants;
import Client.Model.map.Field;
import com.badlogic.gdx.InputProcessor;


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

    public HandleInput(PlayScreen game, int size) {
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
        if (screenX <= Constants.HEIGHT) {
            getCord(screenX, screenY); //zwraca cordy gdzie przycisnelismy
            Field field = game.client.getReceived().getMap()[tab[0]][tab[1]];
            if (!heroChosen && field.getHero() != null && field.getHero().getOwner().getNick().equals(game.swordGame.player.getNick())) {
                heroChosen = true;
                this.y = tab[0];
                this.x = tab[1];
                return true;
            }
            if (heroChosen) {
                Move move = new Move(game.client.getReceived().getMap()[y][x].getHero(), field, game.client.getReceived().getMap()[y][x], game.client.getReceived().getMap()[y][x].getHero().getSkills().get(0));
                if (DistanceValidator.isValid(game.client.getReceived(), move)) {
                    if(!GameEngine.checkMove(move,game.client.getSend().getMoves())) {
                        game.client.getSend().addMove(move);
                        System.out.println("Adding move...");
                    }
                    System.out.println(game.client.getSend().getMoves().size());
                    heroChosen = false;
                    return true;
                }
            }
        }
        return false;
    }

    private void getCord(int screenX, int screenY) {
        tab[0] = (screenX - screenX % Constants.TEXTURE_SIZE)/Constants.TEXTURE_SIZE;
        tab[1] = (screenY - screenY % Constants.TEXTURE_SIZE)/Constants.TEXTURE_SIZE;
        System.out.println(tab[0] + " " + tab[1]);
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
