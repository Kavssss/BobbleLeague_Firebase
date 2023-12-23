package dto;

public class PlayerDTO {
    
    private String name;
    private String nickname;

    public PlayerDTO() {
    }

    public PlayerDTO(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
