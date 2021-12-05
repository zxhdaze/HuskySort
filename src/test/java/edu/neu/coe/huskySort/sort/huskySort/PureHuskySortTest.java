package edu.neu.coe.huskySort.sort.huskySort;

import edu.neu.coe.huskySort.sort.BaseHelper;
import edu.neu.coe.huskySort.sort.huskySortUtils.Coding;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoder;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.huskySort.util.PrivateMethodInvoker;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PureHuskySortTest {

    private final BaseHelper<String> helper = new BaseHelper<>("dummy helper");

    @Test
    public void testSortString1() {
        String[] testCase = {"高炜", "崔大巍", "蔡政翰", "李儒龙", "刘守祥", "牛桂英"};
        String[] ans = {"蔡政翰", "崔大巍", "高炜", "李儒龙", "刘守祥", "牛桂英"};
        //final PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
        PureHuskySort.sortChinese(testCase);
        assertArrayEquals(ans, testCase);
    }
    
    @Test
    public void testSortString2() {
        String[] testCase = {"李大营", "魏晓腾", "卢若冰", "胡会鹏", "颜建新", "姜元元"};
        String[] ans = {"胡会鹏", "姜元元", "李大营", "卢若冰", "魏晓腾", "颜建新"};
        //final PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
        PureHuskySort.sortChinese(testCase);
        assertArrayEquals(ans, testCase);
    }
    
    @Test
    public void testSortString3() {
        String[] testCase = {"吕海婷", "吴宪生", "谷晓明", "杨惠如", "邢红军", "宁金霞"};
        String[] ans = {"谷晓明", "吕海婷", "宁金霞", "吴宪生", "邢红军", "杨惠如"};
        //final PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
        PureHuskySort.sortChinese(testCase);
        assertArrayEquals(ans, testCase);
    }
    

//    @Test
//    public void testSortString2() {
//        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
//        final int N = 1000;
//        helper.init(N);
//        final String[] xs = helper.random(String.class, r -> r.nextLong() + "");
//        sorter.sort(xs);
//        assertTrue("sorted", helper.sorted(xs));
//    }
//
//    @Test
//    public void testSortString3() {
//        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
//        final int N = 1000;
//        helper.init(N);
//        final String[] xs = helper.random(String.class, r -> {
//            int x = r.nextInt(1000000000);
//            final BigInteger b = BigInteger.valueOf(x).multiply(BigInteger.valueOf(1000000));
//            return b.toString();
//        });
//        sorter.sort(xs);
//        assertTrue("sorted", helper.sorted(xs));
//    }
//
//    @Test
//    public void testSortString4() {
//        String[] xs = {"Hello", "Goodbye", "Ciao", "Willkommen"};
//        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
//        sorter.sort(xs);
//        assertTrue("sorted", helper.sorted(xs));
//    }
//
//    @Test
//    public void testSortString5() {
//        String[] xs = {"Hello", "Goodbye", "Ciao", "Welcome"};
//        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
//        sorter.sort(xs);
//        assertTrue("sorted", helper.sorted(xs));
//    }
//
//    @Test
//    public void testFloorLg() {
//        PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(PureHuskySort.class);
//        assertEquals(Integer.valueOf(1), privateMethodInvoker.invokePrivate("floor_lg", 3));
//        assertEquals(Integer.valueOf(2), privateMethodInvoker.invokePrivate("floor_lg", 5));
//    }
//
//    @Test
//    public void testWithInsertionSort() {
//        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, true);
//        PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(sorter);
//        HuskyCoder<String> huskyCoder = (HuskyCoder<String>) privateMethodInvoker.invokePrivate("getHuskyCoder");
//        final int N = 100;
//        helper.init(N);
//        final String[] xs = helper.random(String.class, r -> r.nextLong() + "");
//        Coding coding = huskyCoder.huskyEncode(xs);
//        sorter.insertionSort(xs, coding.longs, 0, N);
//        assertEquals(0, helper.inversions(xs));
//    }
//
//    @Test
//    public void testInsertionSort() {
//        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
//        PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(sorter);
//        HuskyCoder<String> huskyCoder = (HuskyCoder<String>) privateMethodInvoker.invokePrivate("getHuskyCoder");
//        final int N = 100;
//        helper.init(N);
//        final String[] xs = helper.random(String.class, r -> r.nextLong() + "");
//        Coding coding = huskyCoder.huskyEncode(xs);
//        sorter.insertionSort(xs, coding.longs, 0, N);
//        assertEquals(0, helper.inversions(xs));
//    }
}
