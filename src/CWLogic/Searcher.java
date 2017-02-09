package CWLogic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Searcher {

	private Set<String> currentSet;
	private List<String> resultList;

	public Searcher(Set<String> currentSet) {
		this.currentSet = currentSet;
		resultList = new ArrayList<>();
	}

	public List<String> getResultList() {
		return resultList;
	}

	public void find() {
		for (String curr : currentSet) {
			int length = curr.length();
			int i = 0;
			int j = i + 1;
			String temp = "";
			String longest = "";
			boolean check = false;
			boolean fullWord = false;
			while (j <= length) {
				temp = curr.substring(i, j);
				if (temp.length() != length) {
					check = currentSet.contains(temp);
				} else {
					fullWord = true;
					check = false;
				}
				if (j == length) {
					fullWord = true;
				}
				if (check) {
					if (longest.length() < temp.length()) {
						longest = temp;
					}
				}
				if (fullWord && longest.length() != 0) {
					i += longest.length();
					j = i;
					fullWord = false;
					longest = "";
				}
				j++;
			}
			if (check) {
				resultList.add(curr);
			}
		}
	}

	public static Comparator<String> getComparator() {
		Comparator<String> comparator = new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				return s2.length() - s1.length();
			}
		};
		return comparator;

	}

}
