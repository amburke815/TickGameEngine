import tick.Window;

public class Main {

  public static void main(String[] args) {
    System.out.println("startup...");
    Window window = Window.getSingletonInstance(); // singleton
    window.run();

  }
}
