package finance.datainit.vo;

import java.util.List;

public class PaginationVO<T> implements java.io.Serializable
{

    private static final long serialVersionUID = 1L;

    // �?��页数
    private int maxPage;
    // 当前页数
    private int pageNum;
    // 每页显示多少记录
    private int pageSize;
    // 总记录数
    private int total;

    private List<T> items;

    public int getMaxPage() {
        if(total % pageSize == 0) {
            maxPage = total / pageSize;
        } else {
            maxPage = total / pageSize + 1;
        }
        return maxPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

}
