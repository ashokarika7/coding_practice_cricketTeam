/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.player.service;

import com.example.player.repository.PlayerRepository; 
import com.example.player.model.Player; 
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.player.model.PlayerRowMapper;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class PlayerH2Service implements PlayerRepository{

    @Autowired 
    private JdbcTemplate db;
    @Override 
    public ArrayList<Player> getPlayers(){
        List<Player> playerCollection= db.query("SELECT * FROM TEAM", new PlayerRowMapper());
        ArrayList<Player> players= new ArrayList<>(playerCollection);
        return players;
    }

    public Player getPlayerById(int playerId){
        try{
            Player player= db.queryForObject("SELECT * FROM TEAM WHERE playerId= ?", new PlayerRowMapper(), playerId);
            return player;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public  Player addPlayer(Player player){
        db.update("INSERT INTO TEAM(playerName, jerseyNumber, role) VALUES (?, ?, ?)", player.getPlayerName(), player.getJerseyNumber(), player.getRole());
        Player savedPlayer= db.queryForObject("SELECT * FROM TEAM WHERE playerName= ? AND jerseyNumber= ? AND role= ?", new PlayerRowMapper(),
                                              player.getPlayerName(), player.getJerseyNumber(), player.getRole() );
         return savedPlayer;
    }

    public Player updatePlayer(int playerId, Player player){
            if (player.getPlayerName() != null){
                db.update("UPDATE TEAM SET playerName= ? WHERE playerId= ?", player.getPlayerName(), playerId);
            }
            if ( (Integer)player.getJerseyNumber() != null){
                db.update("UPDATE TEAM SET jerseyNumber= ? WHERE playerId= ?", player.getJerseyNumber(), playerId);
            }
            if (player.getRole() != null){
                db.update("UPDATE TEAM SET role= ? WHERE playerId= ?", player.getRole(), playerId);
            }
            return getPlayerById(playerId);
    }

    public void deletePlayer(int playerId){
        db.update("DELETE FROM TEAM WHERE playerId= ?", playerId);
    }

	

}