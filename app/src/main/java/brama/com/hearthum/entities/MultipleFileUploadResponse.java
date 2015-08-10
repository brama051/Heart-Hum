package brama.com.hearthum.entities;

import java.util.List;

/**
 * Created by vedra on 10.08.2015..
 */
public class MultipleFileUploadResponse {
    List<FileUploadResponse> responseList;

    public MultipleFileUploadResponse(){
    }

    public MultipleFileUploadResponse(List<FileUploadResponse> responseList){
        this.responseList = responseList;
    }

    public List<FileUploadResponse> getResponseList(){
        return responseList;
    }

    public void setResponseList(List<FileUploadResponse> responseList){
        this.responseList = responseList;
    }


}
