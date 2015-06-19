package com.lms.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lms.dao.impl.ClxxbDAO;
import com.lms.dao.impl.FkcbDAO;
import com.lms.dao.impl.LssonghbDAO;
import com.lms.dao.impl.SjxxbDAO;
import com.lms.dao.impl.SonghbDAO;
import com.lms.dao.impl.ZfdbhbDAO;
import com.lms.dao.impl.ZlstjbDAO;
import com.lms.domain.Clxxb;
import com.lms.domain.Fkcb;
import com.lms.domain.Lsshb;
import com.lms.domain.Lssonghb;
import com.lms.domain.Sjxxb;
import com.lms.domain.Songhb;
import com.lms.domain.Zfdbhb;
import com.lms.domain.Zlstjb;

public class Solvefbh {
	
	private ZlstjbDAO zlstjbDao;
    private ZfdbhbDAO zfdbhbDao;
    private FkcbDAO fkcbDao;
    private ClxxbDAO clxxbDao;
    private SjxxbDAO sjxxbDao;
    private SonghbDAO songhbDao;
    private LssonghbDAO lssonghbDao;
    private ZongHe zonghe;
	public Solvefbh()
	{
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");		  
		 zlstjbDao = (ZlstjbDAO)ctx.getBean("ZlstjbDAO");
		 zfdbhbDao = (ZfdbhbDAO )ctx.getBean("ZfdbhbDAO");
		 fkcbDao = (FkcbDAO)ctx.getBean("FkcbDAO");
		 clxxbDao = (ClxxbDAO)ctx.getBean("ClxxbDAO");
		 sjxxbDao = (SjxxbDAO)ctx.getBean("SjxxbDAO");
		 songhbDao = (SonghbDAO)ctx.getBean("SonghbDAO");
		 lssonghbDao = (LssonghbDAO)ctx.getBean("LssonghbDAO");
		 zonghe = new ZongHe();
	}
	
