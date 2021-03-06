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
    private String heartPositionListened;
    private int isUploaded;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    private String extension;

    public Record() {
        this.ID = 0;
        this.fileName = "";
        this.fileDirectory = "";
        this.fullPath = "";
        this.timeRecorded = new Date();
        this.heartPositionListened = "General";
        this.isUploaded = 0;
    }

    public Record(int ID, String fileName, String fileDirectory, String fullPath, Date timeRecorded, String heartPositionListened, int isUploaded) {
        this.ID = ID;
        this.fileName = fileName;
        this.fileDirectory = fileDirectory;
        setFullPath();
        this.timeRecorded = timeRecorded;
        this.heartPositionListened = heartPositionListened;
        this.isUploaded = isUploaded;
    }

    public String getHeartPositionListened() {
        return heartPositionListened;
    }

    public void setHeartPositionListened(String heartPositionListened) {
        this.heartPositionListened = heartPositionListened;
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
        this.fullPath = fileDirectory + "/" + fileName + "_" + ID + ".m4a";
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

    public int getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(int isUploaded) {
        this.isUploaded = isUploaded;
    }
}
