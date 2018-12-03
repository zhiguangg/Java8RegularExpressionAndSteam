import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.IntSummaryStatistics;

public class Java8LambdaExpressionStream {

	public static void main(String[] args) {
		
	  /*
	   * Part I. 10 Example of Lambda Expressions and Streams in Java 8	http://javarevisited.blogspot.sg/2014/02/10-example-of-lambda-expressions-in-java8.html  
	   */

		//1. Implementing Runnable with RE
		new Thread(()->System.out.println("Java 8 Regular Expression Rocks")).start();
		
		//2.1 Even handling
		/*
		JButton show = new JButton("Show");
		show.addEventListener((e)->System.out.println("Event Listener Called"));
		*/

		//2.2 Comparator
		Comparator<Developer> names = (Developer o1, Developer o2) -> o1.getName().compareTo(o2.getName());
		List<Developer> devList = new ArrayList<Developer>();
		devList.add(new Developer("Tom", 24));
		devList.add(new Developer("Mary", 21));
		devList.add(new Developer("Jack", 51));
		devList.add(new Developer("Olive", 32));
		Collections.sort(devList, names);
		devList.forEach((dev)->System.out.println("name:"+dev.getName() + " Age:" +dev.getAge()));
		
		//2.3 Java 8 List interface supports sort method directly.
		devList.sort((Developer o1, Developer o2) -> o2.getName().compareTo(o1.getName()));
		devList.forEach((dev)->System.out.println("name:"+dev.getName() + " Age:" +dev.getAge()));

		//3. iterating over list
		List<Integer> numList = Arrays.asList(12, 23, 24, 25, 46);
		numList.forEach(System.out::println);  //method inference when element is not changes

		//4. Using Lambda Expression and functional Predicate
		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

  		System.out.println("Languages which starts with J :");
  		filter(languages, (str)->((String)str).startsWith("J"));

  		System.out.println("Languages which ends with a:");
  		filter(languages, (str)->((String)str).endsWith("a"));

  		System.out.println("Print All Languages");
  		filter(languages, (str)->true);

  		System.out.println("Print No Languages");
  		filter(languages, (str)->false);

  		System.out.println("Languages which length > 4");
  		filter(languages, (str)->((String)str).length() > 4);

  		//5. How to combine Predicate with lambda expression. and(), or() xor()
  		Predicate<String> startsWithJ = (n) ->n.startsWith("J");
  		Predicate<String> greaterAndEqualFour = (n)->n.length() == 4;
  		languages.stream().filter(startsWithJ.and(greaterAndEqualFour)).forEach((n)->System.out.println(n));

  		//6. Map and Reduce 
  		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500, 200, 200);
  		costBeforeTax.stream().map((cost)->cost*(1+0.02)).forEach(System.out::println);
  		double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
		System.out.println("Total : " + bill);

  		//7. create a list of string by filtering
  		List<String> variLength = Arrays.asList("a","bc","def","ghij","klmno","pqrstl");
  		variLength.stream().filter((x)->x.length() > 2).collect(Collectors.toList()).forEach(System.out::println);

		//8. Apply function to each element of the list
		List<String> G7 = Arrays.asList("USA", "Germany", "Britain", "France", "Japan", "Italy", "Canada");
		String G7String = G7.stream().map((x)->x.toUpperCase()).collect(Collectors.joining(","));
		System.out.println(G7String);  		

		//9. Creating a sublist by copying distinct value
		costBeforeTax.stream().map((x)->x * .12).distinct().forEach(System.out::println);

		//10. Calculating Max, Min, Avg and Sum of list element
		IntSummaryStatistics stats = costBeforeTax.stream().mapToInt((x)->x).summaryStatistics();
		System.out.println("Max:"+stats.getMax()+" Min:"+stats.getMin()+" Sum:"+stats.getSum()+" avg"+stats.getAverage());

	/*
     * Part II. Java 8 Stream 	http://www.java67.com/2014/04/java-8-stream-examples-and-tutorial.html 
     *	paralelStream(), filter(), joining(), .count()
	 */
		//1. counting empty strings
		List<String> lists = Arrays.asList("this", "that", "", "those", "empty", "", "");
		long count = lists.stream().filter((x)->x.isEmpty()).count();   //returns long type
		System.out.println("there're " + count + " empty strings");

		//2. count string whose length is more than 3
		count = lists.stream().filter((x)->x.length() > 3).count();
		System.out.println("there're " + count + " strings whose length is greater than 3");

		//3. count number of strings who starts with "t"
		count = lists.stream().filter((x)->x.startsWith("t")).count();
		System.out.println("there're " + count + " strings who starts with t"); 

		//4. remove all empty strings from list
		lists.stream().filter((x)->!x.isEmpty()).collect(Collectors.toList()).forEach(System.out::println);

		//5. create a list of strings with more than four characters
		lists.stream().filter((x)->x.length() > 4).collect(Collectors.toList()).forEach(System.out::println);

		//6. convert a list of strings to uppercase and join them with coma
		String result = lists.stream().map((x)->x.toUpperCase()).collect(Collectors.joining(","));
		System.out.println(result);

		//7. create a list of square of distinct numbers
		List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 10, 10, 40, 60);
		numbers.stream().map((x)->x*x).distinct().collect(Collectors.toList()).forEach(System.out::println);

		/*
		 *	>D. A deeper look into the Java 8 Date and Tme API  		http://www.mscharhag.com/2014/02/java-8-datetime-api.html
	  	 *  >E. Java 8 StampedLocks vs. ReadWriteLocks and Synchronized http://blog.takipi.com/java-8-stampedlocks-vs-readwritelocks-and-synchronized/
	  	 *  >F. Java 8 Tutorials by Benjamin Winterberg  				http://winterbe.com/posts/2014/03/16/java-8-tutorial/
	  	 *  >G. Official Tutorials 										http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
		 */
	}

	//Method correspond to 4.
	public static void filter(List names, Predicate condition) {
	    for(Object name: names)  { //String gives a compilation error 
	       if(condition.test((String)name)) {
	          System.out.println(name + " ");
	       }
	    }
  	}
}

class Developer{
	private String name;
	private int age;

	public Developer(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
