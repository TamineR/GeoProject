package beans;

public class Produit {

	private int id;
	private String reference;
	private double prix;
	private String designation;
	
	public Produit(String reference, double prix, String designation) {
		this.reference = reference;
		this.prix = prix;
		this.designation = designation;
	}

	public Produit(int id, String reference, double prix, String designation) {
		this.id = id;
		this.reference = reference;
		this.prix = prix;
		this.designation = designation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", reference=" + reference + ", prix=" + prix + ", designation=" + designation
				+ "]";
	}
	
	
	
}
