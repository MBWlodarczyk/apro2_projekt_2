package client.controller;

import client.model.map.Field;
import client.view.screens.PlayScreen;
import client.view.utility.Constants;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static client.controller.HandleInput.ControllerState.*;

/**
 * Class to handle all input from user
 */
public class HandleInput implements InputProcessor {

    public ControllerState currentState;
    private int skillChosen;
    private PlayScreen playScreen;
    private int x, y;
    private int[] tab = new int[2];
    private Field field;
    private ArrayList<Rectangle> rectangles;

    public HandleInput(PlayScreen playScreen) {
        this.currentState = IDLE;
        this.playScreen = playScreen;
        this.rectangles = new ArrayList<>();
    }

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

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < Constants.HEIGHT) {
            getCord(screenX, screenY); //zwraca cordy gdzie przycisnelismy
            field = playScreen.client.getReceived().getMap().getFieldsArray()[tab[0]][tab[1]];
            if (currentState == IDLE && field.getHero() != null && field.getHero().getOwner().getNick().equals(playScreen.swordGame.player.getNick())) {
                currentState = HERO_CHOSEN;
                this.y = tab[0];
                this.x = tab[1];
                return true;
            }
        }
        if (currentState == HERO_CHOSEN) {
            for (int i = 0; i < rectangles.size(); i++) {
                if (rectangles.get(i).contains(screenX, screenY)) {
                    skillChosen = i;
                    currentState = PERFORM_SKILL;
                    if (skillChosen == rectangles.size() - 1) { // if the last rectangle is chosen (exit) then and go to idle
                        currentState = IDLE;
                        return true;
                    }
                    return true;
                }
            }
        }
        if (currentState == PERFORM_SKILL) {
            performSkill(skillChosen);
            currentState = IDLE;
            return true;
        }
        return true;
    }

    private void performSkill(int index) {
        System.out.println("performed skill: " + index);
        Field[][] fieldsArray = playScreen.client.getReceived().getMap().getFieldsArray();
        Move move = new Move(fieldsArray[y][x].getHero(), field, fieldsArray[y][x], fieldsArray[y][x].getHero().getSkills().get(index));
        if (GameEngine.isValid(playScreen.client.getReceived().getMap(), move)) {
            if (!GameEngine.checkMove(move, playScreen.client.getSend().getMoves())) {
                playScreen.client.getSend().addMove(move);
                System.out.println("Adding move...");
//                System.out.println(move);
            }
            System.out.println(playScreen.client.getSend().getMoves().size());
        }
    }

    public void getCord(int screenX, int screenY) {
        if (screenX < Constants.HEIGHT) {
            tab[0] = (screenX - screenX % Constants.TEXTURE_SIZE) / Constants.TEXTURE_SIZE;
            tab[1] = (screenY - screenY % Constants.TEXTURE_SIZE) / Constants.TEXTURE_SIZE;
        }
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

    public enum ControllerState {
        IDLE(0),
        HERO_CHOSEN(1),
        PERFORM_SKILL(2),
        ;

        ControllerState(int index) {
        }
    }
}
