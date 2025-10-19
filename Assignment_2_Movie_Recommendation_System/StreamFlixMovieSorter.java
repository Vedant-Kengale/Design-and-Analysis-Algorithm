package Assignment_2_Movie_Recommendation_System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Movie {
	private String title;
	private double imdbRating;
	private int releaseYear;
	private double watchTimePopularity;
	
	public Movie(String title, double imdbRating, int releaseYear, double watchTimePopularity) {
		this.title = title;
		this.imdbRating = imdbRating;
		this.releaseYear = releaseYear;
		this.watchTimePopularity = watchTimePopularity;
	}
	
	public String getTitle(){ 
		return title;
	}
	
	public double getImdbRating(){ 
		return imdbRating;
	}
	
	public int getReleaseYear(){ 
		return releaseYear; 
	}
	
	public double getWatchTimePopularity(){ 
		return watchTimePopularity; 
	}
	
	@Override
	public String toString(){
		return String.format("%-20s | IMDB: %.1f | Year: %d | Popularity: %.1f",
				title, imdbRating, releaseYear, watchTimePopularity);
	}
}

class QuickSorter{
	
	public static void quickSort(List<Movie> movies, int low,int high,Comparator<Movie> comparator){
		if(low < high){
			int pivotIndex = partition(movies,low,high,comparator);
			quickSort(movies,low,pivotIndex - 1, comparator);
			quickSort(movies,pivotIndex + 1,high,comparator);
		}
	}

	private static int partition(List<Movie> movies, int low, int high, Comparator<Movie> comparator) {
		Movie pivot = movies.get(high);
		int i = low-1;
		
		for(int j=low;j<high;j++){
			if(comparator.compare(movies.get(j), pivot) <= 0){
				i++;
				Collections.swap(movies, i, j);
			}
		}
		Collections.swap(movies, i+1, high);
		return i+1;
	}
}

public class StreamFlixMovieSorter {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		List<Movie> movies = new ArrayList<>();
		
		System.out.println(" ===StreamFlix Movie Recommendation System === ");
		System.out.println("Enter Number of Movies: ");
		int n = sc.nextInt();
		sc.nextLine();
		
		for(int i=1;i<=n;i++){
			System.out.println("\nMovie " + i + " details: ");
			System.out.println("Title");
			String title = sc.nextLine();
			
			System.out.println("IMDB Rating");
			double rating = sc.nextDouble();
			
			System.out.println("Release Year: ");
			int year = sc.nextInt();
			
			System.out.println("Watch Time Popularity: ");
			double popularity = sc.nextDouble();
			sc.nextLine();
			
			movies.add(new Movie(title, rating, year,popularity));
		}
		
		boolean running = true;
		
		while(running){
			System.out.println("\nSort Movies By: ");
			System.out.println("1. IMDB Rating");
			System.out.println("2. Release Year");
			System.out.println("3. Watch Time Popularity");
			System.out.println("4. Exit");
			System.out.println("Enter Your Choice (1-4): ");
			int ch = sc.nextInt();
			
			Comparator<Movie> comparator = null;
			switch(ch){
			
				case 1:
					comparator = Comparator.comparingDouble(Movie::getImdbRating);
					break;
					
				case 2:
					comparator = Comparator.comparingDouble(Movie::getReleaseYear);
					break;
				
				case 3:
					comparator = Comparator.comparingDouble(Movie::getWatchTimePopularity);
					break;
				
				case 4:
					System.out.println("Existing StremFlix System....");
					running = false;
					continue;
					
				default:
					System.out.println("Invalid Choice!! Enter option from [1-4]");
					continue;
			}
			
			long startTime = System.nanoTime();
			QuickSorter.quickSort(movies, 0, movies.size() - 1, comparator);
			long endTime = System.nanoTime();
			
			System.out.println("\n== Sorted Movie List ==");
			movies.forEach(System.out::println);
			
			double timeMS = (endTime - startTime) / 1_00_000.0;
			System.out.printf("\nSorting Completed in %.4f ms\n", timeMS);
			
		}
		
		sc.close();
	}

}
