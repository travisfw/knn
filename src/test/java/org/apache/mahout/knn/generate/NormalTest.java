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

package org.apache.mahout.knn.generate;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.math.stats.OnlineSummarizer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class NormalTest {
    @Before
    public void setUp() {
        RandomUtils.useTestSeed();
    }

    @Test
    public void testOffset() {
        OnlineSummarizer s = new OnlineSummarizer();
        Sampler<Double> sampler = new Normal(2, 5);
        for (int i = 0; i < 10001; i++) {
            s.add(sampler.sample());
        }

        assertEquals(String.format("m = %.3f, sd = %.3f", s.getMean(), s.getSD()), 2, s.getMean(), 0.04 * s.getSD());
        assertEquals(5, s.getSD(), 0.12);
    }

    @Test
    public void testSample() throws MathException {
        double[] data = new double[10001];
        Sampler<Double> sampler = new Normal();
        for (int i = 0; i < 10001; i++) {
            data[i] = sampler.sample();
        }
        Arrays.sort(data);

        NormalDistribution reference = new NormalDistributionImpl();

        assertEquals("Median", reference.inverseCumulativeProbability(0.5), data[5000], 0.04);
    }
}
