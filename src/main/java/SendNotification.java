import org.quartz.Job;
import org.quartz.JobExecutionContext;
import services.Router;
import services.ServerRouter;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SendNotification implements Job {

    private Router router;

    public SendNotification() {
        this.router = new ServerRouter();
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            System.out.println("start");
            sendToTeamLead();
            sendToLektor();
            System.out.println("complete");
        }catch (Exception e) {
            System.out.println("Error connection to router service. WSDL not found");
        }
    }

    private void sendToTeamLead() throws NullPointerException{
        List<String> teamLeads = router.getAdmins();
        List<String> untrackedUser = router.getOneDaysNotTrackingUsers();
        if (teamLeads.isEmpty() && untrackedUser.isEmpty()) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        untrackedUser
                .forEach(x->stringBuilder.append(x).append(", "));
                stringBuilder.append("не трекались сегодня");

        teamLeads
                .forEach(x->router.notifyUser(x,stringBuilder.toString()));
    }

    private void sendToLektor() throws NullPointerException {
        List<String> lektors = router.getLecturers();
        List<String> untrackthreedays = router.getThreeDaysNotTrackingUsers();

        if (lektors.isEmpty() && untrackthreedays.isEmpty()) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        untrackthreedays
                .forEach(x->stringBuilder.append(x).append(", "));

                stringBuilder.append("не трекались 3 дня");

        lektors
            .forEach(x->router.notifyUser(x,stringBuilder.toString()));

    }

}
