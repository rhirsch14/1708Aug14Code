package Q18;

public class MyClass extends MyAbstractClass {

	@Override
	public boolean checkUppercases(String s) {
		char[] string = s.toCharArray();
		char[] alph = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		for (int i = 0; i < alph.length; i++) {
			for (int j = 0; j < string.length; j++) {
				if(alph[i] == string[j]) return true;
			}
		}
		return false;
	}

	@Override
	public String convertToUppercase(String s) {
		return s.toUpperCase();
	}

	@Override
	public void convertToInt(String s) {
		int i = Integer.valueOf(s) + 10;
		System.out.println(s+" + 10 = " + i);
	}


}
