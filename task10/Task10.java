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
	NormalNode finalNormalNode = null;

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
			if(numbers.get(i) != 1)
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

	private String normalSearch(ArrayList<Integer> numbers){

		NormalNode root = new NormalNode(numbers.get(0));
		finalNormalNode = null;
		String[] symbols = new String[numbers.size() - 1];

		StringBuilder sb = new StringBuilder();

		populateNormalTree(numbers, 1, root);
		if(finalNormalNode != null){
			for(int i = symbols.length - 1; i >= 0; i--){
				if(finalNormalNode.leftChild){
					symbols[i] = "+";
				} else {
					symbols[i] = "*";
				}
				finalNormalNode = finalNormalNode.parent;
			}
			if(finalNormalNode.equals(root)){
				for(int i = 0; i < symbols.length; i++){
					sb.append(numbers.get(i) + " " + symbols[i] + " ");
				}
				sb.append(numbers.get(numbers.size() - 1));
				return sb.toString();
			}
		} else {
			return "Impossible";
		}
		return "Impossible";
	}

	public void populateNormalTree(ArrayList<Integer> numbers, int i, NormalNode normalNode){
		int currSum = normalNode.leftVal + normalNode.rightVal;
		if(finalNormalNode != null){
			return;
		}

		if(i >= numbers.size()){
			if(currSum == finalGoal && normalNode.level == numbers.size() - 1){
				finalNormalNode = normalNode;
			}
			return;
		}

		if(currSum - normalNode.rightVal != normalNode.leftVal ||
				currSum - normalNode.leftVal != normalNode.rightVal ||
				normalNode.rightVal * numbers.get(i) / numbers.get(i) != normalNode.rightVal ||
				normalNode.rightVal < 0 || normalNode.leftVal < 0){
			return;
		}

		if(currSum > finalGoal){
			return;
		}

		if(currSum + numbers.get(i) <= finalGoal){
			normalNode.left = new NormalNode(numbers.get(i), i, true, normalNode);
			populateNormalTree(numbers, i+1, normalNode.left);
		}

		if(normalNode.rightVal * numbers.get(i) + normalNode.leftVal <= finalGoal){
			normalNode.right = new NormalNode(numbers.get(i), i, false, normalNode);
			populateNormalTree(numbers, i+1, normalNode.right);
		}

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

	private class NormalNode{
		public int leftVal, rightVal;
		public int level;
		public boolean leftChild;
		NormalNode left, right, parent;

		NormalNode(int val, int level, boolean lChild, NormalNode parent){
			this.parent = parent;
			this.leftChild = lChild;

			//Left child, so we do addition
			if(lChild){
				this.level = level;
				this.leftVal = parent.leftVal + parent.rightVal;
				this.rightVal = val;
			}
			//Else right child, so we do multiplication
			else {
				this.leftVal = parent.leftVal;
				this.rightVal = parent.rightVal * val;
				this.level = level;
			}
		}

		NormalNode(int val){
			this.leftVal = 0;
			this.rightVal = val;
			this.parent = null;
		}
	}

	public static void main(String[] args) {
		new Task10();
	}

}
