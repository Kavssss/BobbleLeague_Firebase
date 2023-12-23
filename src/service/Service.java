package service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import dto.PlacarDTO;
import dto.PlayerDTO;
import dto.PowersOfPLayerFromMatchDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import util.Constants;
import util.DateUtils;

public class Service {
    
    //*******Inserções********
    
    public Integer insertStartMatch(int type_match, int idPlayer1, int idPlayer2) {
        this.createColumnAndInsertData(Constants.TABLES.TB_MATCH,
                Arrays.asList(Constants.COLUMNS.TYPE_MATCH, Constants.COLUMNS.ID_PLAYER_1, Constants.COLUMNS.ID_PLAYER_2,
                        Constants.COLUMNS.DATE_MATCH_START, Constants.COLUMNS.DATE_MATCH_END),
                Arrays.asList(type_match, idPlayer1, idPlayer2, DateUtils.getDateTimeFormated(), ""));
        System.out.println("Insert Start Match  ->  "
                + "type_match: " + type_match + " - "
                + "idPlayer1: " + idPlayer1 + " - "
                + "idPlayer2: " + idPlayer2 + " - "
                + "Time Start: " + DateUtils.getDateTimeFormated());
        return this.findLastMatchId();
    }

    public Integer findLastMatchId() {
        try {
            return selectIdFromDatabase(Constants.TABLES.TB_MATCH, Constants.COLUMNS.LAST_ID).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void insertEndMatch(int idMatch) {
        this.insertData(Constants.TABLES.TB_MATCH, Arrays.asList(Constants.COLUMNS.DATE_MATCH_END),
                Arrays.asList(DateUtils.getDateTimeFormated()));
    }
    
    public void insertPower(int idPlayer, int idPower, int idMatch, boolean used, boolean sucess, boolean owngoal) {
        this.createColumnAndInsertData(Constants.TABLES.TB_CATCH_POWER,
                Arrays.asList(Constants.COLUMNS.ID_PLAYER, Constants.COLUMNS.ID_POWER, Constants.COLUMNS.ID_MATCH,
                    Constants.COLUMNS.FL_USED, Constants.COLUMNS.FL_SUCESS, Constants.COLUMNS.FL_OWN_GOAL),
                Arrays.asList(idPlayer, idPower, idMatch, used, sucess, owngoal));
    }
    
    public void insertResult(int idMatch, int id_player1, int id_player2, int scoreP1, int scoreP2, int ptsP1,
            int ptsP2) {
        this.createColumnAndInsertData(Constants.TABLES.TB_RESULTS,
                Arrays.asList(Constants.COLUMNS.ID_MATCH, Constants.COLUMNS.ID_PLAYER, Constants.COLUMNS.GOALS_PRO,
                    Constants.COLUMNS.GOALS_CONTRA, Constants.COLUMNS.POINTS),
                Arrays.asList(idMatch, id_player1, scoreP1, scoreP2, ptsP1));
        
        this.createColumnAndInsertData(Constants.TABLES.TB_RESULTS,
                Arrays.asList(Constants.COLUMNS.ID_MATCH, Constants.COLUMNS.ID_PLAYER, Constants.COLUMNS.GOALS_PRO,
                    Constants.COLUMNS.GOALS_CONTRA, Constants.COLUMNS.POINTS),
                Arrays.asList(idMatch, id_player2, scoreP2, scoreP1, ptsP2));
    }
    
    public void insertGoalTime(int idMatch, int id_player, LocalDateTime startMatch) {
        this.createColumnAndInsertData(Constants.TABLES.TB_GOAL_TIME,
                Arrays.asList(Constants.COLUMNS.ID_MATCH, Constants.COLUMNS.ID_PLAYER, Constants.COLUMNS.GOAL_TIME),
                Arrays.asList(idMatch, id_player, DateUtils.getDifferenceTime(startMatch)));
    }

    public void insertNewPower(String name, String nickname, Double value) {
        this.createColumnAndInsertData(Constants.TABLES.TB_POWER,
                Arrays.asList(Constants.COLUMNS.NAME_POWER, Constants.COLUMNS.DC_POWER, Constants.COLUMNS.VALUE_POWER),
                Arrays.asList(name, nickname, value));
    }
    
    //********Buscas********
    
    public String findPowerById(int idPower) {
        return this.findById(Constants.TABLES.TB_POWER, Constants.COLUMNS.NAME_POWER, idPower);
    }
    
    public Integer findIdByPower(String power) {
        return this.findByName(Constants.TABLES.TB_POWER, Constants.COLUMNS.NAME_POWER, power);
    }
    
    public int findIdByPlayer(String name) {
        return this.findByName(Constants.TABLES.TB_PLAYER, Constants.COLUMNS.NAME_PLAYER, name);
    }
    
    public PlayerDTO findPlayerById(int id) {
        return new PlayerDTO(
                this.findById(Constants.TABLES.TB_PLAYER, Constants.COLUMNS.NAME_PLAYER, id),
                this.findById(Constants.TABLES.TB_PLAYER, Constants.COLUMNS.NICKNAME_PLAYER, id));
    }
    
    public List<String> findAllPlayers() {
        return this.selectAllPlayers();
    }

    public PlacarDTO findScoreByIdMatch(int idMatch) {
        PlacarDTO placar = findScorePlayersByIdMatch(idMatch);
        placar.setNicknamePlayer1(this.findPlayerById(placar.getIdPlayer1()).getNickname());
        placar.setNicknamePlayer2(this.findPlayerById(placar.getIdPlayer2()).getNickname());
        return placar;
    }
    
    public List<PowersOfPLayerFromMatchDTO> findPowerStatsByMatch(int idMatch) {
        return vwPowerStatsByMatch(idMatch);
    }
    
    public List<String> findGoalTime(int idMatch, int idPlayer) {
        List<String> retorno = this.findListGoalTime(idMatch, idPlayer);
        return retorno.isEmpty() ? null : retorno;
    }
    
    public List<String> findAllIdMatches() {
        return this.selectAllIdMatches();
    }
    
    public String findTimeMatch(int idMatch) {
        return this.calcTimeMatch(idMatch);
    }
    
    //*********funções auxiliares*********
    
    private String calcTimeMatch(int idMatch) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference(Constants.TABLES.TB_MATCH + "/" + idMatch);
            System.out.println(databaseReference);
            CompletableFuture<String> future = new CompletableFuture<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String start = String.valueOf(
                            dataSnapshot.child(Constants.COLUMNS.DATE_MATCH_START).getValue()).substring(13);
                    String end = String.valueOf(
                            dataSnapshot.child(Constants.COLUMNS.DATE_MATCH_END).getValue()).substring(13);
                    
                    String value = DateUtils.getDifferenceTime(start, end);
                    future.complete(value);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private List<String> selectAllIdMatches() {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference(Constants.TABLES.TB_MATCH);
            CompletableFuture<List<String>> future = new CompletableFuture<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> value = new ArrayList<>();
                    for (DataSnapshot dado : dataSnapshot.getChildren())
                        value.add(dado.getKey());
                    value.remove(0);
                    value.remove(value.size()-1);
                    future.complete(value);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private List<String> findListGoalTime(int idMatch, int idPlayer) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference(Constants.TABLES.TB_GOAL_TIME);
            CompletableFuture<List<String>> future = new CompletableFuture<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> value = new ArrayList<>();
                    for (DataSnapshot dado : dataSnapshot.getChildren()) {
                        Integer idMatchDb = dado.child(Constants.COLUMNS.ID_MATCH).getValue(Integer.class);
                        if (Objects.nonNull(idMatchDb) && idMatchDb.equals(idMatch)) {
                            Integer idPlayerDb = dado.child(Constants.COLUMNS.ID_PLAYER).getValue(Integer.class);
                            if (Objects.nonNull(idPlayerDb) && idPlayerDb.equals(idPlayer)) {
                                value.add(dado.child(Constants.COLUMNS.GOAL_TIME).getValue(String.class));
                            }
                        }
                    }
                    future.complete(value);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private List<PowersOfPLayerFromMatchDTO> vwPowerStatsByMatch(int idMatch) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference(Constants.TABLES.TB_CATCH_POWER);
            CompletableFuture<List<PowersOfPLayerFromMatchDTO>> future = new CompletableFuture<>();
            PowersOfPLayerFromMatchDTO pfm1 = new PowersOfPLayerFromMatchDTO();
            PowersOfPLayerFromMatchDTO pfm2 = new PowersOfPLayerFromMatchDTO();
            
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dados) {
                    boolean isFirst = true;
                    for (DataSnapshot dado : dados.getChildren()) {
                        Integer idMatchDb = dado.child(Constants.COLUMNS.ID_MATCH).getValue(Integer.class);
                        if (Objects.nonNull(idMatchDb) && idMatchDb == idMatch) {
                            int idPlayerDb = dado.child(Constants.COLUMNS.ID_PLAYER).getValue(Integer.class);
                            int idPowerDb = dado.child(Constants.COLUMNS.ID_POWER).getValue(Integer.class);
                            if (isFirst) {
                                pfm1.setIdPlayer(idPlayerDb);
                                pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdCaught();
                                if (dado.child(Constants.COLUMNS.FL_USED).getValue(Boolean.class))
                                    pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdUsed();
                                if (dado.child(Constants.COLUMNS.FL_SUCESS).getValue(Boolean.class))
                                    pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdSucess();
                                if (dado.child(Constants.COLUMNS.FL_OWN_GOAL).getValue(Boolean.class))
                                    pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdOwnGoal();
                                isFirst = false;
                            } else {
                                if (idPlayerDb == pfm1.getIdPlayer()) {
                                    pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdCaught();
                                    if (dado.child(Constants.COLUMNS.FL_USED).getValue(Boolean.class))
                                        pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdUsed();
                                    if (dado.child(Constants.COLUMNS.FL_SUCESS).getValue(Boolean.class))
                                        pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdSucess();
                                    if (dado.child(Constants.COLUMNS.FL_OWN_GOAL).getValue(Boolean.class))
                                        pfm1.getPowersFromMatch().get(idPowerDb).increaseQtdOwnGoal();
                                } else {
                                    if (Objects.isNull(pfm2.getIdPlayer()))
                                        pfm2.setIdPlayer(idPlayerDb);
                                    pfm2.getPowersFromMatch().get(idPowerDb).increaseQtdCaught();
                                    if (dado.child(Constants.COLUMNS.FL_USED).getValue(Boolean.class))
                                        pfm2.getPowersFromMatch().get(idPowerDb).increaseQtdUsed();
                                    if (dado.child(Constants.COLUMNS.FL_SUCESS).getValue(Boolean.class))
                                        pfm2.getPowersFromMatch().get(idPowerDb).increaseQtdSucess();
                                    if (dado.child(Constants.COLUMNS.FL_OWN_GOAL).getValue(Boolean.class))
                                        pfm2.getPowersFromMatch().get(idPowerDb).increaseQtdOwnGoal();
                                }
                            }
                        }
                    }
                    future.complete(Arrays.asList(pfm1, pfm2));
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private PlacarDTO findScorePlayersByIdMatch(int idMatch) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.TABLES.TB_RESULTS);
            CompletableFuture<PlacarDTO> future = new CompletableFuture<>();
            PlacarDTO placar = new PlacarDTO();
            
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dados) {
                    boolean isPlayer1 = true;
                    for (DataSnapshot dado : dados.getChildren()) {
                        Integer idMatchBd = dado.child(Constants.COLUMNS.ID_MATCH).getValue(Integer.class);
                        if (Objects.nonNull(idMatchBd) && idMatch == idMatchBd) {
                            if (isPlayer1) {
                                placar.setIdPlayer1(dado.child(Constants.COLUMNS.ID_PLAYER).getValue(Integer.class));
                                placar.setGolsPlayer1(dado.child(Constants.COLUMNS.GOALS_PRO).getValue(Integer.class));
                                isPlayer1 = false;
                            } else {
                                placar.setIdPlayer2(dado.child(Constants.COLUMNS.ID_PLAYER).getValue(Integer.class));
                                placar.setGolsPlayer2(dado.child(Constants.COLUMNS.GOALS_PRO).getValue(Integer.class));
                                break;
                            }
                        }
                    }
                    future.complete(placar);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<String> selectAllPlayers() {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.TABLES.TB_PLAYER);
            CompletableFuture<List<String>> future = new CompletableFuture<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dados) {
                    List<String> nomes = new ArrayList<>();
                    for (DataSnapshot dado : dados.getChildren()) {
                        String nome = dado.child(Constants.COLUMNS.NAME_PLAYER).getValue(String.class);
                        if (Objects.nonNull(nome))
                            nomes.add(dado.child(Constants.COLUMNS.NAME_PLAYER).getValue(String.class));
                    }
                    future.complete(nomes);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private Integer findByName(String table, String column, String name) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(table);
            CompletableFuture<Integer> future = new CompletableFuture<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer value = null;
                    for (DataSnapshot dado : dataSnapshot.getChildren()) {
                        String nameDb = dado.child(column).getValue(String.class);
                        if (nameDb != null && nameDb.equals(name)) {
                            value = Integer.parseInt(dado.getKey());
                            break;
                        }
                    }
                    future.complete(value);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private String findById(String table, String column, int id) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(table);
            CompletableFuture<String> future = new CompletableFuture<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.child(String.valueOf(id)).child(column)
                            .getValue(String.class);
                    future.complete(value);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                    future.completeExceptionally(databaseError.toException());
                }
            });
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private CompletableFuture<Integer> selectIdFromDatabase(String tabela, String coluna) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(tabela);
        CompletableFuture<Integer> future = new CompletableFuture<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.child(coluna).getValue(Integer.class);
                future.complete(value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Erro ao ler dados: " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
    
    private void createColumnAndInsertData(String table, List<String> columns, List<Object> values) {
        try {
            Integer nextId = this.selectIdFromDatabase(table, Constants.COLUMNS.LAST_ID).get() + 1;
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(table + "/" + nextId);
            
            for (String column : columns)
                databaseReference.setValueAsync(column);

            for (int i = 0; i < columns.size(); i++)
                databaseReference.child(columns.get(i)).setValueAsync(values.get(i)).get();
            
            databaseReference = FirebaseDatabase.getInstance().getReference(table + "/" + Constants.COLUMNS.LAST_ID);
            databaseReference.setValueAsync(nextId).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void insertData(String table, List<String> columns, List<Object> values) {
        try {
            Integer lastId = this.selectIdFromDatabase(table, Constants.COLUMNS.LAST_ID).get();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(table + "/" + lastId);
            for (int i = 0; i < columns.size(); i++)
                databaseReference.child(columns.get(i)).setValueAsync(values.get(i)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
        
}
