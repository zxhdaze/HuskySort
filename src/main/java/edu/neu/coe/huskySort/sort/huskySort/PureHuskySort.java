package edu.neu.coe.huskySort.sort.huskySort;

import edu.neu.coe.huskySort.sort.huskySortUtils.Coding;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoder;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskySortHelper;
import edu.neu.coe.huskySort.sort.simple.InsertionSort;
import edu.neu.coe.huskySort.util.LazyLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.binarySearch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 * @param <X> the type of the elements to be sorted.
 */
public class PureHuskySort<X extends Comparable<X>> {
    final static String stdPreOutput = "Benchmark - Begin run: %s sort %d Chinese names with %d runs\n";

    final static String stdPostOutput = "Benchmark - Average time is %f ms\n";
    public static void main(final String[] args) {
    	String[] cnNames = FileUtil.getAllNamesToArray();
    	//System.out.print(cnNames.length);
        String[] data1 = new String[250000];
        String[] data2 = new String[500000];
        String[] data3 = new String[1000000];
        String[] data4 = new String[2000000];
        String[] data5 = new String[4000000];
        System.arraycopy(cnNames, 0, data1, 0, 250000);
        huskySortBenchmark(data1);
        System.arraycopy(cnNames, 0, data2, 0, 500000);
        huskySortBenchmark(data2);
        System.arraycopy(cnNames, 0, data3, 0, 1000000);
        huskySortBenchmark(data3);
        // 2000000
        System.arraycopy(cnNames, 0, data4, 0, 1000000);
        System.arraycopy(cnNames, 0, data4, 1000000, 1000000);
        huskySortBenchmark(data4);
        // 4000000
        System.arraycopy(cnNames, 0, data5, 0, 1000000);
        System.arraycopy(cnNames, 0, data5, 1000000, 1000000);
        System.arraycopy(cnNames, 0, data5, 2000000, 1000000);
        System.arraycopy(cnNames, 0, data5, 3000000, 1000000);
        huskySortBenchmark(data5);
    	
    	
    }
    private static void huskySortBenchmark(String[] zhongwen) {
    	System.out.printf(stdPreOutput, "HuskySort", zhongwen.length, 10);
        ArrayList<Long> time = new ArrayList<Long>();
        System.nanoTime();

        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();

            final PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
            //String[] zhongwen = FileUtil.getAllNamesToArray();
            String[] pinyin = preprocess(zhongwen);
            int[] map = new int[zhongwen.length];
            for (int ii = 0; ii < map.length; ii++)
                map[ii] = ii;
            sorter.sort(pinyin,map);
            String[] rawA = new String[zhongwen.length];
            System.arraycopy(zhongwen,0,rawA,0,zhongwen.length);
            for(int ii = 0; ii < zhongwen.length;ii++)
                zhongwen[ii] = rawA[map[ii]];

            Long end = System.nanoTime();
            Long t = (end - start);
            time.add(t);
        }

        long sum = 0;
        for (Long t : time) {
            sum += t;
        }

