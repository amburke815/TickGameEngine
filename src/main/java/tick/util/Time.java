package tick.util;

public class Time {
  public static float timeStarted = System.nanoTime(); // time that the application started (static)

  public static float getCurrentTime() {
    return (float)((System.nanoTime() - timeStarted) * 1E-9);
  }
}
