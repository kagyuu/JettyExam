/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jetty.session;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;
import lombok.NoArgsConstructor;

/**
 *
 * @author atsushihondoh
 */
@NoArgsConstructor
@Singleton
public class HelloSession implements Serializable {
    public String hello(){
        return "HLLO";
    }
    
}
