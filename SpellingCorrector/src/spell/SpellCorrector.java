package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector
{
    Trie trie;
    ArrayList<String> distanceOneWords = new ArrayList<>();
    ArrayList<String> distanceTwoWords = new ArrayList<>();
    HashMap<String, Integer> foundWords = new HashMap<>();

    ArrayList<String> possibleWords = new ArrayList<>();
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    String finalSuggestedWord;


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        String path = dictionaryFileName;
        File file = new File(path);

        Scanner scanner = new Scanner(file);

        trie = new Trie();

//        while(scanner.hasNextLine())
//        {
//            String word = scanner.next();
//            word = word.toLowerCase();
//            trie.add(word);
//        }

        while(scanner.hasNext())
        {
            String word = scanner.next();
            word = word.toLowerCase();
            trie.add(word);
        }

        boolean test = false; //just for testing
    }

    @Override
    public String suggestSimilarWord(String inputWord)
    {
        finalSuggestedWord = "";

        inputWord = inputWord.toLowerCase();

        boolean isthere = trie.find(inputWord) == null; //for testing only

        if(trie.find(inputWord) == null)
        {
            //start working on the distance methods
            fourEditDistanceChallenge(inputWord, distanceOneWords);

            //do it twice
            fourEditDistanceChallengeTwo(distanceOneWords, distanceTwoWords);

            //search if words is dist1 and dist2 lists are in trie
            //store those words in a hashmap, word and occurrence
            for (int i = 0; i < distanceOneWords.size(); i++) {
                if (trie.find(distanceOneWords.get(i)) != null) {
                    INode possibleWord = trie.find(distanceOneWords.get(i));
                    foundWords.put(distanceOneWords.get(i), possibleWord.getValue());
                }
            }


            //make sure to have a contidion that if not found in dist1 list, go to dist2 list
            if (foundWords.isEmpty()) {
                for (int i = 0; i < distanceTwoWords.size(); i++) {
                    if (trie.find(distanceTwoWords.get(i)) != null) {
                        INode possibleWord = trie.find(distanceTwoWords.get(i)); //PREGUNTAR SI ES MEJRO NODE Q INODE
                        foundWords.put(distanceTwoWords.get(i), possibleWord.getValue());
                    }
                }
            }

            //if still empty, return no found word
            if (foundWords.isEmpty()) {
                return null;
            }


            //then do a check to see which words has the most frequency, then clear the list and add them
            int maxOccurrence = 0;

            for (Map.Entry mapElement : foundWords.entrySet()) {
                int value = ((int) mapElement.getValue());

                if (value > maxOccurrence) {
                    maxOccurrence = value;
                }
            }

            for (Map.Entry mapElement : foundWords.entrySet())
            {
                String key = (String) mapElement.getKey();
                int value = ((int) mapElement.getValue());

                if (value == maxOccurrence) { possibleWords.add(key); }
            }

            //if the amount of words is >1, then return the one that is alpphabetically first
            if (possibleWords.size() > 1) { Collections.sort(possibleWords); }

            finalSuggestedWord = possibleWords.get(0);

            //clean up
            cleanUp();

            return finalSuggestedWord;
        }
        else
            {
            return inputWord;
        }

    }

    public void cleanUp()
    {
        distanceOneWords.clear();
        distanceTwoWords.clear();
        possibleWords.clear();
        foundWords.clear();
    }

    public void fourEditDistanceChallenge(String inputWord, ArrayList<String> arrayList)
    {
        //deletion distance
        deletionDistance(inputWord, arrayList);

        //transposition distance
        transpositionDistance(inputWord, arrayList);

        //alteration distance
        alterationDistance(inputWord, arrayList);

        //insertion distance
        insertionDistance(inputWord, arrayList);
    }

    public void fourEditDistanceChallengeTwo(ArrayList<String> arrayList1, ArrayList<String> arrayList2)
    {
        for (int i = 0; i < arrayList1.size(); i++)
        {
            fourEditDistanceChallenge(arrayList1.get(i), arrayList2);
        }
    }

    public void deletionDistance(String inputWord, ArrayList<String> arrayList)
    {
        //let's say that in our tie we have: bird
        //and user input is: ird

        for (int i = 0; i < inputWord.length(); i++)
        {
            String deleteDisWord = inputWord.substring(0,i) + inputWord.substring(i+1);
            arrayList.add(deleteDisWord);
        }
    }

    public void transpositionDistance(String inputWord, ArrayList<String> arrayList)
    {
        for (int i = 0; i < inputWord.length() - 1; i++)
        {
            String first = inputWord.substring(i,i+1);
            String second = inputWord.substring(i+1, i+2);

            String transposDisWord = inputWord.substring(0,i) + second + first + inputWord.substring(i+2);
            arrayList.add(transposDisWord);

        }
    }

    public void alterationDistance(String inputWord, ArrayList<String> arrayList)
    {
        for (int i = 0; i < inputWord.length(); i++)
        {
            for (int j = 0; j < alphabet.length; j++)
            {
                String toAlter = alphabet[j] + "";

                String currLetter = inputWord.charAt(i) + "";

                if(!toAlter.equals(currLetter))
                {
                    String alterDisWord = inputWord.substring(0,i) + toAlter + inputWord.substring(i+1);
                    arrayList.add(alterDisWord);
                }
            }
        }
    }

    public void insertionDistance(String inputWord, ArrayList<String> arrayList)
    {
        for (int i = 0; i < inputWord.length() + 1; i++)
        {
            for (int j = 0; j < alphabet.length; j++)
            {
                String toInsert = alphabet[j] + "";

                String insertDisWord = inputWord.substring(0,i) + toInsert + inputWord.substring(i);
                arrayList.add(insertDisWord);

            }
        }
    }

    public static boolean isUpperCase(String s)
    {
        for (int i=0; i<s.length(); i++)
        {
            if (Character.isLowerCase(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public Trie getTrie()
    {
        return trie;
    }
}