        long avg = sum / 10;
        System.out.println("husky Average Time in ms: " + toMS(avg));
    	
    }
    
    public static void sortChinese(String[] zhongwen) {
        final PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
        //String[] zhongwen = FileUtil.getAllNamesToArray();
        String[] pinyin = preprocess(zhongwen);
        int[] map = new int[zhongwen.length];
        for (int ii = 0; ii < map.length; ii++)
            map[ii] = ii;
        sorter.sort(pinyin,map);
        String[] rawA = new String[zhongwen.length];
        System.arraycopy(zhongwen,0,rawA,0,zhongwen.length);
        for(int ii = 0; ii < zhongwen.length;ii++)
            zhongwen[ii] = rawA[map[ii]];
    }
    private static double toMS(long nano) {
        return TimeUnit.MILLISECONDS.convert(nano, TimeUnit.NANOSECONDS);
    }
    private static String[] preprocess(String[] zhongwen){
        String[] pinyin = new String[zhongwen.length];
        for (int i = 0; i < zhongwen.length; i++) {
            try {
                pinyin[i] = PinyinUtil.getPinYinByName(zhongwen[i]);
            } catch (Exception e) {
            	System.out.print(zhongwen.length+"   ");
            	System.out.print(zhongwen[i]+"   ");
                e.printStackTrace();
            }
        }
        return pinyin;
    }
    /**
     * The main sort method.
     *
     * @param xs the array to be sorted.
     */
    public void sort(final X[] xs, int[] map) {
        // NOTE: we start with a random shuffle
        // This is necessary if we might be sorting a pre-sorted array. Otherwise, we usually don't need it.
        if (mayBeSorted) Collections.shuffle(Arrays.asList(xs));
        // NOTE: First pass where we code to longs and sort according to those.
        final Coding coding = huskyCoder.huskyEncode(xs);
        final long[] longs = coding.longs;
        introSort(xs, longs, 0, longs.length, 2 * floor_lg(xs.length),map);

        // NOTE: Second pass (if required) to fix any remaining inversions.
        if (coding.perfect)
            return;
        if (useInsertionSort)
            new InsertionSort<X>().mutatingSort(xs);
        else
            Arrays.sort(xs);
    }

    /**
     * Primary constructor.
     *
     * @param huskyCoder       the Husky coder to be used for the encoding to longs.
     * @param mayBeSorted      if this is true, then we should perform a random shuffle to prevent an O(N*N) performance.
     *                         NOTE: that even though we are using IntroSort, the random shuffle precaution is necessary when
     * @param useInsertionSort if true, then insertion sort will be used to mop up remaining inversions instead of system sort.
     */
    public PureHuskySort(final HuskyCoder<X> huskyCoder, final boolean mayBeSorted, final boolean useInsertionSort) {
        this.huskyCoder = huskyCoder;
        this.mayBeSorted = mayBeSorted;
        this.useInsertionSort = useInsertionSort;
    }

    // CONSIDER invoke method in IntroSort
    private static int floor_lg(final int a) {
        return (int) (Math.floor(Math.log(a) / Math.log(2)));
    }

    private static final int sizeThreshold = 16;

    // TEST
    @SuppressWarnings({"UnnecessaryLocalVariable"})
    private void introSort(final X[] objects, final long[] longs, final int from, final int to, final int depthThreshold,int[] map) {
        // CONSIDER merge with IntroHuskySort
        if (to - from <= sizeThreshold + 1) {
            insertionSort(objects, longs, from, to,map);
            return;
        }
        if (depthThreshold == 0) {
            heapSort(objects, longs, from, to,map);
            return;
        }

        final int lo = from;
        final int hi = to - 1;

        if (longs[hi] < longs[lo]) swap(objects, longs, map,lo, hi);

        int lt = lo + 1, gt = hi - 1;
        int i = lo + 1;
        while (i <= gt) {
            if (longs[i] < longs[lo]) swap(objects, longs,map, lt++, i++);
            else if (longs[hi] < longs[i]) swap(objects, longs,map, i, gt--);
            else i++;
        }
        swap(objects, longs,map, lo, --lt);
        swap(objects, longs,map, hi, ++gt);
        introSort(objects, longs, lo, lt, depthThreshold - 1,map);
        if (longs[lt] < longs[gt]) introSort(objects, longs, lt + 1, gt, depthThreshold - 1,map);
        introSort(objects, longs, gt + 1, hi + 1, depthThreshold - 1,map);
    }

    // TEST
    private void heapSort(final X[] objects, final long[] longs, final int from, final int to,int[] map) {
        // CONSIDER removing these size checks. They haven't really been tested.
        if (to - from <= sizeThreshold + 1) {
            insertionSort(objects, longs, from, to,map);
            return;
        }
        final int n = to - from;
        for (int i = n / 2; i >= 1; i = i - 1) {
            downHeap(objects, longs, i, n, from,map);
        }
        for (int i = n; i > 1; i = i - 1) {
            swap(objects, longs,map, from, from + i - 1);
            downHeap(objects, longs, 1, i - 1, from,map);
        }
    }

    // TEST
    private void downHeap(final X[] objects, final long[] longs, int i, final int n, final int lo,int[] map) {
        final long d = longs[lo + i - 1];
        final X od = objects[lo + i - 1];
        int odd = map[lo + i - 1];
        int child;
        while (i <= n / 2) {
            child = 2 * i;
            if (child < n && longs[lo + child - 1] < longs[lo + child]) child++;
            if (d >= longs[lo + child - 1]) break;
            longs[lo + i - 1] = longs[lo + child - 1];
            objects[lo + i - 1] = objects[lo + child - 1];
            map[lo + i - 1] = map[lo + child - 1];
            i = child;
        }
        longs[lo + i - 1] = d;
        objects[lo + i - 1] = od;
        map[lo + i - 1] = odd;
    }

    void insertionSort(final X[] objects, final long[] longs, final int from, final int to,int[] map) {
        for (int i = from + 1; i < to; i++)
            if (OPTIMIZED)
                swapIntoSorted(objects, longs, i);
            else
                for (int j = i; j > from && longs[j] < longs[j - 1]; j--)
                    swap(objects, longs,map, j, j - 1);
    }

    /**
     * Regular swap of elements at indexes i and j, not necessarily adjacent.
     * However, for insertion sort, they will always be adjacent.
     *
     * @param xs    the X array.
     * @param longs the long array.
     * @param i     the index of one element to be swapped.
     * @param j     the index of the other element to be swapped.
     */
