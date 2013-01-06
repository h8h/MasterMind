package mastermind_core;

import java.util.ArrayList;
import java.util.Random;

class bot {
  /**
   * Size of the population within a generation.
   */
  private final int POPULATION_SIZE = 2000;
  /**
   * Number of generations. If no feasible code was found after all
   * generations, a new guess with new generations and populations will be
   * made.
   */
  private final int GENERATION_SIZE = 500;
  core mm_core;
  boolean makeValidate;

    private final int FEASIBLE_CODES_MAX = 1;
    private String[][] population;
    private int[] fitness = new int[POPULATION_SIZE];
    private ArrayList<String[]> feasibleCodes = new ArrayList<String[]>();
    private int parentPos = 0;

  /**
   * Class construction
   * @param co core class
   */
  public bot (core co) {
    mm_core = co;
    population = new String[POPULATION_SIZE][mm_core.getCodeSize()];
  }

  /**
   * Check whether the bot should fill out empty pins - fill out empty pins or do some validate if the user wish some help
   *
   * @see core#doitBot()
   */
  protected void setBestColors () {

    int space=0;
    makeValidate = false;

    for(int i=0; i < mm_core.getCodeSize(); i++) {
      if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
        space++;
      }
    }
    if (space==mm_core.getCodeSize()){//find a code
      String[] turn = generateTurn();
      mm_core.setArAt(turn);
    }else if (space>0){	//fill the hole
      String[] turn = generateTurn();
      String[] hturn = mm_core.getArAt(0);
      outer:
      for (int i=0; i<mm_core.getCodeSize();i++){
        for (int j=0; j<mm_core.getCodeSize(); j++){
          if(turn[i].equals(hturn[j])) {
            turn[i] = null;
            hturn[j] = null;
            continue outer;
          }
        }
      }
      outer2:
      for(int i=0; i < mm_core.getCodeSize(); i++) {
        if(mm_core.getValueAt(i) == null || mm_core.getValueAt(i).equals("")) {
          for(int j=0; j < mm_core.getCodeSize(); j++) {
            if(turn[j] != null) {
              mm_core.setValueAt(turn[j],i);
              turn[j] = null;
              continue outer2;
            }
          }
        }
      }

      for(int i=0; i < mm_core.getCodeSize(); i++) {
        if (mm_core.getValueAt(i).equals("")){
          mm_core.setValueAt(mm_core.getEnabledColors()[0],i);
        }
      }
    }else{ //validate
      makeValidate = true;
    }
  }

  /**
   * generate a turn which are able to set to a new row
   *
   * @return generated turn
   *
   * @see #initPopulation()
   * @see #calcFitness()
   * @see #sortFeasibleByFitness(int[], String[][])
   * @see #evolvePopulation()
   * @see #addToFeasibleCodes()
   */

    public String[] generateTurn() {
        String[] turn = new String[mm_core.getCodeSize()];
        boolean doCalc;
        // First try
        if (mm_core.data.size() == 1) {
            return generateRndColor();
        }
        do {
            int genNumber = 0;
            doCalc = true;
            initPopulation();
            calcFitness();
            sortFeasibleByFitness(fitness, population);

            while (doCalc == true && genNumber <= GENERATION_SIZE && feasibleCodes.size() <= FEASIBLE_CODES_MAX) {
                parentPos = 0;
                evolvePopulation();
                calcFitness();
                sortFeasibleByFitness(fitness, population);
                doCalc = addToFeasibleCodes();
                genNumber++;
            }
        } while (feasibleCodes.isEmpty() == true);
        // Choose a turn.
        turn = feasibleCodes.get((int) (Math.random() * feasibleCodes.size()));
        return turn;
    }

    /**
     * Initializes the Population with random turns
     *
     * @see #generateRndColor()
     */

    private void initPopulation() {
        int i = 0;
        feasibleCodes.clear();
        while (i < POPULATION_SIZE) {
            population[i] = generateRndColor();
            i++;
        }
    }



    private boolean addToFeasibleCodes() {
      int[][]cHints = countHints();
      outer:
      for (int i = 0; i < POPULATION_SIZE; i++) {
          for (int j = 1; j < mm_core.data.size(); j++) {
            int[] result = compare(population[i], mm_core.getArAt(j));
            if (result[0] != cHints[j][0] || result[1] != cHints[j][1]){
                continue outer;
            }
          }
          if (feasibleCodes.size() < FEASIBLE_CODES_MAX) {
            if (feasibleCodes.contains(population[i]) == false) {
                feasibleCodes.add(population[i]);
                if (feasibleCodes.size() < FEASIBLE_CODES_MAX) {
                    return false;
                }
            }
          } else {
            return false;
          }
        }
        return true;
    }
    private String[] generateRndColor() {
      String[] colors = new String[mm_core.getCodeSize()];
      int randomizeColor = 0;
      Random r = new Random();
      for (int i = 0; i < mm_core.getCodeSize(); i++) {
        randomizeColor = r.nextInt(mm_core.getEnabledColorsSize());
        colors[i] = mm_core.getEnabledColors()[randomizeColor];
      }
      return colors;
    }

    private void calcFitness() {
        int xtmp;
        int ytmp;
        int[][] cHints = countHints();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            xtmp = 0;
            ytmp = 0;
            for (int j = 1; j < mm_core.data.size(); j++) {
                int[] result = compare(population[i], mm_core.getArAt(j));
                xtmp += Math.abs(result[0] - cHints[j][0]);// black
                ytmp += Math.abs(result[1] - cHints[j][1]);// red
            }
            fitness[i] = (xtmp + ytmp);
        }
    }

    /**
     * This is a Quicksort that sorts the fitness and pop Arrays by the criteria
     * in the fitness-array.
     *
     * @param fitness An int array.
     * @param pop an array of lines.
     *
     * @see #sort(int[], String[][], int, int)
     * @see #divide(int[], String[][], int, int, int)
     * @see #swap(String[][], int, int)
     * @see #swap(int[], int, int)
     */
    private void sortFeasibleByFitness(int[] fitness, String[][] pop) {
        sort(fitness, pop, 0, fitness.length - 1);
    }

    /**
     * Helper function for recursive sorting.
     *
     * @param fitness An int array.
     * @param pop .
     * @param low The lower limit.
     * @param up The upper limit.
     */
   private void sort(int[] fitness, String[][] pop, int low, int up) {
         int p = (low + up) / 2;
          if (up > low) {
              //Feld zerlegen
              int pn = divide(fitness, pop, low, up, p);
              //und sortieren
              sort(fitness, pop, low, pn - 1);
              sort(fitness, pop, pn + 1, up);
          }
      }

   /**
    * Helper function for partitioning.
    *
    * @param fitness An int array.
    * @param pop An array of Rows.
    * @param low The lower limit.
    * @param up The upper limit.
    * @param pivot The position of the Pivot element.
    *
    * @return The new Position of the Pivot element.
    */
    private int divide(int[] fitness, String[][] pop, int low, int up, int pivot) {
        int pn = low;
        int pv = fitness[pivot];
        swap(fitness, pivot, up);
        swap(pop, pivot, up);
        for (int i = low; i < up; i++) {
            if (fitness[i] <= pv) {
                swap(fitness, pn, i);
                swap(pop, pn++, i);
            }
            swap(fitness, up, pn);
            swap(pop, up, pn);
        }
        return pn;
    }

    /**
     * Helper function to swap two elements of an int array.
     *
     * @param fitness An int array.
     * @param a Position of the first element.
     * @param b Position of the second element.
     */
    private void swap(int[] fitness, int a, int b) {
        int tmp = fitness[a];
        fitness[a] = fitness[b];
        fitness[b] = tmp;
    }

    /**
     * Helper function to swap two elements of an array of Rows.
     *
     * @param pop An array of Rows.
     * @param a Position of the first element.
     * @param b Position of the second element.
     *
     */
    private void swap(String[][] pop, int a, int b) {
      String[] tmp = pop[a];
      pop[a] = pop[b];
      pop[b] = tmp;
    }

    private void evolvePopulation() {
        String[][] newPopulation = new String[POPULATION_SIZE][mm_core.getCodeSize()];

        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            if ((int) (Math.random() * 2) == 0) {
                xOver1(newPopulation, i, i + 1);
            } else {
                xOver2(newPopulation, i, i + 1);
            }
        }
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if ((int) (Math.random() * 100) < 3) {
                mutation(newPopulation, i);
            } else if ((int) (Math.random() * 100) < 3) {
                permutation(newPopulation, i);
            } else if ((int) (Math.random() * 100) < 2) {
                inversion(newPopulation, i);
            }
        }
        doubleToRnd(newPopulation);
        population = newPopulation;
    }

    private void xOver1(String[][] newPopulation, int child1Pos, int child2Pos) {
        int mother = getParentPos();
        int father = getParentPos();
        int sep = ((int) (Math.random() * mm_core.getCodeSize())) + 1;
        for (int i = 0; i < mm_core.getCodeSize(); i++) {
            if (i <= sep) {
                newPopulation[child1Pos][i]= population[mother][i];
                newPopulation[child2Pos][i]= population[father][i];
            } else {
                newPopulation[child1Pos][i] = population[father][i];
                newPopulation[child2Pos][i] = population[mother][i];
            }
        }
    }

    private void xOver2(String[][] newPopulation, int child1Pos, int child2Pos) {
        int mother = getParentPos();
        int father = getParentPos();
        int sep1;
        int sep2;
        sep1 = ((int) (Math.random() * mm_core.getCodeSize())) + 1;
        sep2 = ((int) (Math.random() * mm_core.getCodeSize())) + 1;
        if (sep1 > sep2) {
            int temp = sep1;
            sep1 = sep2;
            sep2 = temp;
        }

        for (int i = 0; i < mm_core.getCodeSize(); i++) {
            if (i <= sep1 || i > sep2) {
                newPopulation[child1Pos][i]= population[mother][i];
                newPopulation[child2Pos][i]= population[father][i];
            } else {
                newPopulation[child1Pos][i] = population[father][i];
                newPopulation[child2Pos][i] = population[mother][i];
            }
        }
    }

    private void mutation(String[][] newPopulation, int popPos) {
        newPopulation[popPos][(int) (Math.random() * mm_core.getCodeSize())] = mm_core.getEnabledColors()[(int) (Math.random() * mm_core.getEnabledColorsSize())];
    }

    private void permutation(String[][] newPopulation, int popPos) {
        int pos1 = (int) (Math.random() * mm_core.getCodeSize());
        int pos2 = (int) (Math.random() * mm_core.getCodeSize());
        String tmp = newPopulation[popPos][pos1];
        newPopulation[popPos][pos1] = newPopulation[popPos][pos2];
        newPopulation[popPos][pos2] = tmp;
    }

    private void inversion(String[][] newPopulation, int popPos) {
        int pos1 = (int) (Math.random() * mm_core.getCodeSize());
        int pos2 = (int) (Math.random() * mm_core.getCodeSize());
        if (pos2 < pos1) {
            int tmp = pos2;
            pos2 = pos1;
            pos1 = tmp;
        }
        for (int i = 0; i < (pos2 - pos1)/2; i++) {
            String tmp = newPopulation[popPos][pos1 + i];
            newPopulation[popPos][pos1 + i] = newPopulation[popPos][pos2 - i];
            newPopulation[popPos][pos2 - i] = tmp;
        }
    }

    private void doubleToRnd(String[][] newPopulation) {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (lookForSame(newPopulation, i) == true) {
                newPopulation[i] = generateRndColor();
            }
        }
    }

    protected boolean lookForSame(String[][] newPopulation, int popPos) {
      for (int i=0;i< mm_core.getCodeSize();i++) {
        if(!population[popPos][i].equals(newPopulation[popPos][i])) {
          return false;
        }
      }
      return true;
    }

    private int getParentPos() {
        parentPos += (int) (Math.random() * 7);
        if (parentPos < POPULATION_SIZE / 5) {
            return parentPos;
        } else {
            parentPos = 0;
        }
        return parentPos;
    }

    /**
     *
     * @param a
     * @param b
     *
     * @return
     *
     * @see core#getCodeSize()
     */
    private int[] compare(String[] a, String[] b) {
        int[] result = {0, 0};
        String[] code = new String[mm_core.getCodeSize()];
        String[] secret = new String[mm_core.getCodeSize()];
        System.arraycopy(a, 0, code, 0, mm_core.getCodeSize());
        System.arraycopy(b, 0, secret, 0, mm_core.getCodeSize());
        for (int i = 0; i < mm_core.getCodeSize(); i++) {
            if (code[i].equals(secret[i])) {
                result[0]++;
                secret[i] = null;
                code[i] = null;
            }
        }
        outer:
        for (int i = 0; i < mm_core.getCodeSize(); i++) {
            if (code[i] != null) {
                for (int j = 0; j < mm_core.getCodeSize(); j++) {
                    if (code[i].equals(secret[j])) {
                        result[1]++;
                        secret[j] = null;
                        continue outer;
                    }
                }
            }
        }
        return result;
    }

    /**
     * This function write the pins into an int array
     *
     * counts[row][0]=black
     * counts[row][1]=red
     *
     * @return the black and red pins in a int int array
     *
     * @see core#getHintPane(int)
     * @see core#getCodeSize()
     */
    protected int[][] countHints() {
      int[][] counts = new int[mm_core.data.size()][2];
      for (int i=1;i< counts.length;i++) {
        String[] hints = mm_core.getHintPane(i);
        for(int j=0; j < mm_core.getCodeSize(); j++) {
          if(hints[j].equals("X"))
              counts[i][0]++;
          else if(hints[j].equals("O"))
              counts[i][1]++;
          else
              continue;
        }
      }
      return counts;
    }

   /**
   * Validate the users try and accept or reject turn
   *
   * @return <code>true</code> plus help text - go on playing (next try)<br>
   *         <code>false</code> plus help text - stop current turn - correct your fault
   *
   * @see core#validate()
   * @see validator
   */
  protected validator validate() {
    if (!makeValidate) { return new validator(true,"Keine Kommentar, der Bot hat ja schon geholfen");}
    boolean rightColors = true;
    if (mm_core.data.size()==1)
      return new validator(true,"Jede Wahl ist eine gute Wahl.");
    else{
      for (int i = 1; i<mm_core.data.size(); i++){
        for (int j = 0; j<mm_core.getCodeSize(); j++){
          if (!(mm_core.getValueAt(i,j).equals(mm_core.getValueAt(j))))
            break;
          else if (j==mm_core.getCodeSize()-1)
            return new validator(false,"Das ist die gleiche Zeile wie Zeile: " + (mm_core.data.size()-i));
        }
      }
      if (!(mm_core.getHintPane(1)[mm_core.getCodeSize()-1].equals("-"))){
        String[] validateColor = mm_core.getDataArray(1);
        for (int i = 0; i<mm_core.getCodeSize(); i++){
          for (int j = 0; j<mm_core.getCodeSize(); j++){
            if (mm_core.getValueAt(i).equals(validateColor[j])){
              validateColor[j]="";
              break;
            }
          }
        }
        for (int j = 0; j<mm_core.getCodeSize();j++){
          if (!validateColor[j].equals(""))
            rightColors = false;
        }
        if (rightColors)
          return new validator(true,"Gute Wahl, solange die Farben vertauscht sind.");
        return new validator(false,"Nicht so gut, da schon alle Farben bekannt sind, sie müssen nur noch vertauscht werden.");
      }
      return new validator(true,"Alles ist gut was der Codefindung dient.");
    }
  }
}
