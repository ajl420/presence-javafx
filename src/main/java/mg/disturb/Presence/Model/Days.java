package mg.disturb.Presence.Model;

import java.util.Objects;

public record Days(int dayId, String dayLabel, String dayName) {

    @Override
    public String dayName() {
        return dayName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Days) obj;
        return this.dayId == that.dayId &&
                Objects.equals(this.dayLabel, that.dayLabel) &&
                Objects.equals(this.dayName, that.dayName);
    }



    @Override
    public String toString() {
        return "Days[" +
                "dayId=" + dayId + ", " +
                "dayLabel=" + dayLabel + ", " +
                "dayName=" + dayName + ']';
    }

}
