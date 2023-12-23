package dto;

/**
 *
 * @author Flavio Morais
 */
public class PlacarDTO {
    
    private int idPlayer1;
    private int idPlayer2;
    private String nicknamePlayer1;
    private String nicknamePlayer2;
    private int golsPlayer1;
    private int golsPlayer2;

    public PlacarDTO() {
    }

    public PlacarDTO(int idPlayer1, int idPlayer2, String nicknamePlayer1, String nicknamePlayer2, int golsPlayer1, int golsPlayer2) {
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
        this.nicknamePlayer1 = nicknamePlayer1;
        this.nicknamePlayer2 = nicknamePlayer2;
        this.golsPlayer1 = golsPlayer1;
        this.golsPlayer2 = golsPlayer2;
    }

    public int getIdPlayer1() {
        return idPlayer1;
    }

    public void setIdPlayer1(int idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public int getIdPlayer2() {
        return idPlayer2;
    }

    public void setIdPlayer2(int idPlayer2) {
        this.idPlayer2 = idPlayer2;
    }

    public String getNicknamePlayer1() {
        return nicknamePlayer1;
    }

    public void setNicknamePlayer1(String nicknamePlayer1) {
        this.nicknamePlayer1 = nicknamePlayer1;
    }

    public String getNicknamePlayer2() {
        return nicknamePlayer2;
    }

    public void setNicknamePlayer2(String nicknamePlayer2) {
        this.nicknamePlayer2 = nicknamePlayer2;
    }

    public int getGolsPlayer1() {
        return golsPlayer1;
    }

    public void setGolsPlayer1(int golsPlayer1) {
        this.golsPlayer1 = golsPlayer1;
    }

    public int getGolsPlayer2() {
        return golsPlayer2;
    }

    public void setGolsPlayer2(int golsPlayer2) {
        this.golsPlayer2 = golsPlayer2;
    }
}
