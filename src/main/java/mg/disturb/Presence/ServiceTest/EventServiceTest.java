package mg.disturb.Presence.ServiceTest;

import mg.disturb.Presence.Model.Event;
import mg.disturb.Presence.Model.EventRepeated;
import mg.disturb.Presence.Model.EventSingleDay;
import mg.disturb.Presence.Service.EventService;

import java.sql.Date;
import java.sql.Time;

public class EventServiceTest {
    public static void createEvents() throws Exception {
        EventSingleDay event = EventService.createForOnlyOneDay(
                "Current 10",
                new Time(22,41,0),
                new Time(23,40,0),
                new Date(123,4,11)
        );
        System.out.println(event.toString());
    }

    public static void getNearestEvent(){
        Event event = EventService.getNearestEvent();
        System.out.println(event.toString());
    }
}
