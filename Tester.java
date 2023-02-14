package comp2402a3;
import java.util.Iterator;
import java.util.Random;

public class Tester {
    static <T> void showContents(Iterable<T> ds) {
        System.out.print("[");
        Iterator<T> it = ds.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
            if (it.hasNext()) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    static void skippyTest(int n){
        Random rand = new Random();
        IndexedSSet<Integer> iss = new SlowSkippy<>();
        IndexedSSet<Integer> isf = new FastSkippy<>();
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(3*n);
            System.out.println("add(" + x + ") = " + iss.add(x));
            System.out.println("Fast add(" + x + ") = " + isf.add(x));
        }
        System.out.print("Contents: ");
        showContents(iss);
        System.out.print("Fast Contents: ");
        showContents(isf);

        System.out.println("size()=" + iss.size());
        System.out.println("Fast size()=" + isf.size());

        for (int i = 0; i < iss.size(); i++) {
            System.out.println("get(" + i + ")=" + iss.get(i));
            System.out.println("Fast get(" + i + ")=" + isf.get(i) + "\n");
        }

        for (int i = 0; i < 10; i++) {
            int x = rand.nextInt(3*n);
            int y = rand.nextInt(3*n);
            //System.out.println("rangecount(" + x + ", " + y + ") = "+iss.rangecount(x, y));
            //System.out.println("Fast rangecount(" + x + ", " + y + ") = "+isf.rangecount(x, y));
        }
    }

    static void treeTest(int n){
        BinaryTree t = BinaryTree.randomBST(n);
        System.out.println("Tree:" + t);
        System.out.println("size() = " + t.size());
        System.out.println("leafAndOnlyLeaf() = " + t.leafAndOnlyLeaf());
        System.out.println("dawnOfSpring()  = " + t.dawnOfSpring());
        System.out.println("monkeyLand() = " + t.monkeyLand());
        System.out.println("bracketSequence() = " + t.bracketSequence());
    }

    public static void main(String[] args) {
        skippyTest(20);
        //treeTest(0);
    }
}
