package iit.unimiskolc.FRI.business;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingInformation {

    private int pageNumber;
    private int totalPages;

    public boolean isLast() {
        return pageNumber >= totalPages;
    }
}
