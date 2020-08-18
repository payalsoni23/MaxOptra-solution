import java.util.InputMismatchException;
import java.util.Scanner;

public class SumOfDigits {

	public static void main(String[] args) {

		System.out.println("Enter a number: ");

		try (Scanner input = new Scanner(System.in);) {
			int i = input.nextInt();
			System.out.println("Entered number: " + i);
			String t = i + "";
			int sum = 0;
			for (char c : t.toCharArray()) {
				sum += Character.getNumericValue(c);
			}
			System.out.println("Sum of digits: " + sum);
		} catch (InputMismatchException ime) {
			System.err.println("Invalid Input: " + ime.getMessage());
		} catch (Exception e) {
			System.err.println("Error occured: " + e.getMessage());
		}

	}

}
