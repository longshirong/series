package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.ml.classification.NNClassifier;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.series.clustering.KmeansClusterer;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.SeriesBuilder;
import cn.edu.cug.cs.gtl.series.distances.EuclideanDistanceMetrics;
import org.checkerframework.checker.units.qual.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EUKmeansClustering {
    private static final Logger LOGGER = LoggerFactory.getLogger(EUKmeansClustering.class);
    public static void main(String[] args) {
        LOGGER.debug("begin Eu Kmeans");
        ExperimentalConfig config = new ExperimentalConfig();
        int m = config.getDataFiles().size();
        int n = 1;
        List<Pair<String, double[]>> results = new ArrayList<>();
        int k = 0;
        config.setResultFileName("euclidean_kmeansClustering.xls");
        LOGGER.info("data files:{}", config.getDataFiles().size());
        try {
            for (Pair<String, String> p : config.getDataFiles()) {
                String name = File.getFileNameWithoutSuffix(p.first());
                name = name.substring(0, name.indexOf('_'));
                LOGGER.info(name);
                MultiSeries test = SeriesBuilder.readTSV(p.second());
                TestSet<Series, String> testSet = test.toTestSet();
                double[] r = new double[n];
                k = 0;
                for (int i = 0; i < n; ++i) {
                    EuclideanDistanceMetrics<Series> disFunc = new EuclideanDistanceMetrics<>();
                    KmeansClusterer<Series,String> kmeansClusterer = new KmeansClusterer<>(testSet,disFunc,10,100);
                    r[k] = kmeansClusterer.score();
                    LOGGER.info("score {}", r[k]);
                    k++;
                }
                results.add(new Pair<>(name, r));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.writeResults(results);
    }
}
