package elmir.vip.individualproject.ui.home.events;

public class Event {
    private String eventitle;
    private String eventcategory;
    private Integer eventpicture;

    public Event() {
    }

    public Event(String eventitle, String eventcategory, Integer eventpicture) {
        this.eventitle = eventitle;
        this.eventcategory = eventcategory;
        this.eventpicture = eventpicture;
    }

    public String getEvenTitle() {
        return eventitle;
    }
    public void setEvenTitle(String eventitle) {
        this.eventitle = eventitle;
    }
    public String getEventCategory() {
        return eventcategory;
    }
    public void setEventCategory(String eventcategory) {
        this.eventcategory = eventcategory;
    }
    public Integer getEventPicture() {
        return eventpicture;
    }
    public void setEventPicture(Integer eventpicture) {
        this.eventpicture = eventpicture;
    }
}