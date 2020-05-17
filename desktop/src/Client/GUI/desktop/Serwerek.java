package Client.GUI.desktop;

import Server.SerwerScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Serwerek {
    public static void main(String[] arg) throws Exception {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 50;
        config.height = 50;
        config.resizable = false;
        new LwjglApplication(new SerwerScreen(), config);
    }
}
