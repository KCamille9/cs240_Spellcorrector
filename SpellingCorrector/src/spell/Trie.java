package spell;

import java.util.ArrayList;
import java.util.Arrays;

public class Trie implements ITrie
{
    Node root = null;
    int trieWordCount = 0;
    int trieNodeCount = 0;
    int trieUniqueWords = 0;

    char wordArray[] = new char[26];
    String fullWord = "";


    public Trie()
    {
        root = new Node();
        trieNodeCount = 1;
    }

    @Override
    public void add(String word)
    {
        Node currNode = root;

        for (char c : word.toCharArray()) //for every char in word
        {
            if(currNode.trieNodes[c - 'a'] == null) //if u find a null spot, take it
            {
                currNode.trieNodes[c - 'a'] = new Node();

                trieNodeCount++;
            }

            currNode = currNode.trieNodes[c - 'a']; //keep looping for each char
        }

        if(currNode.isComplete == true) //if word already in trie
        {
            currNode.numOfOccurence++;
            trieWordCount++;
        }
        else
        {
            currNode.isComplete = true;
            currNode.numOfOccurence++;
            trieWordCount++;
            trieUniqueWords++;
        }

    }

    @Override
    public INode find(String word)
    {
        Node currNode = root;

        word = word.toLowerCase(); //change it to lowercase

        for (char c : word.toCharArray()) //for every char in word
        {
            if(currNode.trieNodes[c - 'a'] == null) //if u find a null spot, take it
            {
                return null;
            }

            currNode = currNode.trieNodes[c - 'a']; //keep looping for each char
        }

        if(currNode.isComplete)
        {
            return currNode;
        }

        return null;
    }

    @Override
    public int getWordCount()
    {
        return trieUniqueWords;
    }

    @Override
    public int getNodeCount()
    {
        return trieNodeCount;
    }

    public int hashCode()
    {
        int finalNumber = 0;

        for(int i = 0; i < 26; i++)
        {
            if(root.trieNodes[i] != null)
            {
                finalNumber += i + 1;
                finalNumber += root.trieNodes[i].getValue();
            }
        }

        return finalNumber * trieWordCount * trieNodeCount; //ASK IF CORRECT
    }

    public boolean equals(Object obj)
    {
        Trie otherTrie = (Trie)obj;

        return Same(otherTrie);
    }

    public String toString()
    {
        fullWord = ""; //clean up

        printAllWords(root, wordArray, 0);
        return fullWord;
    }


    private void printWord(char str[], int n)
    {
        String aWord = "";

        for(int i=0; i<n; i++)
        {
            aWord += str[i] + "";
        }

        fullWord += aWord + "\n";
    }


    private void printAllWords(Node root, char wordArray[], int pos)
    {
        if(root == null) {return;}

        if(root.isComplete)
        {
            printWord(wordArray, pos);
        }

        for(int i = 0; i < 26; i++)
        {
            if(root.trieNodes[i] != null)
            {
                wordArray[pos] = (char)(i+'a');
                printAllWords(root.trieNodes[i], wordArray, pos+1);
            }
        }
    }

    public boolean Same(Trie trie2)
    {
        if(this.trieUniqueWords != trie2.trieUniqueWords)
        {
            return false;
        }

        if(this.trieWordCount != trie2.trieWordCount)
        {
            return false;
        }

        if(this.trieNodeCount != trie2.trieNodeCount)
        {
            return false;
        }

        return EqualsTest(root, trie2.root);
    }


    private boolean EqualsTest(Node root, Node otherRoot)
    {
        if(root == null) {return false;}

        if(root.isComplete)
        {
            if(!otherRoot.isComplete)
            {
                return false;
            }

            if(root.getValue() != otherRoot.getValue())
            {
                return false;
            }
        }

        for(int i = 0; i < 26; i++)
        {
            if(root.trieNodes[i] != null)
            {
                if(otherRoot.trieNodes[i] == null)
                {
                    return false;
                }

                EqualsTest(root.trieNodes[i], otherRoot.trieNodes[i]);
            }
            else
            {
                if(otherRoot.trieNodes[i] != null)
                {
                    return false;
                }
            }
        }

        return true;
    }
}
