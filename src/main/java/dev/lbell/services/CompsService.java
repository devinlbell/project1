package dev.lbell.services;

import java.util.ArrayList;
import java.util.List;

import dev.lbell.daos.CompsDao;
import dev.lbell.daos.CompsDaoImpl;
import dev.lbell.models.Comps;

public class CompsService {
	private CompsDao cDao = new CompsDaoImpl();
	
	public List<Comps> findUserPendingComps(int userId) {
		List<Comps> comps = cDao.getUserComps(userId);
		List<Comps> pendComps = new ArrayList<>();
		if(comps != null) {
			for(Comps c: comps) {
				if(c!=null && c.getStatus().equals("PENDING")) {
					pendComps.add(c);
				}
			}
			return pendComps;
		}
		return pendComps;
	}
	
	public List<Comps> findUserResolvedComps(int userId) {
		List<Comps> comps = cDao.getUserComps(userId);
		List<Comps> resolvedComps = new ArrayList<>();
		if(comps != null) {
			for(Comps c: comps) {
				if(c!=null && !c.getStatus().equals("PENDING")) {
					resolvedComps.add(c);
				}
			}
			return resolvedComps;
		}
		return resolvedComps;
	}
	
	public List<Comps> findResolvedComps() {
		List<Comps> comps = cDao.getAllComps();
		List<Comps> resolvedComps = new ArrayList<>();
		if(comps != null) {
			for(Comps c: comps) {
				if(c!=null && !c.getStatus().equals("PENDING")) {
					resolvedComps.add(c);
				}
			}
			return resolvedComps;
		}
		return resolvedComps;
	}
	
	public List<Comps> findPendingComps() {
		List<Comps> comps = cDao.getAllComps();
		List<Comps> pendComps = new ArrayList<>();
		if(comps != null) {
			for(Comps c: comps) {
				if(c!=null && c.getStatus().equals("PENDING")) {
					pendComps.add(c);
				}
			}
			return pendComps;
		}
		return pendComps;
	}
	
	public boolean createComp(Comps comp) {
		comp.setStatus("PENDING");
		return cDao.addComp(comp);
	}
	
	public boolean addressComp(Comps comp, String status) {
		comp.setStatus(status);
		return cDao.changeComp(comp);
	}
	
	public boolean addressComps(List<Comps> comps, String status) {
		for(Comps comp: comps) {
			comp.setStatus(status);
			if(!cDao.changeComp(comp)) {
				return false;
			}
		}
		System.out.println("changed all comps why no statuss");
		return true;
	}
	
	public Comps findCompById(int id) {
		return cDao.getCompById(id);
		
	}
	

}
