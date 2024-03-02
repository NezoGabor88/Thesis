package iit.unimiskolc.FRI.vo;


import lombok.Value;

import java.util.List;

@Value
public class PaperVo {
    String title;
    String journalLabel;
    String journalLocation;
    List<LinkVo> links;
}
