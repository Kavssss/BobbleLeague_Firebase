package boobleleague;

import controller.ServiceController;
import model.DAO;
import views.ViewPrincipal;
import views.ViewBuscaMatches;

public class BoobleLeague {
    public static void main(String[] args) {
        DAO.connection();
        
        new ViewPrincipal().setVisible(true);
//        new ViewBuscaMatches().setVisible(true);
//        ServiceController sc = new ServiceController();
//        sc.insertGoalTime(99, 1, LocalDateTime.of(2023, 12, 12, 14, 50));

    }
}
