package ua.challenge.concat.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.challenge.concat.logic.Searcher;

public class Runner {

	public static void main(String[] args) {
		File file = new File("./resources/words.txt");
		Set<String> set = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				set.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Searcher sr = new Searcher(set);
		sr.findCorrect();
		List<String> list = sr.getResultList();
		Collections.sort(list, Searcher.getComparator());

		System.out.println(
				"First concatenated word : " + list.get(0) + ", with " + list.get(0).length() + " characters.");
		System.out.println(
				"Second concatenated word : " + list.get(1) + ", with " + list.get(1).length() + " characters.");
		System.out.println("Total number of concatenated words : " + list.size());
	}

}
