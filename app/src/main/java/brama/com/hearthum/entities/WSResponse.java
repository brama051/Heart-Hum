package brama.com.hearthum.entities;

/**
 * Created by vedra on 10.08.2015..
 */
public class WSResponse {
    String type;
    String content;

    public WSResponse(String type, String content){
        this.type=type;
        this.content=content;

    }

    public WSResponse(){

    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "WSResponse [type=" + type + ", content=" + content + "]";
    }
}
