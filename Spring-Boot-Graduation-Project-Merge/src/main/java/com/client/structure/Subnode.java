package com.client.structure;

/**
 * (type, name) tuple
 */
public class Subnode implements Comparable<Subnode> {
	
	public String type, name;

	public Subnode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subnode(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Subnode o) {
		// TODO Auto-generated method stub
		return this.name.compareToIgnoreCase(o.name);
	}
	
	
}

