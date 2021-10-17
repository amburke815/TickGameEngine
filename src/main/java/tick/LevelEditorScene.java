package tick;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends AScene {

  private boolean changingScene = false;
  private float timeToChangeScene = 2.0f;
  @Override
  public void updateScene(float dt) {

    if (KeyListener.isKeyPressed(KeyEvent.VK_B) && !changingScene) {
      changingScene = true;
    }

    if (changingScene && timeToChangeScene > 0) {
      timeToChangeScene -= dt;
      Window.getSingletonInstance().r -= dt * 5.0f;
      Window.getSingletonInstance().g -= dt * 3.0f;
      Window.getSingletonInstance().b -= dt * 5.0f;

    }

    else if (changingScene) {
      Window.changeScene(1);
    }

    System.out.println("FPS: " + (1.0f / dt));

  }
}
