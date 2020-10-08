package com.book.managment.util;

import java.util.function.BiFunction;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.book.managment.config.HibernateConfig;

public class DaoUtil {

	private DaoUtil() {
	}

	public static <U, T> T defaultDbOperation(U parameter, BiFunction<Session, U, T> dbCustomOperation) {

		T result = null;
		Transaction transaction = null;
		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();
			result = dbCustomOperation.apply(session, parameter);
			transaction.commit();
		} catch (Exception e) {

			if (transaction != null) {

				transaction.rollback();
			}
			e.printStackTrace();
		}

		return result;
	}
}
