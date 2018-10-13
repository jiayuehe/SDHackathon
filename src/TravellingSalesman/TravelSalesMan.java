package TravellingSalesman;

import javafx.util.Pair;

import java.util.*;

public class TravelSalesMan {
    /*
     Part Two
     */
    /*
    Location HaversineDistance(Location a, Location b){
        double lat1 = b.mLatitude;
        double lat2 = a.mLatitude;
        double lon1 = b.mLongitude;
        double lon2 = a.mLongitude;
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double resultA = (pow((sin(dlat/2*0.0174533)),2) + cos(lat1*0.0174533)* cos(lat2*0.0174533)*pow((sin(dlon/2*0.0174533)),2));
        double c = 2 * atan2(sqrt(resultA), sqrt(1-resultA));
        double result = 3961 * c;
        Location newLocation;
        newLocation.mLongitude = result;
        return newLocation;
    }

    void calculateDifference( std::vector<double>& difference,
                              std::vector<Location>& locationPerPop,
                              std::vector<Location>& tempLocation,std::vector<Location> allLocation, const std::vector<int>& eachPopulation){
        std::transform(eachPopulation.begin(), eachPopulation.end(), std::back_inserter(locationPerPop), [& allLocation](int currentIndex){
            return allLocation[currentIndex];
        });

        locationPerPop.push_back(allLocation[0]);


        std::adjacent_difference(locationPerPop.begin(), locationPerPop.end(), std::back_inserter(tempLocation), HaversineDistance);

        std::transform(tempLocation.begin() + 1, tempLocation.end(), std::back_inserter(difference), [](Location location){
            return location.mLongitude;
        });
    }



/*
 Following code are for part 3
 */

    private static ArrayList<Double> generatePossibility(ArrayList<Pair<Integer, Integer>> difference){
        // sort the list in ascending order
        difference.sort(Comparator.comparingInt(Pair::getValue));


        List<Double> probabilityV = new ArrayList<>();
        for(int i = 0; i  < difference.size(); i++){
            probabilityV.add(1.0/(difference.size()));
        }

        System.out.println("Current probability V is " + probabilityV);
        // Multiply the first two by 6
        int index = difference.get(0).getKey();
        probabilityV.set(index, probabilityV.get(index) * 6);
        index = difference.get(1).getKey();
        probabilityV.set(index, probabilityV.get(index) * 6);

        int firstHalf = difference.size()/2;

        // Multiply the first half by 3
        if(firstHalf > 2){
            for(int i = 2; i < firstHalf; i++){
                index = difference.get(i).getKey();
                probabilityV.set(index, probabilityV.get(index) * 3);
            }
        }
        System.out.println("After Multiplication Current probability V is " + probabilityV);
        double totalNumber = 0;

        for(double currentProb: probabilityV){
            System.out.print(currentProb + "+");
            totalNumber += currentProb;
        }
        System.out.println("Current totalNumber is " + totalNumber);

        ArrayList<Double> normalizedVector = new ArrayList<>();
        for (Double aProbabilityV : probabilityV) {
            normalizedVector.add(aProbabilityV / totalNumber);
        }
        System.out.println("After Normalization, Current probability V is " + normalizedVector);
        return normalizedVector;
    }

    private static List<Pair<Integer,Integer>> constructPairs(List<Double> probability){
        List<Pair<Integer, Integer>> generationPair = new ArrayList<>();
        List<Double> cumulatedVector = new ArrayList<>();
        double counter = 0;

        // construct a cumulative
        for (Double aProbability : probability) {
            double currentProb = aProbability;
            counter += currentProb;
            System.out.println("Current Counter is " + counter);
            cumulatedVector.add(counter);
        }

        System.out.println("Current Cumulative Probability Vector is " + cumulatedVector);

        // generate the pairs
        for(int i = 0; i < probability.size(); i++){
            double randomGeneratorOne = Math.random();
            double randomGeneratorTwo = Math.random();

            System.out.println("Parent one is " + randomGeneratorOne + "Parent two is " + randomGeneratorTwo);
            int parentA = -1;
            int parentB = -1;
            for(int j = 0; j < cumulatedVector.size();j++){
                if(cumulatedVector.get(j) > randomGeneratorTwo){
                    parentB = j;
                    break;
                }
            }

            for(int j = 0; j < cumulatedVector.size();j++){
                if(cumulatedVector.get(j) > randomGeneratorOne){
                    parentA = j;
                    break;
                }
            }
            Pair<Integer, Integer> newPair = new Pair<>(parentA, parentB);
            generationPair.add(newPair);
        }

        return generationPair;
    }

/*
 Following functions are for part 4
 */

