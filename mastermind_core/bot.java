package mastermind_core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Automatic mastermind solver, mastermind helper and tipster
 */
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
	core mmCore;
	boolean makeValidate;

    private final int FEASIBLE_CODES_MAX = 1;
    private String[][] population;
    private int[] fitness = new int[POPULATION_SIZE];
    private ArrayList<String[]> feasibleCodes = new ArrayList<String[]>();
    private int parentPos = 0;

  /**
   * Class construction
   * @param mmCore core class
   */
  public bot (core mmCore) {
    this.mmCore = mmCore;
    population = new String[POPULATION_SIZE][mmCore.getCodeSize()];
  }

  /**
   * Check whether the bot should fill out empty pins - fill out empty pins or do some validate if the user wish some help
   *
   * @see core#doitBot()
   * @see core#getArAt(int)
   * @see core#getCodeSize()
   * @see core#getValueAt(int)
   *
   * @see #generateTurn()
   */
	protected void setBestColors () {

		int space=0;
		makeValidate = false;

		for(int i=0; i < mmCore.getCodeSize(); i++) {
			if(mmCore.getValueAt(i) == null || mmCore.getValueAt(i).equals("")) {
				space++;
			}
		}
		if (space==mmCore.getCodeSize()){	//find a code
	        String[] turn = generateTurn();
	        mmCore.setArAt(turn);
		}else if (space>0){	//fill the hole
			String[] turn = generateTurn();
			String[] hturn = mmCore.getArAt(0);
			outer:
			for (int i=0; i<mmCore.getCodeSize();i++){
				for (int j=0; j<mmCore.getCodeSize(); j++){
					if(turn[i].equals(hturn[j])) {
						turn[i] = null;
						hturn[j] = null;
						continue outer;
					}
				}
			}
			outer2:
			for(int i=0; i < mmCore.getCodeSize(); i++) {
				if(mmCore.getValueAt(i) == null || mmCore.getValueAt(i).equals("")) {
					for(int j=0; j < mmCore.getCodeSize(); j++) {
						if(turn[j] != null) {
							mmCore.setValueAt(turn[j],i);
							turn[j] = null;
							continue outer2;
						}
					}
				}
			}

			for(int i=0; i < mmCore.getCodeSize(); i++) {
				if (mmCore.getValueAt(i).equals("")){
					mmCore.setValueAt(mmCore.getEnabledColors()[0],i);
				}
			}
		}else{ //validate
			makeValidate = true;
		}
	}

	/**
	 * This function generate a turn which are able to set to a new row
	 *
	 * @return generated turn
	 *
	 * @see #initPopulation()
     * @see #calcFitness()
     * @see #sortFeasibleByFitness(int[], String[][])
     * @see #evolvePopulation()
     * @see #addToFeasibleCodes()
	 */
	private String[] generateTurn() {
        String[] turn = new String[mmCore.getCodeSize()];
        boolean doCalc;
        // First try
        if (mmCore.data.size() == 1) {
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

    /**
     * Generates a Array with random colors from the enabled colors
     *
     * @return An array with randomized colors from the enabled colors
     *
     * @see core#getEnabledColors()
     * @see core#getEnabledColorsSize()
     * @see core#getCodeSize()
     */
    private String[] generateRndColor() {
    	String[] colors = new String[mmCore.getCodeSize()];
        int randomizeColor = 0;
        Random r = new Random();
        for (int i = 0; i < mmCore.getCodeSize(); i++) {
          randomizeColor = r.nextInt(mmCore.getEnabledColorsSize());
          colors[i] = mmCore.getEnabledColors()[randomizeColor];
        }
        return colors;
      }

    /**
	 * Test if a code for all done tries makes sense and return the result.<br>
	 * Cancel if nothing makes sense and fesaibleCodes is full.<br>
	 *
	 * @return <code>false</code> if fesaibleCodes is full.<br>
	 * <code>true</code> otherwise.
     *
     * @see core#getArAt(int)
     * @see #compare(String[], String[])
     */
    private boolean addToFeasibleCodes() {
    	int[][]cHints = countHints();
        outer:
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 1; j < mmCore.data.size(); j++) {
                int[] result = compare(population[i], mmCore.getArAt(j));
                if (result[0] != cHints[j][0] || result[1] != cHints[j][1]) {
                    continue outer;
                }
            }
            if (feasibleCodes.size() < FEASIBLE_CODES_MAX) {
                if (!feasibleCodes.contains(population[i])) {
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

    /**
     * Calculates the fitness of every Row in population.
     * A fitness-value and the corresponding element of the population array
     * both have the same index in their respective arrays.
     *
     * It should resemble the function as described in the paper:
     * <a href="https://lirias.kuleuven.be/bitstream/123456789/164803/1/KBI_0806.pdf">
     * Efficient solutions for Mastermind using genetic algorithms</a>
     * on page 6.
     */
    private void calcFitness() {
        int xtmp;
        int ytmp;
        int[][] cHints = countHints();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            xtmp = 0;
            ytmp = 0;
            for (int j = 1; j < mmCore.data.size(); j++) {
                int[] result = compare(population[i], mmCore.getArAt(j));
                xtmp += Math.abs(result[0] - cHints[j][0]);// black
                ytmp += Math.abs(result[1] - cHints[j][1]);// red
            }
            fitness[i] = (xtmp + ytmp);
        }
    }

    /**
     * This function compare two arrays of strings an counts how many
     * strings are the same and on the same position or only in the other array.
     *
     * return[0] = number of same string at both string same position
     * return[1] = number of same string in both string and NOT on the same position
     *
     * @param a The first string for the compare (with includes colors as a hex string)
     * @param b The second string for the compare (with includes colors as a hex string)
     *
     * @return A int array with the result
     *
     * @see core#getCodeSize()
     */
    private int[] compare(String[] a, String[] b) {
        int[] result = {0, 0};
        String[] code = new String[mmCore.getCodeSize()];
        String[] secret = new String[mmCore.getCodeSize()];
        System.arraycopy(a, 0, code, 0, mmCore.getCodeSize());
        System.arraycopy(b, 0, secret, 0, mmCore.getCodeSize());
        for (int i = 0; i < mmCore.getCodeSize(); i++) {
            if (code[i].equals(secret[i])) {
                result[0]++;
                secret[i] = null;
                code[i] = null;
            }
        }
        outer:
        for (int i = 0; i < mmCore.getCodeSize(); i++) {
            if (code[i] != null) {
                for (int j = 0; j < mmCore.getCodeSize(); j++) {
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
	            int pn = divide(fitness, pop, low, up, p);
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

  	/**
  	 * Evolve the population using cross over, mutation, permutation and
     * inversion.
  	 *
     * <a href="http://en.wikipedia.org/wiki/Genetic_algorithm#Reproduction">
     * Reproduction</a>
     *
  	 * @see #xOver1(String[][], int, int)
  	 * @see #xOver2(String[][], int, int)
  	 * @see #mutation(String[][], int)
  	 * @see #permutation(String[][], int)
  	 * @see #inversion(String[][], int)
  	 * @see #doubleToRnd(String[][])
  	 */
    private void evolvePopulation() {
        String[][] newPopulation = new String[POPULATION_SIZE][mmCore.getCodeSize()];

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

    /**
     * One-point crossover. A single crossover point on both parents' organism
     * strings is selected. All data beyond that point in either organism string
     * is swapped between the two parent organisms. The resulting organisms are
     * the children.
     *
     * <a href="http://en.wikipedia.org/wiki/Crossover_%28genetic_algorithm%29#One-point_crossover">
     * One-point crossover</a>
     *
     * @param newPopulation The population array that will be manipulated.
     * @param child1Pos The position of the newPopulation array within the population array that will be changed.
     * @param child2Pos The position of the newPopulation array within the population array that will be changed.
     */
    private void xOver1(String[][] newPopulation, int child1Pos, int child2Pos) {
        int mother = getParentPos();
        int father = getParentPos();
        int sep = ((int) (Math.random() * mmCore.getCodeSize())) + 1;
        for (int i = 0; i < mmCore.getCodeSize(); i++) {
            if (i <= sep) {
                newPopulation[child1Pos][i]= population[mother][i];
                newPopulation[child2Pos][i]= population[father][i];
            } else {
                newPopulation[child1Pos][i] = population[father][i];
                newPopulation[child2Pos][i] = population[mother][i];
            }
        }
    }

    /**
     * Two-point crossover. Two points are selected on the parent organism
     * strings. Everything between the two points is swapped between the parent
     * organisms, rendering two child organisms.
     *
     * <a href="http://en.wikipedia.org/wiki/Crossover_%28genetic_algorithm%29#Two-point_crossover">
     * Two-point crossover</a>
     *
     * @param newPopulation The population array that will be manipulated.
     * @param child1Pos The position of a newPopulation line within the population array that will be changed.
     * @param child2Pos The position of a newPopulation line within the population array that will be changed.
     */
    private void xOver2(String[][] newPopulation, int child1Pos, int child2Pos) {
        int mother = getParentPos();
        int father = getParentPos();
        int sep1;
        int sep2;
        sep1 = ((int) (Math.random() * mmCore.getCodeSize())) + 1;
        sep2 = ((int) (Math.random() * mmCore.getCodeSize())) + 1;
        if (sep1 > sep2) {
            int temp = sep1;
            sep1 = sep2;
            sep2 = temp;
        }

        for (int i = 0; i < mmCore.getCodeSize(); i++) {
            if (i <= sep1 || i > sep2) {
                newPopulation[child1Pos][i]= population[mother][i];
                newPopulation[child2Pos][i]= population[father][i];
            } else {
                newPopulation[child1Pos][i] = population[father][i];
                newPopulation[child2Pos][i] = population[mother][i];
            }
        }
    }

    /**
     * Mutation. Replaces the color of one randomly chosen position by a random
     * other color.
     *
     * @param newPopulation The population array that will be manipulated.
     * @param popPos The position of the line within the population array that
     * will be changed.
     */
    private void mutation(String[][] newPopulation, int popPos) {
        newPopulation[popPos][(int) (Math.random() * mmCore.getCodeSize())] = mmCore.getEnabledColors()[(int) (Math.random() * mmCore.getEnabledColorsSize())];
    }

    /**
     * Permutation. The colors of two random positions are switched.
     *
     * @param newPopulation The population array that will be manipulated.
     * @param popPos The position of the line within the population array that
     * will be changed.
     */
    private void permutation(String[][] newPopulation, int popPos) {
        int pos1 = (int) (Math.random() * mmCore.getCodeSize());
        int pos2 = (int) (Math.random() * mmCore.getCodeSize());
        String tmp = newPopulation[popPos][pos1];
        newPopulation[popPos][pos1] = newPopulation[popPos][pos2];
        newPopulation[popPos][pos2] = tmp;
    }

    /**
     * Inversion. Two positions are randomly picked, and the sequence of colors
     * between these positions is inverted.
     *
     * @param newPopulation The population array that will be manipulated.
     * @param popPos The position of the line within the population array that
     * will be changed.
     */
    private void inversion(String[][] newPopulation, int popPos) {
        int pos1 = (int) (Math.random() * mmCore.getCodeSize());
        int pos2 = (int) (Math.random() * mmCore.getCodeSize());
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

    /**
     * Replaces double elements in newPopulation.
     *
     * @param newPopulation The population array that will be manipulated.
     *
     * @see #lookForSame(String[][], int)
     */
    private void doubleToRnd(String[][] newPopulation) {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (lookForSame(newPopulation, i)) {
                newPopulation[i] = generateRndColor();
            }
        }
    }

    /**
     * Looking for equal rows at popPos in newPopulation.
     *
     * @param newPopulation The population array that will be searched.
     * @param popPos The position of the row within the population array that will be compared.
     *
     * @return true if an equal row is found if not return false.
     * @return <code>true</code> if a equal row is found<br />
     *         <code>false</code> if no equal row is found
     */
	private boolean lookForSame(String[][] newPopulation, int popPos) {
		for (int i=0;i< mmCore.getCodeSize();i++) {
			if(!population[popPos][i].equals(newPopulation[popPos][i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 *  Get best parent position (one of the best fifth) in population array for good fitness value purposes
	 *
	 * @return The best parent position (one of the best fifth)
	 */
    private int getParentPos() {
        parentPos += (int) (Math.random() * 7);
        if (!(parentPos < POPULATION_SIZE / 5))
             parentPos = 0;
         return parentPos;
    }


    /**
     * This function write the pins into an int array
     *
     * <a href="http://en.wikipedia.org/wiki/Mastermind_%28board_game%29#Gameplay_and_rules">
     * Mastermind Rules</a>
     *
     * counts[row][0]=black
     * counts[row][1]=red
     *
     * @return the black and red pins in a int int array
     *
     * @see core#getHintPane(int)
     * @see core#getCodeSize()
     */
    private int[][] countHints() {
		int[][] counts = new int[mmCore.data.size()][2];
		for (int i=1;i< counts.length;i++) {
			String[] hints = mmCore.getHintPane(i);
			for(int j=0; j < mmCore.getCodeSize(); j++) {
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
		if (mmCore.data.size()==1)
			return new validator(true,"Jede Wahl ist eine gute Wahl.");
		else{
			for (int i = 1; i<mmCore.data.size(); i++){
				for (int j = 0; j<mmCore.getCodeSize(); j++){
					if (!(mmCore.getValueAt(i,j).equals(mmCore.getValueAt(j))))
						break;
					else if (j==mmCore.getCodeSize()-1)
						return new validator(false,"Das ist die gleiche Zeile wie Zeile: " + (mmCore.data.size()-i));
				}
			}
            String[] s = mmCore.getHintPane(1);
            boolean isWrong=false;
            for(int i=0; i < s.length;i++) {
              if(s[i].equals("-")) {
                isWrong = true;
                break;
              }
            }
			if (!isWrong){
				String[] validateColor = mmCore.getDataArray(1);
				for (int i = 0; i<mmCore.getCodeSize(); i++){
					for (int j = 0; j<mmCore.getCodeSize(); j++){
						if (mmCore.getValueAt(i).equals(validateColor[j])){
							validateColor[j]="";
							break;
						}
					}
				}
				for (int j = 0; j<mmCore.getCodeSize();j++){
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
