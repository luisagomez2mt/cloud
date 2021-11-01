/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.reports;

import com.rentcloud.cloud.app.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author luigi
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CountClient {
    
    private Long total;
    private Client client;
    
}
