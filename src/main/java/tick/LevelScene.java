package tick;

public class LevelScene extends AScene {

  LevelScene() {
  }

  @Override
  public void updateScene(float dt) {
    System.out.println("FPS: " + (1.0f / dt));
  }
}
