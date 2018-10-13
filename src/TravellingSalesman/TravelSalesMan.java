package TravellingSalesman;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TravelSalesMan {
    /*
    std::vector<Location> readInLocation(std::ifstream& inputFile){
        std::vector<Location> allLocation;
        Location currentLocation;
        while (static_cast<bool>(getline(inputFile,currentLocation.mName,','))) {
            std::string tempString;
            getline(inputFile,tempString,',');
            currentLocation.mLatitude = std::stod(tempString);
            getline(inputFile,tempString);
            currentLocation.mLongitude = stod(tempString);
            allLocation.push_back(currentLocation);
        }
        return allLocation;
    }

    void outputPopulation(std::ofstream& ostream, Population population, int popSize){
        ostream << "INITIAL POPULATION:" << std::endl;
        for(int i = 0; i < popSize; i++)
        {
            bool firstTime = true;
            for(auto& j: population.mMembers[i])
            {
                if(firstTime){
                    ostream << static_cast<int>(j);
                    firstTime = false;
                } else{
                    ostream << "," <<  static_cast<int>(j);
                }
            }
            ostream << std::endl;
        }
    }
*/


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

    void outputFitness(std::ofstream& ostream, std::vector<std::pair<int,double>> difference){
        ostream << "FITNESS:" << std::endl;
        for(auto& i: difference){
            ostream << i.first << ":" << i.second << std::endl;
        }

    }

/*
 Following code are for part 3
 */
/*
    auto compare = [](std::pair<int, double> pairA, std::pair<int,double> pairB){
        return pairA.second < pairB.second;
    };

    std::vector<double> generatePossibility(std::vector<std::pair<int,double>> difference){
        // sort the list in ascending order
        std::sort(difference.begin(),difference.end(),compare);

        // generate the vector of probabilities
        std::vector<double> probablityV(difference.size());

        std::generate(probablityV.begin(), probablityV.end(), [&probablityV](){
            return 1.0/(probablityV.size());
        });


        // Multiply the first two by 6
        probablityV[difference[0].first] *= 6;
        probablityV[difference[1].first] *= 6;

        int firstHalf = difference.size()/2;

        // Multiply the first half by 3
        if(firstHalf > 2){
            std::vector<double> dontmatter;
            std::transform(difference.begin() + 2, difference.begin()+firstHalf, std::back_inserter(dontmatter), [&probablityV](std::pair<int, double> pair){
                probablityV[pair.first] *= 3;
                return pair.second;
            });
        }

        double totalNumber = std::accumulate(probablityV.begin(),probablityV.end(),0.0);
        std::vector<double> normalizedVector;
        std::transform(probablityV.begin(), probablityV.end(), std::back_inserter(normalizedVector), [&totalNumber](float probability){
            return probability/totalNumber;
        });

        return normalizedVector;

    }

    void constructPairArray(const std::vector<double>& cumulatedVector, bool firstControl,double rand, std::vector<int>& finalArray, int index)
    {
        std::transform(cumulatedVector.begin(), cumulatedVector.end(), std::back_inserter(finalArray),[&firstControl, rand, &index](double i){
        int ip = index;
        if((rand < i) && firstControl){
            firstControl = false;
            ip = index;
        }
        else{
            index ++;
            ip = 0;

        }

        return ip;

    });

    }
/*
    std::vector<std::pair<int, int>> constructPairs(std::vector<double> probability,std::mt19937& mtrand){
        std::vector<std::pair<int, int>> generationPair(probability.size());
        std::vector<double> cumulatedVector;
        double counter = 0;

        // construct a cumulative
        std::transform(probability.begin(),probability.end(), std::back_inserter(cumulatedVector),[&probability, &counter](double currentProb){
            counter += currentProb;
            return counter;
        });

        // generate the pairs

        std::generate(generationPair.begin(), generationPair.end(),[&cumulatedVector, &mtrand](){
            std::uniform_real_distribution<double> myDist(0,1);
            double randomGenerateOne = myDist(mtrand);
            std::vector<int> finalArray;


            constructPairArray(cumulatedVector,true,randomGenerateOne, finalArray, 0);
            double randomGenerateTwo = myDist(mtrand);
            constructPairArray(cumulatedVector,true,randomGenerateTwo, finalArray, 0);

            std::vector<int> pairArray(finalArray.size());
            int half = finalArray.size()/2;
            std::copy_if (finalArray.begin(), finalArray.begin()+half, pairArray.begin(), [](int i){return!(i==0);
            });

            std::copy_if (finalArray.begin()+half, finalArray.end(), pairArray.begin()+1, [](int i){return!(i==0);
            });

            std::pair<int, int> newPair(pairArray[0],pairArray[1]);
            return newPair;
        });

        return generationPair;
    }

/*
    void outputPairs(std::ofstream& ostream, std::vector<std::pair<int,int>> allGeneratedPair){
        ostream << "SELECTED PAIRS:" << std::endl;
        for(auto& pair:allGeneratedPair){
            ostream << "(" << pair.first << "," << pair.second << ")" << std::endl;
        }
    }

/*
 Following functions are for part 4
 */
/*
    std::vector<int> createChild(int parentA, int parentB, std::vector<std::vector<int>> populationMember, int crossIndex)
    {

        std::vector<int> secondGeneration;
        std::copy_n(populationMember[parentA].begin(), crossIndex + 1, std::back_inserter(secondGeneration));


        std::copy_if(populationMember[parentB].begin(), populationMember[parentB].end(), std::back_inserter(secondGeneration), [&secondGeneration](int currentMember){
        return std::find(secondGeneration.begin(), secondGeneration.end(), currentMember) == secondGeneration.end();

    });


        return secondGeneration;
    }

    void permutationCheck(std::vector<int>& secondGeneration, std::mt19937& mtrand){
        std::uniform_int_distribution<int> myUniform(1,secondGeneration.size()-1);
        int indexOne = myUniform(mtrand);
        int indexTwo = myUniform(mtrand);
        std::swap(secondGeneration[indexOne], secondGeneration[indexTwo]);
    }

    std::vector<int> crossOver(std::mt19937& mtrand, std::pair<int, int> currentPair,std::vector<std::vector<int>> populationMember,double mutationChance){
        int size = populationMember[0].size();
        std::uniform_int_distribution<int> myUniform(1,size-2);
        int crossIndex = myUniform(mtrand);

        std::uniform_int_distribution<int> anotherUniform(0,1);
        int parentDecide = anotherUniform(mtrand);
        std::vector<int> secondGeneration;
        if(parentDecide == 1){
            secondGeneration = createChild(currentPair.first, currentPair.second, populationMember, crossIndex);
        } else{
            secondGeneration =  createChild(currentPair.second, currentPair.first, populationMember, crossIndex);
        }

        std::uniform_real_distribution<double> mutation(0,1);
        double currentValue = mutation(mtrand);
        // permutation check
        if(currentValue <= mutationChance){
            permutationCheck(secondGeneration, mtrand);
        }

        return secondGeneration;
    }


    std::vector<std::vector<int>> crossOverAll(std::mt19937& mtrand, std::vector<std::pair<int, int>> allParents,std::vector<std::vector<int>> allGeneration, double mutationChance){
        std::vector<std::vector<int>> secondGeneration;
        std::transform(allParents.begin(), allParents.end(), std::back_inserter(secondGeneration), [&mutationChance,&allGeneration,allParents, &mtrand](std::pair<int, int> currentPair){
            std::vector<int> nextgeneration = crossOver(mtrand, currentPair, allGeneration, mutationChance);
            return nextgeneration;
        });

        return secondGeneration;
    }


    void outputGeneration(std::ofstream& ostream, std::vector<std::vector<int>> population, int popSize, int generation){
        ostream << "GENERATION: " << generation << std::endl;
        for(int i = 0; i < popSize; i++)
        {
            bool firstTime = true;
            for(auto& j: population[i])
            {
                if(firstTime){
                    ostream << static_cast<int>(j);
                    firstTime = false;
                } else{
                    ostream << "," << static_cast<int>(j);
                }
            }
            ostream << std::endl;
        }
    }

    void outputFinal(std::ofstream& ostream, Population population,
                     std::vector<Location> allLocation, std::pair<int, double> difference){
        ostream << "SOLUTION:" << std::endl;
        std::cout << "The fittest one is " << difference.first << std::endl;
        std::vector<int> finalResult = population.mMembers[difference.first];
        for(auto& i: finalResult){
            ostream << allLocation[i].mName << std::endl;
        }
        ostream << allLocation[0].mName << std::endl;
        ostream << "DISTANCE: " << difference.second << " miles";
    }
    */

    ArrayList<Pair<Integer, Integer>> calculateFitness(List<List<Integer>> allPopulation, List<Location> allLocation){
        ArrayList<Pair<Integer, Integer>> allFitness = new ArrayList<>();

        for(int i = 0 ; i < allPopulation.size(); i++){
            List<Integer> currentRoute = allPopulation.get(i);
            int counter = 0;
            int currentSum = 0;
            Location fromLocation = allLocation.get(currentRoute.get(counter));
            Location toLocation = allLocation.get(currentRoute.get(++counter));
            currentSum += fromLocation.mapOfLocation.getOrDefault(toLocation,-1);

            for(int j = 1; j < allPopulation.size()-1; j++) {
                fromLocation = toLocation;
                toLocation = allLocation.get(currentRoute.get(++counter));
                currentSum += fromLocation.mapOfLocation.getOrDefault(toLocation,-1);
            }


            allFitness.add(new Pair<>(i, currentSum));
        }

        allFitness.sort(Comparator.comparingInt(Pair::getValue));

        return allFitness;
    }

    static void initialPopulation(int population, int allCities,List<List<Integer>> currentFile){
        List<Integer> currentList = new ArrayList<>();
        for(int j = 0; j < allCities; j ++)
        {
            currentList.add(j);
        }

        Collections.shuffle(currentList);
        System.out.println(currentList);
        currentFile.add(currentList);

        for(int i = 0; i < population; i++){
            List<Integer> row = new ArrayList<>(currentList);
            Collections.shuffle(row);
            System.out.println(row);
            currentFile.add(row);
        }

        System.out.println(currentFile);

    }

    static void processCommand(ArrayList<Location> allLocation, boolean)
    {
        int population = allLocation.size();
        int generation = 8;

        /*
        int seed = atoi(argv[5]);
        std::mt19937 mtrand(seed);

        std::ifstream inputFile;
        inputFile.open (fileName);
*/

        // Generate the permutation size
        int locationSize = allLocation.size();
        List<List<Integer>> allDestiPerm = new ArrayList<>();
        initialPopulation(population, locationSize, allDestiPerm);


        // Part 2 Fitness
        ArrayList<Pair<Integer,Double>> difference = calculateFitness(allDestiPerm, allLocation);
        //outputFitness(ostream, difference);

        /*
        for(int i = 1; i < generation + 1; i++){


            // Part 2 Fitness
            ArrayList<std::pair<int,double>> difference = calculateFitness(population,allLocation);
            outputFitness(ostream, difference);

            // Part 3 Selection
            ArrayList<double> normalizedVector = generatePossibility(difference);
            ArrayList<std::pair<int, int>> allGeneratedPair = constructPairs(normalizedVector,mtrand);
            outputPairs(ostream, allGeneratedPair);

            // Part 4
            ArrayList<std::vector<int>> secondGeneration = crossOverAll(mtrand,allGeneratedPair,population.mMembers, mutationChance);
            outputGeneration(ostream, secondGeneration, popSize, i);
            population.mMembers = secondGeneration;

        }


        // Part 2 Fitness
        std::vector<std::pair<int,double>> difference = calculateFitness(population,allLocation);
        outputFitness(ostream, difference);

        // Final Result
        std::sort(difference.begin(),difference.end(),compare);
        outputFinal(ostream, population,allLocation,difference[0]);


        inputFile.close();
        ostream.close();
        */
    }

    public static void main(String[] args) {
        List<Location> allLocation = new ArrayList<>();



    }
}