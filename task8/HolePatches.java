package task8;

import java.util.ArrayList;
import java.util.Scanner;

public class HolePatches {
	private static int sheetHeight, sheetWidth, patchSize;


	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		String[] first = input.split(" ");
		if(first[0].equals("Sheet")) {
			sheetHeight = Integer.parseInt(first[2]);
			sheetWidth = Integer.parseInt(first[3]);
		}
		input = scan.nextLine();
		String[] second = input.split(" ");
		if(second[0].equals("Patch")) {
			patchSize = Integer.parseInt(second[2]);
		}

//        System.out.println(sheetHeight + " " + sheetWidth + " " + patchSize);
		ArrayList<double[]> holes = new ArrayList<>();
		while(scan.hasNext()) {
			holes.clear();
			input = scan.nextLine();
			if(input.equalsIgnoreCase("positions of holes")) {
				while(!input.equalsIgnoreCase("end")) {
					input = scan.nextLine();
					try {
						if(input.equalsIgnoreCase("end")) throw new NumberFormatException();
						String[] hole = input.split(" {1}");
						double[] holeValues = {Double.parseDouble(hole[0]), Double.parseDouble(hole[1]), Double.parseDouble(hole[2])};
						holes.add(holeValues);
					} catch (NumberFormatException e) {
						holePatch(holes);
						break;
					}
				}
			}
		}
	}

	private static void holePatch(ArrayList<double[]> holes) {
		System.out.println(holes);


	}
}
