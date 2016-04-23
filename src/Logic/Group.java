package Logic;

public class Group {

    private int groupId;
    private String nameGroup;
    private String curator;
    private String speciality;

    public int getGroupId() {

        return groupId;
    }

    public void setGroupId(int groupId) {

        this.groupId = groupId;
    }

    public String getNameGroup() {

        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {

        this.nameGroup = nameGroup;
    }

    public String getCurator() {

        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {

        return nameGroup;
    }
}
