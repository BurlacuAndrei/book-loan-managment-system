package com.book.managment.dao;

import java.util.function.BiFunction;

import org.hibernate.Session;

import com.book.managment.dto.UserDTO;
import com.book.managment.model.User;
import com.book.managment.util.DaoUtil;

public class UserDao {

	public User saveUser(User user) {

		return DaoUtil.defaultDbOperation(user, (Session session, User u) -> {
			session.save(u);
			return u;
		});
	}

	public Boolean validate(UserDTO userDTO) {

		return DaoUtil.defaultDbOperation(userDTO, validateUser());
	}

	public User findUserById(Integer userId) {

		return DaoUtil.defaultDbOperation(userId, (Session session, Integer id) -> session.find(User.class, id));
	}

	public User findUserByUsername(String username) {

		return DaoUtil.defaultDbOperation(username, selectUserByName());
	}

	public User updateUser(User user) {

		return DaoUtil.defaultDbOperation(user, (Session session, User b) -> {
			session.update(b);
			return b;
		});
	}

	private BiFunction<Session, String, User> selectUserByName() {

		return (Session session,
				String username) -> (User) session.createQuery("FROM User u WHERE u.username = :username")
						.setParameter("username", username).uniqueResult();
	}

	private BiFunction<Session, UserDTO, Boolean> validateUser() {

		return (Session session, UserDTO u) -> {
			User user = (User) session.createQuery("FROM User U WHERE U.username = :userName")
					.setParameter("userName", u.getUsername()).uniqueResult();

			if (user != null && user.getPassword().equals(u.getPassword())) {

				return true;
			} else {

				return false;
			}
		};
	}
}
