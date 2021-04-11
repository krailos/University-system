package ua.com.foxminded.krailo.university.util;

public class Paging {

    private int pageSize;
    private int pageNumber;
    private int itemQuantity;

    public Paging(int pageSize, int pageNumber, int itemQuantity) {
	this.pageSize = pageSize;
	this.pageNumber = pageNumber;
	this.itemQuantity = itemQuantity;
    }

    public int getPageSize() {
	return pageSize;
    }

    public int getOffset() {
	return pageSize * (pageNumber - 1);
    }

    public int getPageQuantity() {
	return itemQuantity % pageSize == 0 ? itemQuantity / pageSize : itemQuantity / pageSize + 1;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + itemQuantity;
	result = prime * result + pageNumber;
	result = prime * result + pageSize;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Paging other = (Paging) obj;
	if (itemQuantity != other.itemQuantity)
	    return false;
	if (pageNumber != other.pageNumber)
	    return false;
	if (pageSize != other.pageSize)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Paging [pageSize=" + pageSize + ", pageNumber=" + pageNumber + ", itemQuantity=" + itemQuantity + "]";
    }

}
