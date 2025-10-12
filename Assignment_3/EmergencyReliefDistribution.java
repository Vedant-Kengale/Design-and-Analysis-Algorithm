package daaCollegecodes.Assignment_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class ReliefItem{
	String name;
	double weight, value;
	boolean divisible;
	
	public ReliefItem(String name, double weight, double value, boolean divisible) {
		this.name = name;
		this.weight = weight;
		this.value = value;
		this.divisible = divisible;
	}
	
	double ratio(){
		return value/weight;
	}
}

public class EmergencyReliefDistribution{
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<ReliefItem> items = new ArrayList<>();
		
		System.out.println("==== Emergency Relief Supply Distribution ====");
		System.out.println("Enter Number of items: ");
		int num = sc.nextInt();
		sc.nextLine();
		
		for(int i=1;i<=num;i++){
			System.out.println("\nitem " + i + " details:");
			
			System.out.println("Name: ");
			String name = sc.nextLine();
			
			System.out.println("Weight (in kg): ");
			double w = sc.nextDouble();
			
			System.out.println("Utility value: ");
			double v = sc.nextDouble();
			
			System.out.println("Divisible (True/False): ");
			boolean div = sc.nextBoolean();
			sc.nextLine();
			
			items.add(new ReliefItem(name,w,v,div));
		}
		
		System.out.println("\nEnter boat capacity (kg): ");
		double capacity = sc.nextDouble();
		
		//Sort
		Collections.sort(items, Comparator.comparingDouble(ReliefItem::ratio).reversed());
		
		double totalValue = 0, totalWeight = 0;
		System.out.println("\n===========================");
		System.out.printf("%-15s %-10s %-10s %-15s\n","Item","Weight","Value","Taken");
		System.out.println("\n===========================");
		
		for(ReliefItem item: items){
			if(totalWeight >= capacity)
				break;
			
			double remaining = capacity - totalWeight;
			if(item.weight <= remaining){
				totalWeight += item.weight;
				totalValue += item.value;
				System.out.printf("%-15s %-10.2f %-10.2f %-15s\n",item.name, item.weight, item.value, "Full");
				
			}
			else if(item.divisible){
				double fraction = remaining/item.weight;
				totalWeight += remaining;
				totalValue += item.value * fraction;
				System.out.printf("%-15s %-10.2f %-10.2f %-15s\n",item.name, remaining, item.value * fraction, String.format("%.0f%%", fraction * 100));
				
			}
		}
		
		System.out.println("=============================");
		System.out.printf("Maximun Utility Value: " + String.format("%.2f", totalValue));
		sc.close();
	}
}
