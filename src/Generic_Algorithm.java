
import java.util.Random;

import static java.lang.System.*;


//Main class
public class Generic_Algorithm {

    private Population population;
    private Individual fittest;
    private Individual secondFittest;
    private int generation = 0;

    Generic_Algorithm() {
        population = new Population();
    }

    // Main Method
    public static void main(String[] args) {

        Random rn = new Random();

        Generic_Algorithm ga = new Generic_Algorithm();

        //Initialize population
        ga.population.initializePopulation();

        //Calculate fitness of each individual
        ga.population.calculateFitness();

        //While population gets an individual with maximum fitness
        for (;ga.population.fittest < 5; ga.generation++) {

            //Do selection
            ga.selection();

            //Do crossover
            ga.crossover();

            //Do mutation under a random probability
            if (rn.nextInt()%7 < 5) {
                ga.mutation();
            }

            //Add fittest offspring to population
            ga.addFittestOffspring();

            //Calculate new fitness value
            ga.population.calculateFitness();

            out.println("Generation: " + ga.generation + " Fittest: " + ga.population.fittest);
        }

        out.println("\nSolution found in generation " + ga.generation);
        out.println("Fitness: " + ga.population.getFittest().fit);
        out.print("Genes: ");

        for (int i = 0; i < 5; i++) {
            out.print(ga.population.getFittest().genes[i]);
        }

        out.println(" ");

    }

    //Selection
    private void selection() {

        //Select the most fittest individual
        fittest = population.getFittest();

        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    private void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.people[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    //Mutation
    private void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.people[0].geneLength);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.people[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //Get fittest offspring
    private Individual getFittestOffspring() {
        if (fittest.fit > secondFittest.fit) {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    private void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFit();
        secondFittest.calcFit();

        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.people[leastFittestIndex] = getFittestOffspring();
    }

}


//Individual class
class Individual {

    int fit = 0;
    int[] genes = new int[5];
    int geneLength = 5;

    Individual() {
        Random rn = new Random();

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fit = 0;
    }

    //Calculate fitness
    void calcFit() {

        fit = 0;
        for (int i = 0; i < 5; i++) {
            if (genes[i] == 1) {
                ++fit;
            }
        }
    }

}

//Population class
class Population {

    int populationSize = 10;
    Individual[] people = new Individual[populationSize];
    int fittest = 0;

    //Initialize population
    void initializePopulation() {
        for (int i = 0; i < people.length; i++) {
            people[i] = new Individual();
        }
    }

    //Get the fittest individual
    Individual getFittest() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < people.length; i++) {
            if (maxFit <= people[i].fit) {
                maxFit = people[i].fit;
                maxFitIndex = i;
            }
        }
        fittest = people[maxFitIndex].fit;
        return people[maxFitIndex];
    }

    //Get the second most fittest individual
    Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < people.length; i++) {
            if (people[i].fit > people[maxFit1].fit) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (people[i].fit > people[maxFit2].fit) {
                maxFit2 = i;
            }
        }
        return people[maxFit2];
    }

    //Get index of least fittest individual
    int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < people.length; i++) {
            if (minFitVal >= people[i].fit) {
                minFitVal = people[i].fit;
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    //Calculate fitness of each individual
    void calculateFitness() {

        for (int i = 0; i < people.length; i++) {
            people[i].calcFit();
        }
        getFittest();
    }

}