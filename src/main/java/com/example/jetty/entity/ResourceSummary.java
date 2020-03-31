package com.example.jetty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceSummary implements Comparable<ResourceSummary>{
    private String directory;
    private String name;

    @Override
    public int compareTo(ResourceSummary t) {
        int cmp = directory.compareTo(t.getDirectory());
        if (0 == cmp) {
            cmp = name.compareTo(t.getName());
        }
        return cmp;
    }
}