    private static List<Integer> createChild(int parentA, int parentB, List<List<Integer>> populationMember, int crossIndex)
    {
        List<Integer> secondGeneration = new ArrayList<>();
        for(int i =0; i < crossIndex + 1; i++){
            secondGeneration.add(populationMember.get(parentA).get(i));
        }

        for(int i = 0; i < populationMember.get(parentB).size(); i++){
            if(!secondGeneration.contains(populationMember.get(parentB).get(i))){
                secondGeneration.add(populationMember.get(parentB).get(i));
            }
        }

        return secondGeneration;
    }

    private static void permutationCheck(List<Integer> secondGeneration){
        Random rand = new Random();
        int indexOne = rand.nextInt(secondGeneration.size()-2) +1;
        int indexTwo = rand.nextInt(secondGeneration.size()-2) +1;

        int temp = secondGeneration.get(indexOne);
        secondGeneration.set(indexOne, secondGeneration.get(indexTwo));
        secondGeneration.set(indexTwo,temp);
    }

    static List<Integer> crossOver(Pair<Integer,Integer> currentPair, List<List<Integer>> populationMember,double mutationChance){
        int size = populationMember.get(0).size();
        Random rnd = new Random();
        int crossIndex = rnd.nextInt(size - 2) + 1;

        //std::uniform_int_distribution<int> anotherUniform(0,1);
        int parentDecide = rnd.nextInt(1);
        List<Integer> secondGeneration;
        if(parentDecide == 1){
            secondGeneration = createChild(currentPair.getKey(), currentPair.getValue(), populationMember, crossIndex);
        } else{
            secondGeneration =  createChild(currentPair.getValue(), currentPair.getKey(), populationMember, crossIndex);
        }

        double currentValue = Math.random();
        //std::uniform_real_distribution<double> mutation(0,1);
        //double currentValue = mutation(mtrand);
        // permutation check
        if(currentValue <= mutationChance){
            permutationCheck(secondGeneration);
        }

        return secondGeneration;
    }


    private static List<List<Integer>> crossOverAll(List<Pair<Integer, Integer>> allParents, List<List<Integer>> allGeneration, double mutationChance){
        List<List<Integer>> secondGeneration = new ArrayList<>();
        for(int i = 0; i < allParents.size(); i++){
           secondGeneration.add(crossOver(allParents.get(i),allGeneration,mutationChance));
        }
        return secondGeneration;
    }


    /*
    Following is for part 2
     */
    private static ArrayList<Pair<Integer, Integer>> calculateFitness(List<List<Integer>> allPopulation, List<Location> allLocation) {
        ArrayList<Pair<Integer, Integer>> allFitness = new ArrayList<>();
        for(int i = 0; i < allPopulation.size(); i++){
            List<Integer> currentRoute = allPopulation.get(i);
            System.out.println("Current route is " + currentRoute);
            int counter = 0;
            int currentSum = 0;
            Location fromLocation = allLocation.get(currentRoute.get(counter));
            Location toLocation = allLocation.get(currentRoute.get(++counter));
            System.out.println("from Location is " + fromLocation.currentLoc);
            System.out.println("to Location is " + toLocation.currentLoc);

            currentSum += fromLocation.mapOfLocation.getOrDefault(toLocation.currentLoc, -1);

            counter = 2;
            for (int j = 1; j < allLocation.size() - 1; j++) {
                fromLocation = toLocation;
                toLocation = allLocation.get(currentRoute.get(counter));
                System.out.println("from Location is " + fromLocation.currentLoc);
                System.out.println("to Location is " + toLocation.currentLoc);
                currentSum += fromLocation.mapOfLocation.getOrDefault(toLocation.currentLoc, -1);
                counter ++;
            }

            allFitness.add(new Pair<>(i, currentSum));

        }


        return allFitness;
    }

