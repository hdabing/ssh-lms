package com.lms.action;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.lms.bean.Lbh;
import com.lms.domain.Lsbhb;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/*�����ʷ������*/
public class LsbhbAction extends ActionSupport {
	
	 
	private int mdbh;
	
	private int rowsPerPage = 10;// ÿҳ��ʾ����  
	  
   private int page = 1; // Ĭ�ϵ�ǰҳ  
 
   private int totalPage;// �ܹ�����ҳ  
 
   private int totalNum;// �ܹ������� 
   
   
	
	public int getMdbh() {
	return mdbh;
}

public void setMdbh(int mdbh) {
	this.mdbh = mdbh;
}

public int getRowsPerPage() {
	return rowsPerPage;
}

public void setRowsPerPage(int rowsPerPage) {
	this.rowsPerPage = rowsPerPage;
}

public int getPage() {
	return page;
}

public void setPage(int page) {
	this.page = page;
}

public int getTotalPage() {
	return totalPage;
}

public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
}

public int getTotalNum() {
	return totalNum;
}

public void setTotalNum(int totalNum) {
	this.totalNum = totalNum;
}

public Lbh getLbhb() {
	return lbhb;
}

public void setLbhb(Lbh lbhb) {
	this.lbhb = lbhb;
}

	private Lbh lbhb;
	
	public String execute() throws Exception{
		
		this.setMdbh((Integer)ActionContext.getContext().getSession().get("mdbh"));
		//JOptionPane.showMessageDialog(null, "mdbh: "+mdbh);
		Map request = (Map) ActionContext.getContext().get("request");
		lbhb = new Lbh();
		if(!((Integer)request.get("page")>0))
			this.setPage(1);
		
		List<Lsbhb> lsshb = (List<Lsbhb>)lbhb.showbhjl(mdbh, rowsPerPage, page);
		
		if(lsshb==null)
			return ERROR;
		
		//��ȡ������
		this.setTotalNum(lbhb.sumPage(mdbh));
		//JOptionPane.showMessageDialog(null, "totalNum: "+totalNum);
		this.setTotalPage((int)(totalNum/rowsPerPage));
		
		if( ((Integer)request.get("page")>0) && ((Integer)request.get("page")<totalPage))
			this.setPage((Integer)request.get("page"));  
		else
			this.setPage(1);
		request.put("page", page);
		request.put("lsbhb", lsshb);
	    request.put("totalPage", totalPage);
		return SUCCESS;
	}
}
