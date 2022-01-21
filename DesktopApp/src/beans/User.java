package beans;

public class User {
	private int id;
	private String firsname;
	private String lastname;
	private int age;
	private String sex;
	private String addr;
	
	public User(int id, String firsname, String lastname, int age, String sex, String addr) {
		super();
		this.id = id;
		this.firsname = firsname;
		this.lastname = lastname;
		this.age = age;
		this.sex = sex;
		this.addr = addr;
	}

	public User(String firsname, String lastname, int age, String sex, String addr) {
		super();
		this.firsname = firsname;
		this.lastname = lastname;
		this.age = age;
		this.sex = sex;
		this.addr = addr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirsname() {
		return firsname;
	}

	public void setFirsname(String firsname) {
		this.firsname = firsname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	
}
