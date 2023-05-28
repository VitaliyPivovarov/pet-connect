package api.petpassport.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageDto<O> {
    private List<O> content;
    private Paging paging;
    private int pageSize;
    private int pageNumber;

    public PageDto(List<O> itemsList, int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;

        paging = new Paging();

        if (itemsList.size() > pageSize - 1) {
            int after = pageNumber + 1;
            paging.setAfter(Integer.toString(after));
        }
        if (pageNumber != 0) {
            int before = pageNumber - 1;
            paging.setBefore(Integer.toString(before));
        }
        setPaging(paging);

        setContent(itemsList);
    }
}

