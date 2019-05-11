package task10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Task10 {

	int finalGoal;
	char operation;
	boolean LEFT = false, RIGHT = true;
	Node finalNode = null;

	public Task10(){

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("task10/input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(scanner.hasNextLine()){

			ArrayList<Integer> nums = new ArrayList<>();

			String numString = scanner.nextLine();
			String goal = scanner.nextLine();

			String[] splitNumString = numString.split(" ");
			for(String s : splitNumString){
				nums.add(Integer.parseInt(s));
			}

			String[] splitGoal = goal.split(" ");

			finalGoal = Integer.parseInt(splitGoal[0]);
			operation = splitGoal[1].charAt(0);

			String result = calculateCombination(nums, finalGoal, operation);
			System.out.println(operation + " " + finalGoal + " " + result);
		}

	}

	public String calculateCombination(ArrayList<Integer> numbers, int finalNumber, char operation){

		int minPossibleNumber = 0;

		for(int i = 0; i < numbers.size(); i++){
			minPossibleNumber += numbers.get(i);
		}

		if(minPossibleNumber > finalNumber){
			return ("Impossible");
		} else {

			if(operation == 'L'){

				return(leftToRight(numbers));

			} else if (operation == 'N'){

				return(normalSearch(numbers));

			}

		}
		return null;
	}

	public String leftToRight(ArrayList<Integer> numbers){
		finalNode = null;

		Node root = new Node(numbers.get(0), null, false);
		populateTree(root, 0, numbers);

		if(finalNode == null){
			return ("Impossible");
		}

		StringBuilder result = new StringBuilder();
		String[] symbols = new String[numbers.size() - 1];

		Node curr = finalNode;
		for(int i = numbers.size() - 2; i >= 0; i--){
			if(curr.childOf == LEFT){
				symbols[i] = "+";
			} else {
				symbols[i] = "*";
			}
			curr = curr.parent;
		}

		for(int i = 0; i < numbers.size() - 1; i++){
			result.append(numbers.get(i) + " " + symbols[i] + " ");
		}
		result.append(numbers.get(numbers.size() - 1));

		return result.toString();
	}

	private String normalSearch(ArrayList<Integer> nums){

		return "Nah not implemented yet boi";
	}

	public void populateTree(Node currNode, int currDepth, ArrayList<Integer> nums){
		if(currNode.value == finalGoal){
			if(currDepth == nums.size() - 1){
				finalNode = currNode;
				return;
			}
		}

		if(currDepth+1 >= nums.size()){
			currNode.left = null;
			currNode.right = null;
			return;
		}

		int nextNum = nums.get(currDepth + 1);

		if(currNode.value * nextNum > finalGoal){
			currNode.left = null;
			currNode.right = null;
			return;
		}
		currNode.left = new Node(currNode.value + nextNum, currNode, LEFT);
		currNode.right = new Node(currNode.value * nextNum, currNode,  RIGHT);

		populateTree(currNode.left, currDepth+1, nums);
		populateTree(currNode.right, currDepth+1, nums);
	}

	public void printNodes(Node currNode){
		if(currNode == null){
			System.out.println("Null");
			return;
		}
		System.out.println(currNode.value);
		printNodes(currNode.left);
		printNodes(currNode.right);
	}

	private class Node{
		public int value;
		public Node left, right, parent;
		public boolean childOf;

		public Node(int value, Node parent, boolean child){
			this.value = value;
			this.childOf = child;
			this.parent = parent;
		}
	}

	public static void main(String[] args) {
		new Task10();
	}

}
