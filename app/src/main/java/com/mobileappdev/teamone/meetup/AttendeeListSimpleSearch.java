package com.mobileappdev.teamone.meetup;

public class AttendeeListSimpleSearch {

    private Integer TagId;
    private String TagText;
    private boolean selected;

    public Integer getTagId() {
        return TagId;
    }

    public void setTagId(Integer TagId) {
        this.TagId = TagId;
    }

    public String getTagText() {
        return TagText;
    }

    public void setTagText(String tagText) {
        TagText = tagText;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}