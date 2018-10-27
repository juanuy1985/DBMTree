package amazon.com.company;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import mam.dbmtree.QueryResult;
import mam.features.FeatureExtractor;
import mam.features.FeatureExtractorHistogram;
import mam.objects.Image;
import mam.dbmtree.ChooseSubTreeMinDist;
import mam.dbmtree.SplitNodeMinMax;
import mam.dbmtree.DBMTree;
import mam.distances.Euclidean;


public class Main {

    public static List<String> pathImages(String path) throws Exception{
        return Files.walk(Paths.get(path))
                .filter(s -> s.toString().endsWith(".jpg") || s.toString().endsWith(".jpge"))
                .map(Path::toString)
                .sorted()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception{
        
        DBMTree<Image> tree = new DBMTree<>(
                10,
                new ChooseSubTreeMinDist<>(),
                new SplitNodeMinMax<>(),
                new Euclidean());

        FeatureExtractor extractor = new FeatureExtractorHistogram(8192);

        List<String> paths = pathImages("D:\\Maestria\\RI\\tareas\\MLP\\Briggi\\Wavelets\\Resources\\faces");
        
        System.out.println("Insertando en total: " + paths.size() + " objetos.");
        
        for (int index=0; index<paths.size() ;index++) {
            String path = paths.get(index);
            tree.insert(new Image(path, extractor));
        }

        Image query = new Image("D:\\Maestria\\RI\\tareas\\MLP\\Briggi\\Wavelets\\Resources\\faces\\cfloro\\cfloro.1.jpg", extractor);

        System.out.println("Finish indexing.");

        Set<QueryResult<Image>> result = tree.rangeQuery(query, 0.4);
        //result.forEach(element -> System.out.println(String.format("id: %s\ndistance: %4.3f", element.getResult().getAddress(), element.getDistance())));
        
        Set<QueryResult<Image>> result2 = tree.knnSearch(query, 10);
        result2.forEach(element -> System.out.println(String.format("id: %s\ndistance: %4.3f", element.getResult().getAddress(), element.getDistance())));
    }
}
