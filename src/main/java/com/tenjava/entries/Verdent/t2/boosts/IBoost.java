/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.boosts;

import com.tenjava.entries.Verdent.t2.entity.Jockey;

/**
 *
 * @author Verdent
 */
public interface IBoost {

    public String getName();

    public void activate(Jockey jockey);

    public void deactivate(Jockey jockey);

}
