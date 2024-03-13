package maha.gas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import maha.gas.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

	Users findByEmail(String email);
	
	Users findByEmailAndPassword(String email, String password);

}
