/*
 * 
 * You can use the following import statemets
 * 
 * import org.springframework.web.bind.annotation.*;
 * import java.util.*;
 * 
 */

package com.example.player.controller;

import java.util.*;
import com.example.player.model.Player;
import com.example.player.service.PlayerH2Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class PlayerController{

    @Autowired
    public PlayerH2Service ps;
    @GetMapping("/players")
    public ArrayList<Player> getPlayers(){
        return ps.getPlayers();
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable ("playerId") int playerId){
        return ps.getPlayerById(playerId);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player){
        return ps.addPlayer(player);
    }

    @PutMapping("/players/{playerId}")
    public Player updatePlayer(@PathVariable ("playerId") int playerId, @RequestBody Player player){
        return ps.updatePlayer(playerId, player);
    }

    @DeleteMapping("players/{playerId}")
    public void deletePlayer(@PathVariable ("playerId") int playerId){
        ps.deletePlayer(playerId);
    }


}
