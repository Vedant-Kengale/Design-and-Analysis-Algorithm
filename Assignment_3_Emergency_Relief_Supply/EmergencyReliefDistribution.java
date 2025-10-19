package Assignment_3_Emergency_Relief_Supply;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class ReliefItem {
    String name;
    double weight, value;
    int priority;
    boolean divisible;

    public ReliefItem(String name, double weight, double value, int priority, boolean divisible) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.priority = priority;
        this.divisible = divisible;
    }

    double ratio() {
        return value / weight;
    }

    String type() {
        return divisible ? "Divisible" : "Indivisible";
    }
}

public class EmergencyReliefDistribution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<ReliefItem> items = new ArrayList<>();

        System.out.println("==== Emergency Relief Supply Distribution ====");
        System.out.print("Enter number of items: ");
        int num = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= num; i++) {
            System.out.println("\nItem " + i + " details:");

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Weight (in kg): ");
            double w = sc.nextDouble();

            System.out.print("Utility value: ");
            double v = sc.nextDouble();

            System.out.print("Priority (1 = highest): ");
            int p = sc.nextInt();

            System.out.print("Divisible (true/false): ");
            boolean div = sc.nextBoolean();
            sc.nextLine();

            items.add(new ReliefItem(name, w, v, p, div));
        }

        System.out.print("\nEnter maximum weight capacity of the boat (in kg): ");
        double capacity = sc.nextDouble();

        // Sort by priority (ascending) then by value/weight (descending)
        items.sort(Comparator.comparingInt((ReliefItem r) -> r.priority)
                .thenComparing(Comparator.comparingDouble(ReliefItem::ratio).reversed()));

        // Display sorted items
        System.out.println("\nSorted Items (by Priority, then Value/Weight):");
        System.out.printf("%-20s %-10s %-10s %-10s %-15s %-15s\n",
                "Item", "Weight", "Value", "Priority", "Value/Weight", "Type");
        for(ReliefItem item : items) {
            System.out.printf("%-20s %-10.2f %-10.2f %-10d %-15.2f %-15s\n",
                    item.name, item.weight, item.value, item.priority, item.ratio(), item.type());
        }

        double totalValue = 0, totalWeight = 0;
        List<String> selectedItems = new ArrayList<>();

        System.out.println("\nItems selected for transport:");
        for(ReliefItem item : items) {
            if(totalWeight >= capacity)
                break;

            double remaining = capacity - totalWeight;

            if(item.weight <= remaining) {
                totalWeight += item.weight;
                totalValue += item.value;
                selectedItems.add(String.format(" - %s: %.0f kg, Utility = %.0f, Priority = %d, Type = %s",
                        item.name, item.weight, item.value, item.priority, item.type()));
            }
            else if(item.divisible && remaining > 0) {
                double fraction = remaining / item.weight;
                totalWeight += remaining;
                totalValue += item.value * fraction;
                selectedItems.add(String.format(" - %s: %.0f kg, Utility = %.0f, Priority = %d, Type = %s",
                        item.name, remaining, item.value * fraction, item.priority, item.type()));
            }
        }

        for(String s : selectedItems) {
            System.out.println(s);
        }

        System.out.println("\n===== Final Report =====");
        System.out.printf("Total weight carried: %.2f kg\n", totalWeight);
        System.out.printf("Total utility value carried: %.2f units\n", totalValue);

        sc.close();
    }
}