	public List<Lssonghb>showSong(List<Lssonghb> song,int pageSize, int pageNum)
	{
		List<Lssonghb> reslist = new ArrayList<Lssonghb>();
    	
    	int i =  (pageNum - 1) * pageSize;
    	
    	if(i >= song.size())
    	{
    		return null;
    	}
    	
    	for(;i < pageNum * pageSize && i < song.size(); i++ )
    	{
    		reslist.add(song.get(i));
    	}
    	return reslist;	
	}
	public int pageNum(List<Lssonghb> song)
	{
		return song.size();
	}
	public List<Lssonghb> solvefbh()
	{
		//JOptionPane.showMessageDialog(null, "jjjjj");
		zongHe();
		
		List<Zlstjb> zlstjblist = zlstjbDao.findAll();
		List<Fkcb> fkcblist = fkcbDao.findByMdbh(0);
		List<Zfdbhb> zfdbhblist = zfdbhbDao.findAll();	

		List<TempxieNum> xienumlist = compareZls_kcb(zlstjblist,fkcblist);	
	
		//�޸��ͻ����һ�ε���
		if(xienumlist.size() != 0)
		{
			xiuzheng(zfdbhblist, xienumlist,fkcblist.size());
			//�ڶ��ε������ü��Ϻ���
			zongHe();
			zfdbhblist = zfdbhbDao.findAll();	
			//��ʼ�Ƚ�
						
			//List<TempxieNum> secondXienumlist = compareZls_kcb(zlstjblist,fkcblist);					
		}
		//��ʼ����˾���ͳ���
		try
		{
			return dispatchDriverAndCar(zfdbhblist);
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
	private List<TempxieNum> compareZls_kcb(List<Zlstjb> zlstjblist,List<Fkcb> fkcblist)
	{
		List<TempxieNum> xienumlist = new ArrayList<TempxieNum>();
		int i = 0;
		//�Ƚ��ܿ�������ʷͳ�Ʊ�
		for(Zlstjb zlstjb: zlstjblist)
		{
			if(zlstjb.getNum() > fkcblist.get(i).getNum())
			{
				TempxieNum temp = new TempxieNum(zlstjb.getXb(),zlstjb.getXks(),zlstjb.getNum());
				xienumlist.add(temp);
			}
		}
		return xienumlist;
	}
	//���䳵��
	private List<Lssonghb> dispatchDriverAndCar(List<Zfdbhb> zfdbhblist)
	{
		
		List<Sjxxb> sjxxb = sjxxbDao.findAll();
		List<Clxxb> clxxb = clxxbDao.findAll();
		List<Lssonghb> songhb = new ArrayList<Lssonghb>();
		
		int sum = sumXie();
		int CarCount = 0;
		int cheZaiSum = 0;
		
		boolean flag = false;
		List<String> cphlist= new ArrayList<String>();
		
		//���ſ��г���
		for(Clxxb clxxblist: clxxb)
		{
			if(clxxblist.getIsfree().endsWith("��"))
			{
				flag = true;
				if(cheZaiSum + clxxblist.getZhl() <= sum )
				{
					CarCount++;
					cheZaiSum += clxxblist.getZhl();
					cphlist.add(clxxblist.getCph());
				}
				else
				{
					if(cheZaiSum < sum && sum <= cheZaiSum + clxxblist.getZhl())
					{
						CarCount++;
						cheZaiSum += clxxblist.getZhl();
						cphlist.add(clxxblist.getCph());
					}
				}
			}

		}
		if(!flag)
		{
			return null;
		}
		
		flag = false;
		List<String> sjxm = new ArrayList<String>();
		int sjNum = 0;
		for(Sjxxb sjxxblist : sjxxb)
		{
			if(sjxxblist.getIsfree().equals("��"))
			{
				flag = true;
				sjxm.add(sjxxblist.getXm());
				sjNum++;
			}
		}
		if(!flag)
		{
			return null;
		}

		//����ͻ���
		List<Songhb> songhblist = songhbDao.findAll();		
		for(Songhb songpoint : songhblist)
		{
			songhbDao.delete(songpoint);
		}
		//��ʼ����������
		int xieNum = 0;
		int zongNUm = 0;
		
		int ci = 0;
		int si = 0;
		//lssonghbDao
		int i = 0;
		for(Zfdbhb zfdbhb: zfdbhblist)
		{
			if(zongNUm + zfdbhb.getNum()<= cheZaiSum  && si < sjxm.size() && ci < cphlist.size())
			{
				
				zongNUm += zfdbhb.getNum();
				Date date = new Date();
				Songhb tempSong = new Songhb();
				
				try
				{
					tempSong.setMdbh(zfdbhb.getMdbh());
					tempSong.setClbh(cphlist.get(ci));
					tempSong.setSjbh(sjxm.get(si));
					tempSong.setTimes(date);
					songhbDao.save(tempSong);
				}catch(Exception e)
				{
					return null;
				}
				
				Lssonghb templssonghb = new Lssonghb();
				try
				//����ʷ��¼��
				{
					templssonghb.setXb(zfdbhb.getXb());
					templssonghb.setXks(zfdbhb.getXks());
					templssonghb.setNum(zfdbhb.getNum());
					
					templssonghb.setMdbh(zfdbhb.getMdbh());
					templssonghb.setTimes(date);
					templssonghb.setSjmc(sjxm.get(si));
					templssonghb.setClpz(cphlist.get(ci));
					songhb.add(templssonghb);
					lssonghbDao.save(templssonghb);
				}catch(Exception e)
				{
					return null;
				}
				i++;
				si++;
				ci++;				
			}
			else
			{
				if(i < zfdbhblist.size())
				{
					
				}
			}
		}
			
		return songhb;
	}
	
	//�ۺϺ���
	private boolean zongHe()
	{
		try
		{
			//ʵ���ֵܷ걨��������ʷͳ�Ʊ��ҵ���߼�
			zonghe.zonghe();
			return true;
		}

		catch(Exception e)
		{
			System.out.println("�ۺ��ֵܷ�ͳ�Ƴ�������");
			return false;
		}
	}
	//ͳ���ܺ�
	private int sumXie()
	{
		zongHe();	
		List<Zlstjb> zlstjblistSecond = zlstjbDao.findAll();
		int sum = 0;
		for(Zlstjb zlstjb:zlstjblistSecond)
		{
			sum += zlstjb.getNum();
		}
		return sum;
	}
	private void xiuzheng(List<Zfdbhb> zfdbhblist,List<TempxieNum> xienumlist, int size)
	{
		for(TempxieNum xienum : xienumlist)
		{
			for(Zfdbhb zfdbhb : zfdbhblist)
			{
				if((zfdbhb.getXb() + zfdbhb.getXks()).equals(xienum.getXb() + xienum.getXks()))
				{
					zfdbhb.setNum(xienum.getNum()/size);
				}
			}
		}
	}
	private class TempxieNum
	{
		String xb;
		String xks;
		int num;
		public TempxieNum(String xb, String xks, int num)
		{
			this.xb = xb;
			this.xks = xks;
			this.num = num;
		}
		
		public String getXb() {
			return xb;
		}
		public void setXb(String xb) {
			this.xb = xb;
		}
		public String getXks() {
			return xks;
		}
		public void setXks(String xks) {
			this.xks = xks;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}


	}

}
