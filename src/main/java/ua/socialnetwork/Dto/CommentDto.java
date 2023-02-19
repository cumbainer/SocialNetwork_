package ua.socialnetwork.Dto;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto extends JSONObject{

    private int post_id;

    private String text;

}
