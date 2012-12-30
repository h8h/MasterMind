package mastermind_core;

/**
 * Boolean with Text
 */
public class validator {

  private String text;
  private boolean b;

  /**
   * Class construction
   *
   * @param value fake Boolean
   * @param t for help text purpose
   */
  public validator (boolean value, String t) {
    b = value;
    text = t;
  }

  /**
   * Do some boolean faking
   * @return <code>true</code> if true<br>
   *         <code>false</code> if false
   */
  public boolean isValid() {
    return b;
  }

  /**
   * Get the help text created by the bot
   *
   * @return help text which should help to solve mastermind ;) ... or not
   * @see bot#validate()
   */
  public String getText() {
    return text;
  }
}
