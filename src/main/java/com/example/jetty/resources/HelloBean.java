/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jetty.resources;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author hondou.atsushi
 */
@Data
@NoArgsConstructor
public class HelloBean {
    private String message;
    
    public HelloBean(String name) {
        this.message = String.format("Hello %s!", name);
    }
}
