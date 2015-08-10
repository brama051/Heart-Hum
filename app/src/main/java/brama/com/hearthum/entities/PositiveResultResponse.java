package brama.com.hearthum.entities;

/**
 * Created by vedra on 10.08.2015..
 */
public class PositiveResultResponse {
    String AWSURL;

    public PositiveResultResponse(){
    }

    public PositiveResultResponse(String AWSURL){
        this.AWSURL = AWSURL;
    }

    public String getAWSURL() {
        return AWSURL;
    }

    public void setAWSURL(String AWSURL) {
        this.AWSURL = AWSURL;
    }

    @Override
    public String toString(){
        return "PositiveResultResponse [AWSURL=" + AWSURL + "]";
    }
}
