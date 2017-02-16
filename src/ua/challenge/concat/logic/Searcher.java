package ua.challenge.concat.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Searcher {

	private Set<String> currentSet;
	private List<String> resultList;
	private List<String> words;
	private List<String> combinations;

	public Searcher(Set<String> currentSet) {
		this.currentSet = currentSet;
		resultList = new ArrayList<>();
		words = new ArrayList<>();
		combinations = new ArrayList<>();
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
						if (!(length - temp.length() == 1)) {
							longest = temp;
						}
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

	// correct implementation
	public void findCorrect() {
		List<String> currentList = new ArrayList<>(currentSet);
		Set<String> preWords = new HashSet<>();
		Collections.sort(currentList, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.length() - s2.length();
			}
		});

		for (int i = 0; i < currentList.size(); i++) {
			String temp = currentList.get(i);
			if (canConcat(temp, preWords)) {
				resultList.add(temp);
			}
			preWords.add(temp);
		}
	}

	private boolean canConcat(String word, Set<String> dict) {
		if (dict.isEmpty()) {
			return false;
		}
		boolean[] dp = new boolean[word.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= word.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (!dp[j])
					continue;
				if (dict.contains(word.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[word.length()];
	}

	// too slow
	public void findImprove() {
		List<String> cache = new ArrayList<>();
		for (String current : currentSet) {
			int length = current.length();
			int i = 0;
			int j = 1;
			int step = 0;
			String temp = "";
			boolean check = false;
			boolean fullWord = false;
			while (i < length) {
				if (i >= 1 && cache.isEmpty()) {
					break;
				}
				temp = current.substring(i, j);
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
					if (!cache.contains(temp)) {
						cache.add(temp);
						i = step;
						j = i;
					}
				}
				if (fullWord) {
					step++;
					i = step;
					j = i;
					fullWord = false;
				}
				j++;
			}

			words = cache;
			if (words.size() > 1) {
				permutation(0);
				if (combinations.contains(current)) {
					resultList.add(current);
				}
			}
			words.clear();
			cache.clear();
			combinations.clear();
		}
	}

	private void swap(int position1, int position2) {
		String temp = words.get(position1);
		words.set(position1, words.get(position2));
		words.set(position2, temp);
	}

	private void permutation(int start) {
		if (start != 0) {
			String temp = "";
			for (int i = 0; i < start; i++) {
				temp = temp.concat(words.get(i));
			}
			combinations.add(temp);
		}

		for (int i = start; i < words.size(); i++) {
			swap(start, i);
			permutation(start + 1);
			swap(start, i);
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
