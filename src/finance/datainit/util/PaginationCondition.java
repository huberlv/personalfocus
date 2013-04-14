package finance.datainit.util;

import java.io.Serializable;

public class PaginationCondition implements Serializable {

	private static final long serialVersionUID = 3123461432952978291L;

	private int pageNum = 1;
	
	private int pageSize = 15;
	
	private int countForDelete;

	public int getCountForDelete() {
		return countForDelete;
	}

	public void setCountForDelete(int countForDelete) {
		this.countForDelete = countForDelete;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
	
}
