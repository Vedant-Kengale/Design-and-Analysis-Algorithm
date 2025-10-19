package Assignment_1_Customer_Order_Sorting_System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Order {
	private String orderId;
	private LocalDateTime timestamp;
	private double amount;

	public Order(String orderId, LocalDateTime timestamp, double amount) {
		this.orderId = orderId;
		this.timestamp = timestamp;
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return String.format("Order[%s, %s, $%.2f]", orderId,
				timestamp.format(DateTimeFormatter.ofPattern("MM-dd HH:mm")), amount);
	}
}

class MergeSort {

	public static void sort(Order[] orders){
		if(orders.length <= 1)
			return;
		mergeSort(orders,0,orders.length-1);
	}

	private static void mergeSort(Order[] orders, int left, int right) {
		if (left < right) {
			int mid = left + (right - left) / 2;
			mergeSort(orders, left, mid);
			mergeSort(orders, mid + 1, right);
			merge(orders, left, mid, right);
		}
	}

	private static void merge(Order[] orders, int left, int mid, int right) {
		int n1 = mid - left+1;
		int n2 = right - mid;
		
		Order[] L = new Order[n1];
		Order[] R = new Order[n2];
		
		System.arraycopy(orders, left, L, 0, n1);
		System.arraycopy(orders, mid+1, R, 0, n2);
		
		int i=0, j=0, k=left;
		
		while(i<n1 && j<n2){
			if(L[i].getTimestamp().compareTo(R[j].getTimestamp()) <=0){
				orders[k++] = L[i++];
			}
			else{
				orders[k++] = R[j++];
			}
		}
		
		while(i<n1)
			orders[k++] = L[i++];
		while(j<n2)
			orders[k++] = R[j++];
	}
}

public class CustomerOrderManagement {
	
	public static void main(String[] args) {
		
		System.out.println("=== Customer Order Management System ===");
		
		int size = 1_00_000;
		System.out.println("Generating " +size+"Orders...");
		Order[] orders = generateOrders(size);
		
		System.out.println("\nBefore sorting (first 3)");
		for(int i=0;i<3;i++)
			System.out.println(" " + orders[i]);
		
		System.out.println("\nSorting with Merge Sort..");
		long start = System.nanoTime();
		MergeSort.sort(orders);
		long end = System.nanoTime();
		
		System.out.println("\nAfter Sorting (first 3)");
		for(int i=0;i<3;i++)
			System.out.println(" " + orders[i]);
		
		double timeMs = (end-start)/ 1_00_000.0;
		System.out.printf("\nSorted %,d orders in %.2f ms\n",size,timeMs);
		System.out.println("Verification: " + (isSorted(orders) ? "Passed" : "Failed"));
		
		printAnalysis();
	}

	private static Order[] generateOrders(int size) {
		Order[] orders = new Order[size];
		Random rand = new Random();
		LocalDateTime base = LocalDateTime.now().minusDays(30);
		
		for(int i=0;i<size;i++){
			orders[i] = new Order("ORD" + i,
					base.plusMinutes(rand.nextInt(43200)),
					10 + rand.nextDouble() * 990);
		}
		
		return orders;
	}
	
	private static boolean isSorted(Order[] orders) {
		for(int i=0;i < orders.length - 1;i++){
			if(orders[i].getTimestamp().compareTo(orders[i+1].getTimestamp()) > 0)
				return false;
		}
		return true;
	}
	
	private static void printAnalysis() {
		System.out.println("\n===Time Complexity Analysis===");
		System.out.println("\nMerge Sort:");
		System.out.println("Best/Avg/Worst: O(n log n)");
		System.out.println("Space: O(n)");
		System.out.println("Stable: Yes");
		
		System.out.println("\nVS Other Algorithms:");
		System.out.println("Quick Sort: O(n log n)");
		System.out.println("Bubble Sort: O(n^2)");
		System.out.println("Heap Sort: O(n log n)");
		
		System.out.println("\nWhy Merge Sort?");
		System.out.println("Guaranteed O(n log n) performance");
		System.out.println("Stable - maintains order equality");
		System.out.println("Efficient for 1M+ orders");
		System.out.println("Predictable for product systems");
	}
	
}

