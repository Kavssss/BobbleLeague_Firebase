package dto;

import java.util.ArrayList;
import java.util.List;

public class PowersOfPLayerFromMatchDTO {
    
    private Integer idPlayer;
    private List<PowersFromMatchDTO> powersFromMatch = new ArrayList<>();

    public PowersOfPLayerFromMatchDTO() {
        this.idPlayer = null;
        this.powersFromMatch.add(null);
        for(int i = 1; i < 12; i++)
            this.powersFromMatch.add(new PowersFromMatchDTO(i));
    }

    public PowersOfPLayerFromMatchDTO(Integer idPlayer, List<PowersFromMatchDTO> powersFromMatch) {
        this.idPlayer = idPlayer;
        this.powersFromMatch = powersFromMatch;
    }

    public PowersOfPLayerFromMatchDTO(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public List<PowersFromMatchDTO> getPowersFromMatch() {
        return powersFromMatch;
    }

    public void setPowersFromMatch(List<PowersFromMatchDTO> powersFromMatch) {
        this.powersFromMatch = powersFromMatch;
    }
    
    
}
