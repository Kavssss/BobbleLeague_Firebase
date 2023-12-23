package controller;

import dto.PlacarDTO;
import dto.PlayerDTO;
import dto.PowersOfPLayerFromMatchDTO;
import java.time.LocalDateTime;
import java.util.List;
import service.Service;

public class ServiceController {
    
    Service service = new Service();
    
    public int insertStartMatch(int type_match, int idPlayer1, int idPlayer2) {
        return service.insertStartMatch(type_match, idPlayer1, idPlayer2);
    }
    
    public void insertEndMatch(int idMatch) {
        service.insertEndMatch(idMatch);
    }
    
    public void insertPower(int idPlayer, int idPower, int idMatch, boolean used, boolean sucess, boolean owngoal) {
        service.insertPower(idPlayer, idPower, idMatch, used, sucess, owngoal);
    }
    
    public void insertResult(int idMatch, int id_player1, int id_player2, int scoreP1, int scoreP2, int ptsP1,
                int ptsP2) {
        service.insertResult(idMatch, id_player1, id_player2, scoreP1, scoreP2, ptsP1, ptsP2);
    }
    
    public void insertNewPower(String name, String nickname, Double value) {
        service.insertNewPower(name, nickname, value);
    }
    
    public void insertGoalTime(int idMatch, int id_player, LocalDateTime startMatch) {
        service.insertGoalTime(idMatch, id_player, startMatch);
    }
    
    
    // BUSCAS
    
    
    public int findLastMatchId() {
        return service.findLastMatchId();
    }
    
    public String findPowerById(int idPower) {
        return service.findPowerById(idPower);
    }
    
    public int findIdByPlayer(String name) {
        return service.findIdByPlayer(name);
    }
    
    public PlayerDTO findPlayerById(int id) {
        return service.findPlayerById(id);
    }
    
    public List<String> findAllPlayers() {
        return service.findAllPlayers();
    }
    
    public Integer findIdByPower(String power) {
        return service.findIdByPower(power);
    }
    
    public PlacarDTO findScoreByIdMatch(int idMatch) {
        return service.findScoreByIdMatch(idMatch);
    }
    
    public List<PowersOfPLayerFromMatchDTO> findPowerStatsByMatch(int idMatch) {
        return service.findPowerStatsByMatch(idMatch);
    }
    
    public List<String> findGoalTime(int idMatch, int idPlayer) {
        return service.findGoalTime(idMatch, idPlayer);
    }
    
    public List<String> findAllIdMatches() {
        return service.findAllIdMatches();
    }
    
    public String findTimeMatch(int idMatch) {
        return service.findTimeMatch(idMatch);
    }
    
}
