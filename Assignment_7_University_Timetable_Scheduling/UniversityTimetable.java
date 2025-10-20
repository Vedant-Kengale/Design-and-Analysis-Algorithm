package Assignment_7_University_Timetable_Scheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UniversityTimetable {

	private int numCourses;
	private List<List<Integer>> adjList;
	private String[] courseNames;
	
	public UniversityTimetable(int numCourses){
		this.numCourses = numCourses;
		adjList = new ArrayList<>();
		courseNames = new String[numCourses];
		for(int i=0; i < numCourses; i++)
			adjList.add(new ArrayList<>());
	}
	
	//Add edge between two courses that have common students
	public void addConflict(int course1, int course2){
		adjList.get(course1).add(course2);
		adjList.get(course2).add(course1);
	}
	
	//Assign a course name for clarity
	public void setCourseName(int index, String name){
		courseNames[index] = name;
	}
	
	//Greedy Coloring Algorithm
	public void scheduleExam(){
		int[] result = new int[numCourses];
		Arrays.fill(result, -1);
		
		result[0] = 0;
		
		boolean[] available = new boolean[numCourses];
		
		for(int u=1; u < numCourses; u++){
			Arrays.fill(available, true);
			
			for(int neighbor : adjList.get(u)){
				if(result[neighbor] != -1)
					available[result[neighbor]] = false;
			}
			
			int cr;
			for(cr=0; cr < numCourses; cr++){
				if(available[cr])
					break;
			}
			
			result[u] = cr;
		}
		
		//Print the final schedule
		int maxSlot = Arrays.stream(result).max().getAsInt() + 1;
		System.out.println("\n=== University Exam Timetable ===");
		System.out.println("Total Exam Slots Required: " + maxSlot);
		System.out.println("---------------------------------");
		for(int i=0; i < numCourses; i++)
			System.out.println("Course: " + courseNames[i] + " --> Slot " + (result[i] + 1));
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in) ;
		
		System.out.print("Enter Number of Courses: \n");
		int n = sc.nextInt();
		sc.nextLine();
		
		UniversityTimetable timetable = new UniversityTimetable(n);
		
		for(int i=0; i<n; i++){
			System.out.print("Enter Course Name " + (i+1) + ": ");
			timetable.setCourseName(i, sc.nextLine());
		}
		
		System.out.print("Enter Number of Conflicts (edges): ");
		int e = sc.nextInt();
		
		System.out.print("Enter Conflicting Course Pairs (Index start from 0): ");
		for(int i=0; i<e; i++){
			System.out.print("\nConflict " + (i+1) + ": ");
			int c1 = sc.nextInt();
			int c2 = sc.nextInt();
			timetable.addConflict(c1, c2);
		}
		
		timetable.scheduleExam();
		sc.close();
	}
}
