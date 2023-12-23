package util;

/**
 *
 * @author Flavio Morais
 */
public interface Constants {
    
    interface TABLES {
        String TB_MATCH = "tb_match";
        String TB_POWER = "tb_power";
        String TB_RESULTS = "tb_results";
        String TB_PLAYER = "tb_player";
        String TB_CATCH_POWER = "tb_catch_power";
        String TB_GOAL_TIME = "tb_goal_time";
    }
    
    interface COLUMNS {
        String TYPE_MATCH = "type_match";
        
        String ID_PLAYER = "id_player";
        String ID_PLAYER_1 = "id_player_1";
        String ID_PLAYER_2 = "id_player_2";
        String ID_POWER = "id_power";
        String ID_MATCH = "id_match";
        
        String GOALS_PRO = "goals_pro";
        String GOALS_CONTRA = "goals_contra";
        String POINTS = "points";
        
        String GOAL_TIME = "goal_time";
        
        String FL_USED = "fl_used";
        String FL_SUCESS = "fl_sucess";
        String FL_OWN_GOAL = "fl_own_goal";
        String DATE_MATCH_START = "date_match_start";
        String DATE_MATCH_END = "date_match_end";
        String LAST_ID = "last_id";
        
        String NAME_POWER = "name_power";
        String NAME_PLAYER = "name_player";
        String NICKNAME_PLAYER = "nickname_player";
        
        String DC_POWER = "dc_power";
        String VALUE_POWER = "value_power";
    }
    
}