//    private void swap(final X[] xs, final long[] longs, final int i, final int j) {
//        // Swap longs
//        final long temp1 = longs[i];
//        longs[i] = longs[j];
//        longs[j] = temp1;
//        // Swap xs
//        final X temp2 = xs[i];
//        xs[i] = xs[j];
//        xs[j] = temp2;
//    }
    private void swap(final X[] xs, final long[] longs, int[] map, final int i, final int j) {
        // Swap longs
        final long temp1 = longs[i];
        longs[i] = longs[j];
        longs[j] = temp1;
        // Swap xs
        final X temp2 = xs[i];
        xs[i] = xs[j];
        xs[j] = temp2;
        //Swap map
        int temp3 = map[i];
        map[i] = map[j];
        map[j] = temp3;
    }

    /**
     * Swap method for insertion sort which takes advantage of the known fact that the elements of the array
     * at indices less than i are in order.
     *
     * @param xs    the X array.
     * @param longs the long array.
     * @param i     the index of the element to be moved.
     */
    private void swapIntoSorted(final X[] xs, final long[] longs, final int i) {
        int j = binarySearch(longs, 0, i, longs[i]);
        if (j < 0) j = -j - 1;
        if (j < i) swapInto(xs, longs, j, i);
    }

    /**
     * Swap method which uses half-swaps.
     *
     * @param xs    the X array.
     * @param longs the long array.
     * @param i     the index of the element to be moved.
     * @param j     the index of the destination of that element.
     */
    void swapInto(final X[] xs, final long[] longs, final int i, final int j) {
        if (j > i) {
            final X x = xs[j];
            System.arraycopy(xs, i, xs, i + 1, j - i);
            xs[i] = x;
            final long l = longs[j];
            System.arraycopy(longs, i, longs, i + 1, j - i);
            longs[i] = l;
        }
    }

    private HuskyCoder<X> getHuskyCoder() {
        return huskyCoder;
    }

    // NOTE that we keep this false because, for the size of arrays that we need to sort via insertion sort,
    // This optimization doesn't really help.
    // That might be because (a) arrays are short and (b) the binary search will likely take quite a bit longer than
    // necessary when the array is already close to being in order (since binary search starts in the middle).
    // It would be like looking up aardvark in the dictionary using strict binary search.
    private static final boolean OPTIMIZED = false;

    private final HuskyCoder<X> huskyCoder;
    private final boolean mayBeSorted;
    private final boolean useInsertionSort;

    private final static LazyLogger logger = new LazyLogger(PureHuskySort.class);
}
