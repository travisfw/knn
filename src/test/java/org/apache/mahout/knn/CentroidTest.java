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

package org.apache.mahout.knn;

import org.apache.mahout.knn.generate.MultiNormal;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.function.Functions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CentroidTest {
    @Test
    public void testUpdate() {
        MultiNormal f = new MultiNormal(20);

        Vector a = f.sample();
        Vector b = f.sample();
        Vector c = f.sample();

        final DenseVector x = new DenseVector(a);
        Centroid x1 = new Centroid(1, x);

        x1.update(new Centroid(2, new DenseVector(b)));
        Centroid x2 = new Centroid(x1);
        
        x1.update(c);

        // check for correct value
        assertEquals(0, x1.getVector().minus(a.plus(b).plus(c).assign(Functions.div(3))).norm(1), 1e-8);
        assertEquals(3, x1.getWeight(), 0);
        
        assertEquals(0, x2.minus(a.plus(b).divide(2)).norm(1), 1e-8);
        assertEquals(2, x2.getWeight(), 0);

        assertEquals(0, new Centroid(x1.getKey(), x1, x1.getWeight()).minus(x1).norm(1), 1e-8);

        // and verify shared storage
        assertEquals(0, x.minus(x1).norm(1), 0);

        assertEquals(3, x1.getWeight(), 1e-8);
        assertEquals(1, x1.getKey());
    }
}
