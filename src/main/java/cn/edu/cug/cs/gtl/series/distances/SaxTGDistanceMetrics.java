package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

public class SaxTGDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int alphabet;
    protected long wordSize;

    public SaxTGDistanceMetrics(long w, int alphabet) {
        this.alphabet = alphabet;
        this.wordSize = w;
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.saxtg((Series) a, (Series) b, (int) this.wordSize, this.alphabet);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}