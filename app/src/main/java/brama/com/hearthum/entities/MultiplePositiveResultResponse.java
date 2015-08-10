package brama.com.hearthum.entities;

import java.util.List;

/**
 * Created by vedra on 10.08.2015..
 */
public class MultiplePositiveResultResponse {
    List<PositiveResultResponse> responseList;

    public MultiplePositiveResultResponse(){
    }

    public MultiplePositiveResultResponse(List<PositiveResultResponse> responseList){
        this.responseList = responseList;
    }

    public List<PositiveResultResponse> getResponseList(){
        return responseList;
    }

    public void setResponseList(List<PositiveResultResponse> responseList){
        this.responseList = responseList;
    }
}
