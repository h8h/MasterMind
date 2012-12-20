package mastermind_core;

public final class validator {

  private String text;
  private boolean b;

  public validator (boolean value, String t) {
    b = value;
    text = t;
  }

  public boolean isValid() {
    return b;
  }
//BLUB
  public String getText() {
    return text;
  }
}
