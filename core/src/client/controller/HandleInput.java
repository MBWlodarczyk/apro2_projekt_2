package client.controller;

import client.model.map.Field;
import client.view.screens.PlayScreen;
import client.view.utility.Constants;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Class to handle all input from user
 */
public class HandleInput implements InputProcessor {

    public boolean heroChosen;
    private int skillChosen;
    private PlayScreen game;
    private int x, y;
    private int[] tab = new int[2];
    private Field field;
    private ArrayList<Rectangle> rectangles;

    public int[] getTab() {
        return tab;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSkillChosen() {
        return skillChosen;
    }

    public void setSkillChosen(int skillChosen) {
        this.skillChosen = skillChosen;
    }

    public void addRectangles(float x, float y, float width, float height) {
        this.rectangles.add(new Rectangle(x, y, width, height));
    }

    public HandleInput(PlayScreen game) {
        this.heroChosen = false;
        this.game = game;
        this.rectangles = new ArrayList<>();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < Constants.HEIGHT) {
            getCord(screenX, screenY); //zwraca cordy gdzie przycisnelismy
            field = game.client.getReceived().getMap().getFieldsArray()[tab[0]][tab[1]];
            if (!heroChosen && field.getHero() != null && field.getHero().getOwner().getNick().equals(game.swordGame.player.getNick())) {
                heroChosen = true;
                this.y = tab[0];
                this.x = tab[1];
                return true;
            }
        }
        if (heroChosen) {
            for (int i = 0; i < rectangles.size(); i++) {
                if (rectangles.get(i).contains(screenX, screenY)) {
                    skillChosen = i;
                    break;
                }
            }
            if (skillChosen == rectangles.size()-1) {
                heroChosen = false;
                return true;
            }
            performSkill(skillChosen);
            heroChosen = false;
        }

        return false;
    }

    private void performSkill(int index) {
        Field[][] fieldsArray = game.client.getReceived().getMap().getFieldsArray();
        Move move = new Move(fieldsArray[y][x].getHero(), field, fieldsArray[y][x], fieldsArray[y][x].getHero().getSkills().get(index));
        if (GameEngine.isValid(game.client.getReceived().getMap(), move)) {
            if (!GameEngine.checkMove(move, game.client.getSend().getMoves())) {
                game.client.getSend().addMove(move);
                System.out.println("Adding move...");
                System.out.println(move);
            }
            System.out.println(game.client.getSend().getMoves().size());
        }
    }

    private void getCord(int screenX, int screenY) {
        tab[0] = (screenX - screenX % Constants.TEXTURE_SIZE) / Constants.TEXTURE_SIZE;
        tab[1] = (screenY - screenY % Constants.TEXTURE_SIZE) / Constants.TEXTURE_SIZE;
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
