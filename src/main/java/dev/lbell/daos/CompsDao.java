package dev.lbell.daos;

import java.util.List;

import dev.lbell.models.Comps;

public interface CompsDao {
	public List<Comps> getUserComps(int userId);
	public List<Comps> getAllComps();
	public boolean addComp(Comps comp);
	public boolean changeComp(Comps comp);
	public Comps getCompById(int id);
	
}
