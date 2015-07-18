package brama.com.hearthum;

import java.util.Date;

/**
 * Created by ABM on 18.07.2015..
 */
public class Record {
    private int ID;
    private String fileName;
    private String fileDirectory;
    private String fullPath;
    private Date timeRecorded;
    private String heartPosition;

    public Record() {
        this.ID = 0;
        this.fileName = "";
        this.fileDirectory = "";
        this.fullPath = "";
        this.timeRecorded = new Date();
        this.heartPosition = "General";
    }

    public String getHeartPosition() {
        return heartPosition;
    }

    public void setHeartPosition(String heartPosition) {
        this.heartPosition = heartPosition;
    }

    public Date getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(Date timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    public String getFullPath() {
        return fullPath;
    }

    private void setFullPath() {
        this.fullPath = fileDirectory + "/" + fileName + "_" + ID;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
        setFullPath();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        setFullPath();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
        setFullPath();
    }
}
