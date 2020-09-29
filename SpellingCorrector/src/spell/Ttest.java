package spell;

import java.io.IOException;

public class Ttest

{
    public static void main(String[] args)
    {

        SpellCorrector sp1 = new SpellCorrector();
        try {
            sp1.useDictionary("test_files/testFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SpellCorrector sp2 = new SpellCorrector();
        try {
            sp2.useDictionary("test_files/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Trie trie1 = sp1.getTrie();
        Trie trie2 = sp2.getTrie();
        Trie trie3 = sp1.getTrie();

        Trie trie4 = new Trie();
        Trie trie5 = new Trie();

        trie4.add("car");
        trie5.add("car");
        trie5.add("car");

//        String trieWords = trie.toString();
//        System.out.println(sp.getTrie().toString());

        boolean result = trie4.equals(trie5);

        System.out.println(trie4.equals(trie5));

//        System.out.println(trie2.hashCode());
//

    }
}
