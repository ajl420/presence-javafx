package mg.disturb.Presence.ServiceTest;

import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Service.EventService;

import java.sql.Date;
import java.sql.Time;

public class EventServiceTest {
    public static void createEvents() throws Exception {
//        EventMSingleDay event = EventService.createForOnlyOneDay(
//                "Current 10",
//                new Time(22,41,0),
//                new Time(23,40,0),
//                new Date(123,4,11)
//        );

        EventMRepeated event = EventService.createEventRepeated(
                "Current 1",
                new Time(22,41,0),
                new Time(23,40,0),
                5
        );

        System.out.println(event);
    }

    public static void getNearestEvent(){
        EventM event = EventService.getNearestEvent();
        System.out.println(event.toString());
    }
}
