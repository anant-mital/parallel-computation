import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/*
 * This class demonstrates parallel computation using Java Future interface
 */

public class ArraySum {

	public static void main(String[] args) {

		// Let us define two integer arrays
		int[] left = new int[] { 1, 5, 6, 7, 10, 17, 19, 35 };
		int[] right = new int[] { 31, 35, 63, 74, 101, 147, 119, 345 };
				

		// If we need to calculate sum of all the elements in an integer array
		// we can write a function which traverses over the array and gives us the sum
		// for e.g. see method sum(). To calculate the sum of two arrays we can call the 
		// method twice and then return the result like this,
		
		int sum_synch = sum(left) + sum(right);
				
		// In the above statement, the sum method gets called sequentially. 
		// The power of parallel computation lies in parallel execution of the two calls to sum
		// This can be demonstrated as below.
		
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        FutureTask<Integer> leftsum =
        		   new FutureTask <Integer>(new Callable<Integer>() {
        		     public Integer call() {
        		       return (sum(left));
        		   }});
        
        FutureTask<Integer> rightsum =
     		   new FutureTask <Integer>(new Callable<Integer>() {
     		     public Integer call() {
     		       return (sum(right));
     		   }});
        
        executor.execute(leftsum);
        executor.execute(rightsum);
		
        int sum_asynch = 0;
        
        try {
        	
        	sum_asynch = leftsum.get() + rightsum.get();
		
        }catch(Exception iex) {
			
        	iex.printStackTrace();
			
		}
        

        
	}

	public static int sum(int[] ar) {
		System.out.println(Thread.currentThread().getName() + "- Time of call : " +  LocalDateTime.now() );
		int sum = 0;
		for (int i : ar) {
			sum = sum + i;
		}
		
		return (sum);
	}

}
