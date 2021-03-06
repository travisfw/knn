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
import org.apache.mahout.math.function.DoubleDoubleFunction;

/**
 * A centroid is a weighted vector.  We have it delegate to the vector itself for lots of operations
 * to make it easy to use vector search classes and such.
 */
public class Centroid extends WeightedVector {
    public Centroid(Centroid original) {
        super(original.size(), original.getWeight(), original.getKey());
        delegate = original.like();
        delegate.assign(original);
    }

    public Centroid(int key, Vector initialValue) {
        super(initialValue, 1, key);
    }

    public Centroid(int key, Vector initialValue, double weight) {
        super(initialValue, weight, key);
    }

    public void update(final Centroid other) {
        update(other.delegate, other.getWeight());
    }

    public void update(Vector v) {
        update(v, 1);
    }

    public void update(Vector v, final double w) {
        final double weight = getWeight();
        final double totalWeight = weight + w;
        delegate.assign(v, new DoubleDoubleFunction() {
            @Override
            public double apply(double v, double v1) {
                return (weight * v + w * v1) / totalWeight;
            }
        });
        setWeight(weight + w);
    }

    /**
     * Gets the index of this centroid.  Use getIndex instead to maintain standard names.
     * @return
     */
    @Deprecated
    public int getKey() {
        return getIndex();
    }

    public void addWeight() {
        setWeight(getWeight() + 1);
    }

    public String toString() {
        return String.format("key = %d, weight = %.2f, vector = %s", getIndex(), getWeight(), delegate);
    }

}
