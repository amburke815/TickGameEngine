package tick;

import java.security.Key;
import org.lwjgl.glfw.GLFW;

public class KeyListener {

  private static final KeyListener singletonInstance = new KeyListener();

  private final boolean keyPressed[] = new boolean[350]; // 350 keys in GLFW

  private KeyListener() {

  }

  public static KeyListener getSingletonInstance() {
    return singletonInstance;
  }

  public static void keyCallback(long window, int keyIdx, int scanCode, int action, int mods) {
    if (action == GLFW.GLFW_PRESS) {
      getSingletonInstance().keyPressed[keyIdx] = true;
    } else if (action == GLFW.GLFW_RELEASE) {
      getSingletonInstance().keyPressed[keyIdx] = false;
    }

  }

  public static boolean isKeyPressed(int keyIdx) {
    if (keyIdx > getSingletonInstance().keyPressed.length - 1 || keyIdx < 0) {
      throw new IllegalArgumentException("Invalid key index. Does not exist");
    }
    return getSingletonInstance().keyPressed[keyIdx];
  }

}
