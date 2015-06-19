package com.lms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lms.dao.impl.SjxxbDAO;
import com.lms.domain.Sjxxb;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SJXXAction extends ActionSupport {
	
	private int bh;
	private Sjxxb sjxx;
	private String xm;
	private String lxdh;
	private String isfree;
	
	
	

	public int getBh() {
		return bh;
	}

	public void setBh(int bh) {
		this.bh = bh;
	}

	public Sjxxb getSjxx() {
		return sjxx;
	}

	public void setSjxx(Sjxxb sjxx) {
		this.sjxx = sjxx;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getIsfree() {
		return isfree;
	}

	public void setIsfree(String isfree) {
		this.isfree = isfree;
	}

	public String execute() throws Exception{
		  ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		  //JOptionPane.showMessageDialog(null, "dddddd");
		  SjxxbDAO sjxxbDAO = (SjxxbDAO)ctx.getBean("SjxxbDAO");
		  
		  List<Sjxxb> sjxxlist = (List<Sjxxb>)sjxxbDAO.findAll();
		  //JOptionPane.showMessageDialog(null, "hhhhhhhh");
		  if(sjxxlist == null)
		  {
			  //JOptionPane.showMessageDialog(null, "�������ڶ���?");
			  return ERROR;
		  }
		  Map request = (Map) ActionContext.getContext().get("request");  
		  for(Sjxxb sjxx:sjxxlist)
		  {
		      request.put("sjxxlist", sjxxlist);
		      //���ŵ��Ŵ���session
			  return SUCCESS;
		  }
		  return ERROR;
	}
	
	/*redirectAdd*/
	public String redirectAdd() throws Exception{
		return SUCCESS;
	}
	
	/*���˾����Ϣ*/
	public String addSjxx() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		  //JOptionPane.showMessageDialog(null, "dddddd");
		  SjxxbDAO sjxxbDAO = (SjxxbDAO)ctx.getBean("SjxxbDAO");
		  
		  //���˾����Ϣ
		  sjxxbDAO.save(sjxx);
		  
		  this.execute();
		  /*Map request = (Map) ActionContext.getContext().get("request");  
		  for(Sjxxb sjxx:sjxxlist)
		  {
		      request.put("sjxxlist", sjxxlist);
		      //���ŵ��Ŵ���session
			  return SUCCESS;
		  }*/
		return SUCCESS;
	}
	
	
	/*�޸�˾����Ϣ*/
	public String updatePSJxx() throws Exception{
		this.execute();
		return SUCCESS;
	}
	/*�޸ĵ���˾����Ϣ*/
	public String updateSJxx() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		  //JOptionPane.showMessageDialog(null, "dddddd");
		SjxxbDAO sjxxbDAO = (SjxxbDAO)ctx.getBean("SjxxbDAO");
		  
		  //���˾����Ϣ
		//sjxxbDAO.findById(sjxx.getBh());
		JOptionPane.showMessageDialog(null, "sjxx.getBh():"+sjxx.getBh());
		//List<Sjxxb> sjxxlist = (List<Sjxxb>)sjxxbDAO.findById(3);
		
		List<Sjxxb> sjxxList = new ArrayList<Sjxxb>();
		
		//this.setSjxx(sjxxbDAO.findById(sjxx.getBh()));
		
		sjxxList.add(sjxxbDAO.findById(sjxx.getBh()));
		
		  Map request = (Map) ActionContext.getContext().get("request");
		  
		  request.put("sjxxlists", sjxxList);
		  
		  return SUCCESS;
	}
	
	
	/*д���޸���Ϣ*/
	public String updateOSJxx() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		  //JOptionPane.showMessageDialog(null, "dddddd");
		SjxxbDAO sjxxbDAO = (SjxxbDAO)ctx.getBean("SjxxbDAO");
		
		//����
		sjxxbDAO.attachDirty(sjxx);
		
		this.execute();
		
		return SUCCESS;
	}
	
	
	/*ɾ��˾����Ϣ*/
	public String deleteSJxx() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		  //JOptionPane.showMessageDialog(null, "dddddd");
		SjxxbDAO sjxxbDAO = (SjxxbDAO)ctx.getBean("SjxxbDAO");
		
		//ɾ��
		sjxxbDAO.delete(sjxxbDAO.findById(sjxx.getBh()));
		
		this.execute();
		
		return SUCCESS;
	}
	
	
	
}
