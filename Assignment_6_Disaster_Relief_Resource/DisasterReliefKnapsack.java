package Assignment_6_Disaster_Relief_Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Item{
	String name;
	int weight;
	int utility;
	boolean isPerishable;
	
	public Item(String name, int weight, int utility, boolean isPerishable){
		this.name = name;
		this.weight = weight;
		this.utility = utility;
		this.isPerishable = isPerishable;
	}
	
}

public class DisasterReliefKnapsack {

	public static int knapsackDP(List<Item> items, int capacity){
		int n = items.size();
		int[][] dp = new int[n+1][capacity+1];
		
		for(int i=1;i<=n;i++){
			Item item = items.get(i - 1);
			int weight = item.weight;
			int value = item.utility;
			
			if(item.isPerishable)
				value += 5;
			
			for(int w=1; w<=capacity; w++){
				if(weight <= w){
					dp[i][w] = Math.max(value + dp[i-1][w-weight], dp[i-1][w]);
				}
				else{
					dp[i][w] = dp[i-1][w];
				}
			}
		}
		return dp[n][capacity];
	}
	
	public static double greedyKnapsack(List<Item> items, int capacity){
		
		items.sort((a,b) -> {
			double ratioA = (double) a.utility / a.weight;
			double ratioB = (double) b.utility / b.weight;
			return Double.compare(ratioA, ratioB);
		});
		
		int currentWeight = 0;
		double totalUtility = 0;
		
		for(Item item : items){
			if(currentWeight + item.weight <= capacity){
				currentWeight += item.weight;
				totalUtility += item.utility;
			}
		}
		
		return totalUtility;
	}
	
	public static int multipleTruckKnapsack(List<Item> items, int numTrucks, int capacityPerTruck){
		int totalCapacity = numTrucks * capacityPerTruck;
		return knapsackDP(items, totalCapacity);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter Number of Items: ");
		int n = sc.nextInt();
		
		List<Item> items = new ArrayList<>();
		
		for(int i=1; i<=n; i++){
			System.out.print("\nEnter Name of Item" + i +": ");
			String name = sc.next();
			
			System.out.print("Enter Weight of Item: ");
			int weight = sc.nextInt();
			
			System.out.print("Enter Utility Value of Item: ");
			int utility = sc.nextInt();
			
			System.out.print("Is the Item is Perishable? (true/false): ");
			boolean perish = sc.nextBoolean();
			
			items.add(new Item(name, weight, utility, perish));
			
		}
		
		System.out.print("\nEnter Truck Capacity (in kg): ");
		int capacity = sc.nextInt();
		
		int maxUtility = knapsackDP(items, capacity);
		System.out.println("Maximum Total Utility Value: " + maxUtility);
		
		double greedyUtility = greedyKnapsack(new ArrayList<>(items), capacity);
		System.out.println("Approximate Total Utility Value: " + greedyUtility);
		
		System.out.print("Enter Number of Trucks: ");
		int numTrucks = sc.nextInt();
		int multiTruckUtility = multipleTruckKnapsack(items, numTrucks, capacity);
		System.out.println("Maximum Utility Value: " + multiTruckUtility);
		
		sc.close();
	}

}
