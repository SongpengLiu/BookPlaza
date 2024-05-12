package com.example.bookplaza.surprise;

import com.example.bookplaza.io.HashMapWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

public class TfIdfGenerator {
    // TF-IDF Map to be saved
    private HashMap<Integer, HashMap<Integer, Double>> tfIdfMap = new HashMap<>();
    private HashMapWriter hashMapWriter = new HashMapWriter();


    /**
     * Get TF-IDF map
     * @author Yucheng Zhu
     * @return TF-IDF map
     */
    public HashMap<Integer, HashMap<Integer, Double>> getTfIdfMap() {
        return tfIdfMap;
    }


    /**
     * Calculate the term frequency
     * @author Yucheng Zhu
     * @param doc Current document to check TF
     * @param term Term to check TF
     * @return Term frequency
     */
    public double tf(HashSet<Integer> doc, int term) {
        double result = 0;
        for (int word : doc) {
            if (term == word)
                result++;
        }
        return result / doc.size();
    }

    /**
     * Calculate inverse document frequency
     *
     * @author Yucheng Zhu
     * @param docs All documents
     * @param term Term to check
     * @return Inverse document frequency
     */
    public double idf(List<HashSet<Integer>> docs, int term) {
        double n = 0;
        for (HashSet<Integer> doc : docs) {
            for (int word : doc) {
                if (term == word) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    /**
     * Calculate tf-idf
     * @author Yucheng Zhu
     * @param doc Current document to check TF
     * @param docs All documents
     * @param term Term to check
     * @return Inverse document frequency
     */
    public double tfIdf(HashSet<Integer> doc, List<HashSet<Integer>> docs, int term) {
        return tf(doc, term) * idf(docs, term);
    }

    /**
     * Calculate and store TF-IDF
     * @author Yucheng Zhu
     * @param docs All documents
     */
    public void calculateAndStoreTfIdf(List<HashSet<Integer>> docs) {
        // iterate through all documents
        for (int i = 0; i < docs.size(); i++) {
            // get the current document
            HashSet<Integer> doc = docs.get(i);
            for (int term : doc) { // use a set to avoid duplicate terms
                double tfIdfValue = tfIdf(doc, docs, term);

                // store the current TF-IDF value
                if (!tfIdfMap.containsKey(term)) {
                    tfIdfMap.put(term, new HashMap<>());
                }
                tfIdfMap.get(term).put(Integer.valueOf(i), tfIdfValue);
            }
        }
    }


    /**
     * Calculate and store TF-IDF from genres in files
     * @author Yucheng Zhu
     */
    public void calculateAndStoreTfIdfFromGenres() throws IOException {
        // read all books' genres into a matrix
        TokenisedGenresReader tokenisedGenresReader = new TokenisedGenresReader();
        List<HashSet<Integer>> docs = tokenisedGenresReader.readTokenisedGenres();

        // calculate and store TF-IDF
        calculateAndStoreTfIdf(docs);

        // get the TF-IDF map
        HashMap<Integer, HashMap<Integer, Double>> tfIdfMap = getTfIdfMap();

        // save the map to file
        hashMapWriter.write(
                Files.newOutputStream(Paths.get("src/main/assets/tfIdf.json").toAbsolutePath()),
                tfIdfMap
        );

    }


    /**
     * Write TF-IDF
     * @author Yucheng Zhu
     * @param docs All documents
     */
    public void calculateAndSaveTfIdf(List<HashSet<Integer>> docs) throws IOException {
        calculateAndStoreTfIdfFromGenres();
        HashMap<Integer, HashMap<Integer, Double>> tfIdfMap = getTfIdfMap();
        System.out.println(tfIdfMap.get(0));

    }
}
