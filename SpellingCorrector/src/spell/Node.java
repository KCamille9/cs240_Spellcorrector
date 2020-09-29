package spell;

public class Node implements INode
{
    Node[] trieNodes = new Node[26];
    boolean isComplete = false;
    int numOfOccurence = 0;

    @Override
    public int getValue() {
        return numOfOccurence;
    }

    @Override
    public void incrementValue() { numOfOccurence++; }

    @Override
    public INode[] getChildren() {
        return trieNodes;
    }
}
