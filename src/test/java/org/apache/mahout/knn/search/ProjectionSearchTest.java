/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.knn.search;

import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.knn.generate.MultiNormal;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.MatrixSlice;
import org.junit.BeforeClass;

public class ProjectionSearchTest extends AbstractSearchTest {
    private static Matrix data;
    private static ProjectionSearch searcher;

    @BeforeClass
    public static void setUp() {
        data = new DenseMatrix(1000, 20);
        MultiNormal gen = new MultiNormal(20);
        for (MatrixSlice slice : data) {
            slice.vector().assign(gen.sample());
        }

        searcher = new ProjectionSearch(20, new EuclideanDistanceMeasure(), 4, 20);
    }

    @Override
    public Iterable<MatrixSlice> testData() {
        return data;
    }

    @Override
    public UpdatableSearcher getSearch() {
        return searcher;
    }
}
