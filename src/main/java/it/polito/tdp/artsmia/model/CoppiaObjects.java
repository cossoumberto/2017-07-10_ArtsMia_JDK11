package it.polito.tdp.artsmia.model;

public class CoppiaObjects {
	
	private ArtObject o1;
	private ArtObject o2;
	private Integer peso;
	
	public CoppiaObjects(ArtObject o1, ArtObject o2, Integer peso) {
		super();
		this.o1 = o1;
		this.o2 = o2;
		this.peso = peso;
	}

	public ArtObject getO1() {
		return o1;
	}

	public void setO1(ArtObject o1) {
		this.o1 = o1;
	}

	public ArtObject getO2() {
		return o2;
	}

	public void setO2(ArtObject o2) {
		this.o2 = o2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((o1 == null) ? 0 : o1.hashCode());
		result = prime * result + ((o2 == null) ? 0 : o2.hashCode());
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
		CoppiaObjects other = (CoppiaObjects) obj;
		if (o1 == null) {
			if (other.o1 != null)
				return false;
		} else if (!o1.equals(other.o1))
			return false;
		if (o2 == null) {
			if (other.o2 != null)
				return false;
		} else if (!o2.equals(other.o2))
			return false;
		return true;
	}
	
}
