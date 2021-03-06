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

import org.apache.mahout.math.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SampleSequenceFileWriterTest {
    @Test
    public void testWrite() throws IOException {
        List<Vector> data = SampleSequenceFileWriter.writeTestFile("foo", 30, 1000000, false);
        List<Vector> actual = SampleSequenceFileWriter.readTestFile("foo");
        Assert.assertEquals(data.size(), actual.size());
        Assert.assertTrue(data.size() > 0);
        int i = 0;
        for (Vector vector : data) {
            Assert.assertEquals(0, vector.minus(actual.get(i)).norm(1), 1e-8);
            i++;
        }
    }
}
