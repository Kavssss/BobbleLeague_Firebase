package dto;

public class PowersFromMatchDTO {
    
    private Integer idPower;
    private Integer qtdCaught;
    private Integer qtdUsed;
    private Integer qtdSucess;
    private Integer qtdOwnGoal;

    public PowersFromMatchDTO() {
        this.qtdCaught = 0;
        this.qtdUsed = 0;
        this.qtdSucess = 0;
        this.qtdOwnGoal = 0;
    }

    public PowersFromMatchDTO(Integer idPower, Integer qtdCaught, Integer qtdUsed, Integer qtdSucess, Integer qtdOwnGoal) {
        this.idPower = idPower;
        this.qtdCaught = qtdCaught;
        this.qtdUsed = qtdUsed;
        this.qtdSucess = qtdSucess;
        this.qtdOwnGoal = qtdOwnGoal;
    }
    
    public PowersFromMatchDTO(Integer idPower) {
        this.idPower = idPower;
        this.qtdCaught = 0;
        this.qtdUsed = 0;
        this.qtdSucess = 0;
        this.qtdOwnGoal = 0;
    }
    
    public void increaseQtdCaught() {
        this.qtdCaught++;
    }
    
    public void increaseQtdUsed() {
        this.qtdUsed++;
    }
    
    public void increaseQtdSucess() {
        this.qtdSucess++;
    }
    
    public void increaseQtdOwnGoal() {
        this.qtdOwnGoal++;
    }

    public Integer getIdPower() {
        return idPower;
    }

    public Integer getQtdCaught() {
        return qtdCaught;
    }

    public Integer getQtdUsed() {
        return qtdUsed;
    }

    public Integer getQtdSucess() {
        return qtdSucess;
    }

    public Integer getQtdOwnGoal() {
        return qtdOwnGoal;
    }

    public void setIdPower(Integer idPower) {
        this.idPower = idPower;
    }

    public void setQtdCaught(Integer qtdCaught) {
        this.qtdCaught = qtdCaught;
    }

    public void setQtdUsed(Integer qtdUsed) {
        this.qtdUsed = qtdUsed;
    }

    public void setQtdSucess(Integer qtdSucess) {
        this.qtdSucess = qtdSucess;
    }

    public void setQtdOwnGoal(Integer qtdOwnGoal) {
        this.qtdOwnGoal = qtdOwnGoal;
    }
    
    
    
}