    /*
    Following is for part one
     */
    private static void initialPopulation(int population, int allCities, List<List<Integer>> currentFile) {
        List<Integer> currentList = new ArrayList<>();
        for (int j = 0; j < allCities; j++) {
            currentList.add(j);
        }

        Collections.shuffle(currentList);
        System.out.println(currentList);
        currentFile.add(currentList);

        for (int i = 0; i < population; i++) {
            List<Integer> row = new ArrayList<>(currentList);
            Collections.shuffle(row);
            System.out.println(row);
            currentFile.add(row);
        }

        System.out.println(currentFile);
    }

    /*
    static List<Integer> outputFinal()(
                    List<List<Integer>> allLocation, Pair<Integer, Integer> difference){
        System.out.println("All the difference is " + difference);
        List<Integer> finalResult = allLocation.get(difference.getKey());
        return finalResult;
    }
    */

    private static void processCommand(List<Location> allLocation) {
        int population = allLocation.size();
        int generation = 8;

        // Generate the permutation size
        int locationSize = allLocation.size();
        List<List<Integer>> allDestiPerm = new ArrayList<>();
        initialPopulation(population, locationSize, allDestiPerm);

        for(int i = 0; i < generation + 1; i++){
            // Part 2 Fitness
            ArrayList<Pair<Integer, Integer>> difference = calculateFitness(allDestiPerm, allLocation);

            // Part 3 Selection
            List<Double> normalizedVector = generatePossibility(difference);
            List<Pair<Integer,Integer>> allGeneratedPair = constructPairs(normalizedVector);
            System.out.println("Current Parents are " + allGeneratedPair);

            // Part 4
            allDestiPerm  = crossOverAll(allGeneratedPair,allDestiPerm, 0.05);
            System.out.println("Current Result is " + allDestiPerm);

        }

        //ArrayList<Pair<Integer, Integer>> difference = calculateFitness(allDestiPerm, allLocation);
        //Collections.sort(difference, Comparator.comparingInt(Pair::getValue));

        /*
        // Final Result
        List<Integer> finalArray =  outputFinal(allDestiPerm,difference.get(0));
        List<Location> finalLocation = new ArrayList<>();
        for(int i = 0; i < allLocation.size(); i++){
            finalLocation.add(allLocation.get(finalArray.get(i)));
        }
        */

    }

    public static void main(String[] args) {
        List<Location> allLocation = new ArrayList<>();
        Location paris = new Location(20, 20, "Paris");
        Location london = new Location(30, 30, "London");
        Location Shanghai = new Location(40, 40, "Shanghai");
        Location beijing = new Location(20, 20, "beijing");
        Location tianjing = new Location(20, 20, "tianjing");
        Location nanjing = new Location(20, 20, "nanjing");
        Location fan = new Location(20, 20, "fan");
        Location ben = new Location(20, 20, "ben");
        Location chun = new Location(20, 20, "chun");
        allLocation.add(paris);
        allLocation.add(london);
        allLocation.add(Shanghai);
        allLocation.add(beijing);
        allLocation.add(tianjing);
        allLocation.add(nanjing);
        allLocation.add(fan);
        allLocation.add(ben);
        allLocation.add(chun);
        paris.addLocation(london, 30000);
        paris.addLocation(Shanghai, 50000);
        Shanghai.addLocation(paris, 2000);
        Shanghai.addLocation(london, 5000);
        london.addLocation(paris, 3000);
        london.addLocation(Shanghai, 1000);
        for (Location l : allLocation) {
            for (Location l2 : allLocation) {
                if (l2.getCurrentLoc().equals(l.currentLoc)) continue;
                Random rand = new Random();
                l.addLocation(l2, rand.nextInt(65534));
            }
        }
        Map<String, Integer> priceMap  = london.mapOfLocation;
        System.out.println("Current map is " + priceMap);

        processCommand(allLocation);
    }
}