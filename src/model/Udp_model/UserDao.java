package model.Udp_model;


public class UserDao{

	public static User findByID() {
		User user = new User();
		user.setId((int) (Math.random()*10));
		return user;
	}

	public static User findByID(int id) {
		User user = new User();
		user.setId((int) (Math.random()*10));
		user.setIp("192.168.1.114");
		user.setPort(6363);
		return user;
	}
	
}
