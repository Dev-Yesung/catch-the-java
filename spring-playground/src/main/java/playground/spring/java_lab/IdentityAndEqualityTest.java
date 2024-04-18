package playground.spring.java_lab;

import java.util.ArrayList;
import java.util.List;

public class IdentityAndEqualityTest {

	public static void main(String[] args) {
		String str1 = new String("Identity Test");
		String str2 = new String("Identity Test");
		String str3 = "Identity Test";
		String str4 = "Identity Test";

		System.out.println("Identity Test 1 : " + str1 == str2);
		System.out.println("HashCodes(str1, str2) : " + str1.hashCode() + ", " + str2.hashCode());
		System.out.println("Identity Test 2 : " + str1 == str3);
		System.out.println("HashCodes(str1, str3) : " + str1.hashCode() + ", " + str3.hashCode());
		System.out.println("Identity Test 3 : " + str2 == str3);
		System.out.println("HashCodes(str1, str3) : " + str2.hashCode() + ", " + str3.hashCode());
		System.out.println("Identity Test 4 : " + str3 == str4);
		System.out.println("HashCodes(str3, str4) : " + str3.hashCode() + ", " + str4.hashCode());

		System.out.println();

		List<String> list = List.of(str1, str2, str3, str4);
		list.forEach(elem -> list.forEach(str ->
			System.out.println("( " + elem.hashCode() + ", " + str.hashCode() + " ) : " + elem.equals(str))));
	}
}
