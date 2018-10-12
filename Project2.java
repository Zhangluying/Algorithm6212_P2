package project;

import java.util.ArrayList;
import java.util.List;

public class Project2 {
	public static void randomIsiArray(Integer[] n){
		for(int i=0;i<n.length;i++){
			n[i] = (int) (Math.random()*100);
		}
	}
	
	public static void InsertSort(Integer[] source) { 
	    int i, j; 
	    int insertNode;
	    for (i = 1; i < source.length; i++) { 
	      insertNode = source[i]; 
	      j = i - 1; 
	      while ((j >= 0) && insertNode < source[j]) { 
	        source[j + 1] = source[j]; 
	        j--;  
	      } 
	      source[j + 1] = insertNode;   
	    } 
	  } 
	
	private static int MedianOfMedians(Integer[] a, int k)
	{
		if (a.length <= 10)
		{
			InsertSort(a);
			return a[k-1];
		}
		
		int n = a.length;
		List<Integer[]> list = new ArrayList<Integer[]>();

		int count = 0;
		int m = n/5;
		//1.Divide the array into group of 5, there are n/5 groups.
		for( int i = 0; i < m; i++ ) {
			Integer[] arr = new Integer[5];
			for( int j = 0; j < 5; j++ ) {
				if( count == n ) 
					break;
				arr[j] = a[count++];
			}
			InsertSort(arr);   //2.Sort the small groups.
			list.add(arr);
		}
		
		//3.Collect all the n/5 medians from the n/5 groups.
		Integer[] x = new Integer[m];
		for (int i = 0; i< m; i++ ) {
			x[i] = list.get(i)[2];
		}
		
		int v = x[0];
		if(x.length>2){
			v=MedianOfMedians(x,x.length/2);   //4.Find the median of medians by recursively calling the algorithm.
		}

		//5.Partition the array on the median of medians.
		Integer[] l = l2( a, v );   //L2<Median.
		Integer[] r = l1( a, v );   //L1>Median.
		
		//6.Invoke the algorithm recursively on the left or the right partition depending on the value of k and the size of the partition.
		if( k == l.length+1 ) {
			return v;
		} else if( k <= l.length ){
			return MedianOfMedians(l,k);								
		} else {
			return MedianOfMedians(r,k-l.length-1);							
		}

	}
	
	private static Integer[] l2( Integer[] a, int pivot ) {
		if( a.length == 0)
			return a;
		int j = 0;
		Integer[] b = new Integer[a.length];
		for( int i = 0; i < a.length; i++ ) {
			if(a[i] < pivot) {
				b[j++] = a[i];
			}
		}
		Integer[] l = new Integer[j];
		System.arraycopy(b, 0, l, 0, j);
		return l;
	}

	private static Integer[] l1( Integer[] a, int pivot ) {
		if( a.length == 0)
			return a;
		int j = 0;
		Integer[] b = new Integer[a.length];
		for( int i = 0; i < a.length; i++ ) {
			if(a[i] > pivot) {
				b[j++] = a[i];
			}
		}
		Integer[] r = new Integer[j];
		System.arraycopy(b, 0, r, 0, j);
		return r;
	}
    
	public static void main(String[] args) {
		Integer[] a = new Integer[300];
		System.out.println("Array "+a.length+":");
		randomIsiArray(a);//Randomly generate integers less than 100.
		int k = 8;
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println();
		
		long start=System.nanoTime();
		int res = MedianOfMedians(a, k);    //Group by n/5
		long end=System.nanoTime();
		System.out.println("the "+k+"-smallest number is: "+ res);
		
		long time=end-start;
		System.out.println("execution time is:"+time+" ns");
	}
}
